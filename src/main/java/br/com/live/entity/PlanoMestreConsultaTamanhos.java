package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_plano_mestre_tam")
public class PlanoMestreConsultaTamanhos {

	@Id
	public long id;
	
	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;

	public String grupo;	
	public String sub;
	public String item;    

	public int ordem;
	
	@Column(name = "qtde_estoque")
	public int qtdeEstoque;
	
	@Column(name = "qtde_demanda")
	public int qtdeDemanda;
	
	@Column(name = "qtde_processo")
	public int qtdeProcesso;

	@Column(name = "qtde_saldo")
	public int qtdeSaldo;

	@Column(name = "qtde_sugestao")
	public int qtdeSugestao;
	
	@Column(name = "qtde_equalizado")		
	public int qtdeEqualizado;
	
	@Column(name = "qtde_diferenca")
	public int qtdeDiferenca;
	
	@Column(name = "qtde_programada")
	public int qtdeProgramada;
			
}