package br.com.live.model;

public class DivergenciasPoliticaVendas {
	
	private int codPedido;
	private String dataEmbarque;
	private String dataEmissao;
	private String dataAuditoria;
	private String divergencia;
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
	
	public DivergenciasPoliticaVendas() {
		
	}
	
	public DivergenciasPoliticaVendas(int codPedido, String dataEmbarque, String dataEmissao, String divergencia, String dataAuditoria) {
		
		this.codPedido = codPedido;
		this.dataEmbarque = dataEmbarque;
		this.dataEmissao = dataEmissao;
		this.divergencia = divergencia;
		this.dataAuditoria = dataAuditoria;
	}
}
