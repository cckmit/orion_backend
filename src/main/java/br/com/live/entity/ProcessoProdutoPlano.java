package br.com.live.entity;

public class ProcessoProdutoPlano extends ProcessoProduto {

	public int plano;
	
	public ProcessoProdutoPlano(int id, String nivel, String grupo, String sub, String item, int quantidade,
			int periodo, int plano, int colecao, int linha, int artigo, int artigoCotas, int origem, int permanente, String embarque) {
		super(id, nivel, grupo, sub, item, quantidade, periodo, colecao, linha, artigo, artigoCotas, origem, permanente, embarque);
		this.plano = plano;
	}

}
