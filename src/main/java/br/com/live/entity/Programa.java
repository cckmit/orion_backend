package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_002")
public class Programa {

	@Id	
	public long id;
	
	public String descricao;
	public String modulo;
	public String path;
	public String help;
	
	public Programa() {
		
	}
	
	public Programa(long id, String descricao, String modulo, String path, String help) {
		this.id = id;
		this.descricao = descricao;
		this.modulo = modulo;
		this.path = path;
		this.help = help;
	}
	
}