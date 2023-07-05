package br.com.live.sistema.model;

public class FuncaoPessoaProjeto {

    private Long id;
    private String descricao;

    public FuncaoPessoaProjeto(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public FuncaoPessoaProjeto(){}

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
}
