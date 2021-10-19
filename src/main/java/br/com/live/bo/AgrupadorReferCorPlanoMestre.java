package br.com.live.bo;

import br.com.live.entity.ProdutoPlanoMestre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.ProdutoPlanoMestrePorCor;

public class AgrupadorReferCorPlanoMestre {

	private boolean consideraPrevisaoVendas;
	private List<ProdutoPlanoMestrePorCor> produtos;
	public int tipoDistribuicao;

	public AgrupadorReferCorPlanoMestre(boolean consideraPrevisaoVendas, List<ProdutoPlanoMestre> produtos,
			int tipoDistribuicao) {
		this.consideraPrevisaoVendas = consideraPrevisaoVendas;
		this.produtos = agruparProdutos(produtos, tipoDistribuicao);
	}

	public List<ProdutoPlanoMestrePorCor> getProdutos() {
		return this.produtos;
	}

	private List<ProdutoPlanoMestrePorCor> agruparProdutos(List<ProdutoPlanoMestre> produtos, int tipoDistribuicao) {

		Map<String, ProdutoPlanoMestrePorCor> mapProdutos;

		mapProdutos = agruparProdutosPorCor(produtos, tipoDistribuicao);
		mapProdutos = calcularSugestao(mapProdutos, tipoDistribuicao);
		mapProdutos = calcularRankMaisVendidos(mapProdutos);

		List<ProdutoPlanoMestrePorCor> produtosPlanoMestrePorCor = new ArrayList<ProdutoPlanoMestrePorCor>(
				mapProdutos.values());

		return produtosPlanoMestrePorCor;
	}

	private Map<String, ProdutoPlanoMestrePorCor> calcularRankMaisVendidos(
			Map<String, ProdutoPlanoMestrePorCor> mapProdutos) {

		ProdutoPlanoMestrePorCor produto;

		Map<Integer, Integer> mapQuantidades = new HashMap<Integer, Integer>();

		for (String chave : mapProdutos.keySet()) {
			produto = mapProdutos.get(chave);
			mapQuantidades.put(produto.qtdeDemAcumulado, null);
		}

		int rank = 0;

		List<Integer> listQtdeChaves = new ArrayList<>(mapQuantidades.keySet());
		Collections.sort(listQtdeChaves, Collections.reverseOrder());

		for (Integer quantidade : listQtdeChaves) {
			rank++;
			mapQuantidades.put(quantidade, rank);
		}

		for (String chave : mapProdutos.keySet()) {

			produto = mapProdutos.get(chave);

			if (mapQuantidades.containsKey(produto.qtdeDemAcumulado)) {
				produto.rank = mapQuantidades.get(produto.qtdeDemAcumulado);
				mapProdutos.put(produto.codigo, produto);
			}
		}

		return mapProdutos;
	}

	private Map<String, ProdutoPlanoMestrePorCor> calcularSugestao(Map<String, ProdutoPlanoMestrePorCor> mapProdutos,
			int tipoDistribuicao) {

		ProdutoPlanoMestrePorCor produto;

		for (String chave : mapProdutos.keySet()) {

			produto = mapProdutos.get(chave);

			if (consideraPrevisaoVendas) {
				produto.qtdeSaldoAcumulado = produto.qtdeSaldoAcumulado - produto.qtdePrevisao;
				produto.qtdeSaldoAcumProg = produto.qtdeSaldoAcumProg - produto.qtdePrevisao;
			}

			if (produto.qtdeSaldoAcumProg < 0)
				produto.qtdeSugestao = (produto.qtdeSaldoAcumProg * -1);
			else if (produto.qtdeProgramada > 0)
				produto.qtdeSugestao = produto.qtdeProgramada;

			produto.qtdeEqualizadoSugestao = produto.qtdeSugestao;
			produto.qtdeDiferencaSugestao = produto.qtdeEqualizadoSugestao - produto.qtdeSugestao;

			if (tipoDistribuicao > 0)
				produto.qtdeProgramada = produto.qtdeEqualizadoSugestao;
		}

		return mapProdutos;
	}

