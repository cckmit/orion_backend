package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_095")
public class PercComissaoOrgPedido {

	@Id	
	public int origem;
	
	@Column(name = "perc_comissao")	
	public float percComissao;
	
	public PercComissaoOrgPedido() {
		
	}
	
	public PercComissaoOrgPedido(int origem, float percComissao) {
		this.origem = origem;
		this.percComissao = percComissao;
	}
}
