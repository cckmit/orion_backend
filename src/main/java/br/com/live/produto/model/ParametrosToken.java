package br.com.live.produto.model;

public class ParametrosToken {
	int chave;
	String usuario;
	String senha;
	
	public int getChave() {
		return chave;
	}
	public void setChave(int chave) {
		this.chave = chave;
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
	
	public ParametrosToken() {
		
	}
	public ParametrosToken(int chave, String usuario, String senha) {
		this.chave = chave;
		this.usuario = usuario;
		this.senha = senha;
	}
}
