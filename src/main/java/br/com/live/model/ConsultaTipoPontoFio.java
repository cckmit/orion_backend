package br.com.live.model;

public class ConsultaTipoPontoFio {
    public String id;
    public int sequencia;
    public int idTipoPonto;
    public int tipoFio;
    public float consumoFio;
    public String descFio;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getSequencia() {
        return sequencia;
    }
    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
    public int getIdTipoPonto() {
        return idTipoPonto;
    }
    public void setIdTipoPonto(int idTipoPonto) {
        this.idTipoPonto = idTipoPonto;
    }
    public int getTipoFio() {
        return tipoFio;
    }
    public void setTipoFio(int tipoFio) {
        this.tipoFio = tipoFio;
    }
    public float getConsumoFio() {
        return consumoFio;
    }
    public void setConsumoFio(float consumoFio) {
        this.consumoFio = consumoFio;
    }
    public String getDescFio() {
        return descFio;
    }
    public void setDescFio(String descFio) {
        this.descFio = descFio;
    }

    public ConsultaTipoPontoFio() {
        
    }

    public ConsultaTipoPontoFio(String id, int sequencia, int idTipoPonto, int tipoFio, float consumoFio,
            String descFio) {
        this.id = id;
        this.sequencia = sequencia;
        this.idTipoPonto = idTipoPonto;
        this.tipoFio = tipoFio;
        this.consumoFio = consumoFio;
        this.descFio = descFio;
    }
}
