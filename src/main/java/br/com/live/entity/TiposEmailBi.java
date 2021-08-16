package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_002")
public class TiposEmailBi {

	@Id	
	public String id;
	
	@Column(name="id_programa")
	public String idPrograma;
	
	@Column(name="cod_tipo_email")
	public int codTipoEmail;
	
	public String descricao;
	
	public TiposEmailBi() {
		
	}
	
	public TiposEmailBi(String idPrograma, int codTipoEmail, String descricao) {
		this.id = idPrograma + Integer.toString(codTipoEmail);
		this.idPrograma = idPrograma;
		this.codTipoEmail = codTipoEmail;
		this.descricao = descricao;
	}
	
}
