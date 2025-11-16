package strategy;

import context.FinancialContext;

/**
 * Padrão: Concrete Strategy (Estratégia Concreta)
 * * Justificativa de Design:
 * Encapsula o algoritmo "Expected Shortfall".
 * * Princípios SOLID:
 * - Responsabilidade Única (SRP): Focada apenas no cálculo de ES.
 */
public class ExpectedShortfallAlgorithm implements RiskStrategy {

    @Override
    public void calculateRisk(FinancialContext context) {
        System.out.println("--- Calculando Expected Shortfall (ES) ---");
        System.out.println("   - Analisando perdas além do VaR...");
        System.out.printf("   - Nível de Confiança: %.1f%%%n", context.getConfidenceLevel() * 100);

        // Cálculo dummy
        double es = context.getPortfolioValue() * context.getVolatility() * 2.06; // Fator dummy > VaR
        System.out.printf("   - Risco (ES 95%%): Média de perdas nos piores 5%% dos casos é $%.2f%n", es);
    }
}