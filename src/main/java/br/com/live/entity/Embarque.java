package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_embarques")
public class Embarque {

	@Id
	public long id;
	
	public String descricao;
	
	public Embarque (long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
}
