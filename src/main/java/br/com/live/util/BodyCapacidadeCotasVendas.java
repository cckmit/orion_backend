package br.com.live.util;

import java.util.List;

import br.com.live.model.CapacidadeCotasVendasDadosItem;

public class BodyCapacidadeCotasVendas {	
	public long idCapacidadeCotas;
	public String descricao;
	public int minutosPeriodo;
	public int periodoAtualInicio;
	public int periodoAtualFinal;
	public int periodoAnaliseInicio;
	public int periodoAnaliseFinal;	
	public List<ConteudoChaveNumerica> colecoes;
	public List<ConteudoChaveNumerica> depositos;	
	public boolean listarTempUnit;	
	public List<CapacidadeCotasVendasDadosItem> itens;
}