package br.com.live.model;

public class ConsultaProgramasUsuario {
	private long idUsuario;
	private long idPrograma;
	private String path;
	private String descricao;
	private String modulo;
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(long idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}
