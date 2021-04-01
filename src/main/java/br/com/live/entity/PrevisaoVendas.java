package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_040")
public class PrevisaoVendas {

	@Id
	public String id;

	public int colecao;
	public String grupo;
	public String item;

	@Column(name = "col_tab_preco_sell_in")
	public int colTabPrecoSellIn;

	@Column(name = "mes_tab_preco_sell_in")
	public int mesTabPrecoSellIn;

	@Column(name = "seq_tab_preco_sell_in")
	public int seqTabPrecoSellIn;

	@Column(name = "valor_sell_in")
	public double valorSellIn;

	@Column(name = "col_tab_preco_sell_out")
	public int colTabPrecoSellOut;

	@Column(name = "mes_tab_preco_sell_out")
	public int mesTabPrecoSellOut;

	@Column(name = "seq_tab_preco_sell_out")
	public int seqTabPrecoSellOut;

	@Column(name = "valor_sell_out")
	public double valorSellOut;

	@Column(name = "qtde_previsao")
	public int qtdePrevisaoVendas;

	public PrevisaoVendas() {
	}

	public PrevisaoVendas(int colecao, String grupo, String item, int colTabPrecoSellIn, int mesTabPrecoSellIn,
			int seqTabPrecoSellIn, double valorSellIn, int colTabPrecoSellOut, int mesTabPrecoSellOut,
			int seqTabPrecoSellOut, double valorSellOut, int qtdePrevisaoVendas) {
		
		this.id = colecao + "." + grupo + "." + item;
		this.colecao = colecao; 
		this.grupo = grupo; 
		this.item = item; 
		this.colTabPrecoSellIn = colTabPrecoSellIn; 
		this.mesTabPrecoSellIn = mesTabPrecoSellIn;
		this.seqTabPrecoSellIn = seqTabPrecoSellIn;
		this.valorSellIn = valorSellIn; 
		this.colTabPrecoSellOut = colTabPrecoSellOut; 
		this.mesTabPrecoSellOut = mesTabPrecoSellOut;
		this.seqTabPrecoSellOut = seqTabPrecoSellOut;
		this.valorSellOut = valorSellOut; 
		this.qtdePrevisaoVendas = qtdePrevisaoVendas;
				
	}

}
