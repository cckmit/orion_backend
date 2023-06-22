package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_110")
public class ParametrosMapaEndereco {

	@Id	
	public int deposito;
	
	@Column(name = "bloco_inicio")	
	public String blocoInicio;
	
	@Column(name = "bloco_fim")	
	public String blocoFim;
	
	@Column(name = "corredor_inicio")	
	public int corredorInicio;
	
	@Column(name = "corredor_fim")	
	public int corredorFim;
	
	@Column(name = "box_inicio")	 
	public int boxInicio;
	
	@Column(name = "box_fim")	
	public int boxFim;
	
	@Column(name = "cesto_inicio")	 
	public int cestoInicio;
	
	@Column(name = "cesto_fim")	
	public int cestoFim;
	
	
	public int getDeposito() {
		return deposito;
	}

	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}

	public String getBlocoInicio() {
		return blocoInicio;
	}

	public void setBlocoInicio(String blocoInicio) {
		this.blocoInicio = blocoInicio;
	}

	public String getBlocoFim() {
		return blocoFim;
	}

	public void setBlocoFim(String blocoFim) {
		this.blocoFim = blocoFim;
	}

	public int getCorredorInicio() {
		return corredorInicio;
	}

	public void setCorredorInicio(int corredorInicio) {
		this.corredorInicio = corredorInicio;
	}

	public int getCorredorFim() {
		return corredorFim;
	}

	public void setCorredorFim(int corredorFim) {
		this.corredorFim = corredorFim;
	}

	public int getBoxInicio() {
		return boxInicio;
	}

	public void setBoxInicio(int boxInicio) {
		this.boxInicio = boxInicio;
	}

	public int getBoxFim() {
		return boxFim;
	}

	public void setBoxFim(int boxFim) {
		this.boxFim = boxFim;
	}
	
	public int getCestoInicio() {
		return cestoInicio;
	}

	public void setCestoInicio(int cestoInicio) {
		this.cestoInicio = cestoInicio;
	}

	public int getCestoFim() {
		return cestoFim;
	}

	public void setCestoFim(int cestoFim) {
		this.cestoFim = cestoFim;
	}

	public ParametrosMapaEndereco() {
	}

	public ParametrosMapaEndereco(int deposito, String blocoInicio, String blocoFim, int corredorInicio,
			int corredorFim, int boxInicio, int boxFim, int cestoInicio, int cestoFim) {
		this.deposito = deposito;
		this.blocoInicio = blocoInicio;
		this.blocoFim = blocoFim;
		this.corredorInicio = corredorInicio;
		this.corredorFim = corredorFim;
		this.boxInicio = boxInicio;
		this.boxFim = boxFim;
		this.cestoInicio = cestoInicio;
		this.cestoFim = cestoFim;
	}
}