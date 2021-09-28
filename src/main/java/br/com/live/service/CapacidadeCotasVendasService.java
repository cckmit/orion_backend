package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CapacidadeCotasVendasCustom;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.CapacidadeCotasVendasCapa;
import br.com.live.entity.CapacidadeCotasVendasItens;
import br.com.live.model.CapacidadeCotasVendasCapaItens;
import br.com.live.model.CapacidadeCotasVendasDadosItem;
import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.repository.CapacidadeCotasVendasItensRepository;
import br.com.live.repository.CapacidadeCotasVendasRepository;
import br.com.live.util.ConvertePeriodo;

@Service
@Transactional
public class CapacidadeCotasVendasService {

	private final CapacidadeCotasVendasCustom capacidadeCotasVendasCustom;
	private final CapacidadeCotasVendasRepository capacidadeCotasVendasRepository;
	private final CapacidadeCotasVendasItensRepository capacidadeCotasVendasItensRepository;
	private final EstoqueProdutoCustom estoqueProdutoCustom;
	private final DemandaProdutoCustom demandaProdutoCustom;
	private final ProcessoProdutoCustom processoProdutoCustom;
	private final ProdutoCustom produtoCustom;

	public CapacidadeCotasVendasService(CapacidadeCotasVendasCustom capacidadeCotasVendasCustom,
			CapacidadeCotasVendasRepository capacidadeCotasVendasRepository,
			CapacidadeCotasVendasItensRepository capacidadeCotasVendasItensRepository,
			EstoqueProdutoCustom estoqueProdutoCustom, DemandaProdutoCustom demandaProdutoCustom,
			ProcessoProdutoCustom processoProdutoCustom, ProdutoCustom produtoCustom) {
		this.capacidadeCotasVendasCustom = capacidadeCotasVendasCustom;
		this.capacidadeCotasVendasRepository = capacidadeCotasVendasRepository;
		this.capacidadeCotasVendasItensRepository = capacidadeCotasVendasItensRepository;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.demandaProdutoCustom = demandaProdutoCustom;
		this.processoProdutoCustom = processoProdutoCustom;
		this.produtoCustom = produtoCustom;
	}

	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {
		List<CapacidadesCotasVendas> cotas = capacidadeCotasVendasCustom.findAllCapacidadesCotasVendas();
		for (CapacidadesCotasVendas cota : cotas) {
			cota.setListaColecoes(produtoCustom.findColecoesByCodigos(cota.getColecoes()));
			cota.setListaDepositos(estoqueProdutoCustom.findDepositosByCodigos(cota.getDepositos()));
		}
		return cotas;
	}

	public List<Categoria> findCategoriasProd() {
		return capacidadeCotasVendasCustom.findCategoriasProd();
	}

	public List<CapacidadeCotasVendasDadosItem> findItensByFiltros(long idCapacidadeCotas, int periodoAtualInicial,
			int periodoAtualFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes,
			String depositos, boolean listarTempUnit) {
		List<CapacidadeCotasVendasDadosItem> itens = capacidadeCotasVendasCustom.findProdutosByCategoriaLinha(idCapacidadeCotas,
				colecoes);
		itens = atualizarDadosItens(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal,
				colecoes, depositos, listarTempUnit, itens);
		return itens;
	}

	public void deleteById(long idCapacidadeCotas) {
		capacidadeCotasVendasRepository.deleteById(idCapacidadeCotas);
		capacidadeCotasVendasItensRepository.deleteByIdCapacidadeCota(idCapacidadeCotas);
	}

