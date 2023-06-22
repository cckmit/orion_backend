package br.com.live.sistema.model;

public class SalvarTipoEmailBi {
	
	private String id;
	private String descricao;
	private int codTipoEmail;
	private String idPrograma;
	private String permRelacUsuarios;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCodTipoEmail() {
		return codTipoEmail;
	}

	public void setCodTipoEmail(int codTipoEmail) {
		this.codTipoEmail = codTipoEmail;
	}

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getPermRelacUsuarios() {
		return permRelacUsuarios;
	}

	public void setPermRelacUsuarios(String permRelacUsuarios) {
		this.permRelacUsuarios = permRelacUsuarios;
	}
	
	public SalvarTipoEmailBi () {}
	
	public SalvarTipoEmailBi (String id, String descricao, int codTipoEmail, String idPrograma, String permRelacUsuarios) {
		this.id = id;
		this.descricao = descricao;
		this.codTipoEmail = codTipoEmail;
		this.idPrograma = idPrograma;
		this.permRelacUsuarios = permRelacUsuarios;
	}

}
