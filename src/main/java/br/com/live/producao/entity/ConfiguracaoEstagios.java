package br.com.live.producao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_060")
public class ConfiguracaoEstagios {

	@Id	
	public int sequencia;
	
	public int estagio;
	public int lead;
	
	public ConfiguracaoEstagios() {
		
	}

	public ConfiguracaoEstagios(int sequencia, int estagio, int lead) {
		this.sequencia = sequencia;
		this.estagio = estagio;
		this.lead = lead;
	}

}
