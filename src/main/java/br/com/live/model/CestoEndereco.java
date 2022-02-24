package br.com.live.model;

public class CestoEndereco {

	public String endereco;
	public int qtdeCapacPecas;
	public int qtdeCapacOcupada;
	public int qtdeCapacDisponivel;
	
	public CestoEndereco(String endereco, int qtdeCapacPecas, int qtdeCapacOcupada) {
		this.endereco = endereco; 
		this.qtdeCapacPecas = qtdeCapacPecas; 
		this.qtdeCapacOcupada = qtdeCapacOcupada;
		setQtdeCapacDisponivel();
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getQtdeCapacPecas() {
		return qtdeCapacPecas;
	}

	public void setQtdeCapacPecas(int qtdeCapacPecas) {
		this.qtdeCapacPecas = qtdeCapacPecas;
		setQtdeCapacDisponivel();
	}

	public int getQtdeCapacOcupada() {
		return qtdeCapacOcupada;
	}

	public void setQtdeCapacOcupada(int qtdeCapacOcupada) {
		this.qtdeCapacOcupada = qtdeCapacOcupada;
		setQtdeCapacDisponivel();
	}

	public int getQtdeCapacDisponivel() {
		return qtdeCapacDisponivel;
	}

	private void setQtdeCapacDisponivel() {
		this.qtdeCapacDisponivel = this.qtdeCapacPecas - this.qtdeCapacOcupada;
	}
}
