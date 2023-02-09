package br.com.live.model;

public class ConsultaPrevisaoVendasItem {

	public String id;
	public String grupo;
	public String item;
	public String descricao;		
	public String artigo; 
    public String linha;  
    public String embarque;
	public String valorSellIn;		
	public String valorSellOut;									
	public String grupoBase;
	public String itemBase;
	public String descricaoBase;
	public int qtdeVendidaBase;	           
	public int percAplicar;	
	public int qtdePrevisao;
	public String subGrupo;
	
	public String getDescricaoBase() {
		return descricaoBase;
	}

	public void setDescricaoBase(String descricaoBase) {
		this.descricaoBase = descricaoBase;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getValorSellIn() {
		return valorSellIn;
	}

	public void setValorSellIn(String valorSellIn) {
		this.valorSellIn = formatStringValores(valorSellIn);
	}

	public String getValorSellOut() {
		return valorSellOut;
	}

	public void setValorSellOut(String valorSellOut) {
		this.valorSellOut = formatStringValores(valorSellOut);
	}

	public String getGrupoBase() {
		return grupoBase;
	}

	public void setGrupoBase(String grupoBase) {
		this.grupoBase = grupoBase;
	}

	public String getItemBase() {
		return itemBase;
	}

	public void setItemBase(String itemBase) {
		this.itemBase = itemBase;
	}

	public double getQtdeVendidaBase() {
		return qtdeVendidaBase;
	}

	public void setQtdeVendidaBase(int qtdeVendidaBase) {
		this.qtdeVendidaBase = qtdeVendidaBase;
	}

	public int getPercAplicar() {
		return percAplicar;
	}

	public void setPercAplicar(int percAplicar) {
		this.percAplicar = percAplicar;
	}

	public int getQtdePrevisao() {
		return qtdePrevisao;
	}

	public void setQtdePrevisao(int qtdePrevisao) {
		this.qtdePrevisao = qtdePrevisao;
	}

	public String getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}

	private String formatStringValores(String valor) {
		String[] separadorDecimal = valor.split("[.]");		
		String valorInteiro = separadorDecimal[0];
		if (valorInteiro.equalsIgnoreCase("") || valorInteiro == null) valor = "0" + valor;
		return valor;
	}
}
