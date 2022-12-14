package br.com.live.model;

public class DadosGeracaoOrdemProducaoItem {
	
	private String tamanho;
	private String cor;
	private int quantidade;
	
	public DadosGeracaoOrdemProducaoItem(String tamanho, String cor, int quantidade) {
		super();
		this.tamanho = tamanho;
		this.cor = cor;
		this.quantidade = quantidade;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}	
}
