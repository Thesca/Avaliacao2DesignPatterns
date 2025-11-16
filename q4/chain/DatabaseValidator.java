package chain;
import context.ValidationContext;

public class DatabaseValidator extends AbstractValidator {
    public DatabaseValidator() { super(1500); } // 1.5s timeout

    @Override
    protected void executeValidationLogic(ValidationContext context) {
        // Req 4: Este validador modifica o estado (insere no DB)
        System.out.println("   Inserindo número " + context.getDocumento().getNumero() + " no DB para verificar duplicidade...");
        System.out.println("   Verificação de duplicidade: OK");

        // Adiciona este validador à pilha de rollback.
        // Se algo falhar *depois* disso, o rollback será chamado.
        context.addRollbackHandler(this);
    }
    
    /**
     * Req 4: Implementação do Rollback
     */
    @Override
    public void rollback(ValidationContext context) {
        System.err.println("ROLLBACK [DatabaseValidator]: Removendo " + context.getDocumento().getNumero() + " do DB.");
    }
}