package br.com.live.model;

import javax.persistence.Column;

public class AnaliseQualidade {
	
	public int rolo;
	public float peso;
	public float largura;
	public float gramatura;
	public int ordemTecelagem;
	public int ordemBeneficiamento;
	public String codigoTecido;
	public String descricaoTecido;
	public int ordemTingimento;
	public String data;
	public String narrativa;
	public float coluna1;	
	public float coluna2;	
	public float coluna3;	
	public float coluna4;	
	public float coluna5;
	
	
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
	public int getOrdemTingimento() {
		return ordemTingimento;
	}
	public void setOrdemTingimento(int ordemTingimento) {
		this.ordemTingimento = ordemTingimento;
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
	public float getColuna1() {
		return coluna1;
	}
	public void setColuna1(float coluna1) {
		this.coluna1 = coluna1;
	}
	public float getColuna2() {
		return coluna2;
	}
	public void setColuna2(float coluna2) {
		this.coluna2 = coluna2;
	}
	public float getColuna3() {
		return coluna3;
	}
	public void setColuna3(float coluna3) {
		this.coluna3 = coluna3;
	}
	public float getColuna4() {
		return coluna4;
	}
	public void setColuna4(float coluna4) {
		this.coluna4 = coluna4;
	}
	public float getColuna5() {
		return coluna5;
	}
	public void setColuna5(float coluna5) {
		this.coluna5 = coluna5;
	}
	public AnaliseQualidade() {
		
	}
	public AnaliseQualidade(int rolo, float peso, float largura, float gramatura, int ordemTecelagem,
			int ordemBeneficiamento, String codigoTecido, String descricaoTecido, int ordemTingimento, String data, String narrativa, float coluna1, float coluna2, float coluna3,
			float coluna4, float coluna5) {
		this.rolo = rolo;
		this.peso = peso;
		this.largura = largura;
		this.gramatura = gramatura;
		this.ordemTecelagem = ordemTecelagem;
		this.ordemBeneficiamento = ordemBeneficiamento;
		this.codigoTecido = codigoTecido;
		this.descricaoTecido = descricaoTecido;
		this.ordemTingimento = ordemTingimento;
		this.data = data;
		this.narrativa = narrativa;
		this.coluna1 = coluna1;
		this.coluna2 = coluna2;
		this.coluna3 = coluna3;
		this.coluna4 = coluna4;
		this.coluna5 = coluna5;
		
	}
	
		
}
