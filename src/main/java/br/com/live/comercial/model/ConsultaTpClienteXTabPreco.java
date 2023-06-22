package br.com.live.comercial.model;

import java.util.Date;

public class ConsultaTpClienteXTabPreco {
	
	public long idItem;
	public String id;
	public String idCapa;
	public String tabela;
	public Date periodoInicial;
	public Date periodoFinal;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdCapa() {
		return idCapa;
	}
	public void setIdCapa(String idCapa) {
		this.idCapa = idCapa;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public Date getPeriodoInicial() {
		return periodoInicial;
	}
	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}
	public Date getPeriodoFinal() {
		return periodoFinal;
	}
	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	public long getIdItem() {
		return idItem;
	}
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}
	public ConsultaTpClienteXTabPreco() {
		
	}
	public ConsultaTpClienteXTabPreco(long idItem, String id, String idCapa, String tabela, Date periodoInicial,
			Date periodoFinal) {
		this.idItem = idItem;
		this.id = id;
		this.idCapa = idCapa;
		this.tabela = tabela;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
	}

}
