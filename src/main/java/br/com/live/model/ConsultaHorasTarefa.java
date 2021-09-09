package br.com.live.model;

import java.util.Date;

public class ConsultaHorasTarefa {

	public String usuario;
	public Date dataLancamento;
	public String descricao;
	public float tempoGasto;
	public String id;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getTempoGasto() {
		return tempoGasto;
	}

	public void setTempoGasto(float tempoGasto) {
		this.tempoGasto = tempoGasto;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ConsultaHorasTarefa() {

	}

	public ConsultaHorasTarefa(String id, String usuario, Date dataLancamento, String descricao, float tempoGasto) {
		this.id = id;
		this.usuario = usuario;
		this.dataLancamento = dataLancamento;
		this.descricao = descricao;
		this.tempoGasto = tempoGasto;
	}

}
