package br.com.live.model;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class CapacidadesCotasVendas {
	
	private long id;
	private String descricao;
	private String colecoes;
	private String depositos;
	private int periodoAtualInicio;
	private int periodoAtualFinal;	
	private int periodoAnaliseInicio;
	private int periodoAnaliseFinal;
	private int minutosPeriodo;	
	private List<Colecao> listaColecoes;
	private List<ConteudoChaveNumerica> listaContChaveNumColecoes;	
	private List<Deposito> listaDepositos;
	private List<ConteudoChaveNumerica> listaContChaveNumDepositos;
	
	public List<Colecao> getListaColecoes() {
		return listaColecoes;
	}	
	public void setListaColecoes(List<Colecao> listaColecoes) {		
		this.listaColecoes = listaColecoes;
		this.listaContChaveNumColecoes = Colecao.parseToConteudoChaveNumerica(listaColecoes);
	}
	public List<ConteudoChaveNumerica> getListaContChaveNumColecoes() {
		return listaContChaveNumColecoes;
	}
	public void setListaContChaveNumColecoes(List<ConteudoChaveNumerica> listaContChaveNumColecoes) {
		this.listaContChaveNumColecoes = listaContChaveNumColecoes;
	}	
	public List<Deposito> getListaDepositos() {
		return listaDepositos;
	}
	public void setListaDepositos(List<Deposito> listaDepositos) {						
		this.listaDepositos = listaDepositos;
		this.listaContChaveNumDepositos = Deposito.parseToConteudoChaveNumerica(listaDepositos);
	}
	public List<ConteudoChaveNumerica> getListaContChaveNumDepositos() {
		return listaContChaveNumDepositos;
	}
	public void setListaContChaveNumDepositos(List<ConteudoChaveNumerica> listaContChaveNumDepositos) {
		this.listaContChaveNumDepositos = listaContChaveNumDepositos;
	}
	public String getDepositos() {
		return depositos;
	}
	public void setDepositos(String depositos) {
		this.depositos = depositos;
	}	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getColecoes() {
		return colecoes;
	}
	public void setColecoes(String colecoes) {
		this.colecoes = colecoes;
	}
	public int getPeriodoAtualInicio() {
		return periodoAtualInicio;
	}
	public void setPeriodoAtualInicio(int periodoAtualInicio) {
		this.periodoAtualInicio = periodoAtualInicio;
	}
	public int getPeriodoAtualFinal() {
		return periodoAtualFinal;
	}
	public void setPeriodoAtualFinal(int periodoAtualFinal) {
		this.periodoAtualFinal = periodoAtualFinal;
	}
	public int getPeriodoAnaliseInicio() {
		return periodoAnaliseInicio;
	}
	public void setPeriodoAnaliseInicio(int periodoAnaliseInicio) {
		this.periodoAnaliseInicio = periodoAnaliseInicio;
	}
	public int getPeriodoAnaliseFinal() {
		return periodoAnaliseFinal;
	}
	public void setPeriodoAnaliseFinal(int periodoAnaliseFinal) {
		this.periodoAnaliseFinal = periodoAnaliseFinal;
	}
	public int getMinutosPeriodo() {
		return minutosPeriodo;
	}
	public void setMinutosPeriodo(int minutosPeriodo) {
		this.minutosPeriodo = minutosPeriodo;
	}
}
