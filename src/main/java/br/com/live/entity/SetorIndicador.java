package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ind_050")
public class SetorIndicador {
	
	@Id
	public int id;
	
	@Column(name = "descricao_setor")	
	public String descricaoSetor;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoSetor() {
		return descricaoSetor;
	}
	public void setDescricaoSetor(String descricaoSetor) {
		this.descricaoSetor = descricaoSetor;
	}

	public SetorIndicador() {
		
	}
	public SetorIndicador(int id, String descricaoSetor) {
		this.id = id;
		this.descricaoSetor = descricaoSetor;
	}
}
