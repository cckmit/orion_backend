package br.com.live.model;

import java.util.Date;

public class ConsultaDebCredPorRepresentante {
	
	public int id;
	public Date dataLancto;	
	public String referencia;
	public String tipo;
	public String campanha;
	public String representante;
	public String observacao;
	public float valor;
	public String usuario;
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataLancto() {
		return dataLancto;
	}
	public void setDataLancto(Date dataLancto) {
		this.dataLancto = dataLancto;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCampanha() {
		return campanha;
	}
	public void setCampanha(String campanha) {
		this.campanha = campanha;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public ConsultaDebCredPorRepresentante() {
		
	}
	public ConsultaDebCredPorRepresentante(int id, Date dataLancto, String referencia, String tipo, String campanha, String representante, String observacao, 
			float valor, String usuario) {
		
		this.id = id;
		this.dataLancto = dataLancto;
		this.referencia = referencia;
		this.tipo = tipo;
		this.campanha = campanha;
		this.representante = representante;
		this.observacao = observacao;
		this.valor = valor;
		this.usuario = usuario;
	}
	

}
