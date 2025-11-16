import context.UsinaNuclear;

public class Main {
    public static void main(String[] args) {
        UsinaNuclear usina = new UsinaNuclear();

        System.out.println("--- Cenário 1: Fluxo de Alerta Normal ---");
        usina.ligar();
        usina.getStatus().setTemperatura(310);
        usina.verificarStatus(); // Transição: Normal -> Amarelo
        
        usina.getStatus().setTemperatura(350); // Temp alta, mas não > 400
        usina.verificarStatus(); // Permanece Amarelo, reseta o timer
        
        usina.getStatus().setTemperatura(290);
        usina.verificarStatus(); // Transição: Amarelo -> Normal (Bidirecional)

        System.out.println("\n--- Cenário 2: Escala para Emergência (Regras 2 e 3) ---");
        usina.getStatus().setTemperatura(310);
        usina.verificarStatus(); // Normal -> Amarelo
        
        // Simular 35 segundos de temp > 400
        usina.getStatus().setTemperatura(410);
        for(int i=0; i<35; i++) {
            usina.verificarStatus(); // Vai incrementar o timer em AlertaAmareloState
        }
        // Na 31ª verificação, deve transicionar para Alerta Vermelho
        
        // Agora, em Alerta Vermelho, o resfriamento falha
        usina.getStatus().setSistemaResfriamentoOK(false);
        usina.falhaResfriamento(); // Transição: Vermelho -> EMERGENCIA

        // Tentar fazer algo em Emergência
        usina.ligar();
        usina.entrarEmManutencao();

        System.out.println("\n--- Cenário 3: Modo Manutenção (Override) ---");
        UsinaNuclear usina2 = new UsinaNuclear();
        usina2.ligar(); // Estado: OperacaoNormal
        
        usina2.entrarEmManutencao(); // Override: Manutencao
        
        // A usina "esquenta", mas o override ignora
        usina2.getStatus().setTemperatura(350);
        usina2.verificarStatus(); // O ManutencaoState ignora a temp
        
        // O cliente sai da manutenção
        usina2.sairDeManutencao(); // Override: null
        
        // Na *próxima* verificação, o estado operacional (OperacaoNormal) age
        System.out.println("Verificando status após sair da manutenção...");
        usina2.verificarStatus(); // Transição: Normal -> Amarelo
    }
}