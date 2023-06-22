package br.com.live.produto.model;

public class Maquinas {
	public String codigo;
	public String descricao;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Maquinas() {
		
	}
	
	public Maquinas(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}
