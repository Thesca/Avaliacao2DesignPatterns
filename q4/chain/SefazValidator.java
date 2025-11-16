package chain;
import context.ValidationContext;

public class SefazValidator extends AbstractValidator {
    // Timeout curto de 1 segundo para o teste
    public SefazValidator() { super(1000); } 

    /**
     * Req 2: Validação condicional.
     */
    @Override
    protected boolean canExecute(ValidationContext context) {
        return context.getFailureCount() == 0;
    }

    @Override
    protected void executeValidationLogic(ValidationContext context) throws InterruptedException {
        // Req 5: Simula uma consulta online demorada
        System.out.println("   Conectando à SEFAZ (WebService)...");
        
        // Simula uma operação que *vai* estourar o timeout de 1s
        Thread.sleep(2000); 
        
        System.out.println("   Consulta SEFAZ: OK");
    }
}