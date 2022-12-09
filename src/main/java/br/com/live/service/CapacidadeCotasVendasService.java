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

	public BodyCapacidadeCotasVendas findItens(int periodoProgInicial,int periodoProgFinal, int periodoAnaliseInicial, int periodoAnaliseFinal, String colecoes, String depositos) {
		System.out.println("Localizar cotas vendas por tipo cliente (análise)...");
		List<CapacidadeCotasVendasTipoCliente> tiposClientesAnalise = capacidadeCotasVendasCustom.findDadosPorTipoCliente(periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos);		
		System.out.println("Localizar cotas vendas por tipo cliente (programado)...");
		List<CapacidadeCotasVendasTipoCliente> tiposClientesProg = capacidadeCotasVendasCustom.findDadosPorTipoCliente(periodoAnaliseInicial, periodoAnaliseFinal, periodoProgInicial, periodoProgFinal, colecoes, depositos);				
		System.out.println("Localizar cotas vendas por itens...");
		List<CapacidadeCotasVendas> itens = capacidadeCotasVendasCustom.findItensByFiltros(periodoAnaliseInicial, periodoAnaliseFinal, periodoProgInicial, periodoProgFinal, colecoes, depositos);
		
		int tamArrayAnalise = tiposClientesAnalise.size();
		tamArrayAnalise++;

		int tamArrayProg = tiposClientesProg.size();
		tamArrayProg++;
		
		Object[] arrayValoresAnalise = new Object[tamArrayAnalise];
		Object[] arrayPecasAnalise = new Object[tamArrayAnalise];
		Object[] arrayMinutosAnalise = new Object[tamArrayAnalise];
		Object[] arrayValoresProg = new Object[tamArrayProg];
		Object[] arrayPecasProg = new Object[tamArrayProg];
		Object[] arrayMinutosProg = new Object[tamArrayProg];
		
		int indice = 0;
		Object[] cabVal = {"CANAL", "VALORES"};
		Object[] cabPec = {"CANAL", "PECAS"};
		Object[] cabMin = {"CANAL", "MINUTOS"};
		
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
		
		indice = 0;
		arrayValoresProg [indice] = cabVal;		
		arrayPecasProg [indice] = cabPec;		
		arrayMinutosProg [indice] = cabMin;

		for (CapacidadeCotasVendasTipoCliente tipo : tiposClientesProg) {
			indice++;
			Object[] valores = {tipo.getDescricaoTipo(), tipo.getValorLiqTotal()};   
			Object[] pecas = {tipo.getDescricaoTipo(), tipo.getQtdePecas()};
			Object[] minutos = {tipo.getDescricaoTipo(), tipo.getTempo()};
			arrayValoresProg[indice] = valores;
			arrayPecasProg[indice] = pecas;
			arrayMinutosProg[indice] = minutos;
		}		
		
		System.out.println("Fim");
		return new BodyCapacidadeCotasVendas(itens, arrayValoresAnalise, arrayPecasAnalise, arrayMinutosAnalise, arrayValoresProg, arrayPecasProg, arrayMinutosProg, tiposClientesAnalise, tiposClientesProg);			
	}
}
