package br.com.live.util;

import java.util.List;

import br.com.live.model.ConsultaPrevisaoVendasItens;

public class BodyPrevisaoVendas {

	public long id;
	public String descricao;	
	public int colecao;
	public String idTabelaPrecoSellIn; 
	public String idTabelaPrecoSellOut;	
	public List<ConsultaPrevisaoVendasItens> previsoesVendasItens;

	public int getColTabPrecoSellIn() {
		String[] codigoSeparado = idTabelaPrecoSellIn.split("[.]");
		int colecao = Integer.parseInt(codigoSeparado [0]);
		return colecao;
	}
	
	public int getMesTabPrecoSellIn() {
		String[] codigoSeparado = idTabelaPrecoSellIn.split("[.]");
		int mes = Integer.parseInt(codigoSeparado [1]);
		return mes;
	}
	
	public int getSeqTabPrecoSellIn() {
		String[] codigoSeparado = idTabelaPrecoSellIn.split("[.]");
		int sequencia = Integer.parseInt(codigoSeparado [2]);
		return sequencia;
	}
	
	public int getColTabPrecoSellOut() {
		String[] codigoSeparado = idTabelaPrecoSellOut.split("[.]");
		int colecao = Integer.parseInt(codigoSeparado [0]);
		return colecao;
	}
	
	public int getMesTabPrecoSellOut() {
		String[] codigoSeparado = idTabelaPrecoSellOut.split("[.]");
		int mes = Integer.parseInt(codigoSeparado [1]);
		return mes;
	}
	
	public int getSeqTabPrecoSellOut() {
		String[] codigoSeparado = idTabelaPrecoSellOut.split("[.]");
		int sequencia = Integer.parseInt(codigoSeparado [2]);
		return sequencia;
	}
	
}
