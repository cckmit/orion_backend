package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_125")
public class OrionReportsUsuarioProgramas {
	
	@Id	
	public String id;
	
	@Column(name = "id_usuario")
	public int idUsuario;
	
	@Column(name = "id_programa")
	public String idPrograma;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}
	
	public OrionReportsUsuarioProgramas() {
		
	}
	public OrionReportsUsuarioProgramas(String id, int idUsuario, String idPrograma) {
		
		this.id = idUsuario + "-" + idPrograma;
		this.idUsuario = idUsuario;
		this.idPrograma = idPrograma;
	}	

}
