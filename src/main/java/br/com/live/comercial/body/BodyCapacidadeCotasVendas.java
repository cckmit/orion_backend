package br.com.live.comercial.body;

import java.util.List;

import br.com.live.comercial.model.CapacidadeCotasVendas;
import br.com.live.comercial.model.CapacidadeCotasVendasTipoCliente;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyCapacidadeCotasVendas {	
	public int periodoProgInicio;
	public int periodoProgFinal;
	public int periodoAnaliseInicio;
	public int periodoAnaliseFinal;	
	public List<ConteudoChaveNumerica> colecoes;
	public List<ConteudoChaveNumerica> depositos;
	// Variaveis de retorno
	public List<CapacidadeCotasVendas> listaItens;
	public Object[] arrayValoresAnalise;
	public Object[] arrayPecasAnalise;
	public Object[] arrayMinutosAnalise;
	public Object[] arrayValoresProg;
	public Object[] arrayPecasProg;
	public Object[] arrayMinutosProg;
	
	public List<CapacidadeCotasVendasTipoCliente> listaCapacPorTipoClienteAnalise;
	public List<CapacidadeCotasVendasTipoCliente> listaCapacPorTipoClienteProg;
	
	public BodyCapacidadeCotasVendas(List<CapacidadeCotasVendas> listaItens, Object[] arrayValoresAnalise, Object[] arrayPecasAnalise, Object[] arrayMinutosAnalise, Object[] arrayValoresProg, Object[] arrayPecasProg, Object[] arrayMinutosProg, List<CapacidadeCotasVendasTipoCliente> listaCapacPorTipoClienteAnalise, List<CapacidadeCotasVendasTipoCliente> listaCapacPorTipoClienteProg) {
		super();
		this.listaItens = listaItens;
		this.arrayValoresAnalise = arrayValoresAnalise;
		this.arrayPecasAnalise = arrayPecasAnalise;
		this.arrayMinutosAnalise = arrayMinutosAnalise;
		this.arrayValoresProg = arrayValoresProg;
		this.arrayPecasProg = arrayPecasProg;
		this.arrayMinutosProg = arrayMinutosProg;
		this.listaCapacPorTipoClienteAnalise = listaCapacPorTipoClienteAnalise; 
		this.listaCapacPorTipoClienteProg = listaCapacPorTipoClienteProg;

	}
}