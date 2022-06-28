package br.com.live.model;

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
	
	public ConsultaTag() {

    }
	
	public ConsultaTag(int deposito, float quantEstoque, float quantEmpenhada, float quantSugerida, float saldo,
			String referencia, String numeroTag, int situacao, String nivel, String grupo, String subGrupo, String item,
			String endereco, int quantEndereco) {
		this.deposito = deposito;
		this.quantEstoque = quantEstoque;
		this.quantEmpenhada = quantEmpenhada;
		this.quantSugerida = quantSugerida;
		this.saldo = saldo;
		this.referencia = referencia;
		this.numeroTag = numeroTag;
		this.situacao = situacao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.item = item;
		this.endereco = endereco;
		this.quantEndereco = quantEndereco;
	}
}
