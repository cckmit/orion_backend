package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_046")
public class CapacidadeCotasVendasItens {
	
	@Id	
	public String id;
	
	@Column(name="id_capacidade_cotas")
	public String idCapa;	
	public String referencia;
	public String tamanho;
	public String cor;	
	@Column(name="tempo_unitario")
	public float tempoUnitario;
	
	@Column(name="qtde_estoque")
	public int qtdeEstoque;
	@Column(name="qtde_demanda")
	public int qtdeDemanda;
	@Column(name="qtde_processo")
	public int qtdeProcesso;
	@Column(name="qtde_saldo")
	public int qtdeSaldo;
	@Column(name="qtde_minutos")
	public float qtdeMinutos;	
	@Column(name="qtde_pecas")
	public int qtdePecas;
	@Column(name="bloqueio_venda")
	public int bloqueioVenda;
	
	public CapacidadeCotasVendasItens() {
		
	}
	
	public CapacidadeCotasVendasItens(String idCapa, String referencia, String tamanho, String cor, float tempoUnitario,  int qtdeEstoque, int qtdeDemanda, int qtdeProcesso, float qtdeMinutos, int qtdePecas, int bloqueioVenda) {
		this.id = idCapa + "-" + referencia  + "-" + tamanho  + "-" +  cor;
		this.idCapa = idCapa;
		this.referencia = referencia;
		this.tamanho = tamanho;
		this.cor = cor;		
		this.tempoUnitario = tempoUnitario;				
		this.qtdeEstoque = qtdeEstoque;  
		this.qtdeDemanda = qtdeDemanda; 
		this.qtdeProcesso = qtdeProcesso;
		this.qtdeSaldo = (qtdeEstoque + qtdeProcesso) - qtdeDemanda; 				
		this.qtdePecas = qtdePecas;
		this.qtdeMinutos = ((float) qtdePecas * tempoUnitario);		
		this.bloqueioVenda = bloqueioVenda; 		
	}

}