package chain;
import context.ValidationContext;

public class CertificadoValidator extends AbstractValidator {
    public CertificadoValidator() { super(1000); } // 1s timeout

    @Override
    protected void executeValidationLogic(ValidationContext context) {
        // Simula verificação de certificado (LCR)
        System.out.println("   Validação de Certificado Digital: OK");
    }
}