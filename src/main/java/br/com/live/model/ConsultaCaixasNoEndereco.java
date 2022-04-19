package br.com.live.model;

public class ConsultaCaixasNoEndereco {
	public int numeroCaixa;
	public int periodo;
	public int ordemProducao;
	public int pacote;
	public String sku;
	public String endereco;
	public int quantidade;
	
	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public int getPacote() {
		return pacote;
	}

	public void setPacote(int pacote) {
		this.pacote = pacote;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getNumeroCaixa() {
		return numeroCaixa;
	}
	
	public void setNumeroCaixa(int numeroCaixa) {
		this.numeroCaixa = numeroCaixa;
	}
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public ConsultaCaixasNoEndereco() {
		
	}

	public ConsultaCaixasNoEndereco(int numeroCaixa, int periodo, int ordemProducao, int pacote, String sku,
			String endereco, int quantidade) {
		this.numeroCaixa = numeroCaixa;
		this.periodo = periodo;
		this.ordemProducao = ordemProducao;
		this.pacote = pacote;
		this.sku = sku;
		this.endereco = endereco;
		this.quantidade = quantidade;
	}
}
