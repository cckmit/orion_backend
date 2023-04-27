package br.com.live.model;

public class DadosModalEndereco {
	public String grupo;
	public String subGrupo;
	public String item;
	public String endereco;
	public String colecao;
	public float saldo;
	public int embarque;
	public int quantEndereco;
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSubGrupo() {
		return subGrupo;
	}
	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getColecao() {
		return colecao;
	}
	public void setColecao(String colecao) {
		this.colecao = colecao;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public int getEmbarque() {
		return embarque;
	}
	public void setEmbarque(int embarque) {
		this.embarque = embarque;
	}

	public int getQuantEndereco() {
		return quantEndereco;
	}

	public void setQuantEndereco(int quantEndereco) {
		this.quantEndereco = quantEndereco;
	}

	public DadosModalEndereco() {
	}

	public DadosModalEndereco(String grupo, String subGrupo, String item, String endereco, String colecao, float saldo, int embarque, int quantEndereco) {
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.item = item;
		this.endereco = endereco;
		this.colecao = colecao;
		this.saldo = saldo;
		this.embarque = embarque;
		this.quantEndereco = quantEndereco;
	}
}
