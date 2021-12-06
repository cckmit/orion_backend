package br.com.live.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_090")
public class RequisicaoTecidos {

	@Id
	public long id;
	public String descricao;
	public int situacao; // 0-Em digitação, 1-Liberado 
	public String usuario;
	public Date data;
	 	
	public RequisicaoTecidos() {}
	
	public RequisicaoTecidos(long id, String descricao, int situacao, String usuario, Date data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		this.usuario = usuario;		
		this.data = data;
	}
	
}