	public CapacidadeCotasVendasCapaItens saveCapacidadeCotasVendas(long idCapacidadeCotas, String descricao, int periodoAtualInicial, int periodoAtualFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, int minutosPeriodo, String colecoes, String depositos, List<CapacidadeCotasVendasDadosItem> itens) {
		CapacidadeCotasVendasCapa dadosCapacidadeCapa = capacidadeCotasVendasRepository.findById(idCapacidadeCotas);
		// EDIÇÃO
		if (dadosCapacidadeCapa != null) {			
			dadosCapacidadeCapa.descricao = descricao;
			dadosCapacidadeCapa.periodoAtualInicio = periodoAtualInicial;
			dadosCapacidadeCapa.periodoAtualFinal = periodoAtualFinal;
			dadosCapacidadeCapa.periodoAnaliseInicio = periodoAnaliseInicial;
			dadosCapacidadeCapa.periodoAnaliseFinal = periodoAnaliseFinal;
			dadosCapacidadeCapa.minutosPeriodo = minutosPeriodo;			
			dadosCapacidadeCapa.colecoes = colecoes;
			dadosCapacidadeCapa.depositos = depositos;
		// INSERÇÃO
		} else {					
			long id = capacidadeCotasVendasCustom.findNextIdCapacidadeCotas();
			dadosCapacidadeCapa = new CapacidadeCotasVendasCapa(id, descricao, periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos, minutosPeriodo);
		}
		capacidadeCotasVendasRepository.save(dadosCapacidadeCapa);
		saveItens(dadosCapacidadeCapa.id, itens);		
				
		return new CapacidadeCotasVendasCapaItens(dadosCapacidadeCapa, itens);
	}

	private void saveItens(long idCapacidade, List<CapacidadeCotasVendasDadosItem> itens) {
		capacidadeCotasVendasItensRepository.deleteByIdCapacidadeCota(idCapacidade);
		for (CapacidadeCotasVendasDadosItem item : itens) {
			CapacidadeCotasVendasItens capacidadeCotasItens = new CapacidadeCotasVendasItens(idCapacidade,
					item.getReferencia(), item.getTamanho(), item.getCor(), item.getBloqueioVenda());
			capacidadeCotasVendasItensRepository.save(capacidadeCotasItens);
		}
	}

	private List<CapacidadeCotasVendasDadosItem> atualizarDadosItens(int periodoAtualInicial, int periodoAtualFinal,
			int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos,
			boolean listarTempUnit, List<CapacidadeCotasVendasDadosItem> itens) {
		List<CapacidadeCotasVendasDadosItem> novaLista = new ArrayList<CapacidadeCotasVendasDadosItem>();
		
		
		float tempoUnitario = 0;
		
		System.out.println("ENTROU");
		
		for (CapacidadeCotasVendasDadosItem item : itens) {
			tempoUnitario = capacidadeCotasVendasCustom.findTempoUnitarioByReferenciaColecao(item.getReferencia(),
					colecoes);
			if ((listarTempUnit) && (tempoUnitario == 0.0000))
				continue;
			item.setTempoUnitario(tempoUnitario);
			item.setQtdeEstoque(estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos("1", item.getReferencia(),
					item.getTamanho(), item.getCor(), depositos));
			item.setQtdeDemandaAtual(demandaProdutoCustom.findQtdeDemandaByProdutoAndPeriodos("1", item.getReferencia(),
					item.getTamanho(), item.getCor(), periodoAtualInicial, periodoAnaliseFinal, 0, 0));
			item.setQtdeDemanda(demandaProdutoCustom.findQtdeDemandaByProdutoAndPeriodos("1", item.getReferencia(),
					item.getTamanho(), item.getCor(), periodoAnaliseInicial, periodoAnaliseFinal, periodoAtualInicial,
					periodoAnaliseFinal));
			item.setQtdeProcesso(processoProdutoCustom.findQtdeProcessoByProdutoAndPeriodos("1", item.getReferencia(),
					item.getTamanho(), item.getCor(), ConvertePeriodo.parse(periodoAtualInicial, 500),
					ConvertePeriodo.parse(periodoAtualFinal, 500)));
			item.setQtdeSaldo(item.getQtdeEstoque() + item.getQtdeProcesso() - item.getQtdeDemanda()
					- item.getQtdeDemandaAtual());
			item.setQtdeMinutosDemandaAtual(item.getQtdeDemandaAtual() * item.getTempoUnitario());
			
			novaLista.add(item);
		}
		
		System.out.println("SAIU");
		
		return novaLista;
	}
}
