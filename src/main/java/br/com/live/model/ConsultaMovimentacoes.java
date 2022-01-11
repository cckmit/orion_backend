package br.com.live.model;

import java.util.Date;

public class ConsultaMovimentacoes {
	public int sequencia;
	public int idBem;
	public String tipoMovimentacao;
	public String cnpjOrigem;
	public String cnpjDestino;
	public Date dataEnvio;
	public String notaFiscal;
	public String usuario;
	
	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public int getIdBem() {
		return idBem;
	}

	public void setIdBem(int idBem) {
		this.idBem = idBem;
	}

	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public String getCnpjOrigem() {
		return cnpjOrigem;
	}

	public void setCnpjOrigem(String cnpjOrigem) {
		this.cnpjOrigem = cnpjOrigem;
	}

	public String getCnpjDestino() {
		return cnpjDestino;
	}

	public void setCnpjDestino(String cnpjDestino) {
		this.cnpjDestino = cnpjDestino;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public ConsultaMovimentacoes() {
	}
	
	public ConsultaMovimentacoes(int sequencia, int idBem, String tipoMovimentacao, String cnpjOrigem, String cnpjDestino,
			Date dataEnvio, String notaFiscal, String usuario) {
		this.sequencia = sequencia;
		this.idBem = idBem;
		this.tipoMovimentacao = tipoMovimentacao;
		this.cnpjOrigem = cnpjOrigem;
		this.cnpjDestino = cnpjDestino;
		this.dataEnvio = dataEnvio;
		this.notaFiscal = notaFiscal;
		this.usuario = usuario;
	}
}
