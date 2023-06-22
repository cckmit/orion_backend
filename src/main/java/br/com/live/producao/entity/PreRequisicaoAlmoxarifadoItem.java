package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_sup_002")
public class PreRequisicaoAlmoxarifadoItem {

	@Id
	public String id;		   	  
	@Column(name="id_pre_requisicao")
	public long idPreRequisicao;
	public int sequencia;
	public String nivel;
	public String grupo;
	public String sub;
	public String item;
	@Column(name="cod_deposito")
	public int deposito;
	public double quantidade;
	
	public PreRequisicaoAlmoxarifadoItem () {} 
	
	public PreRequisicaoAlmoxarifadoItem (long idPreRequisicao, int sequencia, String nivel, String grupo, String sub, String item, int deposito, double quantidade) {
		this.id = idPreRequisicao + "-" + sequencia; 
		this.idPreRequisicao = idPreRequisicao; 
		this.sequencia = sequencia; 
		this.nivel = nivel; 
		this.grupo = grupo; 
		this.sub = sub; 
		this.item = item; 
		this.deposito = deposito; 
		this.quantidade = quantidade;	
	}
}
