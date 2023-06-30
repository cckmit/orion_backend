package br.com.live.producao.model;

import java.util.Date;

public class PainelListaPrioridadesCarteiraPedidos {
	private int pedidoVenda;
	private Date dataEmbarque;
	private String referencia;
	private String descReferencia;
	private String tamanho;
	private String cor;
	private String descCor;
	private int qtdeCarteira;
	private int qtdeEstoque;
	private int qtdeEmProducao;
	private int qtdeSugerida;
	private int qtdeColetada;
	
	public PainelListaPrioridadesCarteiraPedidos() {
		super();	
	}

	public PainelListaPrioridadesCarteiraPedidos(int pedidoVenda, Date dataEmbarque, String referencia, String descReferencia, String tamanho, String cor,
			String descCor, int qtdeCarteira, int qtdeEstoque, int qtdeEmProducao,int qtdeSugerida, int qtdeColetada) {
		super();
		this.pedidoVenda = pedidoVenda;
		this.dataEmbarque = dataEmbarque;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.tamanho = tamanho;
		this.cor = cor;
		this.descCor = descCor;
		this.qtdeCarteira = qtdeCarteira;
		this.qtdeEstoque = qtdeEstoque;
		this.qtdeEmProducao = qtdeEmProducao;
		this.qtdeSugerida = qtdeSugerida;
		this.qtdeColetada = qtdeColetada;
	}

	public int getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}

	public Date getDataEmbarque() {
		return dataEmbarque;
	}

	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDescReferencia() {
		return descReferencia;
	}

	public void setDescReferencia(String descReferencia) {
		this.descReferencia = descReferencia;
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

	public String getDescCor() {
		return descCor;
	}

	public void setDescCor(String descCor) {
		this.descCor = descCor;
	}

	public int getQtdeCarteira() {
		return qtdeCarteira;
	}

	public void setQtdeCarteira(int qtdeCarteira) {
		this.qtdeCarteira = qtdeCarteira;
	}

	public int getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public int getQtdeEmProducao() {
		return qtdeEmProducao;
	}

	public void setQtdeEmProducao(int qtdeEmProducao) {
		this.qtdeEmProducao = qtdeEmProducao;
	}

	public int getQtdeSugerida() {
		return qtdeSugerida;
	}

	public void setQtdeSugerida(int qtdeSugerida) {
		this.qtdeSugerida = qtdeSugerida;
	}

	public int getQtdeColetada() {
		return qtdeColetada;
	}

	public void setQtdeColetada(int qtdeColetada) {
		this.qtdeColetada = qtdeColetada;
	}
}
