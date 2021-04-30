package br.com.live.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.model.MarcacaoRisco;

public class CalculoDistribuicaoPecas {

	public static List<ProdutoPlanoMestre> distribuirPelaQtdeSugerida(int qtdeDistribuir,
			List<ProdutoPlanoMestre> produtos) {

		double qtdeTotal = 0.000;
		double proporcao = 0.000;
		double sugestao = 0.000;

		// System.out.println(qtdeDistribuir);

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

			// System.out.println(qtdeDistribuir);
			// System.out.println("proporcao: " + proporcao + " qtdeDistribuir: " +
			// qtdeDistribuir + " programada: " + produto.qtdeProgramada);

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

		// System.out.println("qtdeDistribuir: " + qtdeDistribuir);

		List<ProdutoPlanoMestre> produtosAtualizados = new ArrayList<ProdutoPlanoMestre>();

		for (ProdutoPlanoMestre produto : produtos) {
			qtdeTotalDemanda += produto.qtdeDemAcumulado;
			qtdeTotalSaldo += produto.qtdeSaldoAcumulado;
		}

		// System.out.println("qtdeProgramar: " + qtdeProgramar + " - qtdeTotalSaldo: "
		// + qtdeTotalSaldo);

		qtdeProgramar += qtdeTotalSaldo;

		for (ProdutoPlanoMestre produto : produtos) {
			proporcao = produto.qtdeDemAcumulado / qtdeTotalDemanda;
			produto.qtdeProgramada = (int) Math.ceil(qtdeProgramar * proporcao);

			// System.out.println(produto.sub + " produto.qtdeProgramada: " +
			// produto.qtdeProgramada);

			produto.qtdeProgramada = (produto.qtdeProgramada - (produto.qtdeSaldoAcumulado));

			/*
			if ((produto.grupo.equalsIgnoreCase("83351")) && (produto.item.equalsIgnoreCase("00BC01"))) {
				System.out.println(produto.grupo + " " + produto.item);
				System.out.println("produto.qtdeProgramada: " + produto.qtdeProgramada + " qtdeProgramar: "
						+ qtdeProgramar + " produto.qtdeSaldoAcumulado: " + produto.qtdeSaldoAcumulado + " qtdeDistribuir: " + qtdeDistribuir);
			}
			*/
				
			if ((produto.qtdeProgramada <= 0) || (qtdeDistribuir <= 0))
				produto.qtdeProgramada = 0;
			
			produtosAtualizados.add(produto);
		}

		return produtosAtualizados;
	}

}
