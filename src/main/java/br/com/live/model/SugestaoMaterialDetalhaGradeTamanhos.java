package br.com.live.model;

public class SugestaoMaterialDetalhaGradeTamanhos {
	private int idOrdem;
	private String cor;	
	private int ordemTamanho;
	private String tamanho;
	private int qtdePrevista;
	private int qtdeAtendida;

	public SugestaoMaterialDetalhaGradeTamanhos(int idOrdem, String cor, int ordemTamanho, String tamanho, int qtdePrevista, int qtdeAtendida) {
		this.idOrdem = idOrdem;
		this.cor = cor;	
		this.ordemTamanho = ordemTamanho;
		this.tamanho = tamanho;
		this.qtdePrevista = qtdePrevista;
		this.qtdeAtendida = qtdeAtendida;
	}

	public int getIdOrdem() {
		return idOrdem;
	}

	public void setIdOrdem(int idOrdem) {
		this.idOrdem = idOrdem;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getOrdemTamanho() {
		return ordemTamanho;
	}

	public void setOrdemTamanho(int ordemTamanho) {
		this.ordemTamanho = ordemTamanho;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public int getQtdePrevista() {
		return qtdePrevista;
	}

	public void setQtdePrevista(int qtdePrevista) {
		this.qtdePrevista = qtdePrevista;
	}

	public int getQtdeAtendida() {
		return qtdeAtendida;
	}

	public void setQtdeAtendida(int qtdeAtendida) {
		this.qtdeAtendida = qtdeAtendida;
	}	
}