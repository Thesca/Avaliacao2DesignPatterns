package adapter;

import adaptee.SistemaBancarioLegado;
import target.ProcessadorTransacoes;
import target.RespostaAutorizacao;
import java.util.HashMap;

/**
 * Padrão: Adapter (Adaptador)
 * * Justificativa de Design:
 * Esta é a classe central que resolve o problema.
 * 1. Implementa a interface alvo (Target): `ProcessadorTransacoes`.
 * 2. Contém (via composição) uma instância do `SistemaBancarioLegado` (Adaptee).
 * 3. Sua responsabilidade é traduzir as chamadas em AMBAS as direções.
 */
public class AdaptadorBancario implements ProcessadorTransacoes {

    private final SistemaBancarioLegado servicoLegado;

    /**
     * * Justificativa de Design (SOLID - DIP):
     * Recebemos o sistema legado via injeção de dependência.
     * Isso permite testar o adaptador com um 'mock' do sistema legado.
     */
    public AdaptadorBancario(SistemaBancarioLegado servicoLegado) {
        this.servicoLegado = servicoLegado;
    }

    @Override
    public RespostaAutorizacao autorizar(String cartao, double valor, String moeda) {
        System.out.println("[ADAPTER] Recebida chamada moderna. Traduzindo para o formato legado...");

        // 1. Tradução (Request): Moderno -> Legado
        HashMap<String, Object> parametrosLegados = converterParaLegado(cartao, valor, moeda);

        // 2. Chamar o método legado
        HashMap<String, Object> respostaLegada = servicoLegado.processarTransacao(parametrosLegados);
        System.out.println("[ADAPTER] Resposta legada recebida. Traduzindo para o formato moderno...");

        // 3. Tradução (Response): Legado -> Moderno (Cumprindo o requisito bidirecional)
        return converterParaModerno(respostaLegada);
    }

    /**
     * Método auxiliar privado para a tradução dos dados de requisição.
     * * Justificativa (SOLID - SRP): Isola a lógica de mapeamento.
     */
    private HashMap<String, Object> converterParaLegado(String cartao, double valor, String moeda) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("numero_cartao", cartao);
        params.put("valor_total", (Object) valor);

        // Requisito: Converter moedas (BRL=3, etc.)
        params.put("moeda_int", converterMoedaParaCodigo(moeda));

        // Requisito: Adicionar tratamento para campos obrigatórios do legado
        // que não existem na interface moderna.
        params.put("codigo_loja", "LOJA_VIRTUAL_001");

        return params;
    }

    /**
     * Método auxiliar privado para a conversão de moeda.
     */
    private int converterMoedaParaCodigo(String moeda) {
        switch (moeda.toUpperCase()) {
            case "USD": return 1;
            case "EUR": return 2;
            case "BRL": return 3;
            default: return 0; // Código para moeda desconhecida
        }
    }

    /**
     * Método auxiliar privado para a tradução da resposta legada.
     * Cumpre o requisito de ser bidirecional.
     */
    private RespostaAutorizacao converterParaModerno(HashMap<String, Object> respostaLegada) {
        int statusCode = (int) respostaLegada.getOrDefault("status_code", 500); // 500 = erro interno
        boolean sucesso = (statusCode == 200);
        String id = (String) respostaLegada.get("id_transacao");
        String mensagem = (String) respostaLegada.getOrDefault("mensagem", (String) respostaLegada.get("mensagem_erro"));

        return new RespostaAutorizacao(sucesso, id, mensagem);
    }
}