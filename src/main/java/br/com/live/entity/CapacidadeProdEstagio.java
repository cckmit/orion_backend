package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_035")
public class CapacidadeProdEstagio {

	@Id	
	public String id;
	
	public int periodo;	
	public int estagio;
	
	@Column(name = "qtde_pecas")	
	public int qtdePecas;
	
	@Column(name = "qtde_minutos")
	public int qtdeMinutos;
	
	public CapacidadeProdEstagio() {
		
	}
	
	public CapacidadeProdEstagio(int periodo, int estagio, int qtdePecas, int qtdeMinutos) {
		this.id = periodo + "-" + estagio;
		this.periodo = periodo;
		this.estagio = estagio;
		this.qtdePecas = qtdePecas;
		this.qtdeMinutos = qtdeMinutos;		
	}
	
}
