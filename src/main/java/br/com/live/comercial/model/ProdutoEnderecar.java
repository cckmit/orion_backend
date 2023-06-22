package br.com.live.comercial.model;

public class ProdutoEnderecar {

	public static final String ENDERECO_INDISPONIVEL = "INDISPONIVEL";
	
	public int caixa;
	public String nivel;
	public String referencia;
	public String tamanho;
	public String cor;
	public int deposito;
	public int qtdeEnderecar;
	public int qtdeEnderecada;
	public String endereco;
	
	public ProdutoEnderecar() {
		this.endereco = ENDERECO_INDISPONIVEL;
	}
	
	public ProdutoEnderecar(int caixa, String nivel, String referencia, String tamanho, String cor, int qtdeEnderecar, int qtdeEnderecada) {
		super();
		this.caixa = caixa;  
		this.nivel = nivel; 
		this.referencia = referencia; 
		this.tamanho = tamanho; 
		this.cor = cor; 
		this.qtdeEnderecar = qtdeEnderecar;
		this.qtdeEnderecada = qtdeEnderecada;		
	}

	public int getCaixa() {
		return caixa;
	}

	public void setCaixa(int caixa) {
		this.caixa = caixa;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public int getDeposito() {
		return deposito;
	}

	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}

	public int getQtdeEnderecar() {
		return qtdeEnderecar;
	}

	public void setQtdeEnderecar(int qtdeEnderecar) {
		this.qtdeEnderecar = qtdeEnderecar;
	}

	public int getQtdeEnderecada() {
		return qtdeEnderecada;
	}

	public void setQtdeEnderecada(int qtdeEnderecada) {
		this.qtdeEnderecada = qtdeEnderecada;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}