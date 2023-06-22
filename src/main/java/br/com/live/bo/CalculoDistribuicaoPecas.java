package br.com.live.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.comercial.model.ConsultaPrevisaoVendasItemTam;
import br.com.live.producao.entity.ProdutoPlanoMestre;
import br.com.live.produto.model.MarcacaoRisco;

public class CalculoDistribuicaoPecas {

	public static List<ProdutoPlanoMestre> distribuirPelaQtdeSugerida(int qtdeDistribuir,
			List<ProdutoPlanoMestre> produtos) {

		double qtdeTotal = 0.000;
		double proporcao = 0.000;
		double sugestao = 0.000;

		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre>();

		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTotal += produto.qtdeSugestao;
		}

		for (ProdutoPlanoMestre produto : produtos) {
			sugestao = produto.qtdeSugestao;

			if (qtdeTotal > 0.000)
				proporcao = sugestao / qtdeTotal;
			else
				proporcao = 0.000;

			produto.qtdeProgramada = (int) Math.ceil((double) qtdeDistribuir * proporcao);
			produto.qtdeProgramada = verificaQtdeMinimaProgramar(qtdeDistribuir, produto.qtdeProgramada, produto.qtdeSaldoAcumProg);
			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;

	}

	public static List<ProdutoPlanoMestre> distribuirPelaGradePadrao(int qtdeDistribuir,
			List<ProdutoPlanoMestre> produtos, List<MarcacaoRisco> marcacoes) {

		double qtdeMarcacoes = 0.000;
		double proporcao = 0.000;
		Double qtdeTamanho = 0.000;

		Map<String, Double> mapMarcacoes = new HashMap<String, Double>();
		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre>();

		for (MarcacaoRisco marcacao : marcacoes) {
			qtdeMarcacoes += marcacao.quantidade;
			if (mapMarcacoes.containsKey(marcacao.sub))
				marcacao.quantidade += mapMarcacoes.get(marcacao.sub);
			mapMarcacoes.put(marcacao.sub, (double) marcacao.quantidade);
		}

		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTamanho = mapMarcacoes.get(produto.sub);
			if (qtdeTamanho == null)
				qtdeTamanho = 0.000;

			if (qtdeMarcacoes > 0.000)
				proporcao = qtdeTamanho / qtdeMarcacoes;
			else
				proporcao = 0.000;
			
			produto.qtdeProgramada = (int) Math.ceil((double) qtdeDistribuir * proporcao);
			produto.qtdeProgramada = verificaQtdeMinimaProgramar(qtdeDistribuir, produto.qtdeProgramada, produto.qtdeSaldoAcumProg);
			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;
	}

	public static List<ProdutoPlanoMestre> distribuirPelaGradeVenda(int qtdeDistribuir,
			List<ProdutoPlanoMestre> produtos) {

		double qtdeTotalDemanda = 0.000;
		double qtdeTotalSaldo = 0.000;
		double proporcao = 0.000;
		double qtdeProgramar = (int) qtdeDistribuir;

		//System.out.println("qtdeDistribuir: " + qtdeDistribuir);

		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre>();

		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTotalDemanda += produto.qtdeDemAcumProg;
			if (produto.qtdeSaldoAcumProg < 0) qtdeTotalSaldo += produto.qtdeSaldoAcumProg;
		}

		//System.out.println("qtdeProgramar: " + qtdeProgramar + " - qtdeTotalSaldo: "
		//+ qtdeTotalSaldo);

		qtdeProgramar += qtdeTotalSaldo;

		for (ProdutoPlanoMestre produto : produtos) {
			proporcao = produto.qtdeDemAcumProg / qtdeTotalDemanda;
			produto.qtdeProgramada = (int) Math.ceil(qtdeProgramar * proporcao);

			//System.out.println(produto.sub + " produto.qtdeProgramada: " +
			//produto.qtdeProgramada);

			produto.qtdeProgramada = (produto.qtdeProgramada - (produto.qtdeSaldoAcumProg));
			
			//if ((produto.grupo.equalsIgnoreCase("83351")) && (produto.item.equalsIgnoreCase("00BC01"))) {
			//	System.out.println(produto.grupo + " " + produto.item);
			//	System.out.println("produto.qtdeProgramada: " + produto.qtdeProgramada + " qtdeProgramar: "
			//			+ qtdeProgramar + " produto.qtdeSaldoAcumulado: " + produto.qtdeSaldoAcumProg + " qtdeDistribuir: " + qtdeDistribuir + " proporcao: " + proporcao);
			//}			
				
			produto.qtdeProgramada = verificaQtdeMinimaProgramar(qtdeDistribuir, produto.qtdeProgramada, produto.qtdeSaldoAcumProg);
			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;
	}

	
	public static List<ProdutoPlanoMestre> distribuirPelaGradePrevisao(int qtdeDistribuir, List<ProdutoPlanoMestre> produtos, List<ConsultaPrevisaoVendasItemTam> previsaoTamanhos) {
		
		double qtdeTotalPrevisao = 0.000;
		double proporcao = 0.000;
		Double qtdeTamanho = 0.000;

		Map<String, Double> mapGradePrevisao = new HashMap<String, Double>();
		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre>();

		for (ConsultaPrevisaoVendasItemTam previsao : previsaoTamanhos) {
			qtdeTotalPrevisao += previsao.qtdePrevisao;			
			mapGradePrevisao.put(previsao.sub, (double) previsao.qtdePrevisao);
		}

		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTamanho = mapGradePrevisao.get(produto.sub);
			
			if (qtdeTamanho == null)
				qtdeTamanho = 0.000;

			if (qtdeTotalPrevisao > 0.000)
				proporcao = qtdeTamanho / qtdeTotalPrevisao;
			else
				proporcao = 0.000;
						
			produto.qtdeProgramada = (int) Math.ceil((double) qtdeDistribuir * proporcao);
			produto.qtdeProgramada = verificaQtdeMinimaProgramar(qtdeDistribuir, produto.qtdeProgramada, produto.qtdeSaldoAcumProg);
			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;
	}
	
	private static int verificaQtdeMinimaProgramar(int qtdeDistribuir, int qtdeProgramar, int qtdeSaldo) {
		
		if (qtdeDistribuir <= 0) return 0;
		
		if (qtdeProgramar <= 0) qtdeProgramar = 0;
		
		if (qtdeSaldo < 0) {			
			qtdeSaldo = (qtdeSaldo * -1); // Positiva			
			if (qtdeSaldo > qtdeProgramar) qtdeProgramar = qtdeSaldo; 					
		}
			
		return qtdeProgramar;
	}		
}
