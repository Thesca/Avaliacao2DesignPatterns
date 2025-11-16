package model;

/**
 * DTO que representa a NF-e.
 * Será passado no contexto de validação.
 */
public class DocumentoFiscal {
    private String numero;
    private String xmlConteudo;
    private double valorImpostos;

    public DocumentoFiscal(String numero, String xmlConteudo, double valorImpostos) {
        this.numero = numero;
        this.xmlConteudo = xmlConteudo;
        this.valorImpostos = valorImpostos;
    }
    
    // Getters
    public String getNumero() { return numero; }
    
    // MÉTODO QUE FALTAVA
    public String getXmlConteudo() { return xmlConteudo; } 
    
    // Adicionando este também, caso seja usado em futuras regras
    public double getValorImpostos() { return valorImpostos; }
}