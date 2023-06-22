package br.com.live.producao.model;

import java.util.Date;

public class ConsultaInspecaoQualidLanctoPecas {

	public long id;
	public String motivo;
	public String estagio;
	public String usuario;
	public String revisorOrigem;
	public Date dataHora;
	public int quantidade;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRevisorOrigem() {
		return revisorOrigem;
	}
	public void setRevisorOrigem(String revisorOrigem) {
		this.revisorOrigem = revisorOrigem;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getEstagio() {
		return estagio;
	}
	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
