package br.com.live.model;

public class PedidoVenda {
	
	public int id;
	public String descricao;
	public float percComissao;
	public int pedidoVenda;	
	public int periodo;
	
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
	
	public PedidoVenda() {
		
	}
	
	public PedidoVenda(int id, String descricao, float percComissao, int pedidoVenda, int periodo) {
		this.id = id;
		this.descricao = descricao;
		this.percComissao = percComissao;
		this.pedidoVenda = pedidoVenda;
		this.periodo = periodo;
	}
}