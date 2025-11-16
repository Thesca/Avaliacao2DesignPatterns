package strategy;

import context.FinancialContext;

/**
 * Padrão: Concrete Strategy (Estratégia Concreta)
 * * Justificativa de Design:
 * Encapsula o algoritmo "Stress Testing".
 * * Princípios SOLID:
 * - Responsabilidade Única (SRP): Focada apenas na simulação de cenários extremos.
 */
public class StressTestingAlgorithm implements RiskStrategy {

    @Override
    public void calculateRisk(FinancialContext context) {
        System.out.println("--- Executando Stress Testing ---");
        System.out.println("   - Aplicando cenário: 'Aumento Súbito de Juros (+3%)'");
        
        // Cálculo dummy
        double loss = context.getPortfolioValue() * 0.15; // Simula perda de 15%
        System.out.printf("   - Perda simulada no cenário: $%.2f%n", loss);
    }
}