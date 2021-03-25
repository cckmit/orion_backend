package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_011")
public class PlanoMestreParametros {

	@Id
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
	 
	// Global
	@Column(name="periodo_padrao")
	public int periodoPadrao ; 
	 
	@Column(name="tipo_distribuicao")
	public int tipoDistribuicao ; 
	 
	@Column(name="desc_tipo_distribuicao")
	public String descTipoDistribuicao ; 	
	
	public int multiplicador;
	
	// Análise Produto
	public String colecoes;
	public String colecoes_permanentes;
	public String linhas_produtos;
	public String artigos_produtos;
	public String artigos_cotas;
	public String publicos_alvos;
	public String embarques;
	public String referencias;
	public String cores;
	public String origens_produtos;
	 
	 // Planejamento
	public int plano1_dem_inicio;
	public int plano2_dem_inicio;
	public int plano3_dem_inicio;
	public int plano4_dem_inicio;
	public int plano5_dem_inicio;
	public int plano6_dem_inicio;
	public int plano7_dem_inicio;
	public int plano8_dem_inicio;
	 
	public int plano1_dem_fim;
	public int plano2_dem_fim;
	public int plano3_dem_fim;
	public int plano4_dem_fim;
	public int plano5_dem_fim;
	public int plano6_dem_fim;
	public int plano7_dem_fim;
	public int plano8_dem_fim;
	 
	public int plano1_proc_inicio;
	public int plano2_proc_inicio;
	public int plano3_proc_inicio;
	public int plano4_proc_inicio;
	public int plano5_proc_inicio;
	public int plano6_proc_inicio;
	public int plano7_proc_inicio;
	public int plano8_proc_inicio;
	 
	public int plano1_proc_fim;
	public int plano2_proc_fim;
	public int plano3_proc_fim;
	public int plano4_proc_fim;
	public int plano5_proc_fim;
	public int plano6_proc_fim;
	public int plano7_proc_fim;
	public int plano8_proc_fim;
	 
	// Estoque
	public int considera_deposito;
	public int considera_prod_sem_estq;
	public String depositos;
	 
	// Em processo
	public int considera_prod_sem_proc;
	 
	// Demanda
	public int considera_pedido_bloq;
	public int considera_prod_sem_pedi;
	public int numero_interno;
	public String pedidos;
	
}
