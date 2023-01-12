package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ind_070")
public class IndicadoresSemanal {
	
	@Id
	public String id;
	
	@Column(name = "id_indicador")
	public int idIndicador;
	public String variaveis;
	public String mes;
	public int ano;
	public String codigo;
	public String descricao;
	
	@Column(name = "semana_1")
	public float semana1;
	
	@Column(name = "semana_2")
	public float semana2;
	
	@Column(name = "semana_3")
	public float semana3;
	
	@Column(name = "semana_4")
	public float semana4;
	
	@Column(name = "semana_5")
	public float semana5;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdIndicador() {
		return idIndicador;
	}
	public void setIdIndicador(int idIndicador) {
		this.idIndicador = idIndicador;
	}	
	public String getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(String variaveis) {
		this.variaveis = variaveis;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getSemana1() {
		return semana1;
	}
	public void setSemana1(float semana1) {
		this.semana1 = semana1;
	}
	public float getSemana2() {
		return semana2;
	}
	public void setSemana2(float semana2) {
		this.semana2 = semana2;
	}
	public float getSemana3() {
		return semana3;
	}
	public void setSemana3(float semana3) {
		this.semana3 = semana3;
	}
	public float getSemana4() {
		return semana4;
	}
	public void setSemana4(float semana4) {
		this.semana4 = semana4;
	}
	public float getSemana5() {
		return semana5;
	}
	public void setSemana5(float semana5) {
		this.semana5 = semana5;
	}
	
	public IndicadoresSemanal() {
		
	}
	public IndicadoresSemanal(int idIndicador, String variaveis, String mes, int ano, String codigo, String descricao, float semana1,
			float semana2, float semana3, float semana4, float semana5) {
		this.id = mes + "-" + ano + "-" + codigo.toUpperCase();
		this.idIndicador = idIndicador;
		this.variaveis = variaveis;
		this.mes = mes;
		this.ano = ano;
		this.codigo = codigo.toUpperCase();
		this.descricao = descricao;
		this.semana1 = semana1;
		this.semana2 = semana2;
		this.semana3 = semana3;
		this.semana4 = semana4;
		this.semana5 = semana5;
	}
	
	
		
}
