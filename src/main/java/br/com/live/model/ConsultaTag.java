package br.com.live.model;

import java.util.Date;

public class ConsultaTag {
	public int deposito;
    public float quantEstoque;
    public float quantEmpenhada;
    public float quantSugerida;
    public float saldo;
    public int quantEndereco;
    public String referencia;
    public String numeroTag;
    public int situacao;
    public String nivel;
    public String grupo;
    public String subGrupo;
    public String item;
    public String endereco;
    public int transacao;
    public String descTransacao;
    public String descDeposito;
    public String usuario;
    public String coletor;
    public Date data;
    public String hora;
    public String produto;
    public int id;

    public int getDeposito() {
        return deposito;
    }
    public void setDeposito(int deposito) {
        this.deposito = deposito;
    }
    public float getQuantEstoque() {
        return quantEstoque;
    }
    public void setQuantEstoque(float quantEstoque) {
        this.quantEstoque = quantEstoque;
    }
    public float getQuantEmpenhada() {
        return quantEmpenhada;
    }
    public void setQuantEmpenhada(float quantEmpenhada) {
        this.quantEmpenhada = quantEmpenhada;
    }
    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public String getNumeroTag() {
        return numeroTag;
    }
    public void setNumeroTag(String numeroTag) {
        this.numeroTag = numeroTag;
    }
    public int getSituacao() {
        return situacao;
    }
    public void setSituacao(int situacao) {
        this.situacao = situacao;
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
    public String getSubGrupo() {
        return subGrupo;
    }
    public void setSubGrupo(String subGrupo) {
        this.subGrupo = subGrupo;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public float getQuantSugerida() {
		return quantSugerida;
	}
	public void setQuantSugerida(float quantSugerida) {
		this.quantSugerida = quantSugerida;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getQuantEndereco() {
		return quantEndereco;
	}
	public void setQuantEndereco(int quantEndereco) {
		this.quantEndereco = quantEndereco;
	}	
	public int getTransacao() {
		return transacao;
	}
	public void setTransacao(int transacao) {
		this.transacao = transacao;
	}
	public String getDescTransacao() {
		return descTransacao;
	}
	public void setDescTransacao(String descTransacao) {
		this.descTransacao = descTransacao;
	}
	public String getDescDeposito() {
		return descDeposito;
	}
	public void setDescDeposito(String descDeposito) {
		this.descDeposito = descDeposito;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getColetor() {
		return coletor;
	}
	public void setColetor(String coletor) {
		this.coletor = coletor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public ConsultaTag() {

    }
	
	public ConsultaTag(int deposito, float quantEstoque, float quantEmpenhada, float quantSugerida, float saldo,
			int quantEndereco, String referencia, String numeroTag, int situacao, String nivel, String grupo,
			String subGrupo, String item, String endereco, int transacao, String descTransacao, String descDeposito,
			String usuario, String coletor, Date data, String hora, String produto, int id) {
		this.deposito = deposito;
		this.quantEstoque = quantEstoque;
		this.quantEmpenhada = quantEmpenhada;
		this.quantSugerida = quantSugerida;
		this.saldo = saldo;
		this.quantEndereco = quantEndereco;
		this.referencia = referencia;
		this.numeroTag = numeroTag;
		this.situacao = situacao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.item = item;
		this.endereco = endereco;
		this.transacao = transacao;
		this.descTransacao = descTransacao;
		this.descDeposito = descDeposito;
		this.usuario = usuario;
		this.coletor = coletor;
		this.data = data;
		this.hora = hora;
		this.produto = produto;
		this.id = id;
	}
}
