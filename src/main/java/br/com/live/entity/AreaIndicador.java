package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ind_030")
public class AreaIndicador {
	
	@Id
	public int id;
	
	@Column(name = "descricao_area")	
	public String descricaoArea;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoArea() {
		return descricaoArea;
	}
	public void setDescricaoArea(String descricaoArea) {
		this.descricaoArea = descricaoArea;
	}
	
	public AreaIndicador() {
		
	}
	public AreaIndicador(int id, String descricaoArea) {
		this.id = id;
		this.descricaoArea = descricaoArea;
	}

}
