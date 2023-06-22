package br.com.live.produto.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_080")
public class MarcasFio {
		
	@Id
	public int id;
	public String descricao;
	
	public MarcasFio() {
		
	}

	public MarcasFio(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
}
