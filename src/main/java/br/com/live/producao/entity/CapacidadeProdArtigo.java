package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_036")
public class CapacidadeProdArtigo {

	@Id	
	public String id;
	
	public int periodo;
	public int estagio;
	public int artigo;
	
	@Column(name = "qtde_pecas")	
	public int qtdePecas;
	
	@Column(name = "qtde_minutos")
	public int qtdeMinutos;
	
	public CapacidadeProdArtigo() {
		
	}
	
	public CapacidadeProdArtigo(int periodo, int estagio, int artigo, int qtdePecas, int qtdeMinutos) {
		this.id = periodo + "-" + estagio + "-" + artigo;
		this.periodo = periodo;
		this.estagio = estagio;
		this.artigo = artigo;
		this.qtdePecas = qtdePecas;
		this.qtdeMinutos = qtdeMinutos;		
	}
	
}
