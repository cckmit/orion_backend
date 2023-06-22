package br.com.live.administrativo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_070")
public class ParcelaMostruario {
	
	@Id
	public int id;
	
	public int representante;
	public String estacao;
	public int numParcela;
	public float valor;
	public int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRepresentante() {
		return representante;
	}
	public void setRepresentante(int representante) {
		this.representante = representante;
	}
	public String getEstacao() {
		return estacao;
	}
	public void setEstacao(String estacao) {
		this.estacao = estacao;
	}
	public int getNumParcela() {
		return numParcela;
	}
	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public ParcelaMostruario() {
		
	}
	public ParcelaMostruario(int id, int representante, String estacao, int numParcela, float valor, int status) {
		
		this.id = id;
		this.representante = representante;
		this.estacao = estacao;
		this.numParcela = numParcela;
		this.valor = valor;
		this.status = status;
	}
	
}
