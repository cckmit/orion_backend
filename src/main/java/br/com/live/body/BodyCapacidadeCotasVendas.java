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
	public Object[] arrayValores;
	public Object[] arrayPecas;
	public Object[] arrayMinutos;
	
	public BodyCapacidadeCotasVendas(List<CapacidadeCotasVendas> listaItens, Object[] arrayValores, Object[] arrayPecas,
			Object[] arrayMinutos) {
		super();
		this.listaItens = listaItens;
		this.arrayValores = arrayValores;
		this.arrayPecas = arrayPecas;
		this.arrayMinutos = arrayMinutos;
	}
}