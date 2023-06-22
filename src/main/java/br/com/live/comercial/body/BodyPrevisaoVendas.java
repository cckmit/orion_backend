package br.com.live.comercial.body;

import java.util.List;

import br.com.live.comercial.model.ConsultaPrevisaoVendasItem;
import br.com.live.comercial.model.ConsultaPrevisaoVendasItemTam;
import br.com.live.comercial.model.TabelaPreco;

public class BodyPrevisaoVendas {

	public long id;
	public String descricao;	
	public int colecao;
	public String idTabelaPrecoSellIn; 
	public String idTabelaPrecoSellOut;		
	public List<ConsultaPrevisaoVendasItem> previsaoVendasItens;
	
	public String codigoGrupoItem;
	public List<ConsultaPrevisaoVendasItemTam> previsaoVendasItemTamanhos;

	public int getColTabPrecoSellIn() {
		int colecao = TabelaPreco.getColTabelaPreco(idTabelaPrecoSellIn);
		return colecao;
	}
	
	public int getMesTabPrecoSellIn() {
		int mes = TabelaPreco.getMesTabelaPreco(idTabelaPrecoSellIn);
		return mes;
	}
	
	public int getSeqTabPrecoSellIn() {
		int sequencia = TabelaPreco.getSeqTabelaPreco(idTabelaPrecoSellIn);
		return sequencia;
	}
	
	public int getColTabPrecoSellOut() {
		int colecao = TabelaPreco.getColTabelaPreco(idTabelaPrecoSellOut);
		return colecao;
	}
	
	public int getMesTabPrecoSellOut() {
		int mes = TabelaPreco.getMesTabelaPreco(idTabelaPrecoSellOut);
		return mes;
	}
	
	public int getSeqTabPrecoSellOut() {
		int sequencia = TabelaPreco.getSeqTabelaPreco(idTabelaPrecoSellOut);
		return sequencia;
	}
	
}
