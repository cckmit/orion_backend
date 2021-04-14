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
	
	// Pré-Ordens
	@Column(name="agrupa_por_refer")
	public int agrupaOpPorRefer; 
	@Column(name="qtde_maxima_op")
	public int qtdeMaximaOP;
	@Column(name="qtde_minima_op")
	public int qtdeMinimaOP;
	@Column(name="qtde_maxima_cor")
	public int qtdeMaximaCor;
	@Column(name="periodo_op")
	public int periodoOP;		
	@Column(name="deposito_op")
	public int depositoOP;		
	@Column(name="observacao_op")
	public String observacaoOP;
	
	// Ocupação
	@Column(name="periodo_inicio_ocupacao")
	public int periodoInicioOcupacao;	
	@Column(name="periodo_fim_ocupacao")
	public int periodoFimOcupacao;	

	public int getPeriodoInicioOcupacao() {
		return periodoInicioOcupacao;
	}
	public void setPeriodoInicioOcupacao(int periodoInicioOcupacao) {
		this.periodoInicioOcupacao = periodoInicioOcupacao;
	}
	public int getPeriodoFimOcupacao() {
		return periodoFimOcupacao;
	}
	public void setPeriodoFimOcupacao(int periodoFimOcupacao) {
		this.periodoFimOcupacao = periodoFimOcupacao;
	}	
	public long getIdPlanoMestre() {
		return idPlanoMestre;
	}
	public void setIdPlanoMestre(long idPlanoMestre) {
		this.idPlanoMestre = idPlanoMestre;
	}
	public int getPeriodoPadrao() {
		return periodoPadrao;
	}
	public void setPeriodoPadrao(int periodoPadrao) {
		this.periodoPadrao = periodoPadrao;
	}
	public int getTipoDistribuicao() {
		return tipoDistribuicao;
	}
	public void setTipoDistribuicao(int tipoDistribuicao) {
		this.tipoDistribuicao = tipoDistribuicao;
	}
	public String getDescTipoDistribuicao() {
		return descTipoDistribuicao;
	}
	public void setDescTipoDistribuicao(String descTipoDistribuicao) {
		this.descTipoDistribuicao = descTipoDistribuicao;
	}
	public int getMultiplicador() {
		return multiplicador;
	}
	public void setMultiplicador(int multiplicador) {
		this.multiplicador = multiplicador;
	}
	public String getColecoes() {
		return colecoes;
	}
	public void setColecoes(String colecoes) {
		this.colecoes = colecoes;
	}
	public String getColecoes_permanentes() {
		return colecoes_permanentes;
	}
	public void setColecoes_permanentes(String colecoes_permanentes) {
		this.colecoes_permanentes = colecoes_permanentes;
	}
	public String getLinhas_produtos() {
		return linhas_produtos;
	}
	public void setLinhas_produtos(String linhas_produtos) {
		this.linhas_produtos = linhas_produtos;
	}
	public String getArtigos_produtos() {
		return artigos_produtos;
	}
	public void setArtigos_produtos(String artigos_produtos) {
		this.artigos_produtos = artigos_produtos;
	}
	public String getArtigos_cotas() {
		return artigos_cotas;
	}
	public void setArtigos_cotas(String artigos_cotas) {
		this.artigos_cotas = artigos_cotas;
	}
	public String getPublicos_alvos() {
		return publicos_alvos;
	}
	public void setPublicos_alvos(String publicos_alvos) {
		this.publicos_alvos = publicos_alvos;
	}
	public String getEmbarques() {
		return embarques;
	}
	public void setEmbarques(String embarques) {
		this.embarques = embarques;
	}
	public String getReferencias() {
		return referencias;
	}
	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}
	public String getCores() {
		return cores;
	}
	public void setCores(String cores) {
		this.cores = cores;
	}
	public String getOrigens_produtos() {
		return origens_produtos;
	}
	public void setOrigens_produtos(String origens_produtos) {
		this.origens_produtos = origens_produtos;
	}
	public int getPlano1_dem_inicio() {
		return plano1_dem_inicio;
	}
	public void setPlano1_dem_inicio(int plano1_dem_inicio) {
		this.plano1_dem_inicio = plano1_dem_inicio;
	}
	public int getPlano2_dem_inicio() {
		return plano2_dem_inicio;
	}
	public void setPlano2_dem_inicio(int plano2_dem_inicio) {
		this.plano2_dem_inicio = plano2_dem_inicio;
	}
	public int getPlano3_dem_inicio() {
		return plano3_dem_inicio;
	}
	public void setPlano3_dem_inicio(int plano3_dem_inicio) {
		this.plano3_dem_inicio = plano3_dem_inicio;
	}
	public int getPlano4_dem_inicio() {
		return plano4_dem_inicio;
	}
	public void setPlano4_dem_inicio(int plano4_dem_inicio) {
		this.plano4_dem_inicio = plano4_dem_inicio;
	}
	public int getPlano5_dem_inicio() {
		return plano5_dem_inicio;
	}
	public void setPlano5_dem_inicio(int plano5_dem_inicio) {
		this.plano5_dem_inicio = plano5_dem_inicio;
	}
	public int getPlano6_dem_inicio() {
		return plano6_dem_inicio;
	}
	public void setPlano6_dem_inicio(int plano6_dem_inicio) {
		this.plano6_dem_inicio = plano6_dem_inicio;
	}
	public int getPlano7_dem_inicio() {
		return plano7_dem_inicio;
	}
	public void setPlano7_dem_inicio(int plano7_dem_inicio) {
		this.plano7_dem_inicio = plano7_dem_inicio;
	}
	public int getPlano8_dem_inicio() {
		return plano8_dem_inicio;
	}
	public void setPlano8_dem_inicio(int plano8_dem_inicio) {
		this.plano8_dem_inicio = plano8_dem_inicio;
	}
	public int getPlano1_dem_fim() {
		return plano1_dem_fim;
	}
	public void setPlano1_dem_fim(int plano1_dem_fim) {
		this.plano1_dem_fim = plano1_dem_fim;
	}
	public int getPlano2_dem_fim() {
		return plano2_dem_fim;
	}
	public void setPlano2_dem_fim(int plano2_dem_fim) {
		this.plano2_dem_fim = plano2_dem_fim;
	}
	public int getPlano3_dem_fim() {
		return plano3_dem_fim;
	}
	public void setPlano3_dem_fim(int plano3_dem_fim) {
		this.plano3_dem_fim = plano3_dem_fim;
	}
	public int getPlano4_dem_fim() {
		return plano4_dem_fim;
	}
	public void setPlano4_dem_fim(int plano4_dem_fim) {
		this.plano4_dem_fim = plano4_dem_fim;
	}
	public int getPlano5_dem_fim() {
		return plano5_dem_fim;
	}
	public void setPlano5_dem_fim(int plano5_dem_fim) {
		this.plano5_dem_fim = plano5_dem_fim;
	}
	public int getPlano6_dem_fim() {
		return plano6_dem_fim;
	}
	public void setPlano6_dem_fim(int plano6_dem_fim) {
		this.plano6_dem_fim = plano6_dem_fim;
	}
	public int getPlano7_dem_fim() {
		return plano7_dem_fim;
	}
	public void setPlano7_dem_fim(int plano7_dem_fim) {
		this.plano7_dem_fim = plano7_dem_fim;
	}
	public int getPlano8_dem_fim() {
		return plano8_dem_fim;
	}
	public void setPlano8_dem_fim(int plano8_dem_fim) {
		this.plano8_dem_fim = plano8_dem_fim;
	}
	public int getPlano1_proc_inicio() {
		return plano1_proc_inicio;
	}
	public void setPlano1_proc_inicio(int plano1_proc_inicio) {
		this.plano1_proc_inicio = plano1_proc_inicio;
	}
	public int getPlano2_proc_inicio() {
		return plano2_proc_inicio;
	}
	public void setPlano2_proc_inicio(int plano2_proc_inicio) {
		this.plano2_proc_inicio = plano2_proc_inicio;
	}
	public int getPlano3_proc_inicio() {
		return plano3_proc_inicio;
	}
	public void setPlano3_proc_inicio(int plano3_proc_inicio) {
		this.plano3_proc_inicio = plano3_proc_inicio;
	}
	public int getPlano4_proc_inicio() {
		return plano4_proc_inicio;
	}
	public void setPlano4_proc_inicio(int plano4_proc_inicio) {
		this.plano4_proc_inicio = plano4_proc_inicio;
	}
	public int getPlano5_proc_inicio() {
		return plano5_proc_inicio;
	}
	public void setPlano5_proc_inicio(int plano5_proc_inicio) {
		this.plano5_proc_inicio = plano5_proc_inicio;
	}
	public int getPlano6_proc_inicio() {
		return plano6_proc_inicio;
	}
	public void setPlano6_proc_inicio(int plano6_proc_inicio) {
		this.plano6_proc_inicio = plano6_proc_inicio;
	}
	public int getPlano7_proc_inicio() {
		return plano7_proc_inicio;
	}
	public void setPlano7_proc_inicio(int plano7_proc_inicio) {
		this.plano7_proc_inicio = plano7_proc_inicio;
	}
	public int getPlano8_proc_inicio() {
		return plano8_proc_inicio;
	}
	public void setPlano8_proc_inicio(int plano8_proc_inicio) {
		this.plano8_proc_inicio = plano8_proc_inicio;
	}
	public int getPlano1_proc_fim() {
		return plano1_proc_fim;
	}
	public void setPlano1_proc_fim(int plano1_proc_fim) {
		this.plano1_proc_fim = plano1_proc_fim;
	}
	public int getPlano2_proc_fim() {
		return plano2_proc_fim;
	}
	public void setPlano2_proc_fim(int plano2_proc_fim) {
		this.plano2_proc_fim = plano2_proc_fim;
	}
	public int getPlano3_proc_fim() {
		return plano3_proc_fim;
	}
	public void setPlano3_proc_fim(int plano3_proc_fim) {
		this.plano3_proc_fim = plano3_proc_fim;
	}
	public int getPlano4_proc_fim() {
		return plano4_proc_fim;
	}
	public void setPlano4_proc_fim(int plano4_proc_fim) {
		this.plano4_proc_fim = plano4_proc_fim;
	}
	public int getPlano5_proc_fim() {
		return plano5_proc_fim;
	}
	public void setPlano5_proc_fim(int plano5_proc_fim) {
		this.plano5_proc_fim = plano5_proc_fim;
	}
	public int getPlano6_proc_fim() {
		return plano6_proc_fim;
	}
	public void setPlano6_proc_fim(int plano6_proc_fim) {
		this.plano6_proc_fim = plano6_proc_fim;
	}
	public int getPlano7_proc_fim() {
		return plano7_proc_fim;
	}
	public void setPlano7_proc_fim(int plano7_proc_fim) {
		this.plano7_proc_fim = plano7_proc_fim;
	}
	public int getPlano8_proc_fim() {
		return plano8_proc_fim;
	}
	public void setPlano8_proc_fim(int plano8_proc_fim) {
		this.plano8_proc_fim = plano8_proc_fim;
	}
	public int getConsidera_deposito() {
		return considera_deposito;
	}
	public void setConsidera_deposito(int considera_deposito) {
		this.considera_deposito = considera_deposito;
	}
	public int getConsidera_prod_sem_estq() {
		return considera_prod_sem_estq;
	}
	public void setConsidera_prod_sem_estq(int considera_prod_sem_estq) {
		this.considera_prod_sem_estq = considera_prod_sem_estq;
	}
	public String getDepositos() {
		return depositos;
	}
	public void setDepositos(String depositos) {
		this.depositos = depositos;
	}
	public int getConsidera_prod_sem_proc() {
		return considera_prod_sem_proc;
	}
	public void setConsidera_prod_sem_proc(int considera_prod_sem_proc) {
		this.considera_prod_sem_proc = considera_prod_sem_proc;
	}
	public int getConsidera_pedido_bloq() {
		return considera_pedido_bloq;
	}
	public void setConsidera_pedido_bloq(int considera_pedido_bloq) {
		this.considera_pedido_bloq = considera_pedido_bloq;
	}
	public int getConsidera_prod_sem_pedi() {
		return considera_prod_sem_pedi;
	}
	public void setConsidera_prod_sem_pedi(int considera_prod_sem_pedi) {
		this.considera_prod_sem_pedi = considera_prod_sem_pedi;
	}
	public int getNumero_interno() {
		return numero_interno;
	}
	public void setNumero_interno(int numero_interno) {
		this.numero_interno = numero_interno;
	}
	public String getPedidos() {
		return pedidos;
	}
	public void setPedidos(String pedidos) {
		this.pedidos = pedidos;
	}
	public int getAgrupaOpPorRefer() {
		return agrupaOpPorRefer;
	}
	public void setAgrupaOpPorRefer(int agrupaOpPorRefer) {
		this.agrupaOpPorRefer = agrupaOpPorRefer;
	}
	public int getQtdeMaximaOP() {
		return qtdeMaximaOP;
	}
	public void setQtdeMaximaOP(int qtdeMaximaOP) {
		this.qtdeMaximaOP = qtdeMaximaOP;
	}
	public int getQtdeMinimaOP() {
		return qtdeMinimaOP;
	}
	public void setQtdeMinimaOP(int qtdeMinimaOP) {
		this.qtdeMinimaOP = qtdeMinimaOP;
	}
	public int getQtdeMaximaCor() {
		return qtdeMaximaCor;
	}
	public void setQtdeMaximaCor(int qtdeMaximaCor) {
		this.qtdeMaximaCor = qtdeMaximaCor;
	}
	public int getPeriodoOP() {
		return periodoOP;
	}
	public void setPeriodoOP(int periodoOP) {
		this.periodoOP = periodoOP;
	}
	public int getDepositoOP() {
		return depositoOP;
	}
	public void setDepositoOP(int depositoOP) {
		this.depositoOP = depositoOP;
	}
	public String getObservacaoOP() {
		return observacaoOP;
	}
	public void setObservacaoOP(String observacaoOP) {
		this.observacaoOP = observacaoOP;
	}
	 
}
