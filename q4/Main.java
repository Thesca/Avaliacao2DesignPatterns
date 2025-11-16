import chain.*;
import context.ValidationContext;
import model.DocumentoFiscal;

public class Main {
    public static void main(String[] args) {
        
        // 1. Cria a cadeia de responsabilidade
        IValidator v1 = new XmlSchemaValidator();
        IValidator v2 = new CertificadoValidator();
        IValidator v3 = new RegrasFiscaisValidator();
        IValidator v4 = new DatabaseValidator();
        IValidator v5 = new SefazValidator();

        // 2. Encadeia os validadores
        v1.setNext(v2);
        v2.setNext(v3);
        v3.setNext(v4);
        v4.setNext(v5);

        // 3. Cria o documento e o contexto
        DocumentoFiscal doc = new DocumentoFiscal("NFe-12345", "<xml>...</xml>", 100.0);
        ValidationContext context = new ValidationContext(doc);

        System.out.println("--- INICIANDO VALIDAÇÃO (Cenário de Falha e Rollback) ---");
        
        // 4. Inicia a validação no primeiro elo da cadeia
        v1.validate(context);

        System.out.println("\n--- VALIDAÇÃO FINALIZADA ---");
        System.out.println("Total de falhas: " + context.getFailureCount());

        // 5. Verifica se o rollback é necessário
        if (context.getFailureCount() > 0) {
            // O SefazValidator (v5) falhou por timeout.
            // O DatabaseValidator (v4) executou *antes* dele.
            // Agora, o rollback do DatabaseValidator deve ser chamado.
            context.performRollback();
        }
        
        // -----------------------------------------------------------------
        
        System.out.println("\n--- INICIANDO VALIDAÇÃO (Cenário de Circuit Breaker) ---");
        
        // Simula um documento que falha em 3 validadores
        DocumentoFiscal docRuim = new DocumentoFiscal(null, null, 0);
        ValidationContext ctxRuim = new ValidationContext(docRuim);

        // Reutiliza a cadeia v1
        // Para simular, vamos trocar o Sefaz por um que não dê timeout
        // e force 3 falhas nos 3 primeiros.
        
        IValidator c1 = new XmlSchemaValidator(); // Vai falhar (XML nulo)
        IValidator c2 = new CertificadoValidator(); // Vai falhar (simulado)
        IValidator c3 = new RegrasFiscaisValidator(); // Vai falhar (simulado)
        IValidator c4 = new DatabaseValidator();
        IValidator c5 = new SefazValidator(); // Desta vez, vamos dar um timeout maior
        
        // Criando uma cadeia "ruim" para teste
        AbstractValidator c2_ruim = new CertificadoValidator() {
            protected void executeValidationLogic(ValidationContext context) {
                context.addFailure("Certificado revogado.");
            }
        };
        AbstractValidator c3_ruim = new RegrasFiscaisValidator() {
            protected void executeValidationLogic(ValidationContext context) {
                context.addFailure("Imposto negativo.");
            }
        };

        c1.setNext(c2_ruim);
        c2_ruim.setNext(c3_ruim);
        c3_ruim.setNext(c4);
        c4.setNext(c5); // Este (c5) não deve nem ser executado

        c1.validate(ctxRuim);
        
        // c1 (XML) falha -> 1 falha
        // c2 (Cert) falha -> 2 falhas
        // c3 (Regras) falha -> 3 falhas. ATIVA CIRCUIT BREAKER
        // c4 (DB) será pulado
        // c5 (SEFAZ) será pulado
    }
}