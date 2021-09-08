package br.com.live.model;

import java.util.Date;

public class ConsultaDadosLancHoras {


	public int id;
	public int tipo;
	public int sistema;
	public int origem;
	public String usuarioAtribuido;
	public String titulo;
	public String assunto;
	public int situacao;
	public float tempoEstimado;
	public Date dataPrevista;
	public float tempoGasto;
	
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

	public int getOrigem() {
		return origem;
	}

	public void setOrigem(int origem) {
		this.origem = origem;
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

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
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

	public float getTempoGasto() {
		return tempoGasto;
	}

	public void setTempoGasto(float tempoGasto) {
		this.tempoGasto = tempoGasto;
	}

	public ConsultaDadosLancHoras() {
	}

	public ConsultaDadosLancHoras(int id, int tipo, int sistema, int origem, String usuarioAtribuido, String titulo, String assunto, int situacao, float tempoEstimado, Date dataPrevista, float tempoGasto) {
		this.id = id;
		this.tipo = tipo;
		this.sistema = sistema;
		this.origem = origem;
		this.usuarioAtribuido = usuarioAtribuido;
		this.titulo = titulo;
		this.assunto = assunto;
		this.situacao = situacao;
		this.tempoEstimado = tempoEstimado;
		this.dataPrevista = dataPrevista;
		this.tempoGasto =tempoGasto;
	}
}