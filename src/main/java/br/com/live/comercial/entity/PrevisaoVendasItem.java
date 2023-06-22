package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_041")
public class PrevisaoVendasItem {

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

	public PrevisaoVendasItem() {
	}

	public PrevisaoVendasItem(long idPrevisaoVendas, String grupo, String item, double valorSellIn,
			double valorSellOut, String grupoBase, String itemBase, int qtdeVendidaBase, double percentualAplicar,
			int qtdePrevisao) {
		
		this.setId(idPrevisaoVendas + "-" + grupo + "-" + item);
		this.setIdPrevisaoVendas(idPrevisaoVendas); 
		this.setGrupo(grupo);
		this.setItem(item);
		this.setValorSellIn(valorSellIn);
		this.setValorSellOut(valorSellOut);
		this.setQtdeVendidaBase(qtdeVendidaBase); 
		this.setPercentualAplicar(percentualAplicar);
		this.setQtdePrevisao(qtdePrevisao);
		this.setGrupoBase(grupoBase); 
		this.setItemBase(itemBase);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getIdPrevisaoVendas() {
		return idPrevisaoVendas;
	}

	public void setIdPrevisaoVendas(long idPrevisaoVendas) {
		this.idPrevisaoVendas = idPrevisaoVendas;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo.toUpperCase();
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item.toUpperCase();
	}

	public double getValorSellIn() {
		return valorSellIn;
	}

	public void setValorSellIn(double valorSellIn) {
		this.valorSellIn = valorSellIn;
	}

	public double getValorSellOut() {
		return valorSellOut;
	}

	public void setValorSellOut(double valorSellOut) {
		this.valorSellOut = valorSellOut;
	}

	public String getGrupoBase() {
		return grupoBase;
	}

	public void setGrupoBase(String grupoBase) {		
		this.grupoBase = "";		
		if (grupoBase != null) this.grupoBase = grupoBase.toUpperCase();
		if (this.grupoBase.length() > 5) this.grupoBase = "";		
	}

	public String getItemBase() {
		return itemBase;
	}

	public void setItemBase(String itemBase) {
		this.itemBase = "";		
		if (itemBase != null) this.itemBase = itemBase.toUpperCase();
		if (this.itemBase.length() > 6) this.itemBase = ""; 
	}

	public int getQtdeVendidaBase() {
		return qtdeVendidaBase;
	}

	public void setQtdeVendidaBase(int qtdeVendidaBase) {
		this.qtdeVendidaBase = qtdeVendidaBase;
	}

	public double getPercentualAplicar() {
		return percentualAplicar;
	}

	public void setPercentualAplicar(double percentualAplicar) {		
		if (percentualAplicar > 999.9) percentualAplicar = 999.0;  				
		this.percentualAplicar = percentualAplicar;
	}

	public int getQtdePrevisao() {
		return qtdePrevisao;
	}

	public void setQtdePrevisao(int qtdePrevisao) {
		this.qtdePrevisao = qtdePrevisao;
	}	
	
}
