package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_091")
public class RequisicaoTecidosItem {
	 
	@Id
	public long id;
	
	@Column(name = "id_requisicao")
	public long idRequisicao;
	public int sequencia;	
	public String nivel;
    public String grupo;
	public String sub;
	public String item;
    public int alternativa;
    public int roteiro;
	public double quantidade;

	public RequisicaoTecidosItem() {}
	
	public RequisicaoTecidosItem (long id, long idRequisicao, int sequencia, String nivel, String grupo, String sub, String item, int alternativa, int roteiro, double quantidade) {		
		super();
		this.id = id;
		this.idRequisicao = idRequisicao;
		this.sequencia = sequencia;	
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.quantidade = quantidade;
	}	
}
