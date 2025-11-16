package state;

import context.UsinaNuclear;
import context.UsinaStatus;

/**
 * Padrão: Concrete State
 */
public class AlertaAmareloState implements IUsinaState {

    @Override
    public void verificarStatus(UsinaNuclear usina) {
        UsinaStatus status = usina.getStatus();
        
        // REGRA 2: ALERTA_AMARELO → ALERTA_VERMELHO
        if (status.getTemperatura() > 400.0) {
            // Incrementa o tempo em alerta
            status.setTempoEmAlerta(status.getTempoEmAlerta() + 1); // Simula 1 segundo por verificação
            
            if (status.getTempoEmAlerta() > 30) {
                System.out.println("[TRANSIÇÃO] Temp > 400°C por 30s. Entrando em ALERTA VERMELHO.");
                usina.setEstadoOperacional(new AlertaVermelhoState());
            }
        } 
        // Transição de retorno (Bidirecional)
        else if (status.getTemperatura() <= 300.0) {
            System.out.println("[TRANSIÇÃO] Temperatura normalizada. Retornando para Operação Normal.");
            status.setTempoEmAlerta(0); // Reseta o timer
            usina.setEstadoOperacional(new OperacaoNormalState());
        } else {
            // Permanece em amarelo, mas reseta o timer se a temp baixar de 400
            status.setTempoEmAlerta(0);
        }
    }

    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        System.out.println("[ALERTA] Falha de resfriamento em ALERTA AMARELO!");
        // REGRA DE SEGURANÇA: (EMERGENCIA só pode vir de VERMELHO)
        System.out.println("[TRANSIÇÃO] Escalando para ALERTA VERMELHO.");
        usina.setEstadoOperacional(new AlertaVermelhoState());
    }
    
    // --- Outros métodos (Manutenção, Ligar/Desligar) ---
    // (Implementação similar ao OperacaoNormalState)
    
    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        usina.setEstadoOverride(new ManutencaoState(this));
    }
    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        System.out.println("[AÇÃO INVÁLIDA] Usina não está em manutenção.");
    }
    @Override
    public void ligar(UsinaNuclear usina) { System.out.println("[INFO] Usina já está ligada."); }
    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("Desligando usina... (A partir de Alerta Amarelo)");
        usina.setEstadoOperacional(new DesligadaState());
    }
}