package br.com.live.model;

import java.util.Date;

public class ConsultaPreOrdemProducaoItem extends ConsultaPreOrdemProducao{

	public String tamanho;
	public String cor;
	public Date dataEmbarque;
	
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
	public Date getDataEmbarque() {
		return dataEmbarque;
	}
	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
}
