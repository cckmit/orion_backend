package br.com.live.produto.model;

public class ConsultaConsumoMetragem {
    public String id;
    public int pacote;
    public float consumoFio;
    public float metragemTotal;
    public float metragemUm;
    public String maquina;
    public int tipoFio;
    public String descTipoFio;
    public String descOperacao;
    public int sequencia;
    public String observacao;
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPacote() {
        return pacote;
    }

    public void setPacote(int pacote) {
        this.pacote = pacote;
    }

    public float getConsumoFio() {
        return consumoFio;
    }

    public void setConsumoFio(float consumoFio) {
        this.consumoFio = consumoFio;
    }

    public float getMetragemTotal() {
        return metragemTotal;
    }

    public void setMetragemTotal(float metragemTotal) {
        this.metragemTotal = metragemTotal;
    }

    public float getMetragemUm() {
        return metragemUm;
    }

    public void setMetragemUm(float metragemUm) {
        this.metragemUm = metragemUm;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public int getTipoFio() {
        return tipoFio;
    }

    public void setTipoFio(int tipoFio) {
        this.tipoFio = tipoFio;
    }

    public String getDescTipoFio() {
        return descTipoFio;
    }

    public void setDescTipoFio(String descTipoFio) {
        this.descTipoFio = descTipoFio;
    }

    public String getDescOperacao() {
        return descOperacao;
    }

    public void setDescOperacao(String descOperacao) {
        this.descOperacao = descOperacao;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ConsultaConsumoMetragem() {
    }

    public ConsultaConsumoMetragem(int pacote, float consumoFio, float metragemTotal, float metragemUm, String maquina,
            int tipoFio, String descTipoFio, String descOperacao, int sequencia, String observacao) {
        this.pacote = pacote;
        this.consumoFio = consumoFio;
        this.metragemTotal = metragemTotal;
        this.metragemUm = metragemUm;
        this.maquina = maquina;
        this.tipoFio = tipoFio;
        this.descTipoFio = descTipoFio;
        this.descOperacao = descOperacao;
        this.sequencia = sequencia;
        this.observacao = observacao;
    } 
}
