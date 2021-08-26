package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_051")
public class InspecaoQualidadePecaLancto {
	
	@Id
	@Column(name = "id_lancamento")
	public long id;
			
	@Column(name = "id_inspecao")	
	public long idInspecao;
	
	@Column(name = "cod_motivo")
	public int codMotivo;
		
	public int quantidade;	
	
	public String usuario;
	
	@Column(name = "data_hora")
	public Date dataHora; 
}