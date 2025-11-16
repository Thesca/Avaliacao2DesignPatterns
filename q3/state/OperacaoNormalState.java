package state;

import context.UsinaNuclear;
import context.UsinaStatus;

/**
 * Padrão: Concrete State
 */
public class OperacaoNormalState implements IUsinaState {

    @Override
    public void verificarStatus(UsinaNuclear usina) {
        UsinaStatus status = usina.getStatus();
        
        // REGRA 1: OPERACAO_NORMAL → ALERTA_AMARELO
        if (status.getTemperatura() > 300.0) {
            System.out.println("[TRANSIÇÃO] Temperatura > 300°C. Entrando em ALERTA AMARELO.");
            usina.setEstadoOperacional(new AlertaAmareloState());
        }
    }

    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        usina.setEstadoOverride(new ManutencaoState(this));
    }

    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        System.out.println("[AÇÃO INVÁLIDA] Usina não está em manutenção.");
    }

    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        System.out.println("[ALERTA] Falha de resfriamento em Operação Normal!");
        // REGRA DE SEGURANÇA (Prevenção de Transição Perigosa):
        // A falha de resfriamento não leva direto à EMERGENCIA.
        // Ela deve primeiro escalar para ALERTA VERMELHO.
        System.out.println("[TRANSIÇÃO] Escalando para ALERTA VERMELHO.");
        usina.setEstadoOperacional(new AlertaVermelhoState());
    }

    @Override
    public void ligar(UsinaNuclear usina) {
        System.out.println("[INFO] Usina já está em operação normal.");
    }

    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("Desligando usina... Retornando ao estado Desligada.");
        usina.setEstadoOperacional(new DesligadaState());
    }
}