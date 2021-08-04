package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_045")
public class CapacidadeCotasVendasCapa {
	
	@Id	
	public String id;
	public int periodo;
	public int linha;
	public int colecao;
	
	@Column(name = "tempo_total")
	public int minDistribuir;
	
	public CapacidadeCotasVendasCapa() {
		
	}
	
	public CapacidadeCotasVendasCapa(int periodo, int linha, int colecao, int minDistribuir) {
		this.id = periodo + "-" + colecao + "-" + linha;
		this.periodo = periodo;
		this.linha = linha;
		this.colecao = colecao;
		this.minDistribuir = minDistribuir;
	}

}
