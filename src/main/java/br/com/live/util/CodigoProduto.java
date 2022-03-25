package br.com.live.util;

public class CodigoProduto {

	private String codigo;
	
	public CodigoProduto(String nivel, String grupo) {
		this.codigo = nivel + "." + grupo;
	}

	public CodigoProduto(String nivel, String grupo, String sub) {
		this.codigo = nivel + "." + grupo + "." + sub;
	}
	
	public CodigoProduto(String nivel, String grupo, String sub, String item) {
		this.codigo = nivel + "." + grupo + "." + sub + "." + item;
	}	
	
	public String getCodigo() {
		return codigo;
	}

	public String getNivel() {
		String[] codigoSeparado = codigo.split("[.]");
		String parte = codigoSeparado [0];
		return parte;
	}
	
	public String getGrupo() {
		String[] codigoSeparado = codigo.split("[.]");
		String parte = codigoSeparado [1];
		return parte;
	}
	
	public String getSub() {
		String[] codigoSeparado = codigo.split("[.]");
		String parte = codigoSeparado [2];
		return parte;
	}

	public String getItem() {
		String[] codigoSeparado = codigo.split("[.]");
		String parte = codigoSeparado [3];
		return parte;
	}
}
