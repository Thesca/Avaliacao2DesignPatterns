package state;

import context.UsinaNuclear;
import context.UsinaStatus;

/**
 * Padrão: State (Interface)
 * * Justificativa de Design:
 * Define o contrato para todos os estados. Força todos os estados
 * (operacionais ou de override) a implementar os mesmos métodos de
 * manipulação de eventos.
 * * Princípios SOLID (LSP):
 * Qualquer classe que implemente esta interface pode ser
 * substituída por outra, garantindo o Princípio da Substituição de Liskov.
 */
public interface IUsinaState {
    
    /**
     * Método principal de "tick" ou verificação.
     * Os estados usam isso para verificar as condições e
     * solicitar transições.
     */
    void verificarStatus(UsinaNuclear usina);

    /**
     * Tenta entrar no modo de manutenção.
     */
    void entrarEmManutencao(UsinaNuclear usina);

    /**
     * Tenta sair do modo de manutenção.
     */
    void sairDeManutencao(UsinaNuclear usina);

    /**
     * Evento externo: o sistema de resfriamento falhou.
     */
    void falhaResfriamento(UsinaNuclear usina);
    
    /**
     * Evento externo: a usina foi ligada.
     */
    void ligar(UsinaNuclear usina);

    /**
     * Evento externo: a usina foi desligada.
     */
    void desligar(UsinaNuclear usina);
}