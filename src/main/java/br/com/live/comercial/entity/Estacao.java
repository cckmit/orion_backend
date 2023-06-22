package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_070")
public class Estacao {

	@Id	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	public String descricao;
	public int catalogo;
	
	public Estacao() {
		
	}

	public Estacao(long codEstacao, String descricao, int catalogo) {
		this.codEstacao = codEstacao;
		this.descricao = descricao;
		this.catalogo = catalogo;
	}
}
