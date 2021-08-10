package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_003")
public class UsuarioPrograma {

	@Id	
	public String id;
	
	@Column(name = "id_usuario")
	public long idUsuario;
	
	@Column(name = "id_programa")
	public long idPrograma;
	
	public UsuarioPrograma() {
		
	}
	
	public UsuarioPrograma(long idUsuario, long idPrograma) {
		this.id = idUsuario + "-" + idPrograma;
		this.idUsuario = idUsuario;
		this.idPrograma = idPrograma;
	}
	
}