package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_132")
public class ParametrosMapaEnderecoCaixa {

	@Id	
	public int deposito;
	
	@Column(name = "rua_inicio")	
	public String ruaInicio;
	
	@Column(name = "rua_fim")
	public String ruaFim;
	
	@Column(name = "box_inicio")
	public int boxInicio;
	
	@Column(name = "box_fim")
	public int boxFim;
	
	public ParametrosMapaEnderecoCaixa() {
	}

	public ParametrosMapaEnderecoCaixa(int deposito, String ruaInicio, String ruaFim, int boxInicio, int boxFim) {
		this.deposito = deposito;
		this.ruaInicio = ruaInicio;
		this.ruaFim = ruaFim;
		this.boxInicio = boxInicio;
		this.boxFim = boxFim;
	}
}
