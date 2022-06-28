package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_sup_001")
public class PreRequisicaoAlmoxarifado {

	@Id
	public long id;
	public String descricao;
	@Column(name="cod_empresa")
	public int empresa;
    @Column(name="divisao_producao")
    public int divisaoProducao;
	@Column(name="centro_custo")
	public int centroCusto;
	
	public PreRequisicaoAlmoxarifado () {}
	
	public PreRequisicaoAlmoxarifado (long id, String descricao, int empresa, int divisaoProducao, int centroCusto) {
		this.id = id; 
		this.descricao = descricao.toUpperCase(); 
		this.empresa = empresa; 
		this.divisaoProducao = divisaoProducao;  
		this.centroCusto = centroCusto;
	}	
}
