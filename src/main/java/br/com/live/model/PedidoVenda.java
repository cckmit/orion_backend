package br.com.live.model;

import java.util.Date;

import br.com.live.util.FormataCNPJ;

public class PedidoVenda {
	
	public int id;
	public String descricao;
	public float percComissao;
	public int pedidoVenda;	
	public int periodo;
	public double valorTotal;
	public String cnpjCpfCliente;
	public String descCliente; 
	public Date dataEmissao;
	public Date dataEmbarque;
	public String canal;
	
	public int getPedidoVenda() {
		return pedidoVenda;
	}
	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}
	public int getPeriodo() {
		return periodo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getPercComissao() {
		return percComissao;
	}
	public void setPercComissao(float percComissao) {
		this.percComissao = percComissao;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}	
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getCnpjCpfCliente() {
		return cnpjCpfCliente;
	}
	public void setCnpjCpfCliente(String cnpjCpfCliente) {
		this.cnpjCpfCliente = FormataCNPJ.formatar(cnpjCpfCliente);;
	}
	public String getDescCliente() {
		return descCliente;
	}
	public void setDescCliente(String descCliente) {
		this.descCliente = descCliente;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Date getDataEmbarque() {
		return dataEmbarque;
	}
	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}	
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public PedidoVenda() {
		
	}	
	public PedidoVenda(int id, String descricao, float percComissao, int pedidoVenda, int periodo) {
		this.id = id;
		this.descricao = descricao;
		this.percComissao = percComissao;
		this.pedidoVenda = pedidoVenda;
		this.periodo = periodo;
		this.valorTotal = 0;
		this.cnpjCpfCliente = "";
		this.descCliente = ""; 
		this.dataEmissao = null;
		this.dataEmbarque = null;
		this.canal = "";
	}
	public PedidoVenda(int id, String descricao, float percComissao, int pedidoVenda, int periodo, double valor, String cnpjCpfCliente, String descCliente, Date dataEmissão, Date dataEmbarque, String canal) {
		this.id = id;
		this.descricao = descricao;
		this.percComissao = percComissao;
		this.pedidoVenda = pedidoVenda;
		this.periodo = periodo;
		this.valorTotal = valor;
		this.cnpjCpfCliente = FormataCNPJ.formatar(cnpjCpfCliente);
		this.descCliente = descCliente; 
		this.dataEmissao = dataEmissão;
		this.dataEmbarque = dataEmbarque;
		this.canal = canal;
	}	
}