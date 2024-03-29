package br.com.live.comercial.model;

public class TabelaPreco {

	public String id;
	public int colecao;
	public int mes;
	public int sequencia;
	public String descricao;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getColecao() {
		return colecao;
	}
	public void setColecao(int colecao) {
		this.colecao = colecao;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	public static int getColTabelaPreco(String idTabela) {
		String[] codigoSeparado = idTabela.split("[.]");
		int colecao = Integer.parseInt(codigoSeparado [0]);
		return colecao;
	}
	
	public static int getMesTabelaPreco(String idTabela) {
		String[] codigoSeparado = idTabela.split("[.]");
		int mes = Integer.parseInt(codigoSeparado [1]);
		return mes;
	}
	
	public static int getSeqTabelaPreco(String idTabela) {
		String[] codigoSeparado = idTabela.split("[.]");
		int seqeuncia = Integer.parseInt(codigoSeparado [2]);
		return seqeuncia;
	}
}
