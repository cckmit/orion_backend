package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_240")
public class MetasProducaoSemana {
	
	@Id	
	public long id;
	
	@Column(name = "id_mes")
	public String idMes;
	
	@Column(name = "nr_semana")
	public int nrSemana;
	
	@Column(name = "dias_uteis")
	public int diasUteis;
	
	@Column(name = "data_inicio")
	public Date dataInicio;
	
	@Column(name = "data_fim")
	public Date dataFim;
	
	@Column(name = "meta_real")
	public int metaReal;
	
	@Column(name = "meta_real_turno")
	public int metaRealTurno;
	
	@Column(name = "meta_ajustada")
	public int metaAjustada;
	
	@Column(name = "meta_ajustada_turno")
	public int metaAjustadaTurno;
	
	public MetasProducaoSemana() {
		
	}

	public MetasProducaoSemana(long id, String idMes, int nrSemana, int diasUteis, Date dataInicio, Date dataFim, int metaReal, int metaRealTurno, int metaAjustada, int metaAjustadaTurno) {
		this.id = id;
		this.idMes = idMes;
		this.nrSemana = nrSemana;
		this.diasUteis = diasUteis;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.metaReal = metaReal;
		this.metaRealTurno = metaRealTurno;
		this.metaAjustada = metaAjustada;
		this.metaAjustadaTurno = metaAjustadaTurno;
	}

}
