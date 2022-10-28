package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_361")
public class LoteSugestaoColetaPorArea {

	@Id
	private Long id;
	
	@Column(name="id_lote")
	private Long idLote;
	
	@Column(name="id_area")
	private Long idArea;
	
	public LoteSugestaoColetaPorArea() {
		super();
	}
	
	public LoteSugestaoColetaPorArea(Long id, Long idLote, Long idArea) {
		super();
		this.id = id;
		this.idLote = idLote;
		this.idArea = idArea;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLote() {
		return idLote;
	}

	public void setIdLote(Long idLote) {
		this.idLote = idLote;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}
}
