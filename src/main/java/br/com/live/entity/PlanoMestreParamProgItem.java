package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_017")
public class PlanoMestreParamProgItem {
	
	@Id	
	@Column(name="num_item_plano_mestre")
	public long idItemPlanoMestre;
		
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
		
	public int alternativa;
	public int roteiro;
	public int periodo;
	public int multiplicador;
	
	public long getIdItemPlanoMestre() {
		return idItemPlanoMestre;
	}
	public void setIdItemPlanoMestre(long idItemPlanoMestre) {
		this.idItemPlanoMestre = idItemPlanoMestre;
	}
	public long getIdPlanoMestre() {
		return idPlanoMestre;
	}
	public void setIdPlanoMestre(long idPlanoMestre) {
		this.idPlanoMestre = idPlanoMestre;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getMultiplicador() {
		return multiplicador;
	}
	public void setMultiplicador(int multiplicador) {
		this.multiplicador = multiplicador;
	}
	
}
