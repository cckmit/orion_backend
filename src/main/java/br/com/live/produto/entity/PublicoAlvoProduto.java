package br.com.live.produto.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_produtos_publico")
public class PublicoAlvoProduto {

	@Id		
	public int id;
    public String grupo;
    public int codigo;
    
}
