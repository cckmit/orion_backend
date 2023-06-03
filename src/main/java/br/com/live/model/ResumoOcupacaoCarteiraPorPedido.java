package br.com.live.model;

public class ResumoOcupacaoCarteiraPorPedido extends ResumoOcupacaoCarteiraPorCanalVenda{
	private int pedidoVenda;
	
	
	public ResumoOcupacaoCarteiraPorPedido() {
		super();
		this.pedidoVenda = 0;
	}
	
	public ResumoOcupacaoCarteiraPorPedido(String canal, int pedidoVenda, double valorOrcado, double valorReal,
			double valorConfirmar) {
		super(canal, valorOrcado, valorReal, valorConfirmar);		
		this.pedidoVenda = pedidoVenda;
	}

	public int getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}
}
