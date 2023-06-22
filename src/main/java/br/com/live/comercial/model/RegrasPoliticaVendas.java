package br.com.live.comercial.model;

public class RegrasPoliticaVendas {
	
	private int id;
	private String formaPagamento;
	private String portador;
	private String cnpj;
	private String codfuncionario;
	private float desccapa;
	private String tipopedido;
	private String depositoitens;
	private float descmaxcliente;
	private float comissao;
	private String condPagamento;
	private String tipoCliente;
	private String naturezaOperacao;
	private float desconto;
	private String tabPreco;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public String getPortador() {
		return portador;
	}
	public void setPortador(String portador) {
		this.portador = portador;
	}	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCodfuncionario() {
		return codfuncionario;
	}
	public void setCodfuncionario(String codfuncionario) {
		this.codfuncionario = codfuncionario;
	}
	public float getDesccapa() {
		return desccapa;
	}
	public void setDesccapa(float desccapa) {
		this.desccapa = desccapa;
	}
	public String getTipopedido() {
		return tipopedido;
	}
	public void setTipopedido(String tipopedido) {
		this.tipopedido = tipopedido;
	}
	public String getDepositoitens() {
		return depositoitens;
	}
	public void setDepositoitens(String depositoitens) {
		this.depositoitens = depositoitens;
	}
	public float getDescmaxcliente() {
		return descmaxcliente;
	}
	public void setDescmaxcliente(float descmaxcliente) {
		this.descmaxcliente = descmaxcliente;
	}
	public float getComissao() {
		return comissao;
	}
	public void setComissao(float comissao) {
		this.comissao = comissao;
	}
	public String getCondPagamento() {
		return condPagamento;
	}
	public void setCondPagamento(String condPagamento) {
		this.condPagamento = condPagamento;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}
	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
	public float getDesconto() {
		return desconto;
	}
	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
	public String getTabPreco() {
		return tabPreco;
	}
	public void setTabPreco(String tabPreco) {
		this.tabPreco = tabPreco;
	}
	public RegrasPoliticaVendas() {
		
	}
	
	public RegrasPoliticaVendas(int id, String formaPagamento, String portador, String cnpj, String codfuncionario, float desccapa, String tipopedido, String depositoitens,
			float descmaxcliente, float comissao, String condPagamento, String tipoCliente, String naturezaOperacao, float desconto, String tabPreco) {
		
		this.id = id;
		this.formaPagamento = formaPagamento;
		this.portador = portador;
		this.cnpj = cnpj;
		this.codfuncionario = codfuncionario;
		this.desccapa = desccapa;
		this.tipopedido = tipopedido;
		this.depositoitens = depositoitens;
		this.descmaxcliente = descmaxcliente;
		this.comissao = comissao;
		this.condPagamento = condPagamento;
		this.tipoCliente = tipoCliente;
		this.naturezaOperacao = naturezaOperacao;
		this.desconto = desconto;
		this.tabPreco = tabPreco;
	}
	

}
