package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_130")
public class AberturaCaixas {
	
	@Id
	@Column(name = "numero_caixa")	
	public int numeroCaixa;
	
	@Column(name = "situacao_caixa")	
	public int situacaoCaixa;

	public String usuario;
	
	@Column(name = "data_hora_inicio")	
	public Date dataHoraInicio;
	
	@Column(name = "data_hora_fim")	
	public Date dataHoraFim;
	
	public AberturaCaixas() {
	}

	public AberturaCaixas(int numeroCaixa, int situacaoCaixa, String usuario, Date dataHoraInicio, Date dataHoraFim) {
		this.numeroCaixa = numeroCaixa;
		this.situacaoCaixa = situacaoCaixa;
		this.usuario = usuario;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
	}
}
