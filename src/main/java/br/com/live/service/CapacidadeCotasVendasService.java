package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CapacidadeCotasVendasCustom;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.model.CapacidadeCotasVendas;

@Service
@Transactional
public class CapacidadeCotasVendasService {

	private final CapacidadeCotasVendasCustom capacidadeCotasVendasCustom;

	public CapacidadeCotasVendasService(CapacidadeCotasVendasCustom capacidadeCotasVendasCustom,
			EstoqueProdutoCustom estoqueProdutoCustom, DemandaProdutoCustom demandaProdutoCustom,
			ProcessoProdutoCustom processoProdutoCustom, ProdutoCustom produtoCustom) {
		this.capacidadeCotasVendasCustom = capacidadeCotasVendasCustom;
	}

	public List<CapacidadeCotasVendas> findItens(int periodoAtualInicial,int periodoAtualFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos) {
		List<CapacidadeCotasVendas> itens = capacidadeCotasVendasCustom.findItensByFiltros(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos);
		return atualizarDadosItens(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos, itens);		
	}

	private List<CapacidadeCotasVendas> atualizarDadosItens(int periodoAtualInicial, int periodoAtualFinal,
			int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos, List<CapacidadeCotasVendas> itens) {		
		for (CapacidadeCotasVendas item : itens) {						
			item.setTempoUnitario(capacidadeCotasVendasCustom.findTempoUnitarioByReferenciaColecao(item.getReferencia(),colecoes));			
			item.setQtdeSaldo(item.getQtdeEstoque() + item.getQtdeProcesso() - item.getQtdeDemanda());
			item.setQtdeMinutosDemandaAtual(item.getQtdeDemandaAtual() * item.getTempoUnitario());									
		}
		return itens;
	}
}
