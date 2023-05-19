package br.com.live.model;

public class DivergenciasPoliticaVendas {
	
	private int codPedido;
	private String canal;
	private String dataEmbarque;
	private String dataEmissao;
	private String dataAuditoria;
	private String divergencia;
	private String pedidoCliente;
	private String cliente;
	private String funcionario;
	private int numero;
	
	public int getCodPedido() {
		return codPedido;
	}
	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}
	public String getDataEmbarque() {
		return dataEmbarque;
	}
	public void setDataEmbarque(String dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getDivergencia() {
		return divergencia;
	}
	public void setDivergencia(String divergencia) {
		this.divergencia = divergencia;
	}	
	public String getDataAuditoria() {
		return dataAuditoria;
	}
	public void setDataAuditoria(String dataAuditoria) {
		this.dataAuditoria = dataAuditoria;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getPedidoCliente() {
		return pedidoCliente;
	}
	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public DivergenciasPoliticaVendas() {
		
	}
	
	public DivergenciasPoliticaVendas(int codPedido, String canal, String dataEmbarque, String dataEmissao, String divergencia, String dataAuditoria,
			String pedidoCliente, String cliente, String funcionario, int numero) {
		
		this.codPedido = codPedido;
		this.canal = canal;
		this.dataEmbarque = dataEmbarque;
		this.dataEmissao = dataEmissao;
		this.divergencia = divergencia;
		this.dataAuditoria = dataAuditoria;
		this.pedidoCliente = pedidoCliente;
		this.cliente = cliente;
		this.funcionario = funcionario;
		this.numero = numero;
	}
}
