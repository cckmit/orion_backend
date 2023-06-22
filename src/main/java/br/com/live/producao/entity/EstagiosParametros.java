package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_062")
public class EstagiosParametros {

	@Id	
	public String id;
	
	public int sequencia;
	
	@Column(name="ano_calendario")
	public int anoCalendario;
	
	public int estagio;
	public int lead;
	
	@Column(name="data_inicio")
	public Date dataInicio;
	
	@Column(name="data_fim")
	public Date dataFim;
	
	public EstagiosParametros() {
		
	}

	public EstagiosParametros(int sequencia, int anoCalendario, int estagio, int lead, Date dataInicio, Date dataFim) {
		this.id = anoCalendario + "-" + sequencia;
		this.sequencia = sequencia;
		this.anoCalendario = anoCalendario;
		this.estagio = estagio;
		this.lead = lead;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

}
