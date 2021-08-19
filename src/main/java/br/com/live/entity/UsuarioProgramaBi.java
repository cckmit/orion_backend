package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_004")
public class UsuarioProgramaBi {

	@Id	
	public String id;
	
	@Column(name = "cod_usuario")
	public long codUsuario;
	
	@Column(name = "id_programa")
	public String idPrograma;
	
	public UsuarioProgramaBi() {
		
	}
	
	public UsuarioProgramaBi(long codUsuario, String idPrograma) {
		this.id = codUsuario + "-" + idPrograma;
		this.codUsuario = codUsuario;
		this.idPrograma = idPrograma;
	}
	
}