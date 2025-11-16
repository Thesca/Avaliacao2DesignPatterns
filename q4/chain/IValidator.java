package chain;

import context.ValidationContext;

/**
 * Padrão: Handler (Interface)
 * * Justificativa de Design:
 * Define o contrato para todos os elos da cadeia.
 * Inclui 'validate' (executar) e 'rollback' (desfazer).
 */
public interface IValidator {
    /**
     * Define o próximo validador na cadeia.
     */
    void setNext(IValidator next);

    /**
     * Executa a validação.
     */
    void validate(ValidationContext context);

    /**
     * Executa o rollback. A implementação padrão (em
     * AbstractValidator) não fará nada.
     */
    void rollback(ValidationContext context);
}