package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_290")
public class EncolhimentoCad {
	
	@Id
	public int id;
	public int usuario;
	
	@Column(name = "data_registro")
	public Date dataRegistro;
	
	public String nivel;
	public String grupo;
	public String subgrupo;
	public String item;
	
	@Column(name = "larg_acomodacao")
	public float largAcomodacao;
	
	@Column(name = "comp_acomodacao")
	public float compAcomodacao;
	
	@Column(name = "larg_termo")
	public float largTermo;
	
	@Column(name = "comp_termo")
	public float compTermo;
	
	@Column(name = "larg_estampa")
	public float largEstampa;
	
	@Column(name = "comp_estampa")
	public float compEstampa;
	
	@Column(name = "larg_estampa_poli")
	public float largEstampaPoli;
	
	@Column(name = "comp_estampa_poli")
	public float compEstampaPoli;
	
	@Column(name = "larg_polimerizadeira")
	public float largPolimerizadeira;
	
	@Column(name = "comp_polimerizadeira")
	public float compPolimerizadeira;   
    
	@Column(name = "larg_estampa_prensa")
	public float largEstampaPrensa;
	
	@Column(name = "comp_estampa_prensa")
	public float compEstampaPrensa;
    
    public String observacao;

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
	public Date getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
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
    
	public EncolhimentoCad() {
		
	}
	public EncolhimentoCad(int id, int usuario, Date dataRegistro, String nivel, String grupo, String subgrupo,
			String item, float largAcomodacao, float compAcomodacao, float largTermo, float compTermo,
			float largEstampa, float compEstampa, float largEstampaPoli, float compEstampaPoli,
			float largPolimerizadeira, float compPolimerizadeira, float largEstampaPrensa, float compEstampaPrensa,
			String observacao) {
		this.id = id;
		this.usuario = usuario;
		this.dataRegistro = dataRegistro;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.item = item;
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
