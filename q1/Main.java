import context.FinancialContext;
import context.RiskAnalyzer;
import strategy.ExpectedShortfallAlgorithm;
import strategy.StressTestingAlgorithm;
import strategy.VaRAlgorithm;

/**
 * Cliente (Main)
 * * Demonstra o uso do padrão Strategy.
 * O cliente decide qual estratégia usar e quando trocá-la.
 */
public class Main {
    public static void main(String[] args) {
        
        // 1. Cria o contexto financeiro complexo que será compartilhado
        FinancialContext context = new FinancialContext(1_000_000.00, 0.15, 10, 0.95);

        // 2. Cria o analisador (Context)
        RiskAnalyzer analyzer = new RiskAnalyzer(context);

        // 3. O cliente (negócio) decide usar VaR
        analyzer.setStrategy(new VaRAlgorithm());
        analyzer.performAnalysis();

        System.out.println("\n" + "=".repeat(40) + "\n");

        // 4. O cliente (negócio) decide mudar dinamicamente para Stress Testing
        analyzer.setStrategy(new StressTestingAlgorithm());
        analyzer.performAnalysis();

        System.out.println("\n" + "=".repeat(40) + "\n");
        
        // 5. O cliente (negócio) muda novamente, agora para Expected Shortfall
        analyzer.setStrategy(new ExpectedShortfallAlgorithm());
        analyzer.performAnalysis();
    }
}