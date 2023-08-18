package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_345")
public class CaixaPretaLocalConfeccao {
	
	@Id	
	public int id;
	
	public String descricao;
	
	@Column(name = "data_cadastro")	
	public Date dataCadastro;
	
	@Column(name = "ultima_alteracao")	
	public Date ultimaAlteracao;
	
	public int situacao;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
	
	public CaixaPretaLocalConfeccao() {
		
	}
	public CaixaPretaLocalConfeccao(int id, String descricao, Date dataCadastro, Date ultimaAlteracao, int situacao) {
		
		this.id = id;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.situacao = situacao;
	}

}
