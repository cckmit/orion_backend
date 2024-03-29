package br.com.live.producao.model;

import java.util.Date;

public class SugestaoReservaPorMaterial implements Comparable<SugestaoReservaPorMaterial> {

	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String descricao;
	private String unidade;	
	private boolean substitutosLocalizados;
	private Double qtdeNecessidade;
	private Double qtdeEstoque;
	private Double qtdeEmpenhada;	
	protected Double qtdeDisponivel;
	protected Double qtdeSugerido;
	protected Double qtdeSaldo;
	private int pedidoCompraAberto;
	private Date dataEntregaPedidoCompra;
	private double qtdeReceber;

	public SugestaoReservaPorMaterial(String nivel, String grupo, String sub, String item, String descricao, String unidade, double qtdeNecessidade, double qtdeEstoque, double qtdeEmpenhada, double qtdeSugerido, int pedidoCompraAberto, Date dataEntregaPedidoCompra, double qtdeReceber) {
		this.nivel = nivel; 
		this.grupo = grupo; 
		this.sub = sub; 
		this.item = item; 
		this.descricao = descricao;
		this.unidade = unidade;
		this.qtdeNecessidade = qtdeNecessidade;
		this.qtdeEstoque = qtdeEstoque;
		this.qtdeEmpenhada = qtdeEmpenhada;		
		this.qtdeSugerido = qtdeSugerido;
		this.pedidoCompraAberto = pedidoCompraAberto;
		this.dataEntregaPedidoCompra = dataEntregaPedidoCompra;
		this.qtdeReceber = qtdeReceber;
		acertaSaldos();
	}
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Double getQtdeNecessidade() {
		return qtdeNecessidade;
	}

	public void setQtdeNecessidade(Double qtdeNecessidade) {
		this.qtdeNecessidade = qtdeNecessidade;
	}

	public Double getQtdeDisponivel() {
		return qtdeDisponivel;
	}

	public Double getQtdeSugerido() {
		return qtdeSugerido;
	}

	public void setQtdeSugerido(Double qtdeSugerido) {
		this.qtdeSugerido = qtdeSugerido;
		acertaSaldos();
	}

	public Double getQtdeSaldo() {
		return qtdeSaldo;
	}

	public Double getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(Double qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
		acertaSaldos();
	}

	public Double getQtdeEmpenhada() {
		return qtdeEmpenhada;		
	}

	public void setQtdeEmpenhada(Double qtdeEmpenhada) {
		this.qtdeEmpenhada = qtdeEmpenhada;
		acertaSaldos();
	}
	
	private void acertaSaldos() {
		this.qtdeDisponivel = this.qtdeEstoque - this.qtdeEmpenhada;
		this.qtdeSaldo = this.qtdeDisponivel - this.qtdeSugerido;		
	}
	
	public boolean isSubstitutosLocalizados() {
		return substitutosLocalizados;
	}

	public void setSubstitutosLocalizados(boolean substitutosLocalizados) {
		this.substitutosLocalizados = substitutosLocalizados;
	}
	@Override
	public int compareTo(SugestaoReservaPorMaterial outroTecido) {
		
		String codOutroTecido = outroTecido.getNivel() + "." + outroTecido.getGrupo() + "." + outroTecido.getSub() + "." + outroTecido.getItem(); 
		String codTecidoAtual = getNivel() + "." + getGrupo() + "." + getSub() + "." + getItem();
		
		return (codTecidoAtual.compareToIgnoreCase(codOutroTecido));			
	}

	public int getPedidoCompraAberto() {
		return pedidoCompraAberto;
	}

	public void setPedidoCompraAberto(int pedidoCompraAberto) {
		this.pedidoCompraAberto = pedidoCompraAberto;
	}

	public Date getDataEntregaPedidoCompra() {
		return dataEntregaPedidoCompra;
	}

	public void setDataEntregaPedidoCompra(Date dataEntregaPedidoCompra) {
		this.dataEntregaPedidoCompra = dataEntregaPedidoCompra;
	}

	public double getQtdeReceber() {
		return qtdeReceber;
	}

	public void setQtdeReceber(double qtdeReceber) {
		this.qtdeReceber = qtdeReceber;
	}
}
