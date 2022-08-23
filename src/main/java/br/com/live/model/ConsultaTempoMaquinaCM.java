package br.com.live.model;

public class ConsultaTempoMaquinaCM {

	private long id;
	private String grupo;
	private String nomeGrupoMaq;
	private String subgrupo;
	private String nomeSbgrupoMaq;
	private float medida;
	private float tempo;
	private float interferencia;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getNomeGrupoMaq() {
		return nomeGrupoMaq;
	}
	public void setNomeGrupoMaq(String nomeGrupoMaq) {
		this.nomeGrupoMaq = nomeGrupoMaq;
	}
	public String getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
	public String getNomeSbgrupoMaq() {
		return nomeSbgrupoMaq;
	}
	public void setNomeSbgrupoMaq(String nomeSbgrupoMaq) {
		this.nomeSbgrupoMaq = nomeSbgrupoMaq;
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
	public float getInterferencia() {
		return interferencia;
	}
	public void setInterferencia(float interferencia) {
		this.interferencia = interferencia;
	}

}
