package br.com.live.model;

public class DadosTagProd {
	public int periodo;
	public int ordem;
	public int pacote;
	public int sequencia;
	
	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getPacote() {
		return pacote;
	}

	public void setPacote(int pacote) {
		this.pacote = pacote;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public DadosTagProd() {
	}
	
	public DadosTagProd(int periodo, int ordem, int pacote, int sequencia) {
		this.periodo = periodo;
		this.ordem = ordem;
		this.pacote = pacote;
		this.sequencia = sequencia;
	}
}