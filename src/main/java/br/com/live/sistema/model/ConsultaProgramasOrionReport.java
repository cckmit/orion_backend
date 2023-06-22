package br.com.live.sistema.model;

public class ConsultaProgramasOrionReport {
	
	public String idPrograma;
	public String area;
	public String modulo;
	public int nrPrograma;
	public String descricaoPrograma;
	public int powerbi;
	public String linkPowerbi;
	public int situacao;
	
	
	public String getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public int getNrPrograma() {
		return nrPrograma;
	}
	public void setNrPrograma(int nrPrograma) {
		this.nrPrograma = nrPrograma;
	}
	public String getDescricaoPrograma() {
		return descricaoPrograma;
	}
	public void setDescricaoPrograma(String descricaoPrograma) {
		this.descricaoPrograma = descricaoPrograma;
	}
	public int getPowerbi() {
		return powerbi;
	}
	public void setPowerbi(int powerbi) {
		this.powerbi = powerbi;
	}
	public String getLinkPowerbi() {
		return linkPowerbi;
	}
	public void setLinkPowerbi(String linkPowerbi) {
		this.linkPowerbi = linkPowerbi;
	}
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
	
	public ConsultaProgramasOrionReport() {
		
	}
	
	public ConsultaProgramasOrionReport(String idPrograma, String area, String modulo, int nrPrograma, String descricaoPrograma,
			int powerbi, String linkPowerbi, int situacao) {
		
		this.area = area;
		this.modulo = modulo;
		this.nrPrograma = nrPrograma;
		this.descricaoPrograma = descricaoPrograma;
		this.powerbi = powerbi;
		this.linkPowerbi = linkPowerbi;
		this.situacao = situacao;
	}
	

}
