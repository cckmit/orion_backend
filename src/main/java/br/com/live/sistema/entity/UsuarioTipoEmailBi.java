package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_005")
public class UsuarioTipoEmailBi {

	@Id	
	public String id;
	
	@Column(name = "cod_usuario")
	public long codUsuario;
	
	@Column(name = "id_tipo_email")
	public String idTipoEmail;
	
	@Column(name = "id_programa")
	public String idPrograma;

	public UsuarioTipoEmailBi() {
		
	}
	
	public UsuarioTipoEmailBi(long codUsuario, String idTipoEmail, String idPrograma) {
		this.id = codUsuario + "-" + idTipoEmail;
		this.codUsuario = codUsuario;
		this.idTipoEmail = idTipoEmail;
		this.idPrograma = idPrograma;
	}
	
}