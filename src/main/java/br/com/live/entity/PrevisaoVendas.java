package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_040")
public class PrevisaoVendas {

	@Id
	public long id;

	public String descricao;
	public int colecao;

	@Column(name = "col_tab_preco_sell_in")
	public int colTabPrecoSellIn;

	@Column(name = "mes_tab_preco_sell_in")
	public int mesTabPrecoSellIn;

	@Column(name = "seq_tab_preco_sell_in")
	public int seqTabPrecoSellIn;

	@Column(name = "col_tab_preco_sell_out")
	public int colTabPrecoSellOut;

	@Column(name = "mes_tab_preco_sell_out")
	public int mesTabPrecoSellOut;

	@Column(name = "seq_tab_preco_sell_out")
	public int seqTabPrecoSellOut;

	public PrevisaoVendas() {
		
	}

	public PrevisaoVendas(long id, String descricao, int colecao, int colTabPrecoSellIn, int mesTabPrecoSellIn,
			int seqTabPrecoSellIn, int colTabPrecoSellOut, int mesTabPrecoSellOut,
			int seqTabPrecoSellOut) {
		
		this.id = id;
		this.descricao = descricao.toUpperCase();
		this.colecao = colecao; 
		this.colTabPrecoSellIn = colTabPrecoSellIn; 
		this.mesTabPrecoSellIn = mesTabPrecoSellIn;
		this.seqTabPrecoSellIn = seqTabPrecoSellIn;
		this.colTabPrecoSellOut = colTabPrecoSellOut; 
		this.mesTabPrecoSellOut = mesTabPrecoSellOut;
		this.seqTabPrecoSellOut = seqTabPrecoSellOut;				
	}

}
