package br.com.live.sistema.model;

public class TipoAtividadeProjetoConsulta {
    private Long id;
    private String descricao;
    private int exigeDoc;
    private double tempoEstimado;

    public TipoAtividadeProjetoConsulta(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getExigeDoc() {
        return exigeDoc;
    }

    public void setExigeDoc(int exigeDoc) {
        this.exigeDoc = exigeDoc;
    }

    public double getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(double tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }
}
