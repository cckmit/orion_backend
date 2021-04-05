package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.entity.PrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.repository.PrevisaoVendasRepository;

@Service
@Transactional
public class PrevisaoVendasService {

	private final PrevisaoVendasCustom previsaoVendasCustom;
	private final PrevisaoVendasRepository previsaoVendasRepository;
	private final TabelaPrecoCustom tabelaPrecoCustom;

	public PrevisaoVendasService(PrevisaoVendasCustom previsaoVendasCustom,
			PrevisaoVendasRepository previsaoVendasRepository,
			TabelaPrecoCustom tabelaPrecoCustom) {
		this.previsaoVendasCustom = previsaoVendasCustom;
		this.previsaoVendasRepository = previsaoVendasRepository;
		this.tabelaPrecoCustom = tabelaPrecoCustom;
	}

	public List<ConsultaPrevisaoVendas> findPrevisaoByColecao(int colecao) {
		return previsaoVendasCustom.findPrevisaoByColecao(colecao);
	}

	public String findIdTabelaSellIn (int colecao) {
		return previsaoVendasCustom.findIdTabelaSellIn(colecao);
	}

	public String findIdTabelaSellOut (int colecao) {
		return previsaoVendasCustom.findIdTabelaSellOut(colecao);
	}

	public void savePrevisoes(int colecao, int colTabPrecoSellIn, int mesTabPrecoSellIn, int seqTabPrecoSellIn,
			int colTabPrecoSellOut, int mesTabPrecoSellOut, int seqTabPrecoSellOut,
			List<ConsultaPrevisaoVendas> previsoesVenda) {

		System.out.println("savePrevisoes");
		
		PrevisaoVendas previsaoVenda;

		double valorSellIn = 0.000;
		double valorSellOut = 0.000;

		for (ConsultaPrevisaoVendas previsao : previsoesVenda) {

			valorSellIn = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellIn, mesTabPrecoSellIn, seqTabPrecoSellIn, previsao.grupo, previsao.item);
			valorSellOut = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellOut, mesTabPrecoSellOut, seqTabPrecoSellOut, previsao.grupo, previsao.item);

			System.out.println(previsao.grupo + " sell in: " + valorSellIn + " sell out: " + valorSellOut);			
			
			previsaoVenda = new PrevisaoVendas(colecao, previsao.grupo, previsao.item, colTabPrecoSellIn,
					mesTabPrecoSellIn, seqTabPrecoSellIn, valorSellIn, colTabPrecoSellOut, mesTabPrecoSellOut,
					seqTabPrecoSellOut, valorSellOut, previsao.qtdePrevisaoVendas);
			
			previsaoVendasRepository.save(previsaoVenda);
		}
	}
}
