package br.com.live.entity;

public class DemandaProdutoPlano extends DemandaProduto {

	public int plano;

	public DemandaProdutoPlano(int id, String nivel, String grupo, String sub, String item, Double quantidade,
			int periodo, int plano, int colecao, int linha, int artigo, int artigoCotas, int origem, int permanente,
			int natureza, int nrInterno, int pedido, String embarque, int situacaoVenda) {
		super(id, nivel, grupo, sub, item, quantidade, periodo, colecao, linha, artigo, artigoCotas, origem, permanente,
				natureza, nrInterno, pedido, embarque, situacaoVenda);
		this.plano = plano;
	}

}
