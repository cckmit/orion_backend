package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_220")
public class RepresentanteAntigoXNovo {
	
	@Id
	public int id;
	
	@Column(name = "repres_antigo")
	public int represAntigo;
	
	@Column(name = "repres_novo")
	public int represNovo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getRepresAntigo() {
		return represAntigo;
	}
	public void setRepresAntigo(int represAntigo) {
		this.represAntigo = represAntigo;
	}
	public int getRepresNovo() {
		return represNovo;
	}
	public void setRepresNovo(int represNovo) {
		this.represNovo = represNovo;
	}
	
	public RepresentanteAntigoXNovo() {
		
	}

	public RepresentanteAntigoXNovo(int id, int represAntigo, int represNovo) {
		
		this.id = id;
		this.represAntigo = represAntigo;
		this.represNovo = represNovo;
	}

}
