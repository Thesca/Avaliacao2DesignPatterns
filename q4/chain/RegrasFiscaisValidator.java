package chain;
import context.ValidationContext;

public class RegrasFiscaisValidator extends AbstractValidator {
    public RegrasFiscaisValidator() { super(800); } // 800ms timeout
    
    /**
     * Req 2: Esta é a validação condicional.
     * Só executa se os validadores anteriores (Schema e Cert) passarem.
     */
    @Override
    protected boolean canExecute(ValidationContext context) {
        return context.getFailureCount() == 0;
    }

    @Override
    protected void executeValidationLogic(ValidationContext context) {
        // Simula cálculo de impostos
        System.out.println("   Validação de Regras Fiscais (Impostos): OK");
        
        // Simulação de falha
        // if (context.getDocumento().getValorImpostos() < 10) {
        //     context.addFailure("Cálculo de imposto falhou.");
        // }
    }
}