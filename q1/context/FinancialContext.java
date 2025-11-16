package context;

/**
 * Representa o "contexto complexo com múltiplos parâmetros financeiros".
 * Este objeto é imutável e é passado para as estratégias.
 */
public class FinancialContext {
    private final double portfolioValue;
    private final double volatility;
    private final int timeHorizonDays;
    private final double confidenceLevel;

    public FinancialContext(double portfolioValue, double volatility, int timeHorizonDays, double confidenceLevel) {
        this.portfolioValue = portfolioValue;
        this.volatility = volatility;
        this.timeHorizonDays = timeHorizonDays;
        this.confidenceLevel = confidenceLevel;
    }

    // Getters para os parâmetros
    public double getPortfolioValue() { return portfolioValue; }
    public double getVolatility() { return volatility; }
    public int getTimeHorizonDays() { return timeHorizonDays; }
    public double getConfidenceLevel() { return confidenceLevel; }
}