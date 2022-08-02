package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_eng_230")
public class Micromovimentos {
	
	@Id
	public String id;
	public String descricao;
	public float tempo;
	public float interferencia;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	public float getInterferencia() {
		return interferencia;
	}

	public void setInterferencia(float interferencia) {
		this.interferencia = interferencia;
	}

	public Micromovimentos() {
	}

	public Micromovimentos(String id, String descricao, float tempo, float interferencia) {
		this.id = id;
		this.descricao = descricao;
		this.tempo = tempo;
		this.interferencia = interferencia;
	}
}
