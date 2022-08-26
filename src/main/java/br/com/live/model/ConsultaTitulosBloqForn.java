package br.com.live.model;

import java.util.Date;

public class ConsultaTitulosBloqForn {
	public String fornecedor;
	public String id;
	public String motivo;
	public Date dataBloqueio;
	public Date dataDesbloqueio;
	
	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public Date getDataDesbloqueio() {
		return dataDesbloqueio;
	}

	public void setDataDesbloqueio(Date dataDesbloqueio) {
		this.dataDesbloqueio = dataDesbloqueio;
	}

	public ConsultaTitulosBloqForn() {
		
	}

	public ConsultaTitulosBloqForn(String fornecedor, String id, String motivo, Date dataBloqueio, Date dataDesbloqueio) {
		this.fornecedor = fornecedor;
		this.id = id;
		this.motivo = motivo;
		this.dataBloqueio = dataBloqueio;
		this.dataDesbloqueio = dataDesbloqueio;
	}
}
