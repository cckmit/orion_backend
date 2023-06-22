package br.com.live.comercial.model;

import java.util.Date;

public class ConsultaNotaFiscal {

	private int codEmpresa; 
    private int notaFiscal; 
	private String serieFiscal;
	private Date dataEmissao; 
	private int cnpj9; 
	private int cnpj4; 
	private int cnpj2;

	public int getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(int codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public int getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(int notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	public String getSerieFiscal() {
		return serieFiscal;
	}
	public void setSerieFiscal(String serieFiscal) {
		this.serieFiscal = serieFiscal;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
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
}
