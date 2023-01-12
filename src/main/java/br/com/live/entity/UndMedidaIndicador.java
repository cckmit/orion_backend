package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ind_120")
public class UndMedidaIndicador {
	
	@Id
	public int id;
	
	@Column(name="descricao_und_medida")
	public String descricaoUndMedida;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoUndMedida() {
		return descricaoUndMedida;
	}
	public void setDescricaoUndMedida(String descricaoUndMedida) {
		this.descricaoUndMedida = descricaoUndMedida;
	}
	
	public UndMedidaIndicador() {
		
	}
	public UndMedidaIndicador(int id, String descricaoUndMedida) {
		this.id = id;
		this.descricaoUndMedida = descricaoUndMedida;
	}
}
