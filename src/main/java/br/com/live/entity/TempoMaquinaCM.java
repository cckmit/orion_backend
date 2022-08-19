package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_eng_240")
public class TempoMaquinaCM {

	@Id
	public String id;
	public String grupo;
	public String subgrupo;
	public float medida;
	public float tempo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}

	public float getMedida() {
		return medida;
	}

	public void setMedida(float medida) {
		this.medida = medida;
	}

	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}

	public TempoMaquinaCM() {

	}

	public TempoMaquinaCM(String grupo, String subgrupo, float medida, float tempo) {
		this.id = grupo + "-" + subgrupo;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.medida = medida;
		this.tempo = tempo;
	}
}
