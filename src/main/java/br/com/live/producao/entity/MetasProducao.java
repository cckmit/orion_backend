package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_230")
public class MetasProducao {
	
	@Id	
	public String id;
	public int mes;
	public int ano;
	
	@Column(name = "cod_estagio")
	public int codEstagio;
	
	@Column(name = "meta_mes")
	public int metaMes;
  
	@Column(name = "dias_uteis")
	public int diasUteis;
	
	@Column(name = "meta_diaria")
	public int metaDiaria;
	
	@Column(name = "meta_ajustada")
	public int metaAjustada;
	
	@Column(name = "numero_expedidor")
	public int numeroExpedidor;
	
	@Column(name = "meta_expedidor")
	public int metaExpedidor;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getCodEstagio() {
		return codEstagio;
	}

	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}

	public int getMetaMes() {
		return metaMes;
	}

	public void setMetaMes(int metaMes) {
		this.metaMes = metaMes;
	}

	public int getDiasUteis() {
		return diasUteis;
	}

	public void setDiasUteis(int diasUteis) {
		this.diasUteis = diasUteis;
	}

	public int getMetaDiaria() {
		return metaDiaria;
	}

	public void setMetaDiaria(int metaDiaria) {
		this.metaDiaria = metaDiaria;
	}

	public int getMetaAjustada() {
		return metaAjustada;
	}

	public void setMetaAjustada(int metaAjustada) {
		this.metaAjustada = metaAjustada;
	}

	public int getNumeroExpedidor() {
		return numeroExpedidor;
	}

	public void setNumeroExpedidor(int numeroExpedidor) {
		this.numeroExpedidor = numeroExpedidor;
	}

	public int getMetaExpedidor() {
		return metaExpedidor;
	}

	public void setMetaExpedidor(int metaExpedidor) {
		this.metaExpedidor = metaExpedidor;
	}

	public MetasProducao() {
		
	}

	public MetasProducao(int mes, int ano, int codEstagio, int metaMes, int diasUteis, int metaDiaria, int metaAjustada, int numeroExpedidor,
						int	metaExpedidor) {
		this.id = mes + "-" + ano + "-" + codEstagio;
		this.mes = mes;
		this.ano = ano;
		this.codEstagio = codEstagio;
		this.metaMes = metaMes;
		this.diasUteis = diasUteis;
		this.metaDiaria = metaDiaria;
		this.metaAjustada = metaAjustada;
		this.numeroExpedidor = numeroExpedidor;
		this.metaExpedidor = metaExpedidor;
		
	}
}
