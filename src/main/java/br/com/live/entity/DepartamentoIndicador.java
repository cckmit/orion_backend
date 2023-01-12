package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ind_040")
public class DepartamentoIndicador {
	
	@Id
	public int id;
	
	@Column(name = "descricao_departamento")	
	public String descricaoDepartamento;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoDepartamento() {
		return descricaoDepartamento;
	}
	public void setDescricaoDepartamento(String descricaoDepartamento) {
		this.descricaoDepartamento = descricaoDepartamento;
	}
	public DepartamentoIndicador() {
		
	}

	public DepartamentoIndicador(int id, String descricaoDepartamento) {
		
		this.id = id;
		this.descricaoDepartamento = descricaoDepartamento;
	}
}
