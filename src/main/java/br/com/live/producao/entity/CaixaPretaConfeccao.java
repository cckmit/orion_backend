package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_340")
public class CaixaPretaConfeccao {
	
	@Id	
	public int id;
	
	@Column(name = "centro_custo")	
	public int centroCusto;
	
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
	public int getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(int centroCusto) {
		this.centroCusto = centroCusto;
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
	
	public CaixaPretaConfeccao() {
		
	}
	public CaixaPretaConfeccao(int id, int centroCusto, String descricao, Date dataCadastro, Date ultimaAlteracao,
			int situacao) {
		
		this.id = id;
		this.centroCusto = centroCusto;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.situacao = situacao;
	}

}
