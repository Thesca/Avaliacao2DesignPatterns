package chain;

import context.ValidationContext;
import java.util.concurrent.*;

/**
 * Padrão: Template Method e Abstract Handler
 * * Justificativa de Design:
 * Esta classe abstrata gerencia a lógica complexa da cadeia
 * (Circuit Breaker, Condicionais, Timeouts e Rollback)
 * e delega a lógica de validação *específica* para as subclasses.
 */
public abstract class AbstractValidator implements IValidator {
    
    protected IValidator nextValidator;
    private final long timeoutMillis;

    public AbstractValidator(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
    
    @Override
    public void setNext(IValidator next) {
        this.nextValidator = next;
    }

    /**
     * Padrão: Template Method
     * Este é o esqueleto do algoritmo.
     */
    @Override
    public final void validate(ValidationContext context) {
        // Req 3: Verifica o Circuit Breaker
        if (!context.shouldContinue()) {
            System.out.printf("   [Skipping %s: Circuit Breaker ativo]%n", this.getClass().getSimpleName());
            return;
        }

        // Req 2: Verifica a Validação Condicional
        if (!canExecute(context)) {
            System.out.printf("   [Skipping %s: Condição não atendida]%n", this.getClass().getSimpleName());
            passToNext(context); // Pula, mas continua a cadeia
            return;
        }

        System.out.printf("-> Executando: %s...%n", this.getClass().getSimpleName());
        
        // Req 5: Executa a lógica com Timeout Individual
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                // Chama o método que a subclasse implementa
                executeValidationLogic(context);
            } catch (InterruptedException e) {
                // Interrompido (ex: pelo timeout)
                Thread.currentThread().interrupt();
            }
        });
        executor.shutdown(); // Impede novas tarefas

        try {
            // Tenta obter o resultado dentro do tempo limite
            future.get(this.timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // Req 5: Timeout
            context.addFailure(this.getClass().getSimpleName() + " falhou por TIMEOUT.");
            future.cancel(true); // Interrompe a thread em execução
        } catch (Exception e) {
            context.addFailure(this.getClass().getSimpleName() + " falhou: " + e.getMessage());
        }

        // Passa para o próximo item na cadeia
        passToNext(context);
    }
    
    /**
     * Lógica de rollback padrão (não faz nada).
     * Subclasses (como DatabaseValidator) podem sobrescrever.
     */
    @Override
    public void rollback(ValidationContext context) {
        // Por padrão, validadores não modificam estado,
        // então o rollback é vazio.
    }

    /**
     * Passa a execução para o próximo elo.
     */
    protected void passToNext(ValidationContext context) {
        if (nextValidator != null) {
            nextValidator.validate(context);
        }
    }

    /**
     * Ponto de extensão para subclasses (Template Method).
     * @param context O contexto da validação.
     */
    protected abstract void executeValidationLogic(ValidationContext context) throws InterruptedException;

    /**
     * Ponto de extensão para subclasses (Condicionais - Req 2).
     * Por padrão, todos podem executar.
     */
    protected boolean canExecute(ValidationContext context) {
        return true;
    }
}