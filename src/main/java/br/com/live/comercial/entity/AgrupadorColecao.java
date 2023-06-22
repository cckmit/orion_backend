package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_076")
public class AgrupadorColecao {

	@Id
	public String id;
	
	@Column(name = "cod_agrupador")
	public int codAgrupador;
	
	public int colecao;
	
	@Column(name = "sub_colecao")
	public int subColecao;
	
	public AgrupadorColecao() {
	}

	public AgrupadorColecao(int codAgrupador, int colecao, int subColecao) {
		this.id = codAgrupador + "-" + colecao + "-" + subColecao; 
		this.codAgrupador = codAgrupador;
		this.colecao = colecao;
		this.subColecao = subColecao;
	}
}
