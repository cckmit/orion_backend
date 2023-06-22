package br.com.live.produto.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_itens_sub_colecoes")
public class SubColecao {

	@Id	
	public int id;	
	public int colecao; 
	public String grupo;  
	public String sub;
	public String item;
	
}
