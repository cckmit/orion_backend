package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_260")
public class PoliticaVendas {
	
	@Id
	public int id;
	public int tipo;
	
	@Column(name = "forma_pagamento")
	public int formaPagamento;
	
	public int portador;
	public int cnpj9;
	public int cnpj4;
	public int cnpj2;
	
	@Column(name = "cod_funcionario")
	public int codFuncionario;
	
	@Column(name = "desc_capa")
	public float descCapa;
	
	@Column(name = "tipo_pedido")
	public int tipoPedido;
	
	@Column(name = "deposito_itens")
	public int depositoItens;
	
	@Column(name = "desc_max_cliente")
	public float descMaxCliente;
	
	public float comissao;
	
	@Column(name = "cond_pgto")
	public int condPgto;
	
	@Column(name = "natureza_operacao")
	public int naturezaOperacao;
	
	public float desconto;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public int getPortador() {
		return portador;
	}

	public void setPortador(int portador) {
		this.portador = portador;
	}

	public int getCnpj9() {
		return cnpj9;
	}

	public void setCnpj9(int cnpj9) {
		this.cnpj9 = cnpj9;
	}

	public int getCnpj4() {
		return cnpj4;
	}

	public void setCnpj4(int cnpj4) {
		this.cnpj4 = cnpj4;
	}

	public int getCnpj2() {
		return cnpj2;
	}

	public void setCnpj2(int cnpj2) {
		this.cnpj2 = cnpj2;
	}

	public int getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public float getDescCapa() {
		return descCapa;
	}

	public void setDescCapa(float descCapa) {
		this.descCapa = descCapa;
	}

	public int getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(int tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public int getDepositoItens() {
		return depositoItens;
	}

	public void setDepositoItens(int depositoItens) {
		this.depositoItens = depositoItens;
	}

	public float getDescMaxCliente() {
		return descMaxCliente;
	}

	public void setDescMaxCliente(float descMaxCliente) {
		this.descMaxCliente = descMaxCliente;
	}

	public float getComissao() {
		return comissao;
	}

	public void setComissao(float comissao) {
		this.comissao = comissao;
	}

	public int getCondPgto() {
		return condPgto;
	}

	public void setCondPgto(int condPgto) {
		this.condPgto = condPgto;
	}

	public int getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(int naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
	
	public PoliticaVendas() {
		
	}

	public PoliticaVendas(int id, int tipo, int formaPagamento, int portador, int cnpj9, int cnpj4, int cnpj2, int codFuncionario, float descCapa, int tipoPedido, int depositoItens,
			float descMaxCliente, float comissao, int condPgto, int naturezaOperacao, float desconto) {
		this.id = id;
		this.tipo = tipo;
		this.formaPagamento = formaPagamento;
		this.portador = portador;
		this.cnpj9 = cnpj9;
		this.cnpj4 = cnpj4;
		this.cnpj2 = cnpj2;
		this.codFuncionario = codFuncionario;
		this.descCapa = descCapa;
		this.tipoPedido = tipoPedido;
		this.depositoItens = depositoItens;
		this.descMaxCliente = descMaxCliente;
		this.comissao = comissao;
		this.condPgto = condPgto;
		this.naturezaOperacao = naturezaOperacao;
		this.desconto = desconto;
		
	}

}
