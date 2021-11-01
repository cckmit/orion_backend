package br.com.live.model;

public class ConsultaEstacaoAgrupadores {
	
	public String id;
	public int codAgrupador;
	public int codEstacao;
	public String descAgrupador;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCodAgrupador() {
		return codAgrupador;
	}
	public void setCodAgrupador(int codAgrupador) {
		this.codAgrupador = codAgrupador;
	}
	public int getCodEstacao() {
		return codEstacao;
	}
	public void setCodEstacao(int codEstacao) {
		this.codEstacao = codEstacao;
	}
	public String getDescAgrupador() {
		return descAgrupador;
	}
	public void setDescAgrupador(String descAgrupador) {
		this.descAgrupador = descAgrupador;
	}
	
	public ConsultaEstacaoAgrupadores() {
		
	}
	
	public ConsultaEstacaoAgrupadores(String id, int codAgrupador, int codEstacao, String descAgrupador) {
		this.id = id;
		this.codAgrupador = codAgrupador;
		this.codEstacao = codEstacao;
		this.descAgrupador = descAgrupador;
	}	
}
