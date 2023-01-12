package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ind_020")
public class GrupoIndicador {
	
	@Id
	public int id;
	
	@Column(name = "descricao_grupo")	
	public String descricaoGrupo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoGrupo() {
		return descricaoGrupo;
	}
	public void setDescricaoGrupo(String descricaoGrupo) {
		this.descricaoGrupo = descricaoGrupo;
	}
	public GrupoIndicador() {
		
	}
	public GrupoIndicador(int id, String descricaoGrupo) {
		this.id = id;
		this.descricaoGrupo = descricaoGrupo;
	}
}
