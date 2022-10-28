package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_362")
public class LoteSugestaoColetaPorAreaItem {

	@Id
	private Long id;
	
	@Column(name="id_lote_area")
	private Long idLoteArea;
	
	@Column(name="pedido_venda")
	private int pedidoVenda;

	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String endereco;
	
	@Column(name="qtde_coletar")
	private int qtdeColetar;

	public LoteSugestaoColetaPorAreaItem() {
		super();
	}	
	
	public LoteSugestaoColetaPorAreaItem(Long id, Long idLoteArea, int pedidoVenda, String nivel, String grupo,
			String sub, String item, String endereco, int qtdeColetar) {
		super();
		this.id = id;
		this.idLoteArea = idLoteArea;
		this.pedidoVenda = pedidoVenda;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.endereco = endereco;
		this.qtdeColetar = qtdeColetar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLoteArea() {
		return idLoteArea;
	}

	public void setIdLoteArea(Long idLoteArea) {
		this.idLoteArea = idLoteArea;
	}

	public int getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getQtdeColetar() {
		return qtdeColetar;
	}

	public void setQtdeColetar(int qtdeColetar) {
		this.qtdeColetar = qtdeColetar;
	}
}
