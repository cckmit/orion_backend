package br.com.live.model;

import java.util.Date;

public class ConsultaHorasLancadas {
	public String id;
	public String usuario;
	public Date data;
	public String tarefa;
	public int situacao;
	public int origem;
	public float tempo;
	public int sistema;
	public float horaTotalOrigem;
	public String origemStr;
	public float percentual;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTarefa() {
		return tarefa;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public int getOrigem() {
		return origem;
	}

	public void setOrigem(int origem) {
		this.origem = origem;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
	
	public int getSistema() {
		return sistema;
	}

	public void setSistema(int sistema) {
		this.sistema = sistema;
	}

	public float getHoraTotalOrigem() {
		return horaTotalOrigem;
	}

	public void setHoraTotalOrigem(float horaTotalOrigem) {
		this.horaTotalOrigem = horaTotalOrigem;
	}
	
	public String getOrigemStr() {
		return origemStr;
	}

	public void setOrigemStr(String origemStr) {
		this.origemStr = origemStr;
	}

	public float getPercentual() {
		return percentual;
	}

	public void setPercentual(float percentual) {
		this.percentual = percentual;
	}

	public ConsultaHorasLancadas() {
	}

	public ConsultaHorasLancadas(String id, String usuario, Date data, String tarefa, int situacao, int origem,
			float tempo, int sistema, float horaTotalOrigem, String origemStr) {
		this.id = id;
		this.usuario = usuario;
		this.data = data;
		this.tarefa = tarefa;
		this.situacao = situacao;
		this.origem = origem;
		this.tempo = tempo;
		this.sistema = sistema;
		this.horaTotalOrigem = horaTotalOrigem;
		this.origemStr = origemStr;
	}

	
}
