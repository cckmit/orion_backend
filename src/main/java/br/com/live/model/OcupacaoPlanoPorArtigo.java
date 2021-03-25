package br.com.live.model;

public class OcupacaoPlanoPorArtigo {

	public int estagio;
	public int artigo;
	public String descricao;
	public int capacidadePecas;
	public int qtdePecas;
	public double percOcupacaoPecas;
	public int sobraFaltaPecas;
	public double capacidadeMinutos;
	public double qtdeMinutos;
	public double percOcupacaoMinutos;
	public double sobraFaltaMinutos;

	public int getArtigo() {
		return artigo;
	}
	public void setArtigo(int artigo) {
		this.artigo = artigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getCapacidadePecas() {
		return capacidadePecas;
	}
	public void setCapacidadePecas(int capacidadePecas) {
		this.capacidadePecas = capacidadePecas;
	}
	public int getQtdePecas() {
		return qtdePecas;
	}
	public void setQtdePecas(int qtdePecas) {
		this.qtdePecas = qtdePecas;
	}
	public double getPercOcupacaoPecas() {
		return percOcupacaoPecas;
	}
	public void setPercOcupacaoPecas(double percOcupacaoPecas) {
		this.percOcupacaoPecas = percOcupacaoPecas;
	}
	public int getSobraFaltaPecas() {
		return sobraFaltaPecas;
	}
	public void setSobraFaltaPecas(int sobraFaltaPecas) {
		this.sobraFaltaPecas = sobraFaltaPecas;
	}
	public double getCapacidadeMinutos() {
		return capacidadeMinutos;
	}
	public void setCapacidadeMinutos(double capacidadeMinutos) {
		this.capacidadeMinutos = capacidadeMinutos;
	}
	public double getQtdeMinutos() {
		return qtdeMinutos;
	}
	public void setQtdeMinutos(double qtdeMinutos) {
		this.qtdeMinutos = qtdeMinutos;
	}
	public double getPercOcupacaoMinutos() {
		return percOcupacaoMinutos;
	}
	public void setPercOcupacaoMinutos(double percOcupacaoMinutos) {
		this.percOcupacaoMinutos = percOcupacaoMinutos;
	}
	public double getSobraFaltaMinutos() {
		return sobraFaltaMinutos;
	}
	public void setSobraFaltaMinutos(double sobraFaltaMinutos) {
		this.sobraFaltaMinutos = sobraFaltaMinutos;
	}		
}
