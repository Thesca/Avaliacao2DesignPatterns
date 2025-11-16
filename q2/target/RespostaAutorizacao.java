package target;

/**
 * Representa uma resposta de transação moderna, limpa e fortemente tipada.
 * * Justificativa de Design:
 * Usar uma classe de resposta dedicada (em vez de um Map) é crucial
 * para a manutenibilidade e segurança de tipos (Type Safety).
 * Isso cumpre a parte "bidirecional" do requisito, dando ao cliente
 * uma resposta no formato que ele espera.
 */
public class RespostaAutorizacao {
    public final boolean sucesso;
    public final String idTransacao;
    public final String mensagem;

    public RespostaAutorizacao(boolean sucesso, String idTransacao, String mensagem) {
        this.sucesso = sucesso;
        this.idTransacao = idTransacao;
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return String.format("[RESPOSTA MODERNA] Sucesso: %b | ID: %s | Mensagem: '%s'",
                sucesso, idTransacao, mensagem);
    }
}