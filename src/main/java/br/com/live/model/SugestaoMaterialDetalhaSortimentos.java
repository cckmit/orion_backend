package br.com.live.model;

public class SugestaoMaterialDetalhaSortimentos {
	private int idOrdem;
	private String cor;		
	private int qtdePrevista;	
	private int qtdeAtendidaPorTecido;
	private int qtdeAtendidaPorAviamento;
	
	public SugestaoMaterialDetalhaSortimentos(int idOrdem, String cor, int qtdePrevista, int qtdeAtendidaPorTecido, int qtdeAtendidaPorAviamento) {
		this.idOrdem = idOrdem;
		this.cor = cor;		
		this.qtdePrevista = qtdePrevista;
		this.qtdeAtendidaPorTecido = qtdeAtendidaPorTecido;
		this.qtdeAtendidaPorAviamento = qtdeAtendidaPorAviamento;
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

	public int getQtdeAtendidaPorTecido() {
		return qtdeAtendidaPorTecido;
	}

	public void setQtdeAtendidaPorTecido(int qtdeAtendidaPorTecido) {
		this.qtdeAtendidaPorTecido = qtdeAtendidaPorTecido;
	}

	public int getQtdeAtendidaPorAviamento() {
		return qtdeAtendidaPorAviamento;
	}

	public void setQtdeAtendidaPorAviamento(int qtdeAtendidaPorAviamento) {
		this.qtdeAtendidaPorAviamento = qtdeAtendidaPorAviamento;
	}
		
}
