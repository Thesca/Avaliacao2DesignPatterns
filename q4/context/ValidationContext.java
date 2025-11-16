package context;

import model.DocumentoFiscal;
import chain.IValidator;
import java.util.LinkedList;

/**
 * Objeto central que carrega o documento e o estado da validação
 * através da cadeia (Chain of Responsibility).
 */
public class ValidationContext {
    private final DocumentoFiscal documento;
    private int failureCount = 0;
    
    // Req 3: Circuit Breaker
    private static final int MAX_FAILURES = 3;
    private boolean isChainBroken = false;

    // Req 4: Rollback
    // Usamos um LinkedList como Pilha (Stack)
    private LinkedList<IValidator> rollbackStack = new LinkedList<>();

    public ValidationContext(DocumentoFiscal documento) {
        this.documento = documento;
    }

    /**
     * Registra uma falha.
     * Ativa o "Circuit Breaker" se o limite for atingido.
     */
    public void addFailure(String message) {
        System.err.println("FALHA: " + message);
        this.failureCount++;
        if (this.failureCount >= MAX_FAILURES) {
            System.err.println("[CIRCUIT BREAKER] Limite de 3 falhas atingido. Interrompendo a cadeia.");
            this.isChainBroken = true;
        }
    }

    /**
     * Adiciona um validador à pilha de rollback.
     * Chamado por validadores que modificam o estado.
     */
    public void addRollbackHandler(IValidator handler) {
        this.rollbackStack.push(handler);
    }

    /**
     * Executa o rollback de todos os handlers na pilha (em ordem LIFO).
     */
    public void performRollback() {
        System.out.println("\n--- INICIANDO ROLLBACK ---");
        // O método .pop() remove e retorna o topo da pilha (LIFO)
        while (!this.rollbackStack.isEmpty()) {
            IValidator handler = this.rollbackStack.pop();
            handler.rollback(this);
        }
        System.out.println("--- ROLLBACK FINALIZADO ---");
    }
    
    // Getters
    public int getFailureCount() { return failureCount; }
    public DocumentoFiscal getDocumento() { return documento; }
    
    // Req 2 e 3: Verifica se a cadeia pode continuar
    public boolean shouldContinue() { return !isChainBroken; }
}