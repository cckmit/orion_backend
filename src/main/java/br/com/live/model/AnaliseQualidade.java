package br.com.live.model;


public class AnaliseQualidade {
	
	public int rolo;
	public float peso;
	public float largura;
	public float gramatura;
	public int ordemTecelagem;
	public int ordemBeneficiamento;
	public String codigoTecido;
	public String descricaoTecido;
	public String data;
	public String narrativa;
	public String descricaoLote;
	public float valorLote;	
	
	public int getRolo() {
		return rolo;
	}
	public void setRolo(int rolo) {
		this.rolo = rolo;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public float getLargura() {
		return largura;
	}
	public void setLargura(float largura) {
		this.largura = largura;
	}
	public float getGramatura() {
		return gramatura;
	}
	public void setGramatura(float gramatura) {
		this.gramatura = gramatura;
	}	
	public int getOrdemTecelagem() {
		return ordemTecelagem;
	}
	public void setOrdemTecelagem(int ordemTecelagem) {
		this.ordemTecelagem = ordemTecelagem;
	}
	public int getOrdemBeneficiamento() {
		return ordemBeneficiamento;
	}
	public void setOrdemBeneficiamento(int ordemBeneficiamento) {
		this.ordemBeneficiamento = ordemBeneficiamento;
	}
	public String getCodigoTecido() {
		return codigoTecido;
	}
	public void setCodigoTecido(String codigoTecido) {
		this.codigoTecido = codigoTecido;
	}
	public String getDescricaoTecido() {
		return descricaoTecido;
	}
	public void setDescricaoTecido(String descricaoTecido) {
		this.descricaoTecido = descricaoTecido;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNarrativa() {
		return narrativa;
	}
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
	public String getDescricaoLote() {
		return descricaoLote;
	}
	public void setDescricaoLote(String descricaoLote) {
		this.descricaoLote = descricaoLote;
	}
	public float getValorLote() {
		return valorLote;
	}
	public void setValorLote(float valorLote) {
		this.valorLote = valorLote;
	}
	public AnaliseQualidade() {
		
	}
	public AnaliseQualidade(int rolo, float peso, float largura, float gramatura, int ordemTecelagem, int ordemBeneficiamento, String codigoTecido, 
			String descricaoTecido, String data, String narrativa, String descricaoLote, float valorLote) {
		this.rolo = rolo;
		this.peso = peso;
		this.largura = largura;
		this.gramatura = gramatura;
		this.ordemTecelagem = ordemTecelagem;
		this.ordemBeneficiamento = ordemBeneficiamento;
		this.codigoTecido = codigoTecido;
		this.descricaoTecido = descricaoTecido;
		this.data = data;
		this.narrativa = narrativa;		
		this.descricaoLote = descricaoLote;
		this.valorLote = valorLote;
	}
	
		
}
