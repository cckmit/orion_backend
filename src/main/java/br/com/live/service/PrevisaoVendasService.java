package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PrevisaoVendasCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.entity.PrevisaoVendas;
import br.com.live.entity.PrevisaoVendasItem;
import br.com.live.entity.PrevisaoVendasItemTam;
import br.com.live.model.ConsultaPrevisaoVendas;
import br.com.live.model.ConsultaPrevisaoVendasItem;
import br.com.live.model.ConsultaPrevisaoVendasItemTam;
import br.com.live.repository.PrevisaoVendasItemRepository;
import br.com.live.repository.PrevisaoVendasItemTamRepository;
import br.com.live.repository.PrevisaoVendasRepository;

@Service
@Transactional
public class PrevisaoVendasService {

	private final PrevisaoVendasCustom previsaoVendasCustom;
	private final PrevisaoVendasRepository previsaoVendasRepository;
	private final PrevisaoVendasItemRepository previsaoVendasItemRepository;
	private final PrevisaoVendasItemTamRepository previsaoVendasItemTamRepository;
	private final TabelaPrecoCustom tabelaPrecoCustom;
	private final ProdutoCustom produtoCustom;

	public PrevisaoVendasService(PrevisaoVendasCustom previsaoVendasCustom,
			PrevisaoVendasRepository previsaoVendasRepository,
			PrevisaoVendasItemRepository previsaoVendasItemRepository, TabelaPrecoCustom tabelaPrecoCustom,
			ProdutoCustom produtoCustom, PrevisaoVendasItemTamRepository previsaoVendasItemTamRepository) {
		this.previsaoVendasCustom = previsaoVendasCustom;
		this.previsaoVendasRepository = previsaoVendasRepository;
		this.previsaoVendasItemRepository = previsaoVendasItemRepository;
		this.tabelaPrecoCustom = tabelaPrecoCustom;
		this.produtoCustom = produtoCustom;
		this.previsaoVendasItemTamRepository = previsaoVendasItemTamRepository;
	}

	public List<ConsultaPrevisaoVendas> findPrevisoesVendas() {
		return previsaoVendasCustom.findPrevisoesVendas();
	}

	public PrevisaoVendas findPrevisaoVendas(long idPrevisaoVendas) {
		return previsaoVendasRepository.findById(idPrevisaoVendas);
	}

	public List<ConsultaPrevisaoVendasItem> findPrevisaoVendasItensByIdPrevisaoVendaColecao(long idPrevisaoVendas,
			int colecao) {
		return previsaoVendasCustom.findPrevisaoVendasItensByIdPrevisaoVendaColecao(idPrevisaoVendas, colecao);
	}

	public List<ConsultaPrevisaoVendasItemTam> findPrevisaoVendasItemTamByIdPrevisaoVendasGrupoItem(
			long idPrevisaoVendas, String grupo, String item) {
		return previsaoVendasCustom.findPrevisaoVendasItemTamByIdPrevisaoVendasGrupoItem(idPrevisaoVendas, grupo, item);
	}

