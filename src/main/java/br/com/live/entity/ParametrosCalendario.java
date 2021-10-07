package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_061")
public class ParametrosCalendario {

	@Id	
	@Column(name="ano_calendario")
	public int anoCalendario;
	
	@Column(name="periodo_inicio")
	public int periodoInicio;
	
	@Column(name="periodo_fim")
	public int periodoFim;
	
	@Column(name="considera_sabado")
	public boolean consideraSabado;
	
	@Column(name="considera_domingo")
	public boolean consideraDomingo;
	
	@Column(name="considera_feriado")
	public boolean consideraFeriado;
	
	public ParametrosCalendario() {
		
	}

	public ParametrosCalendario(int anoCalendario, int periodoInicio, int periodoFim, boolean consideraSabado, boolean consideraDomingo, boolean consideraFeriado) {
		this.anoCalendario = anoCalendario;
		this.periodoInicio = periodoInicio;
		this.periodoFim = periodoFim;
		this.consideraSabado = consideraSabado;
		this.consideraDomingo = consideraDomingo;
		this.consideraFeriado = consideraFeriado;
	}

}
