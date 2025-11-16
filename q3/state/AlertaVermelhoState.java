package state;

import context.UsinaNuclear;

/**
 * Padrão: Concrete State
 * * Justificativa de Design (Regra de Transição):
 * Este é o único estado que pode transicionar para EMERGENCIA.
 * A transição é acionada pelo evento 'falhaResfriamento()'.
 */
public class AlertaVermelhoState implements IUsinaState {
    
    @Override
    public void verificarStatus(UsinaNuclear usina) {
        // Em Alerta Vermelho, a usina não se recupera sozinha
        System.out.println("[PERIGO] Estado: ALERTA VERMELHO. Aguardando intervenção ou estabilização.");
    }
    
    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        System.out.println("[EMERGÊNCIA] Falha catastrófica do resfriamento em ALERTA VERMELHO.");
        // REGRA 3: ALERTA_VERMELHO → EMERGENCIA
        System.out.println("[TRANSIÇÃO] >>> ENTRANDO EM ESTADO DE EMERGÊNCIA <<<");
        usina.setEstadoOperacional(new EmergenciaState());
    }
    
    // Em Alerta Vermelho, a manutenção pode ser bloqueada
    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        System.out.println("[ERRO] Impossível entrar em manutenção durante ALERTA VERMELHO.");
    }

    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        System.out.println("[AÇÃO INVÁLIDA] Usina não está em manutenção.");
    }

    @Override
    public void ligar(UsinaNuclear usina) { System.out.println("[INFO] Usina já está ligada."); }

    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("Desligamento de emergência... (A partir de Alerta Vermelho)");
        usina.setEstadoOperacional(new DesligadaState());
    }
}