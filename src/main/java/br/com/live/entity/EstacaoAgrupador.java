package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_075")
public class EstacaoAgrupador {

	@Id
	public String id;
	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	@Column(name = "cod_agrupador")
	public int codAgrupador;
	
	public EstacaoAgrupador() {
		
	}

	public EstacaoAgrupador(int codAgrupador, long codEstacao) {
		this.id = codAgrupador + "-" + codEstacao;
		this.codAgrupador = codAgrupador;
		this.codEstacao = codEstacao;
	}
}
