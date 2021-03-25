package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_plano_mestre_itens")
public class PlanoMestreConsultaItens {

	@Id
	public long id;
	
	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;

	public String codigo;
	
	public String grupo;	
	public String item;    
	
	public String embarque; 	
	public int rank;	
	
	@Column(name = "sug_cancel_prod")
	public String sugCancelProducao;	
	
	@Column(name = "qtde_previsao")
	public int qtdePrevisao;
	
	@Column(name = "qtde_estoque")
	public int qtdeEstoque;
	
	@Column(name = "qtde_dem_plano1")
	public int qtdeDemPlano1;
	
	@Column(name = "qtde_dem_plano2")
	public int qtdeDemPlano2;
	
	@Column(name = "qtde_dem_plano3")
	public int qtdeDemPlano3;
	
	@Column(name = "qtde_dem_plano4")
	public int qtdeDemPlano4;
	
	@Column(name = "qtde_dem_plano5")
	public int qtdeDemPlano5;
	
	@Column(name = "qtde_dem_plano6")
	public int qtdeDemPlano6;
	
	@Column(name = "qtde_dem_plano7")
	public int qtdeDemPlano7;
	
	@Column(name = "qtde_dem_plano8")
	public int qtdeDemPlano8;
	
	
	@Column(name = "qtde_proc_plano1")
	public int qtdeProcPlano1;
	
	@Column(name = "qtde_proc_plano2")
	public int qtdeProcPlano2;
	
	@Column(name = "qtde_proc_plano3")
	public int qtdeProcPlano3;
	
	@Column(name = "qtde_proc_plano4")
	public int qtdeProcPlano4;
	
	@Column(name = "qtde_proc_plano5")
	public int qtdeProcPlano5;
	
	@Column(name = "qtde_proc_plano6")
	public int qtdeProcPlano6;
	
	@Column(name = "qtde_proc_plano7")
	public int qtdeProcPlano7;
	
	@Column(name = "qtde_proc_plano8")
	public int qtdeProcPlano8;
	
	
	@Column(name = "qtde_saldo_plano1")
	public int qtdeSaldoPlano1;
	
	@Column(name = "qtde_saldo_plano2")
	public int qtdeSaldoPlano2;
	
	@Column(name = "qtde_saldo_plano3")
	public int qtdeSaldoPlano3;
	
	@Column(name = "qtde_saldo_plano4")
	public int qtdeSaldoPlano4;
	
	@Column(name = "qtde_saldo_plano5")
	public int qtdeSaldoPlano5;
	
	@Column(name = "qtde_saldo_plano6")
	public int qtdeSaldoPlano6;
	
	@Column(name = "qtde_saldo_plano7")
	public int qtdeSaldoPlano7;
	
	@Column(name = "qtde_saldo_plano8")
	public int qtdeSaldoPlano8;
	
	
	@Column(name = "qtde_dem_acumulado")
	public int qtdeDemAcumulado;
	
	@Column(name = "qtde_proc_acumulado")
	public int qtdeProcAcumulado;
	
	@Column(name = "qtde_saldo_acumulado")
	public int qtdeSaldoAcumulado;
	
	@Column(name = "qtde_sugestao")
	public int qtdeSugestao;
	
	@Column(name = "qtde_equalizado_sugestao")
	public int qtdeEqualizadoSugestao;
	
	@Column(name = "qtde_dif_sugestao")
	public int qtdeDiferencaSugestao;
	
	@Column(name = "qtde_programada")
	public int qtdeProgramada;		
	
}