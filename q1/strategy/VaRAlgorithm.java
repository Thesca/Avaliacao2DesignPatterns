package strategy;

import context.FinancialContext;

/**
 * Padrão: Concrete Strategy (Estratégia Concreta)
 * * Justificativa de Design:
 * Encapsula o algoritmo específico "Value at Risk".
 * * Princípios SOLID:
 * - Responsabilidade Única (SRP): Esta classe tem apenas uma razão
 * para mudar: se a lógica de cálculo do VaR mudar.
 */
public class VaRAlgorithm implements RiskStrategy {

    @Override
    public void calculateRisk(FinancialContext context) {
        System.out.println("--- Calculando Value at Risk (VaR) ---");
        System.out.printf("   - Nível de Confiança: %.1f%%%n", context.getConfidenceLevel() * 100);
        System.out.printf("   - Valor do Portfólio: $%.2f%n", context.getPortfolioValue());
        
        // Cálculo dummy
        double var = context.getPortfolioValue() * context.getVolatility() * 1.645; // Simula Z-score para 95%
        System.out.printf("   - Risco (VaR 95%%, %d dias): $%.2f%n", context.getTimeHorizonDays(), var);
    }
}