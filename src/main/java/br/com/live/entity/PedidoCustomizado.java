package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_280")
public class PedidoCustomizado {
	
	@Id	
	public long id;
	
	public int solicitacao; 
	
	@Column(name = "pedido_venda")
	public int pedidoVenda;
	
	@Column(name = "data_emis_venda")
	public Date dataEmisVenda;
	
	@Column(name = "data_entr_venda")
	public Date dataEntrVenda;
	
	@Column(name = "cli_ped_cgc_cli9")
	public int cliPedCgcCli9;
	
	@Column(name = "cli_ped_cgc_cli4")
	public int cliPedCgcCli4;
	
	@Column(name = "cli_ped_cgc_cli2")
	public int cliPedCgcCli2;
	
	@Column(name = "cd_it_pe_grupo")
	public String cdItPeGrupo;
	
	@Column(name = "cd_it_pe_subgrupo")
	public String cdItPeSubgrupo;
	
	@Column(name = "cd_it_pe_item")
	public String cdItPeItem;
	
	@Column(name = "codigo_deposito")
	public int codigoDeposito;   
	
	@Column(name = "qtde_pedida")
	public int qtdePedida;
	                      
	@Column(name = "caminho_arquivo")
	public String caminhoArquivo;
	
	@Column(name = "ordem_producao")
	public int ordemProducao;           
	              
	public int periodo;
	public int situacao;
	public int selecao;
	
	@Column(name = "data_registro")
	public Date dataRegistro;                         
	
	public int alternativa;
	public int roteiro;
	                     
	@Column(name = "seq_item_pedido")
	public int seqItemPedido;
	
	@Column(name = "flag_imagem")
	public int flagImagem;                         
	
	@Column(name = "ordem_tamanho")
	public int ordemTamanho;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(int solicitacao) {
		this.solicitacao = solicitacao;
	}

	public int getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}

	public Date getDataEmisVenda() {
		return dataEmisVenda;
	}

	public void setDataEmisVenda(Date dataEmisVenda) {
		this.dataEmisVenda = dataEmisVenda;
	}

	public Date getDataEntrVenda() {
		return dataEntrVenda;
	}

	public void setDataEntrVenda(Date dataEntrVenda) {
		this.dataEntrVenda = dataEntrVenda;
	}

	public int getCliPedCgcCli9() {
		return cliPedCgcCli9;
	}

	public void setCliPedCgcCli9(int cliPedCgcCli9) {
		this.cliPedCgcCli9 = cliPedCgcCli9;
	}

	public int getCliPedCgcCli4() {
		return cliPedCgcCli4;
	}

	public void setCliPedCgcCli4(int cliPedCgcCli4) {
		this.cliPedCgcCli4 = cliPedCgcCli4;
	}

	public int getCliPedCgcCli2() {
		return cliPedCgcCli2;
	}

	public void setCliPedCgcCli2(int cliPedCgcCli2) {
		this.cliPedCgcCli2 = cliPedCgcCli2;
	}

	public String getCdItPeGrupo() {
		return cdItPeGrupo;
	}

	public void setCdItPeGrupo(String cdItPeGrupo) {
		this.cdItPeGrupo = cdItPeGrupo;
	}

	public String getCdItPeSubgrupo() {
		return cdItPeSubgrupo;
	}

	public void setCdItPeSubgrupo(String cdItPeSubgrupo) {
		this.cdItPeSubgrupo = cdItPeSubgrupo;
	}

	public String getCdItPeItem() {
		return cdItPeItem;
	}

	public void setCdItPeItem(String cdItPeItem) {
		this.cdItPeItem = cdItPeItem;
	}

	public int getCodigoDeposito() {
		return codigoDeposito;
	}

	public void setCodigoDeposito(int codigoDeposito) {
		this.codigoDeposito = codigoDeposito;
	}

	public int getQtdePedida() {
		return qtdePedida;
	}

	public void setQtdePedida(int qtdePedida) {
		this.qtdePedida = qtdePedida;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public int getSelecao() {
		return selecao;
	}

	public void setSelecao(int selecao) {
		this.selecao = selecao;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public int getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}

	public int getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}

	public int getSeqItemPedido() {
		return seqItemPedido;
	}

	public void setSeqItemPedido(int seqItemPedido) {
		this.seqItemPedido = seqItemPedido;
	}

	public int getFlagImagem() {
		return flagImagem;
	}

	public void setFlagImagem(int flagImagem) {
		this.flagImagem = flagImagem;
	}

	public int getOrdemTamanho() {
		return ordemTamanho;
	}

	public void setOrdemTamanho(int ordemTamanho) {
		this.ordemTamanho = ordemTamanho;
	}

	public PedidoCustomizado() {
		
	}

	public PedidoCustomizado(long id, int solicitacao, int pedidoVenda, Date dataEmisVenda, Date dataEntrVenda,
			int cliPedCgcCli9, int cliPedCgcCli4, int cliPedCgcCli2, String cdItPeGrupo, String cdItPeSubgrupo,
			String cdItPeItem, int codigoDeposito, int qtdePedida, String caminhoArquivo, int ordemProducao,
			int periodo, int situacao, int selecao, Date dataRegistro, int alternativa, int roteiro, int seqItemPedido,
			int flagImagem, int ordemTamanho) {
		this.id = id;
		this.solicitacao = solicitacao;
		this.pedidoVenda = pedidoVenda;
		this.dataEmisVenda = dataEmisVenda;
		this.dataEntrVenda = dataEntrVenda;
		this.cliPedCgcCli9 = cliPedCgcCli9;
		this.cliPedCgcCli4 = cliPedCgcCli4;
		this.cliPedCgcCli2 = cliPedCgcCli2;
		this.cdItPeGrupo = cdItPeGrupo;
		this.cdItPeSubgrupo = cdItPeSubgrupo;
		this.cdItPeItem = cdItPeItem;
		this.codigoDeposito = codigoDeposito;
		this.qtdePedida = qtdePedida;
		this.caminhoArquivo = caminhoArquivo;
		this.ordemProducao = ordemProducao;
		this.periodo = periodo;
		this.situacao = situacao;
		this.selecao = selecao;
		this.dataRegistro = dataRegistro;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.seqItemPedido = seqItemPedido;
		this.flagImagem = flagImagem;
		this.ordemTamanho = ordemTamanho;
	}
	
}
