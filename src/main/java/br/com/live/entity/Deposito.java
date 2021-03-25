package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_depositos")
public class Deposito {

	@Id
	public int id;
	
	public String descricao;
	
}
