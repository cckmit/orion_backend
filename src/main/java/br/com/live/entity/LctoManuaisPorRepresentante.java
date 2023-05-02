package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_040")
public class LctoManuaisPorRepresentante {
	
	@Id
	public long id;
	
	@Column(name = "data_lancto")
	public Date dataLancto;
	
	public int mes;
	public int ano;
	public int tipo;
	public String campanha;
	public int representante;
	public String descricao;
	public float valor;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDataLancto() {
		return dataLancto;
	}
	public void setDataLancto(Date dataLancto) {
		this.dataLancto = dataLancto;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getCampanha() {
		return campanha;
	}
	public void setCampanha(String campanha) {
		this.campanha = campanha;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getRepresentante() {
		return representante;
	}
	public void setRepresentante(int representante) {
		this.representante = representante;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public LctoManuaisPorRepresentante() {
		
	}
	public LctoManuaisPorRepresentante(long id, Date dataLancto, int mes, int ano, int tipo, String campanha, int representante,
			String descricao, float valor) {
		
		this.id = id;
		this.dataLancto = dataLancto;
		this.mes = mes;
		this.ano = ano;
		this.tipo = tipo;
		this.representante = representante;
		this.descricao = descricao;
		this.valor = valor;
		this.campanha = campanha;
	}
	
}
