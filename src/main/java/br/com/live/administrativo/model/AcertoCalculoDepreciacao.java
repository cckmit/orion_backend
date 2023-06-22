package br.com.live.administrativo.model;

public class AcertoCalculoDepreciacao {
	
	public int code;
	public int sequence;
	public int ano;
	public int id;
	public int mes;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public AcertoCalculoDepreciacao() {
		
	}
	
	public AcertoCalculoDepreciacao(int code, int sequence, int ano, int id, int mes) {
		this.code = code;
		this.sequence = sequence;
		this.ano = ano;
		this.id = id;
		this.mes = mes;
	}
}
