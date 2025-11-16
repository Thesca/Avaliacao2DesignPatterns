package context;

/**
 * Classe DTO (Data Transfer Object)
 * * Justificativa de Design:
 * Encapsula as "condições complexas" (temp, pressão, etc.)
 * em um único objeto. Isso é mais limpo do que passar 5-6
 * parâmetros para cada método de estado.
 */
public class UsinaStatus {
    private double temperatura;
    private double pressao;
    private double tempoEmAlerta; // Em segundos
    private boolean sistemaResfriamentoOK;

    public UsinaStatus() {
        this.temperatura = 50.0;
        this.pressao = 10.0;
        this.tempoEmAlerta = 0.0;
        this.sistemaResfriamentoOK = true;
    }

    // Getters e Setters
    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double t) { this.temperatura = t; }
    
    public double getTempoEmAlerta() { return tempoEmAlerta; }
    public void setTempoEmAlerta(double t) { this.tempoEmAlerta = t; }
    
    public boolean isSistemaResfriamentoOK() { return sistemaResfriamentoOK; }
    public void setSistemaResfriamentoOK(boolean ok) { this.sistemaResfriamentoOK = ok; }
}