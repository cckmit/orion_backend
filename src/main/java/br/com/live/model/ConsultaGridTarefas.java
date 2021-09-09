package br.com.live.model;

import java.util.Date;

public class ConsultaGridTarefas {

	public int id;
	public int tipo;
	public int sistema;
	public String usuarioSolicitante;
	public String usuarioAtribuido;
	public String titulo;
	public int situacao;
	public float tempoEstimado;
	public Date dataPrevista;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getSistema() {
		return sistema;
	}

	public void setSistema(int sistema) {
		this.sistema = sistema;
	}

	public String getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(String usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public String getUsuarioAtribuido() {
		return usuarioAtribuido;
	}

	public void setUsuarioAtribuido(String usuarioAtribuido) {
		this.usuarioAtribuido = usuarioAtribuido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public float getTempoEstimado() {
		return tempoEstimado;
	}

	public void setTempoEstimado(float tempoEstimado) {
		this.tempoEstimado = tempoEstimado;
	}

	public Date getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public ConsultaGridTarefas() {

	}

	public ConsultaGridTarefas(int id, String titulo, int sistema, float tempoEstimado, Date dataPrevista, int situacao, String usuarioAtribuido, String usuarioSolicitante, int tipo) {
		this.dataPrevista = dataPrevista;
		this.id = id;
		this.sistema = sistema;
		this.situacao = situacao;
		this.tempoEstimado = tempoEstimado;
		this.tipo = tipo;
		this.titulo = titulo;
		this.usuarioAtribuido = usuarioAtribuido;
		this.usuarioSolicitante = usuarioSolicitante;
	}

}
