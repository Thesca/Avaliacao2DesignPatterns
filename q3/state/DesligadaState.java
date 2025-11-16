package state;

import context.UsinaNuclear;

/**
 * Padrão: Concrete State
 */
public class DesligadaState implements IUsinaState {
    
    @Override
    public void verificarStatus(UsinaNuclear usina) {
        // Desligada, nada a fazer
    }

    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        // Armazena o estado atual (Desligada) para saber para onde voltar
        usina.setEstadoOverride(new ManutencaoState(this));
    }

    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        System.out.println("[AÇÃO INVÁLIDA] Usina não está em manutenção.");
    }

    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        System.out.println("[INFO] Sistema de resfriamento falhou, mas a usina está desligada. Sem risco.");
    }

    @Override
    public void ligar(UsinaNuclear usina) {
        System.out.println("Ligando usina... Transicionando para Operação Normal.");
        // Transição de estado operacional
        usina.setEstadoOperacional(new OperacaoNormalState());
    }

    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("[INFO] Usina já está desligada.");
    }
}