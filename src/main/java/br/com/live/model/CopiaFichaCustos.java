package br.com.live.model;

public class CopiaFichaCustos {
    public String produto;
    public String descParametro;
    public int sequenciaParam;
    public float consumo;
    public String valorPercentual;
    public int anoDestino;
    public int mesDestino;

    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDescParametro() {
        return descParametro;
    }

    public void setDescParametro(String descParametro) {
        this.descParametro = descParametro;
    }

    public int getSequenciaParam() {
        return sequenciaParam;
    }

    public void setSequenciaParam(int sequenciaParam) {
        this.sequenciaParam = sequenciaParam;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public String getValorPercentual() {
        return valorPercentual;
    }

    public void setValorPercentual(String valorPercentual) {
        this.valorPercentual = valorPercentual;
    }

    public int getAnoDestino() {
        return anoDestino;
    }

    public void setAnoDestino(int anoDestino) {
        this.anoDestino = anoDestino;
    }

    public int getMesDestino() {
        return mesDestino;
    }

    public void setMesDestino(int mesDestino) {
        this.mesDestino = mesDestino;
    }

    public CopiaFichaCustos() {

    }

    public CopiaFichaCustos(String produto, String descParametro, int sequenciaParam, float consumo, String valorPercentual, int anoDestino, int mesDestino) {
        this.produto = produto;
        this.descParametro = descParametro;
        this.sequenciaParam = sequenciaParam;
        this.consumo = consumo;
        this.valorPercentual = valorPercentual;
        this.anoDestino = anoDestino;
        this.mesDestino = mesDestino;
    }
}
