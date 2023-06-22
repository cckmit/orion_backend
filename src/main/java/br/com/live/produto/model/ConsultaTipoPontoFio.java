package br.com.live.produto.model;

public class ConsultaTipoPontoFio {
    public String id;
    public int sequencia;
    public int idTipoPonto;
    public int tipoFio1;
    public int tipoFio2;
    public int tipoFio3;
    public float consumoFio;
    public String descFio1;
    public String descFio2;
    public String descFio3;
    public String descricao;
    
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

    public int getTipoFio1() {
        return tipoFio1;
    }

    public void setTipoFio1(int tipoFio1) {
        this.tipoFio1 = tipoFio1;
    }

    public int getTipoFio2() {
        return tipoFio2;
    }

    public void setTipoFio2(int tipoFio2) {
        this.tipoFio2 = tipoFio2;
    }

    public int getTipoFio3() {
        return tipoFio3;
    }

    public void setTipoFio3(int tipoFio3) {
        this.tipoFio3 = tipoFio3;
    }

    public float getConsumoFio() {
        return consumoFio;
    }

    public void setConsumoFio(float consumoFio) {
        this.consumoFio = consumoFio;
    }

    public String getDescFio1() {
        return descFio1;
    }

    public void setDescFio1(String descFio1) {
        this.descFio1 = descFio1;
    }

    public String getDescFio2() {
        return descFio2;
    }

    public void setDescFio2(String descFio2) {
        this.descFio2 = descFio2;
    }

    public String getDescFio3() {
        return descFio3;
    }

    public void setDescFio3(String descFio3) {
        this.descFio3 = descFio3;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ConsultaTipoPontoFio() {
        
    }

    public ConsultaTipoPontoFio(String id, int sequencia, int idTipoPonto, int tipoFio1, int tipoFio2, int tipoFio3,
            float consumoFio, String descFio1, String descFio2, String descFio3, String descricao) {
        this.id = id;
        this.sequencia = sequencia;
        this.idTipoPonto = idTipoPonto;
        this.tipoFio1 = tipoFio1;
        this.tipoFio2 = tipoFio2;
        this.tipoFio3 = tipoFio3;
        this.consumoFio = consumoFio;
        this.descFio1 = descFio1;
        this.descFio2 = descFio2;
        this.descFio3 = descFio3;
        this.descricao = descricao;
    }
}
