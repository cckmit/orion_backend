package br.com.live.comercial.model;

public class ConsultaEstacaoTabelaPreco {
	
	public String id;
	public int codEstacao;
	public int colTab;
	public int mesTab;
	public int seqTab;
	public String descTabela;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCodEstacao() {
		return codEstacao;
	}
	public void setCodEstacao(int codEstacao) {
		this.codEstacao = codEstacao;
	}
	public int getcolTab() {
		return colTab;
	}
	public void setcolTab(int colTab) {
		this.colTab = colTab;
	}
	public int getMesTab() {
		return mesTab;
	}
	public void setMesTab(int mesTab) {
		this.mesTab = mesTab;
	}
	public int getSeqTab() {
		return seqTab;
	}
	public void setSeqTab(int seqTab) {
		this.seqTab = seqTab;
	}
	public String getDescTabela() {
		return descTabela;
	}
	public void setDescTabela(String descTabela) {
		this.descTabela = descTabela;
	}
	
	public ConsultaEstacaoTabelaPreco() {
		
	}
	
	public ConsultaEstacaoTabelaPreco(String id, int codEstacao, int colTab, int mesTab, int seqTab,
			String descTabela) {
		this.id = id;
		this.codEstacao = codEstacao;
		this.colTab = colTab;
		this.mesTab = mesTab;
		this.seqTab = seqTab;
		this.descTabela = descTabela;
	}
}
