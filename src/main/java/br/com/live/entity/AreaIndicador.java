package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "orion_ind_020")
public class AreaIndicador {
	
	@Id
	public String id;
	public int tipo;
	public int sequencia;
	public String descricao;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public AreaIndicador() {
		
	}
	public AreaIndicador(String id, int tipo, int sequencia, String descricao) {
		this.id = tipo + "-" + sequencia;
		this.tipo = tipo;
		this.sequencia = sequencia;
		this.descricao = descricao;
	}
	
}
