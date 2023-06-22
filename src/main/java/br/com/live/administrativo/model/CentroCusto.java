package br.com.live.administrativo.model;

public class CentroCusto {
	
	public static final String CAMPOS_BASE_DADOS = "centro_custo codigo, descricao, local_entrega empresa";
	private int codigo;
	private String descricao;
	private int empresa;
	
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
	public int getEmpresa() {
		return empresa;
	}
	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
}
