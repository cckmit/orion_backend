package br.com.live.produto.model;

import java.util.Date;

public class ConsultaReferenciasIntegradas {
	public int id;
	public String referencia;
	public Date data;
	public String observacao;
	public int status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ConsultaReferenciasIntegradas() {}

	public ConsultaReferenciasIntegradas(int id, String referencia, Date data, String observacao, int status) {
		this.id = id;
		this.referencia = referencia;
		this.data = data;
		this.observacao = observacao;
		this.status = status;
	}
}
