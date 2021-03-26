package br.com.live.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.model.MarcacaoRisco;

public class CalculoDistribuicaoPecas {

	public static List<ProdutoPlanoMestre> distribuirPelaQtdeSugerida(int qtdeDistribuir, List<ProdutoPlanoMestre> produtos) {
		
		double qtdeTotal = 0.000;		
		double proporcao = 0.000;
		double sugestao = 0.000;
		
		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre> ();
		
		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTotal += produto.qtdeSugestao;
		}
		
		for (ProdutoPlanoMestre produto : produtos) {
			sugestao = produto.qtdeSugestao;
			
			if (qtdeTotal > 0.000) proporcao = sugestao / qtdeTotal;
			else proporcao = 0.000;
			
			produto.qtdeProgramada = (int) (qtdeDistribuir * proporcao);			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;
		
	}
	
	public static List<ProdutoPlanoMestre> distribuirPelaGradePadrao(int qtdeDistribuir, List<ProdutoPlanoMestre> produtos, List<MarcacaoRisco> marcacoes) {
		
		double qtdeMarcacoes = 0.000;
		double proporcao = 0.000; 		
		Double qtdeTamanho = 0.000;
		
		Map<String, Double> mapMarcacoes = new HashMap<String, Double>();
		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre> ();		
		
		for (MarcacaoRisco marcacao : marcacoes) {
			qtdeMarcacoes += marcacao.quantidade;
			mapMarcacoes.put(marcacao.sub, (double) marcacao.quantidade);
		}
		
		System.out.println("qtdeDistribuir: " + qtdeDistribuir);
		
		for (ProdutoPlanoMestre produto : produtos) {			
			
     		qtdeTamanho = mapMarcacoes.get(produto.sub); 
     		if (qtdeTamanho == null) qtdeTamanho = 0.000;     		
			
			if (qtdeMarcacoes > 0.000) proporcao = qtdeTamanho / qtdeMarcacoes;
			else proporcao = 0.000;
			
			System.out.println("proporcao: " + proporcao);
			
			produto.qtdeProgramada = (int) (qtdeDistribuir * proporcao);
			
			System.out.println("produto.qtdeProgramada: " + produto.qtdeProgramada);
			
			produtosAtualizados.add(produto);
		}
		
		return produtosAtualizados;
	}
	
}
