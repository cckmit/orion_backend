package br.com.live.model;

import java.util.Date;

public class ConsultaPreOrdemProducaoItem extends ConsultaPreOrdemProducao{

	public String tamanho;
	public String cor;	
	public int alternativaItem;
	
	public int getAlternativaItem() {
		return alternativaItem;
	}
	public void setAlternativa(int alternativaItem) {
		this.alternativaItem = alternativaItem;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
}
