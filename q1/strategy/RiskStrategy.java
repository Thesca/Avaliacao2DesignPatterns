package strategy;

import context.FinancialContext;

/**
 * Padrão: Strategy (Interface)
 * * Justificativa de Design:
 * Esta interface é a abstração central do padrão.
 * Ela define o "contrato" que todos os algoritmos de risco devem seguir.
 * * Princípios SOLID:
 * - Inversão de Dependência (DIP): O RiskAnalyzer dependerá desta interface,
 * não das implementações concretas.
 * - Princípio Aberto/Fechado (OCP): Permite que novas estratégias sejam
 * adicionadas sem modificar o código do cliente.
 */
public interface RiskStrategy {
    
    /**
     * Executa o cálculo de risco.
     * @param context O objeto de contexto contendo todos os dados financeiros
     * necessários para o cálculo.
     */
    void calculateRisk(FinancialContext context);
}