package adaptee;

import java.util.HashMap;

/**
 * Padrão: Adaptee
 * * Justificativa de Design:
 * Esta classe simula o sistema legado. Ela não pode ser modificada.
 * Sua interface é incompatível, usando HashMap para entrada e saída,
 * e exigindo dados específicos (códigos de moeda, etc.).
 */
public class SistemaBancarioLegado {

    /**
     * O método legado incompatível.
     * @param parametros Um 'saco de dados' com chaves e valores.
     * @return Um 'saco de dados' com o resultado.
     */
    public HashMap<String, Object> processarTransacao(HashMap<String, Object> parametros) {
        System.out.println("[LEGADO] Recebido para processamento: " + parametros);
        HashMap<String, Object> resposta = new HashMap<>();

        // Validação de campos obrigatórios que o adapter deve fornecer
        if (!parametros.containsKey("codigo_loja")) {
            resposta.put("status_code", 400); // Bad Request
            resposta.put("mensagem_erro", "Campo 'codigo_loja' é obrigatório.");
            return resposta;
        }

        // Validação da moeda
        if (!parametros.containsKey("moeda_int") || ((Integer)parametros.get("moeda_int") == 0)) {
            resposta.put("status_code", 400);
            resposta.put("mensagem_erro", "Código de moeda inválido.");
            return resposta;
        }

        // Simulação de sucesso
        String idLegado = "tx_legado_" + (int) (Math.random() * 100000);
        resposta.put("status_code", 200); // OK
        resposta.put("id_transacao", idLegado);
        resposta.put("mensagem", "Transação Autorizada pelo Banco Legado");
        System.out.println("[LEGADO] Processamento OK. Enviando resposta.");
        return resposta;
    }
}