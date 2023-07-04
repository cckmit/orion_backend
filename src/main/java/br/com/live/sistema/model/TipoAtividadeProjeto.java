package br.com.live.sistema.model;

public class TipoAtividadeProjeto {

    private Long id;
    private String descricao;
    private int exigeDoc;

    public TipoAtividadeProjeto(Long id, String descricao, int exigeDoc) {
        this.id = id;
        this.descricao = descricao;
        this.exigeDoc = exigeDoc;
    }

    public TipoAtividadeProjeto(){

    }

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
}
