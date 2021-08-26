package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_052")
public class InspecaoQualidadeMedidaBKP {

	@Id
	public long id;
	
	@Column(name = "id_medida")
	public long id_medida;
		
	@Column(name = "id_inspecao")	
	public long idInspecao;
	
	public String usuario;
	
	@Column(name = "data_hora")
	public Date dataHora;
	
	public int sequencia;
	public String Descricao;
	public float padrao;
	
	@Column(name = "toler_max")
	public float toleranciaMaxima;
	
	@Column(name = "toler_min")
	public float toleranciaMinima;
	
	public float real;
	public float variacao;
}
