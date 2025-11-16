package context;

import strategy.RiskStrategy;

/**
 * Padrão: Context (Contexto)
 * * Justificativa de Design:
 * Esta é a classe que o cliente utiliza. Ela mantém uma referência à
 * estratégia ATUAL e os dados (FinancialContext).
 * Ela delega a execução do cálculo para o objeto de estratégia.
 * * Princípios SOLID:
 * - OCP: Pode trabalhar com qualquer nova RiskStrategy sem modificação.
 * - DIP: Depende da abstração RiskStrategy, não de implementações concretas.
 */
public class RiskAnalyzer {
    
    private RiskStrategy strategy;
    private final FinancialContext context;

    /**
     * O RiskAnalyzer é configurado com o contexto de dados que será
     * compartilhado por todas as estratégias.
     */
    public RiskAnalyzer(FinancialContext context) {
        this.context = context;
    }

    /**
     * Permite ao cliente trocar a estratégia dinamicamente em tempo de execução.
     */
    public void setStrategy(RiskStrategy strategy) {
        this.strategy = strategy;
        System.out.printf("[RiskAnalyzer] Estratégia alterada para: %s%n", 
            strategy.getClass().getSimpleName());
    }

    /**
     * Executa a análise de risco usando a estratégia atualmente configurada.
     */
    public void performAnalysis() {
        if (strategy == null) {
            System.out.println("[ERRO] Nenhuma estratégia de risco foi definida.");
            return;
        }
        // Delega a chamada para o objeto de estratégia
        strategy.calculateRisk(context);
    }
}