package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_074")
public class Agrupador {

	@Id
	@Column(name = "cod_agrupador")
	public int codAgrupador;
	
	public String descricao;
	
	public Agrupador() {
		
	}

	public Agrupador(int codAgrupador,String descricao) {
		this.codAgrupador = codAgrupador;
		this.descricao = descricao;
	}
}
