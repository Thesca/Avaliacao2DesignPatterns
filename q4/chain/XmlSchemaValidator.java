package chain;
import context.ValidationContext;

public class XmlSchemaValidator extends AbstractValidator {
    public XmlSchemaValidator() { super(500); } // 500ms timeout

    @Override
    protected void executeValidationLogic(ValidationContext context) {
        // Simula validação de XSD
        if (context.getDocumento().getXmlConteudo() == null) {
            context.addFailure("Schema XML não pode ser nulo.");
        }
        System.out.println("   Validação de Schema XML: OK");
    }
}