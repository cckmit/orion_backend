package br.com.live.model;

import java.util.Date;

public class ConsultaControleRelaxadeira {
    public String id;
    public int status;
    public int codRolo;
    public String motivoRejeicao;
    public float quantidade;
    public float perdaMetros;
    public int sitSincronizacao;
    public Date dataSincronizacao;
    public String codAnalista;
    public String tecido;
    public String descTecido;
    public float pesoRolo;
    public float largura;
    public float gramatura;
    public float metragem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCodRolo() {
        return codRolo;
    }

    public void setCodRolo(int codRolo) {
        this.codRolo = codRolo;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getPerdaMetros() {
        return perdaMetros;
    }

    public void setPerdaMetros(float perdaMetros) {
        this.perdaMetros = perdaMetros;
    }

    public int getSitSincronizacao() {
        return sitSincronizacao;
    }

    public void setSitSincronizacao(int sitSincronizacao) {
        this.sitSincronizacao = sitSincronizacao;
    }

    public Date getDataSincronizacao() {
        return dataSincronizacao;
    }

    public void setDataSincronizacao(Date dataSincronizacao) {
        this.dataSincronizacao = dataSincronizacao;
    }

    public String getCodAnalista() {
        return codAnalista;
    }

    public void setCodAnalista(String codAnalista) {
        this.codAnalista = codAnalista;
    }

    public String getTecido() {
        return tecido;
    }

    public void setTecido(String tecido) {
        this.tecido = tecido;
    }

    public String getDescTecido() {
        return descTecido;
    }

    public void setDescTecido(String descTecido) {
        this.descTecido = descTecido;
    }

    public float getPesoRolo() {
        return pesoRolo;
    }

    public void setPesoRolo(float pesoRolo) {
        this.pesoRolo = pesoRolo;
    }

    public float getLargura() {
		return largura;
	}

	public void setLargura(float largura) {
		this.largura = largura;
	}

	public float getGramatura() {
		return gramatura;
	}

	public void setGramatura(float gramatura) {
		this.gramatura = gramatura;
	}

	public float getMetragem() {
		return metragem;
	}

	public void setMetragem(float metragem) {
		this.metragem = metragem;
	}

	public ConsultaControleRelaxadeira(){}

	public ConsultaControleRelaxadeira(String id, int status, int codRolo, String motivoRejeicao, float quantidade,
			float perdaMetros, int sitSincronizacao, Date dataSincronizacao, String codAnalista, String tecido,
			String descTecido, float pesoRolo, float largura, float gramatura, float metragem) {
		this.id = id;
		this.status = status;
		this.codRolo = codRolo;
		this.motivoRejeicao = motivoRejeicao;
		this.quantidade = quantidade;
		this.perdaMetros = perdaMetros;
		this.sitSincronizacao = sitSincronizacao;
		this.dataSincronizacao = dataSincronizacao;
		this.codAnalista = codAnalista;
		this.tecido = tecido;
		this.descTecido = descTecido;
		this.pesoRolo = pesoRolo;
		this.largura = largura;
		this.gramatura = gramatura;
		this.metragem = metragem;
	}
}
