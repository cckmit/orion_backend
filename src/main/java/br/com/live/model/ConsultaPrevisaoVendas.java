package br.com.live.model;

public class ConsultaPrevisaoVendas {

	public long id;
	public int colecao;
	public String grupo;
	public String item;
	public String descricao;		
	public String artigo; 
    public String linha;  
    public String embarque;
	public int colTabPrecoSellIn;		
	public int mesTabPrecoSellIn;		
	public int seqTabPrecoSellIn;		
	public double valorSellIn;		
	public int colTabPrecoSellOut;		
	public int mesTabPrecoSellOut;		
	public int seqTabPrecoSellOut;	
	public double valorSellOut;								
	public int qtdePrevisaoVendas;	

	public String getArtigo() {
		return artigo;
	}
	public void setArtigo(String artigo) {
		this.artigo = artigo;
	}
	public String getLinha() {
		return linha;
	}
	public void setLinha(String linha) {
		this.linha = linha;
	}
	public String getEmbarque() {
		return embarque;
	}
	public void setEmbarque(String embarque) {
		this.embarque = embarque;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public int getColecao() {
		return colecao;
	}
	public void setColecao(int colecao) {
		this.colecao = colecao;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getColTabPrecoSellIn() {
		return colTabPrecoSellIn;
	}
	public void setColTabPrecoSellIn(int colTabPrecoSellIn) {
		this.colTabPrecoSellIn = colTabPrecoSellIn;
	}
	public int getMesTabPrecoSellIn() {
		return mesTabPrecoSellIn;
	}
	public void setMesTabPrecoSellIn(int mesTabPrecoSellIn) {
		this.mesTabPrecoSellIn = mesTabPrecoSellIn;
	}
	public int getSeqTabPrecoSellIn() {
		return seqTabPrecoSellIn;
	}
	public void setSeqTabPrecoSellIn(int seqTabPrecoSellIn) {
		this.seqTabPrecoSellIn = seqTabPrecoSellIn;
	}
	public double getValorSellIn() {
		return valorSellIn;
	}
	public void setValorSellIn(double valorSellIn) {
		this.valorSellIn = valorSellIn;
	}
	public int getColTabPrecoSellOut() {
		return colTabPrecoSellOut;
	}
	public void setColTabPrecoSellOut(int colTabPrecoSellOut) {
		this.colTabPrecoSellOut = colTabPrecoSellOut;
	}
	public int getMesTabPrecoSellOut() {
		return mesTabPrecoSellOut;
	}
	public void setMesTabPrecoSellOut(int mesTabPrecoSellOut) {
		this.mesTabPrecoSellOut = mesTabPrecoSellOut;
	}
	public int getSeqTabPrecoSellOut() {
		return seqTabPrecoSellOut;
	}
	public void setSeqTabPrecoSellOut(int seqTabPrecoSellOut) {
		this.seqTabPrecoSellOut = seqTabPrecoSellOut;
	}
	public double getValorSellOut() {
		return valorSellOut;
	}
	public void setValorSellOut(double valorSellOut) {
		this.valorSellOut = valorSellOut;
	}
	public int getQtdePrevisaoVendas() {
		return qtdePrevisaoVendas;
	}
	public void setQtdePrevisaoVendas(int qtdePrevisaoVendas) {
		this.qtdePrevisaoVendas = qtdePrevisaoVendas;
	}
}
