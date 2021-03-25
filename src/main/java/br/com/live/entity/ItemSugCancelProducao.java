package br.com.live.entity;

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
	
	public ItemSugCancelProducao() {
		
	}
	
	public ItemSugCancelProducao(String nivel, String grupo, String item) {
		this.id = nivel + '.' + grupo + '.' + item;
		this.nivel = nivel;
		this.grupo = grupo;		
		this.item = item;		 
	}
		
}
