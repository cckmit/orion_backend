package br.com.live.comercial.model;

import java.util.Date;

public class SugestaoColeta {
	public int pedido;
	public String cliente;
	public Date embarque;
	public int qtdePedido;
	public int qtdeFaturada;
	public int qtdeSaldo;
	public float valorSaldo;
	public int qtdeColetada;
	public int qtdeColetar;
	public int prioridade;
	public Date emissao;
	
	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getEmbarque() {
		return embarque;
	}

	public void setEmbarque(Date embarque) {
		this.embarque = embarque;
	}

	public int getQtdePedido() {
		return qtdePedido;
	}

	public void setQtdePedido(int qtdePedido) {
		this.qtdePedido = qtdePedido;
	}

	public int getQtdeFaturada() {
		return qtdeFaturada;
	}

	public void setQtdeFaturada(int qtdeFaturada) {
		this.qtdeFaturada = qtdeFaturada;
	}

	public int getQtdeSaldo() {
		return qtdeSaldo;
	}

	public void setQtdeSaldo(int qtdeSaldo) {
		this.qtdeSaldo = qtdeSaldo;
	}

	public float getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(float valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public int getQtdeColetada() {
		return qtdeColetada;
	}

	public void setQtdeColetada(int qtdeColetada) {
		this.qtdeColetada = qtdeColetada;
	}

	public int getQtdeColetar() {
		return qtdeColetar;
	}

	public void setQtdeColetar(int qtdeColetar) {
		this.qtdeColetar = qtdeColetar;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public SugestaoColeta() {
		
	}

	public SugestaoColeta(int pedido, String cliente, Date embarque, int qtdePedido, int qtdeFaturada, int qtdeSaldo,
			float valorSaldo, int qtdeColetada, int qtdeColetar, int prioridade, Date emissao) {
		this.pedido = pedido;
		this.cliente = cliente;
		this.embarque = embarque;
		this.qtdePedido = qtdePedido;
		this.qtdeFaturada = qtdeFaturada;
		this.qtdeSaldo = qtdeSaldo;
		this.valorSaldo = valorSaldo;
		this.qtdeColetada = qtdeColetada;
		this.qtdeColetar = qtdeColetar;
		this.prioridade = prioridade;
		this.emissao = emissao;
	}
}
