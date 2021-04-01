package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.entity.PrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.repository.PrevisaoVendasRepository;

@Service
@Transactional
public class PrevisaoVendasService {

	private final PrevisaoVendasCustom previsaoVendasCustom;
	private final PrevisaoVendasRepository previsaoVendasRepository;

	public PrevisaoVendasService(PrevisaoVendasCustom previsaoVendasCustom,
			PrevisaoVendasRepository previsaoVendasRepository) {
		this.previsaoVendasCustom = previsaoVendasCustom;
		this.previsaoVendasRepository = previsaoVendasRepository;
	}

	public List<ConsultaPrevisaoVendas> findPrevisaoByColecao(int colecao) {
		return previsaoVendasCustom.findPrevisaoByColecao(colecao);
	}

	public int colTabPrecoSellIn;
	public int mesTabPrecoSellIn;
	public int seqTabPrecoSellIn;

	public void savePrevisoes(int colecao, int colTabPrecoSellIn, int mesTabPrecoSellIn, int seqTabPrecoSellIn,
			int colTabPrecoSellOut, int mesTabPrecoSellOut, int seqTabPrecoSellOut,
			List<ConsultaPrevisaoVendas> previsoesVenda) {

		PrevisaoVendas previsaoVenda;

		double valorSellIn = 0.000;
		double valorSellOut = 0.000;

		for (ConsultaPrevisaoVendas previsao : previsoesVenda) {

			previsaoVenda = new PrevisaoVendas(colecao, previsao.grupo, previsao.item, colTabPrecoSellIn,
					mesTabPrecoSellIn, seqTabPrecoSellIn, valorSellIn, colTabPrecoSellOut, mesTabPrecoSellOut,
					seqTabPrecoSellOut, valorSellOut, previsao.qtdePrevisaoVendas);

		}
	}
}
