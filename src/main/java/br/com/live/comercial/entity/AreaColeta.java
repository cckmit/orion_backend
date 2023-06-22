package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_350")
public class AreaColeta {

	@Id
	private long id;
	private String descricao;
	
	@Column(name = "endereco_inicial")
	private String enderecoInicio;
	
	@Column(name = "endereco_final")
	private String enderecoFim;	
	
	public AreaColeta() {
		this.id = 0;
		this.descricao = "";
		this.enderecoInicio = ""; 
		this.enderecoFim = "";		
	}
	
	public AreaColeta(long id, String descricao, String enderecoInicio, String enderecoFim) {
		this.id = id;
		this.descricao = descricao.toUpperCase();
		this.enderecoInicio = enderecoInicio.toUpperCase(); 
		this.enderecoFim = enderecoFim.toUpperCase();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

	public String getEnderecoInicio() {
		return enderecoInicio;
	}

	public void setEnderecoInicio(String enderecoInicio) {
		this.enderecoInicio = enderecoInicio.toUpperCase();
	}

	public String getEnderecoFim() {
		return enderecoFim;
	}

	public void setEnderecoFim(String enderecoFim) {
		this.enderecoFim = enderecoFim.toUpperCase();
	}	
}
