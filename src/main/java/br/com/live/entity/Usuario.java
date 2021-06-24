package br.com.live.entity;

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
	
	public Usuario() {
		
	}
	
	public Usuario(long id, String nome, String usuario, String senha, int situacao) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.situacao = situacao;
	}
	
}