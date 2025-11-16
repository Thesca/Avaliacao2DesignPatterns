package context;

import state.DesligadaState;
import state.IUsinaState;
import state.ManutencaoState;

/**
 * Padrão: Context (Contexto)
 * * Justificativa de Design:
 * A classe UsinaNuclear é o Contexto. Ela mantém a referência
 * aos estados e delega todo o comportamento para eles.
 * * Abordagem de Override:
 * Mantém DOIS estados: 'estadoOperacional' (o principal) e
 * 'estadoOverride' (para Manutenção).
 * A lógica de delegação sempre prioriza o 'estadoOverride'.
 */
public class UsinaNuclear {
    
    private IUsinaState estadoOperacional;
    private IUsinaState estadoOverride; // Inicia como null
    
    private UsinaStatus statusAtual;

    public UsinaNuclear() {
        this.statusAtual = new UsinaStatus();
        // O estado operacional inicial é Desligada
        this.estadoOperacional = new DesligadaState();
        this.estadoOverride = null;
    }

    /**
     * Retorna o estado ativo no momento (override ou operacional).
     */
    private IUsinaState getEstadoAtivo() {
        if (estadoOverride != null) {
            return estadoOverride;
        }
        return estadoOperacional;
    }
    
    // --- Métodos de Delegação ---
    // O cliente chama estes métodos. A usina delega para
    // o estado ativo correto.
    
    public void verificarStatus() {
        getEstadoAtivo().verificarStatus(this);
    }
    
    public void ligar() {
        getEstadoAtivo().ligar(this);
    }

    public void desligar() {
        getEstadoAtivo().desligar(this);
    }

    public void falhaResfriamento() {
        // Eventos críticos podem ser tratados de forma especial
        // Aqui, forçamos o evento no estado operacional,
        // mesmo que esteja em manutenção.
        if (estadoOverride != null) {
            System.out.println("[AVISO] Falha de resfriamento durante MANUTENÇÃO!");
            estadoOverride.falhaResfriamento(this); // O estado de manutenção decide o que fazer
        } else {
            estadoOperacional.falhaResfriamento(this);
        }
    }

    public void entrarEmManutencao() {
        getEstadoAtivo().entrarEmManutencao(this);
    }
    
    public void sairDeManutencao() {
        getEstadoAtivo().sairDeManutencao(this);
    }

    // --- Getters e Setters para os Estados ---
    // Estes são usados pelos próprios Estados para a transição.
    
    public UsinaStatus getStatus() { return statusAtual; }
    
    public void setEstadoOperacional(IUsinaState novoEstado) {
        this.estadoOperacional = novoEstado;
        System.out.printf("[USINA] Estado operacional alterado para: %s%n", 
            novoEstado.getClass().getSimpleName());
    }

    /**
     * Ativa ou desativa o modo de override.
     * @param overrideState O estado de Manutenção, ou 'null' para desativar.
     */
    public void setEstadoOverride(IUsinaState overrideState) {
        this.estadoOverride = overrideState;
        if (overrideState != null) {
            System.out.printf("[USINA] MODO OVERRIDE ATIVADO: %s%n", 
                overrideState.getClass().getSimpleName());
        } else {
            System.out.println("[USINA] MODO OVERRIDE DESATIVADO.");
        }
    }
    
    /**
     * Retorna o estado operacional *real*, mesmo se estiver
     * em manutenção. Usado pelo ManutencaoState para saber
     * para onde voltar.
     */
    public IUsinaState getEstadoOperacional() {
        return this.estadoOperacional;
    }
}