package br.com.live.comercial.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_com_200")
public class CanalDistribuicao {
	
	@Id
	public int id;
	public String descricao;
	public String modalidade;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	
	public CanalDistribuicao() {
		
	}
	public CanalDistribuicao(int id, String descricao, String modalidade) {
		
		this.id = id;
		this.descricao = descricao;
		this.modalidade = modalidade;
	}
	
	
	
	

}
