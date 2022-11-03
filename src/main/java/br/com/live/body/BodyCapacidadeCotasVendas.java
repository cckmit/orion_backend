package br.com.live.body;

import java.util.List;

import br.com.live.model.CapacidadeCotasVendas;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyCapacidadeCotasVendas {	
	public int periodoAtualInicio;
	public int periodoAtualFinal;
	public int periodoAnaliseInicio;
	public int periodoAnaliseFinal;	
	public List<ConteudoChaveNumerica> colecoes;
	public List<ConteudoChaveNumerica> depositos;
	// Variaveis de retorno
	public List<CapacidadeCotasVendas> listaItens;
	public Object[] arrayValoresAtual;
	public Object[] arrayPecasAtual;
	public Object[] arrayMinutosAtual;
	public Object[] arrayValoresAnalise;
	public Object[] arrayPecasAnalise;
	public Object[] arrayMinutosAnalise;
	
	public BodyCapacidadeCotasVendas(List<CapacidadeCotasVendas> listaItens, Object[] arrayValoresAtual, Object[] arrayPecasAtual, Object[] arrayMinutosAtual, Object[] arrayValoresAnalise, Object[] arrayPecasAnalise, Object[] arrayMinutosAnalise) {
		super();
		this.listaItens = listaItens;
		this.arrayValoresAtual = arrayValoresAtual;
		this.arrayPecasAtual = arrayPecasAtual;
		this.arrayMinutosAtual = arrayMinutosAtual;
		this.arrayValoresAnalise = arrayValoresAnalise;
		this.arrayPecasAnalise = arrayPecasAnalise;
		this.arrayMinutosAnalise = arrayMinutosAnalise;
	}
}