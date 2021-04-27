package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.entity.PrevisaoVendas;
import br.com.live.entity.PrevisaoVendasItens;
import br.com.live.entity.ProdutoReferCor;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItens;
import br.com.live.repository.PrevisaoVendasItensRepository;
import br.com.live.repository.PrevisaoVendasRepository;

@Service
@Transactional
public class PrevisaoVendasService {

	private final PrevisaoVendasCustom previsaoVendasCustom;
	private final PrevisaoVendasRepository previsaoVendasRepository;
	private final PrevisaoVendasItensRepository previsaoVendasItensRepository;
	private final TabelaPrecoCustom tabelaPrecoCustom;
	private final ProdutoCustom produtoCustom;

	public PrevisaoVendasService(PrevisaoVendasCustom previsaoVendasCustom,
			PrevisaoVendasRepository previsaoVendasRepository, PrevisaoVendasItensRepository previsaoVendasItensRepository, 
			TabelaPrecoCustom tabelaPrecoCustom, ProdutoCustom produtoCustom) {
		this.previsaoVendasCustom = previsaoVendasCustom;
		this.previsaoVendasRepository = previsaoVendasRepository;
		this.previsaoVendasItensRepository = previsaoVendasItensRepository;
		this.tabelaPrecoCustom = tabelaPrecoCustom;
		this.produtoCustom = produtoCustom;
	}

	public List<ConsultaPrevisaoVendas> findPrevisoesVendas() {
		return previsaoVendasCustom.findPrevisoesVendas();
	}

	public List<ConsultaPrevisaoVendasItens> findPrevisoesVendasItensByIdPrevisaoVendaColecao(long idPrevisaoVendas, int colecao) {
		return previsaoVendasCustom.findPrevisoesVendasItensByIdPrevisaoVendaColecao(idPrevisaoVendas, colecao);
	}

	public String findIdTabelaSellIn(int colecao) {
		return previsaoVendasCustom.findIdTabelaSellIn(colecao);
	}

	public String findIdTabelaSellOut(int colecao) {
		return previsaoVendasCustom.findIdTabelaSellOut(colecao);
	}

	public PrevisaoVendas savePrevisaoVendas(long idPrevisaoVendas, String descricao, int colecao, int colTabPrecoSellIn,
			int mesTabPrecoSellIn, int seqTabPrecoSellIn, int colTabPrecoSellOut, int mesTabPrecoSellOut,
			int seqTabPrecoSellOut, List<ConsultaPrevisaoVendasItens> previsoesVenda) {

		PrevisaoVendas previsaoVendas = previsaoVendasRepository.findById(idPrevisaoVendas);

		if (previsaoVendas == null) {
			previsaoVendas = new PrevisaoVendas(idPrevisaoVendas, descricao, colecao, colTabPrecoSellIn,
					mesTabPrecoSellIn, seqTabPrecoSellIn, colTabPrecoSellOut, mesTabPrecoSellOut, seqTabPrecoSellOut);
		} else {
			previsaoVendas.descricao = descricao;
			previsaoVendas.colecao = colecao;
			previsaoVendas.colTabPrecoSellIn = colTabPrecoSellIn; 
			previsaoVendas.mesTabPrecoSellIn = mesTabPrecoSellIn; 
			previsaoVendas.seqTabPrecoSellIn = seqTabPrecoSellIn; 
			previsaoVendas.colTabPrecoSellOut = colTabPrecoSellOut; 
			previsaoVendas.mesTabPrecoSellOut = mesTabPrecoSellOut; 
			previsaoVendas.seqTabPrecoSellOut = seqTabPrecoSellOut;
		}

		previsaoVendasRepository.save(previsaoVendas);
		idPrevisaoVendas = previsaoVendas.id;
		
		double valorSellIn = 0.000;
		double valorSellOut = 0.000;
		
		for (ConsultaPrevisaoVendasItens previsao : previsoesVenda) {

			valorSellIn = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellIn, mesTabPrecoSellIn, seqTabPrecoSellIn,
					previsao.grupo, previsao.item);
			valorSellOut = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellOut, mesTabPrecoSellOut,
					seqTabPrecoSellOut, previsao.grupo, previsao.item);

			ProdutoReferCor item = produtoCustom.findItemByCodigo(previsao.grupoBase, previsao.itemBase);
			
			if (item == null) {				
				previsao.grupoBase = ""; 
				previsao.itemBase = "";
				previsao.qtdeVendidaBase = 0;
			} else {
				previsao.qtdeVendidaBase = previsaoVendasCustom.findQtdeVendidaByItem(previsao.grupoBase, previsao.itemBase);
				if (previsao.percentualAplicar > 0) previsao.qtdePrevisao = (previsao.qtdeVendidaBase * previsao.percentualAplicar) / 100;  				
			}
						
			PrevisaoVendasItens previsaoVendasItens = new PrevisaoVendasItens(idPrevisaoVendas, previsao.grupo, previsao.item, valorSellIn, valorSellOut, previsao.grupoBase, previsao.itemBase, previsao.qtdeVendidaBase, (double) previsao.percentualAplicar, previsao.qtdePrevisao);
			previsaoVendasItensRepository.save(previsaoVendasItens);
			
		}
		
		return previsaoVendas;
	}	
}
