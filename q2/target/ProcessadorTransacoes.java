package target;

/**
 * Padrão: Target Interface
 * * Justificativa de Design:
 * Esta é a interface moderna e limpa que o nosso sistema cliente utilizará.
 * Ela define o "contrato" que o cliente espera, escondendo toda
 * a complexidade do sistema legado.
 */
public interface ProcessadorTransacoes {
    /**
     * Autoriza uma transação usando parâmetros modernos e fortemente tipados.
     * @return um objeto de resposta moderno.
     */
    RespostaAutorizacao autorizar(String cartao, double valor, String moeda);
}