package br.com.live.produto.model;

public class CorProduto {

	public int id;
	public String item;
	public String descricao;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CorProduto() { }
	
	public CorProduto(int id, String item, String descricao) {
		this.id = id;
		this.item = item;
		this.descricao = descricao;
	}

}