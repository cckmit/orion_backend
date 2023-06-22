package br.com.live.producao.model;

import java.util.Date;

public class ConsultaEncolhimentoCad {

	public int id;
	public String usuario;
	public Date dataRegistro;
	public String tecido;
	public float largAcomodacao;
	public float compAcomodacao;
	public float largTermo;
	public float compTermo;
	public float largEstampa;
	public float compEstampa;
	public float largEstampaPoli;
	public float compEstampaPoli;
	public float largPolimerizadeira;
	public float compPolimerizadeira;
	public float largEstampaPrensa;
	public float compEstampaPrensa; 
	public String tipo;
	public float largura;
	public float comprimento;		
    public String observacao;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
	public String getTecido() {
		return tecido;
	}
	public void setTecido(String tecido) {
		this.tecido = tecido;
	}
	public float getLargAcomodacao() {
		return largAcomodacao;
	}
	public void setLargAcomodacao(float largAcomodacao) {
		this.largAcomodacao = largAcomodacao;
	}
	public float getCompAcomodacao() {
		return compAcomodacao;
	}
	public void setCompAcomodacao(float compAcomodacao) {
		this.compAcomodacao = compAcomodacao;
	}
	public float getLargTermo() {
		return largTermo;
	}
	public void setLargTermo(float largTermo) {
		this.largTermo = largTermo;
	}
	public float getCompTermo() {
		return compTermo;
	}
	public void setCompTermo(float compTermo) {
		this.compTermo = compTermo;
	}
	public float getLargEstampa() {
		return largEstampa;
	}
	public void setLargEstampa(float largEstampa) {
		this.largEstampa = largEstampa;
	}
	public float getCompEstampa() {
		return compEstampa;
	}
	public void setCompEstampa(float compEstampa) {
		this.compEstampa = compEstampa;
	}
	public float getLargEstampaPoli() {
		return largEstampaPoli;
	}
	public void setLargEstampaPoli(float largEstampaPoli) {
		this.largEstampaPoli = largEstampaPoli;
	}
	public float getCompEstampaPoli() {
		return compEstampaPoli;
	}
	public void setCompEstampaPoli(float compEstampaPoli) {
		this.compEstampaPoli = compEstampaPoli;
	}
	public float getLargPolimerizadeira() {
		return largPolimerizadeira;
	}
	public void setLargPolimerizadeira(float largPolimerizadeira) {
		this.largPolimerizadeira = largPolimerizadeira;
	}
	public float getCompPolimerizadeira() {
		return compPolimerizadeira;
	}
	public void setCompPolimerizadeira(float compPolimerizadeira) {
		this.compPolimerizadeira = compPolimerizadeira;
	}
	public float getLargEstampaPrensa() {
		return largEstampaPrensa;
	}
	public void setLargEstampaPrensa(float largEstampaPrensa) {
		this.largEstampaPrensa = largEstampaPrensa;
	}
	public float getCompEstampaPrensa() {
		return compEstampaPrensa;
	}
	public void setCompEstampaPrensa(float compEstampaPrensa) {
		this.compEstampaPrensa = compEstampaPrensa;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getLargura() {
		return largura;
	}
	public void setLargura(float largura) {
		this.largura = largura;
	}
	public float getComprimento() {
		return comprimento;
	}
	public void setComprimento(float comprimento) {
		this.comprimento = comprimento;
	}
	public ConsultaEncolhimentoCad() {
		
	}
	public ConsultaEncolhimentoCad(int id, String usuario, Date dataRegistro, String tecido, float largAcomodacao,
			float compAcomodacao, float largTermo, float compTermo, float largEstampa, float compEstampa,
			float largEstampaPoli, float compEstampaPoli, float largPolimerizadeira, float compPolimerizadeira,
			float largEstampaPrensa, float compEstampaPrensa, String observacao) {
		this.id = id;
		this.usuario = usuario;
		this.dataRegistro = dataRegistro;
		this.tecido = tecido;
		this.largAcomodacao = largAcomodacao;
		this.compAcomodacao = compAcomodacao;
		this.largTermo = largTermo;
		this.compTermo = compTermo;
		this.largEstampa = largEstampa;
		this.compEstampa = compEstampa;
		this.largEstampaPoli = largEstampaPoli;
		this.compEstampaPoli = compEstampaPoli;
		this.largPolimerizadeira = largPolimerizadeira;
		this.compPolimerizadeira = compPolimerizadeira;
		this.largEstampaPrensa = largEstampaPrensa;
		this.compEstampaPrensa = compEstampaPrensa;
		this.observacao = observacao;
	}
	
}
