package br.com.live.model;

public class PreOrdemProducao {

	public int id;
	public String grupo;
	public int alternativa;
	public int roteiro;
	public int periodo;
	public int qtdeProgramada;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getQtdeProgramada() {
		return qtdeProgramada;
	}
	public void setQtdeProgramada(int qtdeProgramada) {
		this.qtdeProgramada = qtdeProgramada;
	}	
}
