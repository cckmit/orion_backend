package br.com.live.model;

public class Produto {

	public String nivel;
	public String grupo;
	public String sub;
	public String item;
	public String narrativa;	
	public String unidade;
	public int qtdePrevisaoVendas;
	public int seqTamanho;
	public String id;
	public String referencia;
	public int quantCesto;
	
	public int getSeqTamanho() {
		return seqTamanho;
	}
	public void setSeqTamanho(int seqTamanho) {
		this.seqTamanho = seqTamanho;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getNarrativa() {
		return narrativa;
	}
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}	
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public int getQtdePrevisaoVendas() {
		return qtdePrevisaoVendas;
	}
	public void setQtdePrevisaoVendas(int qtdePrevisaoVendas) {
		this.qtdePrevisaoVendas = qtdePrevisaoVendas;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public int getQuantCesto() {
		return quantCesto;
	}
	public void setQuantCesto(int quantCesto) {
		this.quantCesto = quantCesto;
	}
}
