package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_001")
public class Usuario {

	@Id	
	public long id;
	
	public String nome;
	public String usuario;
	public String senha;
	public int situacao;
	public String email;
	
	@Column(name = "libera_inspecao_qualid")	
	public int liberaInspecaoQualidade;
	
	@Column(name = "usuario_repositor")	
	public int usuarioRepositor;
	
	@Column(name = "usuario_systextil")	
	public String usuarioSystextil;
	
	@Column(name = "empresa_systextil")	
	public int empresaSystextil;
	
	public Usuario() {
		
	}
	
	public Usuario(long id, String nome, String usuario, String senha, int situacao, int liberaInspecaoQualidade, String email, int usuarioRepositor, String usuarioSystextil, int empresaSystextil) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.situacao = situacao;
		this.liberaInspecaoQualidade = liberaInspecaoQualidade;
		this.email = email;
		this.usuarioRepositor = usuarioRepositor;
		this.usuarioSystextil = usuarioSystextil;
		this.empresaSystextil = empresaSystextil;
	}
	
}