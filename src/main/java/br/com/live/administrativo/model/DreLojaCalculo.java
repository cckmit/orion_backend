package br.com.live.administrativo.model;

public class DreLojaCalculo {

    public double valPropriedadeMesAnoAnterior;
    public double valPropriedadeOrcadoMesAnoAtual;
    public double valPropriedadeMesAnoAtual;

    public DreLojaCalculo(double valPropriedadeMesAnoAnterior, double valPropriedadeOrcadoMesAnoAtual, double valPropriedadeMesAnoAtual) {
        this.valPropriedadeMesAnoAnterior = valPropriedadeMesAnoAnterior;
        this.valPropriedadeOrcadoMesAnoAtual = valPropriedadeOrcadoMesAnoAtual;
        this.valPropriedadeMesAnoAtual = valPropriedadeMesAnoAtual;
    }

    public DreLojaCalculo(){

    }

    public double getValPropriedadeMesAnoAnterior() {
        return valPropriedadeMesAnoAnterior;
    }

    public void setValPropriedadeMesAnoAnterior(double valPropriedadeMesAnoAnterior) {
        this.valPropriedadeMesAnoAnterior = valPropriedadeMesAnoAnterior;
    }

    public double getValPropriedadeOrcadoMesAnoAtual() {
        return valPropriedadeOrcadoMesAnoAtual;
    }

    public void setValPropriedadeOrcadoMesAnoAtual(double valPropriedadeOrcadoMesAnoAtual) {
        this.valPropriedadeOrcadoMesAnoAtual = valPropriedadeOrcadoMesAnoAtual;
    }

    public double getValPropriedadeMesAnoAtual() {
        return valPropriedadeMesAnoAtual;
    }

    public void setValPropriedadeMesAnoAtual(double valPropriedadeMesAnoAtual) {
        this.valPropriedadeMesAnoAtual = valPropriedadeMesAnoAtual;
    }
}
