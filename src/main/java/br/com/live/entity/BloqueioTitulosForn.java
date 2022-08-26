package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_com_250")
public class BloqueioTitulosForn {
	
	@Id
	public int id;
	
	@Column(name = "cnpj_fornecedor_9")
	public int cnpjFornecedor9;
	
	@Column(name = "cnpj_fornecedor_4")
	public int cnpjFornecedor4;
	
	@Column(name = "cnpj_fornecedor_2")
	public int cnpjFornecedor2;
	
	@Column(name = "data_bloqueio")
	public Date dataBloqueio;
	
	@Column(name = "data_desbloqueio")
	public Date dataDesbloqueio;
	
	public String motivo;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCnpjFornecedor9() {
		return cnpjFornecedor9;
	}

	public void setCnpjFornecedor9(int cnpjFornecedor9) {
		this.cnpjFornecedor9 = cnpjFornecedor9;
	}

	public int getCnpjFornecedor4() {
		return cnpjFornecedor4;
	}

	public void setCnpjFornecedor4(int cnpjFornecedor4) {
		this.cnpjFornecedor4 = cnpjFornecedor4;
	}

	public int getCnpjFornecedor2() {
		return cnpjFornecedor2;
	}

	public void setCnpjFornecedor2(int cnpjFornecedor2) {
		this.cnpjFornecedor2 = cnpjFornecedor2;
	}

	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public Date getDataDesbloqueio() {
		return dataDesbloqueio;
	}

	public void setDataDesbloqueio(Date dataDesbloqueio) {
		this.dataDesbloqueio = dataDesbloqueio;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public BloqueioTitulosForn( ) {
		
	}

	public BloqueioTitulosForn(int cnpjFornecedor9, int cnpjFornecedor4, int cnpjFornecedor2, Date dataBloqueio, Date dataDesbloqueio, String motivo) {
		this.id = cnpjFornecedor9 + cnpjFornecedor4 + cnpjFornecedor2;
		this.cnpjFornecedor9 = cnpjFornecedor9;
		this.cnpjFornecedor4 = cnpjFornecedor4;
		this.cnpjFornecedor2 = cnpjFornecedor2;
		this.dataBloqueio = dataBloqueio;
		this.dataDesbloqueio = dataDesbloqueio;
		this.motivo = motivo;
	}
}
