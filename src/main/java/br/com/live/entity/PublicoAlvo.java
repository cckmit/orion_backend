package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_publicos_alvos")
public class PublicoAlvo {

	@Id	
	public int id;
    public String descricao;
    
}
