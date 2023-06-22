package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_360")
public class LoteSugestaoColeta {
	
	@Id
	private Long id;	
	private int situacao;  
		
	@Column(name="id_usuario")
	private Long idUsuario;

	public LoteSugestaoColeta() {
		super();		
	}
	
	public LoteSugestaoColeta(Long id, int situacao, Long idUsuario) {
		super();
		this.id = id;		
		this.situacao = situacao;
		this.idUsuario = idUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}