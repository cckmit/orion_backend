package br.com.live.model;

public class ConsultaTabelaConsumo {
    public String id;
    public int tipoPonto;
    public float comprimentoCostura;
    public String descricao;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getTipoPonto() {
        return tipoPonto;
    }
    public void setTipoPonto(int tipoPonto) {
        this.tipoPonto = tipoPonto;
    }
    public float getComprimentoCostura() {
        return comprimentoCostura;
    }
    public void setComprimentoCostura(float comprimentoCostura) {
        this.comprimentoCostura = comprimentoCostura;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ConsultaTabelaConsumo() {
        
    }

    public ConsultaTabelaConsumo(String id, int tipoPonto, float comprimentoCostura, String descricao) {
        this.id = id;
        this.tipoPonto = tipoPonto;
        this.comprimentoCostura = comprimentoCostura;
        this.descricao = descricao;
    }
}
