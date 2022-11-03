package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyCapacidadeCotasVendas;
import br.com.live.custom.CapacidadeCotasVendasCustom;
import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.custom.EstoqueProdutoCustom;
import br.com.live.custom.ProcessoProdutoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.model.CapacidadeCotasVendas;
import br.com.live.model.CapacidadeCotasVendasTipoCliente;

@Service
@Transactional
public class CapacidadeCotasVendasService {

	private final CapacidadeCotasVendasCustom capacidadeCotasVendasCustom;

	public CapacidadeCotasVendasService(CapacidadeCotasVendasCustom capacidadeCotasVendasCustom,
			EstoqueProdutoCustom estoqueProdutoCustom, DemandaProdutoCustom demandaProdutoCustom,
			ProcessoProdutoCustom processoProdutoCustom, ProdutoCustom produtoCustom) {
		this.capacidadeCotasVendasCustom = capacidadeCotasVendasCustom;
	}

	public BodyCapacidadeCotasVendas findItens(int periodoAtualInicial,int periodoAtualFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos) {
		System.out.println("Localizar cotas vendas por tipo cliente (periodo atual)...");
		List<CapacidadeCotasVendasTipoCliente> tiposClientesAtual = capacidadeCotasVendasCustom.findDadosPorTipoCliente(periodoAtualInicial, periodoAtualFinal, colecoes);
		System.out.println("Localizar cotas vendas por tipo cliente (periodo análise)...");
		List<CapacidadeCotasVendasTipoCliente> tiposClientesAnalise = capacidadeCotasVendasCustom.findDadosPorTipoCliente(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes);		
		System.out.println("Localizar cotas vendas por itens...");
		List<CapacidadeCotasVendas> itens = capacidadeCotasVendasCustom.findItensByFiltros(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos);
		
		int tamArrayAtual = tiposClientesAtual.size();
		tamArrayAtual++;

		int tamArrayAnalise = tiposClientesAnalise.size();
		tamArrayAnalise++;
		
		Object[] arrayValoresAtual = new Object[tamArrayAtual];
		Object[] arrayPecasAtual = new Object[tamArrayAtual];
		Object[] arrayMinutosAtual = new Object[tamArrayAtual];
		Object[] arrayValoresAnalise = new Object[tamArrayAnalise];
		Object[] arrayPecasAnalise = new Object[tamArrayAnalise];
		Object[] arrayMinutosAnalise = new Object[tamArrayAnalise];
		
		int indice = 0;
		Object[] cabVal = {"CANAL", "VALORES"};
		Object[] cabPec = {"CANAL", "PECAS"};
		Object[] cabMin = {"CANAL", "MINUTOS"};
		
		arrayValoresAtual [indice] = cabVal;		
		arrayPecasAtual [indice] = cabPec;		
		arrayMinutosAtual [indice] = cabMin;

		for (CapacidadeCotasVendasTipoCliente tipo : tiposClientesAtual) {
			indice++;
			Object[] valores = {tipo.getDescricaoTipo(), tipo.getValorLiqTotal()};   
			Object[] pecas = {tipo.getDescricaoTipo(), tipo.getQtdePecas()};
			Object[] minutos = {tipo.getDescricaoTipo(), tipo.getTempo()};
			arrayValoresAtual[indice] = valores;
			arrayPecasAtual[indice] = pecas;
			arrayMinutosAtual[indice] = minutos;
		}		
		
		indice = 0;
		arrayValoresAnalise [indice] = cabVal;		
		arrayPecasAnalise [indice] = cabPec;		
		arrayMinutosAnalise [indice] = cabMin;

		for (CapacidadeCotasVendasTipoCliente tipo : tiposClientesAnalise) {
			indice++;
			Object[] valores = {tipo.getDescricaoTipo(), tipo.getValorLiqTotal()};   
			Object[] pecas = {tipo.getDescricaoTipo(), tipo.getQtdePecas()};
			Object[] minutos = {tipo.getDescricaoTipo(), tipo.getTempo()};
			arrayValoresAnalise[indice] = valores;
			arrayPecasAnalise[indice] = pecas;
			arrayMinutosAnalise[indice] = minutos;
		}		
		
		return new BodyCapacidadeCotasVendas(itens, arrayValoresAtual, arrayPecasAtual, arrayMinutosAtual, arrayValoresAnalise, arrayPecasAnalise, arrayMinutosAnalise);		
	}
}
