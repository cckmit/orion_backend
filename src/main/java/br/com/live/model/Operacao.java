package br.com.live.model;

public class Operacao {

	private int codigo;
	private String descricao;
	private String grupoMaquina;
	private String subMaquina;
	private String descMaquina;
	private float interferencia;

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getGrupoMaquina() {
		return grupoMaquina;
	}
	public void setGrupoMaquina(String grupoMaquina) {
		this.grupoMaquina = grupoMaquina;
	}
	public String getSubMaquina() {
		return subMaquina;
	}
	public void setSubMaquina(String subMaquina) {
		this.subMaquina = subMaquina;
	}	
	public String getDescMaquina() {
		return descMaquina;
	}
	public void setDescMaquina(String descMaquina) {
		this.descMaquina = descMaquina;
	}
	public float getInterferencia() {
		return interferencia;
	}
	public void setInterferencia(float interferencia) {
		this.interferencia = interferencia;
	}
}
