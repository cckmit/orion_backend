package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_363")
public class LoteSugestaoColetaPorColetor {

	@Id
	private Long id;
	
	@Column(name="id_lote_area")
	private Long idLoteArea;

	@Column(name="id_coletor")
	private String idColetor;

	public LoteSugestaoColetaPorColetor(Long id, Long idLoteArea, String idColetor) {
		super();
		this.id = id;
		this.idLoteArea = idLoteArea;
		this.idColetor = idColetor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLoteArea() {
		return idLoteArea;
	}

	public void setIdLoteArea(Long idLoteArea) {
		this.idLoteArea = idLoteArea;
	}

	public String getIdColetor() {
		return idColetor;
	}

	public void setIdColetor(String idColetor) {
		this.idColetor = idColetor;
	}
}
