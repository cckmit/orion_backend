package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_linhas_produtos")
public class LinhaProduto {

	@Id		
	public int id;   
    public String descricao; 
		
}
