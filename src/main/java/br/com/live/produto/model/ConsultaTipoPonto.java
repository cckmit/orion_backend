package br.com.live.produto.model;

public class ConsultaTipoPonto {
    public int id;
    public String descricao;
    public String maquina;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getMaquina() {
        return maquina;
    }
    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public ConsultaTipoPonto() {

    }
    
    public ConsultaTipoPonto(int id, String descricao, String maquina) {
        this.id = id;
        this.descricao = descricao;
        this.maquina = maquina;
    }
}
