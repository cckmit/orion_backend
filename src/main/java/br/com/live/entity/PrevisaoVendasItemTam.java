package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_042")
public class PrevisaoVendasItemTam {

	@Id
	public String id;
	@Column(name = "id_previsao_vendas")
	public long idPrevisaoVendas;
	@Column(name = "id_item_previsao_vendas")
	public String idItemPrevisaoVendas;	
	public String grupo;
	public String item;	
	public String sub;	
	@Column(name = "qtde_previsao")
	public int qtdePrevisao;
	
	public PrevisaoVendasItemTam() {		
	}
	
	public PrevisaoVendasItemTam(long idPrevisaoVendas, String idItemPrevisaoVendas, String grupo, String item, String sub, int qtdePrevisao) {		
		this.id = (idItemPrevisaoVendas + "-" + sub);
		this.idPrevisaoVendas = idPrevisaoVendas;
		this.idItemPrevisaoVendas = idItemPrevisaoVendas;
		this.grupo = grupo;
		this.item = item;
		this.sub = sub;
		this.qtdePrevisao = qtdePrevisao;		
	}	
}
