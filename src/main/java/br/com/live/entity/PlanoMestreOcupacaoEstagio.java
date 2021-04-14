package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_025")
public class PlanoMestreOcupacaoEstagio {

	@Id
	public String id;

	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;
	public int estagio;

	@Column(name = "qtde_capacidade_pecas")
	public int qtdeCapacidadePecas;	
			
	@Column(name = "qtde_plano_pecas")
	public int qtdePlanoPecas;
	
	@Column(name = "qtde_programado_pecas")
	public int qtdeProgramadoPecas;
			
	@Column(name = "perc_ocupacao_pecas")
	public double percOcupacaoPecas;								
	
	@Column(name = "qtde_falta_sobra_pecas")
	public int qtdeFaltaSobraPecas;
			
	@Column(name = "qtde_capacidade_minutos")
	public int qtdeCapacidadeMinutos;
									
	@Column(name = "qtde_plano_minutos")
	public int qtdePlanoMinutos;
	
	@Column(name = "qtde_programado_minutos") 
	public int qtdeProgramadoMinutos;
	
	@Column(name = "perc_ocupacao_minutos")
	public double percOcupacaoMinutos;
	
	@Column(name = "qtde_falta_sobra_minutos")
	public int qtdeFaltaSobraMinutos;	
	
}