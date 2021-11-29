package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_081")
public class TiposFio {
	@Id
	public int id;
	public String descricao;
	public int titulo;
	
	@Column(name="centim_cone")
	public int centimetroCone;
	
	@Column(name="centim_cone2")
	public int centimetroCone2;

	@Column(name="centim_cone3")
	public int centimetroCone3;

	public TiposFio() {
		
	}

	public TiposFio(int id, String descricao, int titulo, int centimetroCone, int centimetroCone2, int centimetroCone3) {
		this.id = id;
		this.descricao = descricao;
		this.titulo = titulo;
		this.centimetroCone = centimetroCone;
		this.centimetroCone2 = centimetroCone2;
		this.centimetroCone3 = centimetroCone3;
	}
}
