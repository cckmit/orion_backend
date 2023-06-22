package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_110")
public class OrionReportsProgramas {
	
	@Id	
	public String id;
	
	@Column(name="id_area")
	public String idArea;
	
	@Column(name="id_modulo")
	public String idModulo;
	
	@Column(name="nr_programa")
	public int nrPrograma;
	
	public String descricao;
	public int powerbi;
	
	@Column(name="link_powerbi")
	public String linkPowerbi;
	
	public int situacao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}

	public int getNrPrograma() {
		return nrPrograma;
	}

	public void setNrPrograma(int nrPrograma) {
		this.nrPrograma = nrPrograma;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
	public OrionReportsProgramas() {
		
	}

	public OrionReportsProgramas(String id, String idArea, String idModulo, int nrPrograma, String descricao,
			int powerbi, String linkPowerbi, int situacao) {
		
		this.id = id;
		this.idArea = idArea;
		this.idModulo = idModulo;
		this.nrPrograma = nrPrograma;
		this.descricao = descricao;
		this.powerbi = powerbi;
		this.linkPowerbi = linkPowerbi;
		this.situacao = situacao;
	}

}
