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
		List<CapacidadeCotasVendasTipoCliente> tiposClientes = capacidadeCotasVendasCustom.findDadosPorTipoCliente(periodoAtualInicial, periodoAtualFinal, colecoes);
		List<CapacidadeCotasVendas> itens = capacidadeCotasVendasCustom.findItensByFiltros(periodoAtualInicial, periodoAtualFinal, periodoAnaliseInicial, periodoAnaliseFinal, colecoes, depositos);
		
		int tamArray = tiposClientes.size();
		tamArray++;
		
		Object[] arrayValores = new Object[tamArray];
		Object[] arrayPecas = new Object[tamArray];
		Object[] arrayMinutos = new Object[tamArray];
		
		int indice = 0;
		Object[] cabVal = {"CANAL", "VALORES"};
		arrayValores [indice] = cabVal;
		Object[] cabPec = {"CANAL", "PECAS"};
		arrayPecas [indice] = cabPec;
		Object[] cabMin = {"CANAL", "MINUTOS"};
		arrayMinutos [indice] = cabMin;

		for (CapacidadeCotasVendasTipoCliente tipo : tiposClientes) {
			indice++;
			Object[] valores = {tipo.getDescricaoTipo(), tipo.getValorLiqTotal()};   
			Object[] pecas = {tipo.getDescricaoTipo(), tipo.getQtdePecas()};
			Object[] minutos = {tipo.getDescricaoTipo(), tipo.getTempo()};
			arrayValores[indice] = valores;
			arrayPecas[indice] = pecas;
			arrayMinutos[indice] = minutos;
		}		
		
		return new BodyCapacidadeCotasVendas(itens, arrayValores, arrayPecas, arrayMinutos);		
	}
}
