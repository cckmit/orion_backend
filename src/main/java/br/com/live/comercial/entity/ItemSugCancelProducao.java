package br.com.live.comercial.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_030")
public class ItemSugCancelProducao {

	@Id	
	public String id;	 
	
	public String nivel;
	public String grupo;	
	public String item;
	public int colecao;
	
	public ItemSugCancelProducao() {
		
	}
	
	public ItemSugCancelProducao(String nivel, String grupo, String item, int colecao) {
		this.id = nivel + '.' + grupo + '.' + item + '.' + colecao;
		this.nivel = nivel;
		this.grupo = grupo;		
		this.item = item;		 
		this.colecao = colecao;
	}
		
}
