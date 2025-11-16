package state;

import context.UsinaNuclear;

/**
 * Padrão: Concrete State (para a função de Override)
 * * Justificativa de Design:
 * Esta classe implementa o requisito de "override".
 * Ela armazena o estado operacional anterior para saber
 * para onde voltar quando a manutenção terminar.
 * Enquanto ativo, ele "intercepta" as chamadas da Usina.
 */
public class ManutencaoState implements IUsinaState {

    // Armazena o estado para onde deve voltar
    private final IUsinaState estadoAnterior; 

    public ManutencaoState(IUsinaState estadoOperacionalAnterior) {
        this.estadoAnterior = estadoOperacionalAnterior;
    }
    
    @Override
    public void verificarStatus(UsinaNuclear usina) {
        System.out.printf("[MANUTENÇÃO] Ignorando verificações. Estado operacional real: %s%n",
            this.estadoAnterior.getClass().getSimpleName());
        // Não faz nada com a temperatura, etc.
    }

    @Override
    public void entrarEmManutencao(UsinaNuclear usina) {
        System.out.println("[INFO] Usina já está em manutenção.");
    }

    @Override
    public void sairDeManutencao(UsinaNuclear usina) {
        System.out.println("Saindo do modo de manutenção...");
        // Remove o "override"
        usina.setEstadoOverride(null);
        System.out.printf("[USINA] Retornando ao estado operacional: %s%n",
            usina.getEstadoOperacional().getClass().getSimpleName());
    }

    @Override
    public void falhaResfriamento(UsinaNuclear usina) {
        System.out.println("[PERIGO] Falha de resfriamento durante manutenção!");
        System.out.println("[AÇÃO] Saindo da manutenção e escalando para ALERTA VERMELHO.");
        usina.setEstadoOverride(null); // Aborta manutenção
        usina.setEstadoOperacional(new AlertaVermelhoState());
    }

    @Override
    public void ligar(UsinaNuclear usina) {
        System.out.println("[MANUTENÇÃO] Use o painel de manutenção para ligar/desligar componentes.");
    }

    @Override
    public void desligar(UsinaNuclear usina) {
        System.out.println("[MANUTENÇÃO] Use o painel de manutenção para ligar/desligar componentes.");
    }
}