package br.com.live.producao.model;

import java.util.Date;

public class SugestaoReservaPorOrdemMaterial extends SugestaoReservaPorMaterial {

	private long idOrdem;
	private double qtdeNecessidadeUnit;
	private double qtdeDisponivelTecidoSubstituto;
	private double qtdeDisponivelTotal;
	
	public SugestaoReservaPorOrdemMaterial(long idOrdem, String nivel, String grupo, String sub, String item, String descricao,
			String unidade, double qtdeNecessidadeUnit, double qtdeNecessidade, double qtdeEstoque, double qtdeEmpenhada, double qtdeSugerido, int pedidoCompraAberto, Date dataEntregaPedidoCompra, double qtdeReceber) {
		super(nivel, grupo, sub, item, descricao, unidade, qtdeNecessidade, qtdeEstoque, qtdeEmpenhada, qtdeSugerido, pedidoCompraAberto, dataEntregaPedidoCompra, qtdeReceber);
		this.idOrdem = idOrdem;
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit; 
	}
	public long getIdOrdem() {
		return idOrdem;
	}
	public void setIdOrdem(long idOrdem) {
		this.idOrdem = idOrdem;
	}
	public double getQtdeNecessidadeUnit() {
		return qtdeNecessidadeUnit;
	}
	public void setQtdeNecessidadeUnit(double qtdeNecessidadeUnit) {
		this.qtdeNecessidadeUnit = qtdeNecessidadeUnit;
	}		
	public void setQtdeDisponivel(double qtdeDisponivel) {
		this.qtdeDisponivel = qtdeDisponivel;
		acertaSaldo();
	}	
	public void setQtdeSugerido(double qtdeSugerido) {
		this.qtdeSugerido = qtdeSugerido;
		acertaSaldo();
	}		
	private void acertaSaldo() {
		double qtdeDispTecido = 0;
		double qtdeDispSubstituto = 0;
		
		if (this.qtdeDisponivel > 0 ) qtdeDispTecido = this.qtdeDisponivel;   
		if (this.qtdeDisponivelTecidoSubstituto > 0 ) qtdeDispSubstituto = this.qtdeDisponivelTecidoSubstituto;
		
		this.qtdeDisponivelTotal = (qtdeDispTecido + qtdeDispSubstituto);		
		this.qtdeSaldo = this.qtdeDisponivelTotal - this.qtdeSugerido; 
	}
	public double getQtdeDisponivelTecidoSubstituto() {
		return qtdeDisponivelTecidoSubstituto;
	}
	public void setQtdeDisponivelTecidoSubstituto(double qtdeDisponivelTecidoSubstituto) {
		this.qtdeDisponivelTecidoSubstituto = qtdeDisponivelTecidoSubstituto;
		acertaSaldo();
	}
	public double getQtdeDisponivelTotal() {
		return qtdeDisponivelTotal;
	}
}