	public PrevisaoVendas savePrevisaoVendas(long idPrevisaoVendas, String descricao, int colecao,
			int colTabPrecoSellIn, int mesTabPrecoSellIn, int seqTabPrecoSellIn, int colTabPrecoSellOut,
			int mesTabPrecoSellOut, int seqTabPrecoSellOut, List<ConsultaPrevisaoVendasItem> previsoesVenda) {

		PrevisaoVendas previsaoVendas = null;

		// EDIÇÃO
		if (idPrevisaoVendas > 0) {
			previsaoVendas = previsaoVendasRepository.findById(idPrevisaoVendas);

			// Quando alterado a colecao, os itens são excluídos para a inclusão dos itens
			// da nova coleção
			if (previsaoVendas.colecao != colecao)
				previsaoVendasItemRepository.deleteByIdPrevisaoVendas(idPrevisaoVendas);

			previsaoVendas.descricao = descricao.toUpperCase();
			previsaoVendas.colecao = colecao;
			previsaoVendas.colTabPrecoSellIn = colTabPrecoSellIn;
			previsaoVendas.mesTabPrecoSellIn = mesTabPrecoSellIn;
			previsaoVendas.seqTabPrecoSellIn = seqTabPrecoSellIn;
			previsaoVendas.colTabPrecoSellOut = colTabPrecoSellOut;
			previsaoVendas.mesTabPrecoSellOut = mesTabPrecoSellOut;
			previsaoVendas.seqTabPrecoSellOut = seqTabPrecoSellOut;
		} else {
			// INSERÇÃO
			idPrevisaoVendas = previsaoVendasCustom.findNextIdPrevisaoVendas();

			previsaoVendas = new PrevisaoVendas(idPrevisaoVendas, descricao, colecao, colTabPrecoSellIn,
					mesTabPrecoSellIn, seqTabPrecoSellIn, colTabPrecoSellOut, mesTabPrecoSellOut, seqTabPrecoSellOut);
		}

		previsaoVendasRepository.save(previsaoVendas);

		double valorSellIn = 0.000;
		double valorSellOut = 0.000;

		for (ConsultaPrevisaoVendasItem previsao : previsoesVenda) {
			valorSellIn = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellIn, mesTabPrecoSellIn, seqTabPrecoSellIn,
					previsao.grupo, previsao.item);
			valorSellOut = tabelaPrecoCustom.findPrecoProduto(colTabPrecoSellOut, mesTabPrecoSellOut,
					seqTabPrecoSellOut, previsao.grupo, previsao.item);

			if (!produtoCustom.existsItem(previsao.getGrupoBase(), previsao.getItemBase())) {
				previsao.setGrupoBase("");
				previsao.setItemBase("");
				previsao.setQtdeVendidaBase(0);
			} else {
				previsao.setQtdeVendidaBase(
						previsaoVendasCustom.findQtdeVendidaByItem(previsao.grupoBase, previsao.itemBase));
				if (previsao.getPercAplicar() > 0)
					previsao.setQtdePrevisao((previsao.qtdeVendidaBase * previsao.percAplicar) / 100);
			}

			PrevisaoVendasItem previsaoVendasItens = new PrevisaoVendasItem(idPrevisaoVendas, previsao.grupo,
					previsao.item, valorSellIn, valorSellOut, previsao.grupoBase, previsao.itemBase,
					previsao.qtdeVendidaBase, (double) previsao.percAplicar, previsao.qtdePrevisao);
			previsaoVendasItemRepository.save(previsaoVendasItens);
		}

		return previsaoVendas;
	}

	public void saveTamanhosPrevisaoVendas(long idPrevisaoVendas, String grupo, String item,
			List<ConsultaPrevisaoVendasItemTam> tamanhosPrevisao) {

		for (ConsultaPrevisaoVendasItemTam tamanhoPrevisao : tamanhosPrevisao) {
			PrevisaoVendasItemTam tamanho = previsaoVendasItemTamRepository
					.findByIdPrevisaoVendasGrupoItemSub(idPrevisaoVendas, grupo, item, tamanhoPrevisao.sub);

			if (tamanho == null)
				tamanho = new PrevisaoVendasItemTam(idPrevisaoVendas, tamanhoPrevisao.idItemPrevisaoVendas, grupo, item,
						tamanhoPrevisao.sub, tamanhoPrevisao.qtdePrevisao);
			else {
				tamanho.qtdePrevisao = tamanhoPrevisao.qtdePrevisao;
			}
			
			previsaoVendasItemTamRepository.save(tamanho);
		}
	}

	public void deletePrevisaoVendas(long idPrevisaoVendas) {		
		previsaoVendasItemTamRepository.deleteByIdPrevisaoVendas(idPrevisaoVendas);
		previsaoVendasItemRepository.deleteByIdPrevisaoVendas(idPrevisaoVendas);
		previsaoVendasRepository.deleteById(idPrevisaoVendas);
	}
	
}
