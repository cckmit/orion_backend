package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_051")
public class InspecaoQualidadeLanctoPeca {
	
	@Id
	@Column(name = "id_lancamento")
	public long id;
			
	@Column(name = "id_inspecao")	
	public long idInspecao;
	
	@Column(name = "cod_motivo")
	public int codMotivo;
	
	@Column(name = "cod_estagio_defeito")	
	public int codEstagioDefeito;
		
	public int quantidade;	
	
	public String usuario;
	
	@Column(name = "revisor_origem")
	public String revisorOrigem;
	
	@Column(name = "data_hora")
	public Date dataHora;

}