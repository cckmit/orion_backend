package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_041")
public class PrevisaoVendasItens {

	@Id
	public String id;

	@Column(name = "id_previsao_vendas")
	public long idPrevisaoVendas;

	public String grupo;
	public String item;

	@Column(name = "valor_sell_in")
	public double valorSellIn;
	@Column(name = "valor_sell_out")
	public double valorSellOut;

	@Column(name = "grupo_base")
	public String grupoBase;
	@Column(name = "item_base")
	public String itemBase;
	@Column(name = "qtde_vendida_base")
	public int qtdeVendidaBase;

	@Column(name = "percentual_aplicar")
	public double percentualAplicar;
	@Column(name = "qtde_previsao")
	public int qtdePrevisao;

	public PrevisaoVendasItens() {
	}

	public PrevisaoVendasItens(long idPrevisaoVendas, String grupo, String item, double valorSellIn,
			double valorSellOut, String grupoBase, String itemBase, int qtdeVendidaBase, double percentualAplicar,
			int qtdePrevisao) {
		this.id = idPrevisaoVendas + "-" + grupo + "-" + item;
		this.idPrevisaoVendas = idPrevisaoVendas; 
		this.grupo = grupo;
		this.item = item;
		this.valorSellIn = valorSellIn;
		this.valorSellOut = valorSellOut;
		this.grupoBase = grupoBase; 
		this.itemBase = itemBase; 
		this.qtdeVendidaBase = qtdeVendidaBase; 
		this.percentualAplicar = percentualAplicar;
		this.qtdePrevisao = qtdePrevisao;
	}

}
