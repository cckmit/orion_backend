package br.com.live.model;

import java.util.Date;

public class AnaliseQualidade {
	
	public int rolo;
	public float peso;
	public float largura;
	public float gramatura;
	public int ordemTecelagem;
	public int ordemBeneficiamento;
	public String codigoTecido;
	public String descricaoTecido;
	public Date data;
	public String dataOrdem;
	public String narrativa;
	public String descricaoLote;
	public float valorLote;	
	public int cod;
	public float min;
	public float max;
	public String situacao;
	public String nuance;	
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDataOrdem() {
		return dataOrdem;
	}
	public void setDataOrdem(String dataOrdem) {
		this.dataOrdem = dataOrdem;
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
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNuance() {
		return nuance;
	}
	public void setNuance(String nuance) {
		this.nuance = nuance;
	}
	public AnaliseQualidade() {
		
	}
	public AnaliseQualidade(int rolo, float peso, float largura, float gramatura, int ordemTecelagem, int ordemBeneficiamento, String codigoTecido, 
			String descricaoTecido, Date data, String dataOrdem, String narrativa, String descricaoLote, float valorLote, int cod, float min, float max, String situacao, String nuance) {
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
		this.cod = cod;
		this.min = min;
		this.max = max;
		this.situacao = situacao;
		this.nuance = nuance;
		this.dataOrdem = dataOrdem;
	}
	
		
}
