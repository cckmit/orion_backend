package br.com.live.produto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_itens")
public class ProdutoReferenciaCor {

	@Id
	public String id;
	public String grupo;	
	public String item;
	public String descricao;
	public int colecao;
	public int permanente;
	public String embarque;
	
	@Column(name = "alternativa_padrao")
	public int alternativaPadrao;
	
	@Column(name = "roteiro_padrao")
	public int roteiroPadrao;
	
	@Column(name = "risco_padrao")
	public int riscoPadrao;
	
	public ProdutoReferenciaCor() {
		
	}
	
	public ProdutoReferenciaCor(String id, String grupo, String item, String descricao, int colecao,
			int permanente, String embarque, int alternativaPadrao, int roteiroPadrao, int riscoPadrao) {
		this.id = id;
		this.grupo = grupo;		
		this.item = item;
		this.descricao = descricao;
		this.colecao = colecao;
		this.permanente = permanente;
		this.embarque = embarque;
		this.alternativaPadrao = alternativaPadrao;
		this.roteiroPadrao = roteiroPadrao;
		this.riscoPadrao = riscoPadrao;
	}
}
