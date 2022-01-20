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

	public ConsultaHorasLancadas() {
	}

	public ConsultaHorasLancadas(String id, String usuario, Date data, String tarefa, int situacao, int origem,
			float tempo, int sistema) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.data = data;
		this.tarefa = tarefa;
		this.situacao = situacao;
		this.origem = origem;
		this.tempo = tempo;
		this.sistema = sistema;
	}
}
