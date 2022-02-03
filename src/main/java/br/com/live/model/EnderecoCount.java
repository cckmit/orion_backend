package br.com.live.model;

public class EnderecoCount {
	public int totalPecas;
	public String referencia;
	public String endereco;
	
	public int getTotalPecas() {
		return totalPecas;
	}
	public void setTotalPecas(int totalPecas) {
		this.totalPecas = totalPecas;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public EnderecoCount() {
		
	}
	
	public EnderecoCount(int totalPecas, String referencia, String endereco) {
		this.totalPecas = totalPecas;
		this.referencia = referencia;
		this.endereco = endereco;
	}	
}
