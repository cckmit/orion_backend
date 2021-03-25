package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_plano_mestre_itens")
@SequenceGenerator(name = "ID_ORION_016", sequenceName = "ID_ORION_016", initialValue = 1, allocationSize = 1)
public class ProdutoPlanoMestrePorCor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORION_016")
	public long id;
	
	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;

	public String codigo;	
	public String grupo;	
	public String item;    
	
	public int rank;	
	
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
	
	public ProdutoPlanoMestrePorCor () {
		this.codigo = "";		
		this.grupo = "";
		this.item = "";	
		
		this.qtdePrevisao = 0;
		this.qtdeEstoque = 0;
		
		this.qtdeDemPlano1 = 0;
		this.qtdeDemPlano2 = 0;
		this.qtdeDemPlano3 = 0;
		this.qtdeDemPlano4 = 0;
		this.qtdeDemPlano5 = 0;
		this.qtdeDemPlano6 = 0;
		this.qtdeDemPlano7 = 0;
		this.qtdeDemPlano8 = 0;
		
		this.qtdeProcPlano1 = 0;
		this.qtdeProcPlano2 = 0;
		this.qtdeProcPlano3 = 0;
		this.qtdeProcPlano4 = 0;
		this.qtdeProcPlano5 = 0;
		this.qtdeProcPlano6 = 0;
		this.qtdeProcPlano7 = 0;
		this.qtdeProcPlano8 = 0;
		
		this.qtdeSaldoPlano1 = 0;
		this.qtdeSaldoPlano2 = 0;
		this.qtdeSaldoPlano3 = 0;
		this.qtdeSaldoPlano4 = 0;
		this.qtdeSaldoPlano5 = 0;
		this.qtdeSaldoPlano6 = 0;
		this.qtdeSaldoPlano7 = 0;
		this.qtdeSaldoPlano8 = 0;
		
		this.qtdeDemAcumulado = 0;
		this.qtdeProcAcumulado = 0;
		this.qtdeSaldoAcumulado = 0;
		
		this.qtdeSugestao = 0;
		this.qtdeEqualizadoSugestao = 0;
		this.qtdeDiferencaSugestao = 0;
		this.qtdeProgramada = 0;	
	}
	
}