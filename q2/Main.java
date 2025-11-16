import adaptee.SistemaBancarioLegado;
import adapter.AdaptadorBancario;
import target.ProcessadorTransacoes;
import target.RespostaAutorizacao;

/**
 * Cliente (Main)
 * * Justificativa de Design (SOLID - DIP):
 * O cliente depende *apenas* das abstrações do pacote 'target'
 * (ProcessadorTransacoes e RespostaAutorizacao).
 * Ele não tem NENHUMA dependência dos pacotes 'adaptee' ou 'adapter'.
 * A inicialização é feita aqui, mas o uso é desacoplado.
 */
public class Main {
    public static void main(String[] args) {

        // 1. O cliente só conhece a interface moderna
        ProcessadorTransacoes processador;

        // 2. No "mundo real" (invisível ao cliente), inicializamos o sistema legado
        SistemaBancarioLegado sistemaLegado = new SistemaBancarioLegado();

        // 3. Criamos o Adaptador, injetando o sistema legado
        // O cliente recebe uma implementação de 'ProcessadorTransacoes'
        processador = new AdaptadorBancario(sistemaLegado);

        System.out.println("Cliente fazendo chamada com a interface moderna...\n");
        System.out.println("=".repeat(50));

        // 4. O cliente usa o método moderno para BRL.
        RespostaAutorizacao respBRL = processador.autorizar("1234-5678-8765-4321", 500.99, "BRL");
        System.out.println(respBRL);

        System.out.println("=".repeat(50));

        // 5. O cliente usa o método moderno para USD.
        RespostaAutorizacao respUSD = processador.autorizar("9876-5432-1234-5678", 89.50, "USD");
        System.out.println(respUSD);

        System.out.println("=".repeat(50));

        // 6. O cliente usa o método moderno para uma moeda inválida.
        RespostaAutorizacao respINV = processador.autorizar("1111-2222-3333-4444", 10.00, "JPY");
        System.out.println(respINV);
    }
}