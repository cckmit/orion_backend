package br.com.live.comercial.model;

public class CestoEndereco {

	public String endereco;
	public int qtdeCapacidade;
	public int qtdeOcupado;
	
	public CestoEndereco() {}
	
	public CestoEndereco(String endereco, int qtdeCapacidade, int qtdeOcupado) {
		super();
		this.endereco = endereco; 
		this.qtdeCapacidade = qtdeCapacidade; 
		this.qtdeOcupado = qtdeOcupado;		
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getQtdeCapacidade() {
		return qtdeCapacidade;
	}

	public void setQtdeCapacidade(int qtdeCapacidade) {
		this.qtdeCapacidade = qtdeCapacidade;
	}

	public int getQtdeOcupado() {
		return qtdeOcupado;
	}

	public void setQtdeOcupado(int qtdeOcupado) {
		this.qtdeOcupado = qtdeOcupado;
	}
}
