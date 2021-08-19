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
	
	public UsuarioBi() {
		
	}
	
	public UsuarioBi(long codUsuario, String nome, String usuario, String senha, String email, int situacao, int administrador) {
		
		this.codUsuario = codUsuario;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.email = email;
		this.situacao = situacao;
		this.administrador = administrador;
	}
}
