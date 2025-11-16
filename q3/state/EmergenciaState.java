package state;

import context.UsinaNuclear;

/**
 * Padrão: Concrete State
 * Estado final. Previne transições circulares perigosas.
 */
public class EmergenciaState implements IUsinaState {
    
    private void logEstadoFinal() {
        System.out.println("[BLOQUEADO] Usina em estado de EMERGÊNCIA. Nenhuma ação permitida exceto desligamento manual.");
    }
    
    @Override
    public void verificarStatus(UsinaNuclear usina) {
        logEstadoFinal();
    }
    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        logEstadoFinal();
    }
    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        logEstadoFinal();
    }
    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        logEstadoFinal();
    }
    @Override
    public void ligar(UsinaNuclear usina) {
        logEstadoFinal();
    }
    
    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("Desligamento de emergência... Usina movida para DESLIGADA.");
        usina.setEstadoOperacional(new DesligadaState());
    }
}