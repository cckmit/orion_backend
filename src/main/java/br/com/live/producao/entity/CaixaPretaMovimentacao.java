package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_350")
public class CaixaPretaMovimentacao {
	
	@Id	
	public int id;
	
	public int usuario;
	
	@Column(name = "id_caixa")	
	public int idCaixa;
	
	@Column(name = "centro_custo")	
	public int centroCusto;
	
	public Date data;
	public String hora;
	
	@Column(name = "id_local")	
	public int idLocal;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public int getIdCaixa() {
		return idCaixa;
	}
	public void setIdCaixa(int idCaixa) {
		this.idCaixa = idCaixa;
	}
	public int getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(int centroCusto) {
		this.centroCusto = centroCusto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getIdLocal() {
		return idLocal;
	}
	public void setIdLocal(int idLocal) {
		this.idLocal = idLocal;
	}
	
	public CaixaPretaMovimentacao() {
		
	}
	public CaixaPretaMovimentacao(int id, int usuario, int idCaixa, int centroCusto, Date data, String hora,
			int idLocal) {
		
		this.id = id;
		this.usuario = usuario;
		this.idCaixa = idCaixa;
		this.centroCusto = centroCusto;
		this.data = data;
		this.hora = hora;
		this.idLocal = idLocal;
	}	

}