	private Map<String, ProdutoPlanoMestrePorCor> agruparProdutosPorCor(List<ProdutoPlanoMestre> produtos,
			int tipoDistribuicao) {

		String codProduto = "";

		ProdutoPlanoMestrePorCor produtoCor;

		Map<String, ProdutoPlanoMestrePorCor> mapProdutos;
		mapProdutos = new HashMap<String, ProdutoPlanoMestrePorCor>();

		for (ProdutoPlanoMestre produto : produtos) {

			codProduto = produto.grupo + "." + produto.item;

			if (mapProdutos.containsKey(codProduto)) {
				produtoCor = mapProdutos.get(codProduto);
			} else {
				produtoCor = new ProdutoPlanoMestrePorCor();
			}

			produtoCor.codigo = codProduto;
			produtoCor.grupo = produto.grupo;
			produtoCor.item = produto.item;

			produtoCor.qtdePrevisao = produto.qtdePrevisao;
			produtoCor.qtdeEstoque += produto.qtdeEstoque;

			produtoCor.qtdeDemPlano1 += produto.qtdeDemPlano1;
			produtoCor.qtdeDemPlano2 += produto.qtdeDemPlano2;
			produtoCor.qtdeDemPlano3 += produto.qtdeDemPlano3;
			produtoCor.qtdeDemPlano4 += produto.qtdeDemPlano4;
			produtoCor.qtdeDemPlano5 += produto.qtdeDemPlano5;
			produtoCor.qtdeDemPlano6 += produto.qtdeDemPlano6;
			produtoCor.qtdeDemPlano7 += produto.qtdeDemPlano7;
			produtoCor.qtdeDemPlano8 += produto.qtdeDemPlano8;

			produtoCor.qtdeProcPlano1 += produto.qtdeProcPlano1;
			produtoCor.qtdeProcPlano2 += produto.qtdeProcPlano2;
			produtoCor.qtdeProcPlano3 += produto.qtdeProcPlano3;
			produtoCor.qtdeProcPlano4 += produto.qtdeProcPlano4;
			produtoCor.qtdeProcPlano5 += produto.qtdeProcPlano5;
			produtoCor.qtdeProcPlano6 += produto.qtdeProcPlano6;
			produtoCor.qtdeProcPlano7 += produto.qtdeProcPlano7;
			produtoCor.qtdeProcPlano8 += produto.qtdeProcPlano8;

			produtoCor.qtdeSaldoPlano1 += produto.qtdeSaldoPlano1;
			produtoCor.qtdeSaldoPlano2 += produto.qtdeSaldoPlano2;
			produtoCor.qtdeSaldoPlano3 += produto.qtdeSaldoPlano3;
			produtoCor.qtdeSaldoPlano4 += produto.qtdeSaldoPlano4;
			produtoCor.qtdeSaldoPlano5 += produto.qtdeSaldoPlano5;
			produtoCor.qtdeSaldoPlano6 += produto.qtdeSaldoPlano6;
			produtoCor.qtdeSaldoPlano7 += produto.qtdeSaldoPlano7;
			produtoCor.qtdeSaldoPlano8 += produto.qtdeSaldoPlano8;

			produtoCor.qtdeDemAcumProg += produto.qtdeDemAcumProg;
			produtoCor.qtdeProcAcumProg += produto.qtdeProcAcumProg;
			produtoCor.qtdeSaldoAcumProg += produto.qtdeSaldoAcumProg;

			produtoCor.qtdeDemAcumulado += produto.qtdeDemAcumulado;
			produtoCor.qtdeProcAcumulado += produto.qtdeProcAcumulado;
			produtoCor.qtdeSaldoAcumulado += produto.qtdeSaldoAcumulado;

			if (tipoDistribuicao > 0) {
				if (produto.qtdeSaldoAcumProg < 0)
					produtoCor.qtdeProgramada += (produto.qtdeSaldoAcumProg * -1);
			}
			
			mapProdutos.put(codProduto, produtoCor);
		}

		return mapProdutos;
	}

}
