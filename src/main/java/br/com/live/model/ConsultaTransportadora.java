package br.com.live.model;

public class ConsultaTransportadora {
	public String nome;
	public String endereco;
	public String complemento;
	public String cidade;
	public int cep;
	public String bairro;
	public String estado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public ConsultaTransportadora() {
		
	}
	
	public ConsultaTransportadora(String nome, String endereco, String complemento, String cidade, int cep,
			String bairro, String estado) {
		this.nome = nome;
		this.endereco = endereco;
		this.complemento = complemento;
		this.cidade = cidade;
		this.cep = cep;
		this.bairro = bairro;
		this.estado = estado;
	}
}
