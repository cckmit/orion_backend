package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_045")
public class CapacidadeCotasVendasCapa {

	@Id
	public long id;
	public String descricao;
	@Column(name = "periodo_atual_inicio")
	public int periodoAtualInicio;
	@Column(name = "periodo_atual_final")
	public int periodoAtualFinal;
	@Column(name = "periodo_analise_inicio")
	public int periodoAnaliseInicio;
	@Column(name = "periodo_analise_final")
	public int periodoAnaliseFinal;
	public String colecoes;
	public String depositos;
	@Column(name = "minutos_periodo")
	public int minutosPeriodo;

	public CapacidadeCotasVendasCapa() {

	}

	public CapacidadeCotasVendasCapa(long id, String descricao, int periodoAtualInicio, int periodoAtualFinal,
			int periodoAnaliseInicio, int periodoAnaliseFinal, String colecoes, String depositos, int minutosPeriodo) {
		this.id = id;
		this.descricao = descricao;
		this.periodoAtualInicio = periodoAtualInicio;
		this.periodoAtualFinal = periodoAtualFinal;
		this.periodoAnaliseInicio = periodoAnaliseInicio;
		this.periodoAnaliseFinal = periodoAnaliseFinal;
		this.colecoes = colecoes;
		this.depositos = depositos;
		this.minutosPeriodo = minutosPeriodo;
	}
}
