package br.com.live.model;

public class DivisaoProducao {
	public static final String CAMPOS_BASE_DADOS = "divisao_producao codigo, descricao";
	private int codigo;
	private String descricao;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
}
