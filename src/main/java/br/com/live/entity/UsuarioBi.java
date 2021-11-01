package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_003")
public class UsuarioBi {

	@Id
	@Column(name="cod_usuario")
	public long codUsuario;
	public String nome;
	public String usuario;
	public String senha;
	public String email;
	public int situacao;
	public int administrador;
	
	public UsuarioBi() {}
	
	public UsuarioBi(long codUsuario, String nome, String usuario, String senha, String email, int situacao, int administrador) {
		super();
		this.codUsuario = codUsuario;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.email = email;
		this.situacao = situacao;
		this.administrador = administrador;
	}
	
	public long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public int getAdministrador() {
		return administrador;
	}

	public void setAdministrador(int administrador) {
		this.administrador = administrador;
	}
}
