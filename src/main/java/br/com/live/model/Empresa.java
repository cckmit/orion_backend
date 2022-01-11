package br.com.live.model;

public class Empresa {
	public int codigo;
	public String nome;
	public String cnpjEmpresa;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public Empresa() {

	}
	
	public Empresa(int codigo, String nome, String cnpjEmpresa) {
		this.codigo = codigo;
		this.nome = nome;
		this.cnpjEmpresa = cnpjEmpresa;
	}
}
