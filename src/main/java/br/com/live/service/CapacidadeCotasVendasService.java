package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CapacidadeCotasVendasCustom;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.entity.CapacidadeCotasVendasCapa;
import br.com.live.entity.CapacidadeCotasVendasItens;
import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.Deposito;
import br.com.live.model.ProdutosCapacidadeProd;
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

	public CapacidadeCotasVendasService(CapacidadeCotasVendasCustom capacidadeCotasVendasCustom,
			CapacidadeCotasVendasRepository capacidadeCotasVendasRepository,
			CapacidadeCotasVendasItensRepository capacidadeCotasVendasItensRepository,
			EstoqueProdutoCustom estoqueProdutoCustom, DemandaProdutoCustom demandaProdutoCustom,
			ProcessoProdutoCustom processoProdutoCustom) {
		this.capacidadeCotasVendasCustom = capacidadeCotasVendasCustom;
		this.capacidadeCotasVendasRepository = capacidadeCotasVendasRepository;
		this.capacidadeCotasVendasItensRepository = capacidadeCotasVendasItensRepository;
		this.estoqueProdutoCustom = estoqueProdutoCustom;
		this.demandaProdutoCustom = demandaProdutoCustom;
		this.processoProdutoCustom = processoProdutoCustom;
	}

	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {		
		List<CapacidadesCotasVendas> cotas = capacidadeCotasVendasCustom.findAllCapacidadesCotasVendas();		
		for (CapacidadesCotasVendas cota : cotas) {
			cota.setListaDepositos(estoqueProdutoCustom.findDepositosByCodigos(cota.getDepositos()));
		}		
		return cotas;
	}

	public List<Categoria> findCategoriasProd() {
		return capacidadeCotasVendasCustom.findCategoriasProd();
	}

	public List<ProdutosCapacidadeProd> findProdutosByCategoriaLinha(int colecao, int linha, int periodo, boolean listarTempUnit) {		
		List<ProdutosCapacidadeProd> itens = capacidadeCotasVendasCustom.findProdutosByCategoriaLinha(colecao, linha, periodo, listarTempUnit);
		atualizarTempoUnit(colecao, itens);
		return itens;
	}
	
	public void deleteById(String idCapacidadeCotas) {
		capacidadeCotasVendasRepository.deleteById(idCapacidadeCotas);
		capacidadeCotasVendasItensRepository.deleteByIdCapa(idCapacidadeCotas);
	}

	public void saveCapacidadeCotasVendas(int periodo, int colecao, int linha, int minDistribuir, int periodoInicial, int periodoFinal, String depositos, List<ProdutosCapacidadeProd> itens) {
		
		boolean distribuirMinutosCapac = true;		
		CapacidadeCotasVendasCapa dadosCapacidadeCapa = capacidadeCotasVendasRepository.findByPeriodoColecaoLinha(periodo, colecao, linha);

		// EDIÇÃO
		if (dadosCapacidadeCapa != null) {
			if (dadosCapacidadeCapa.minDistribuir == minDistribuir) distribuirMinutosCapac = false;						
			dadosCapacidadeCapa.minDistribuir = minDistribuir;
			dadosCapacidadeCapa.periodoInicial = periodoInicial;
			dadosCapacidadeCapa.periodoFinal = periodoFinal;
			dadosCapacidadeCapa.depositos = depositos;
		// INSERÇÃO
		} else {					
			dadosCapacidadeCapa = new CapacidadeCotasVendasCapa(periodo, linha, colecao, minDistribuir, periodoInicial, periodoFinal, depositos);
		}

		capacidadeCotasVendasRepository.save(dadosCapacidadeCapa);
		atualizarDadosItens(colecao, periodoInicial, periodoFinal, depositos, itens);
		if (distribuirMinutosCapac) distribuirMinutos(colecao, minDistribuir, itens);
		
		saveItens(dadosCapacidadeCapa.id, itens);
	}

	private void saveItens(String idCapacidade, List<ProdutosCapacidadeProd> itens) {
		capacidadeCotasVendasItensRepository.deleteByIdCapa(idCapacidade);		
		for (ProdutosCapacidadeProd item : itens) {
			CapacidadeCotasVendasItens capacidadeCotasItens = new CapacidadeCotasVendasItens(idCapacidade, item.getReferencia(), item.getTamanho(), item.getCor(), item.getTempoUnitario(), item.getQtdeEstoque(), item.getQtdeDemanda(), item.getQtdeProcesso(), item.getQtdeMinutos(), item.getQtdePecas(), item.getBloqueioVenda());
			capacidadeCotasVendasItensRepository.save(capacidadeCotasItens);
		}
	}
	
	private void atualizarTempoUnit(int colecao, List<ProdutosCapacidadeProd> itens) {
		for (ProdutosCapacidadeProd item : itens) {
			item.setTempoUnitario(capacidadeCotasVendasCustom.findTempoUnitarioByReferenciaColecao(item.getReferencia(), colecao));
		}
	}
	
	private void atualizarDadosItens(int colecao, int periodoInicial, int periodoFinal, String depositos, List<ProdutosCapacidadeProd> itens) { 
		for (ProdutosCapacidadeProd item : itens) {
			item.setTempoUnitario(capacidadeCotasVendasCustom.findTempoUnitarioByReferenciaColecao(item.getReferencia(), colecao));
			item.setQtdeEstoque(estoqueProdutoCustom.findQtdeEstoqueByProdutoAndDepositos("1", item.getReferencia(), item.getTamanho(), item.getCor(), depositos));
			item.setQtdeDemanda(demandaProdutoCustom.findQtdeDemandaByProdutoAndPeriodos("1", item.getReferencia(), item.getTamanho(), item.getCor(), periodoInicial, periodoFinal));
			item.setQtdeProcesso(processoProdutoCustom.findQtdeProcessoByProdutoAndPeriodos("1", item.getReferencia(), item.getTamanho(), item.getCor(), ConvertePeriodo.parse(periodoInicial,500), ConvertePeriodo.parse(periodoFinal,500)));
			item.setQtdeSaldo(item.getQtdeEstoque() + item.getQtdeProcesso() - item.getQtdeDemanda());			
		}
	}	
	
	private void distribuirMinutos(int colecao, int minDistribuir, List<ProdutosCapacidadeProd> itens) {
		
		int qtdePecas;
		float minutosUnitario;
		float minutosPadrao = 0;
		int qtdeItensTempoUnitario;
		
		qtdeItensTempoUnitario = getQtdeItensComTempoUnitario(itens);
		
		if (qtdeItensTempoUnitario > 0) minutosPadrao = (float) ((float) minDistribuir / (float) qtdeItensTempoUnitario);
		
		for (ProdutosCapacidadeProd item : itens) {			
			qtdePecas = 0;
			minutosUnitario = item.getTempoUnitario();									
			if (minutosUnitario > 0.0000) qtdePecas = (int) (minutosPadrao / minutosUnitario);							
			item.setQtdePecas(qtdePecas);
			if (minutosUnitario > 0) item.setQtdeMinutos(minutosPadrao);			
		}	
	}
	
	private int getQtdeItensComTempoUnitario(List<ProdutosCapacidadeProd> itens) {
		int qtde = 0;		
		for (ProdutosCapacidadeProd item : itens) {			
			if (item.getTempoUnitario() > 0) qtde++;			
		}		
		return qtde;
	}
}
