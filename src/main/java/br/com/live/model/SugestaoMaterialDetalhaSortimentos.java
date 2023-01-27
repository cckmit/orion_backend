package br.com.live.model;

public class SugestaoMaterialDetalhaSortimentos {
	private int idOrdem;
	private String cor;		
	private int qtdePrevista;
	private int qtdeAtendida;
	
	public SugestaoMaterialDetalhaSortimentos(int idOrdem, String cor, int qtdePrevista, int qtdeAtendida) {
		this.idOrdem = idOrdem;
		this.cor = cor;		
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
