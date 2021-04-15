package br.com.live.model;

public class OcupacaoPlanoMestre {

	public int estagio;
	public int artigo; 
	
	public String descEstagio;
	public String descArtigo;
	
	public int qtdeCapacidadePecas;
	public int qtdePecasPlano;
	public int qtdePecasProgramado;
	public double percOcupacaoPecas;
	public int qtdeSobraFaltaPecas;

	public double qtdeCapacidadeMinutos;
	public double qtdeMinutosPlano;
	public double qtdeMinutosProgramado;
	public double percOcupacaoMinutos;
	public double qtdeSobraFaltaMinutos;
	
	public String getDescEstagio() {
		return descEstagio;
	}
	public void setDescEstagio(String descEstagio) {
		this.descEstagio = descEstagio;
	}
	public String getDescArtigo() {
		return descArtigo;
	}
	public void setDescArtigo(String descArtigo) {
		this.descArtigo = descArtigo;
	}	
	public int getArtigo() {
		return artigo;
	}
	public void setArtigo(int artigo) {
		this.artigo = artigo;
	}
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getQtdeCapacidadePecas() {
		return qtdeCapacidadePecas;
	}
	public void setQtdeCapacidadePecas(int qtdeCapacidadePecas) {
		this.qtdeCapacidadePecas = qtdeCapacidadePecas;
	}
	public int getQtdePecasPlano() {
		return qtdePecasPlano;
	}
	public void setQtdePecasPlano(int qtdePecasPlano) {
		this.qtdePecasPlano = qtdePecasPlano;
	}
	public int getQtdePecasProgramado() {
		return qtdePecasProgramado;
	}
	public void setQtdePecasProgramado(int qtdePecasProgramado) {
		this.qtdePecasProgramado = qtdePecasProgramado;
	}
	public double getPercOcupacaoPecas() {
		return percOcupacaoPecas;
	}
	public void setPercOcupacaoPecas(double percOcupacaoPecas) {
		this.percOcupacaoPecas = percOcupacaoPecas;
	}
	public int getQtdeSobraFaltaPecas() {
		return qtdeSobraFaltaPecas;
	}
	public void setQtdeSobraFaltaPecas(int qtdeSobraFaltaPecas) {
		this.qtdeSobraFaltaPecas = qtdeSobraFaltaPecas;
	}
	public double getQtdeCapacidadeMinutos() {
		return qtdeCapacidadeMinutos;
	}
	public void setQtdeCapacidadeMinutos(double qtdeCapacidadeMinutos) {
		this.qtdeCapacidadeMinutos = qtdeCapacidadeMinutos;
	}
	public double getQtdeMinutosPlano() {
		return qtdeMinutosPlano;
	}
	public void setQtdeMinutosPlano(double qtdeMinutosPlano) {
		this.qtdeMinutosPlano = qtdeMinutosPlano;
	}
	public double getQtdeMinutosProgramado() {
		return qtdeMinutosProgramado;
	}
	public void setQtdeMinutosProgramado(double qtdeMinutosProgramado) {
		this.qtdeMinutosProgramado = qtdeMinutosProgramado;
	}
	public double getPercOcupacaoMinutos() {
		return percOcupacaoMinutos;
	}
	public void setPercOcupacaoMinutos(double percOcupacaoMinutos) {
		this.percOcupacaoMinutos = percOcupacaoMinutos;
	}
	public double getQtdeSobraFaltaMinutos() {
		return qtdeSobraFaltaMinutos;
	}
	public void setQtdeSobraFaltaMinutos(double qtdeSobraFaltaMinutos) {
		this.qtdeSobraFaltaMinutos = qtdeSobraFaltaMinutos;
	}			
}
