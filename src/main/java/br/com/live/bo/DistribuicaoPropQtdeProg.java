package br.com.live.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.live.entity.ProdutoPlanoMestre;

public class DistribuicaoPropQtdeProg {

	private double qtdeDistribuir;
	private List<ProdutoPlanoMestre> produtos;
	private List<ProdutoPlanoMestre> produtosAtualizados;
		
	public DistribuicaoPropQtdeProg(int qtdeDistribuir, List<ProdutoPlanoMestre> produtos) {		
		this.qtdeDistribuir = (double) qtdeDistribuir;
		this.produtos = produtos;		
		calcular();
	}
	
	public void calcular() {		
		double qtdeTotal = 0.000;		
		double proporcao = 0.000;
		double sugestao = 0.000;
		
		produtosAtualizados = new ArrayList<ProdutoPlanoMestre> ();
		
		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTotal += produto.qtdeSugestao;
		}
		
		for (ProdutoPlanoMestre produto : produtos) {
			sugestao = produto.qtdeSugestao;
			proporcao = sugestao / qtdeTotal;
			
			produto.qtdeProgramada = (int) (qtdeDistribuir * proporcao);
			
			produtosAtualizados.add(produto);
		}
	}
	
	public List<ProdutoPlanoMestre> getProdutosAtualizados () {
		return this.produtosAtualizados; 
	}
}
