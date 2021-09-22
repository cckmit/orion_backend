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
	@Column(name = "periodo_inicial")
	public int periodoInicial;
	@Column(name = "periodo_final")
	public int periodoFinal;
	public String depositos;

	public CapacidadeCotasVendasCapa() {
		
	}
	
	public CapacidadeCotasVendasCapa(int periodo, int linha, int colecao, int minDistribuir, int periodoInicial, int periodoFinal, String depositos) {
		this.id = periodo + "-" + colecao + "-" + linha;
		this.periodo = periodo;
		this.linha = linha;
		this.colecao = colecao;
		this.minDistribuir = minDistribuir;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
		this.depositos = depositos;
	}
}
