package br.com.live.comercial.model;

import java.util.Date;

public class ConsultaPedidoCustomizado {
	
	public int id;
	public int solicitacao;
	public int pedidoVenda;
	public Date dataEmisVenda;
	public Date dataEntrVenda;
	public String cliente;
	public String cdItPeGrupo;
	public String cdItPeSubgrupo;
	public String cdItPeItem;
	public int codigoDeposito;
	public int qtdePedida;
	public String caminhoArquivo;
	public int ordemProducao;
	public int periodo;
	public int situacao;
	public int selecao;
	public Date dataRegistro;
	public int alternativa;
	public int roteiro;
	public int seqItemPedido;
	public int flagImagem;
	public int ordemTamanho;
	public int cliPedCgcCli9;
	public int cliPedCgcCli4;
	public int cliPedCgcCli2;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	public ConsultaPedidoCustomizado() {
		
	}
	public ConsultaPedidoCustomizado(int id, int solicitacao, int pedidoVenda, Date dataEmisVenda, Date dataEntrVenda,
			String cliente, String cdItPeGrupo, String cdItPeSubgrupo, String cdItPeItem, int codigoDeposito,
			int qtdePedida, String caminhoArquivo, int ordemProducao, int periodo, int situacao, int selecao,
			Date dataRegistro, int alternativa, int roteiro, int seqItemPedido, int flagImagem, int ordemTamanho, int cliPedCgcCli9, int cliPedCgcCli4, int cliPedCgcCli2) {
	
		this.id = id;
		this.solicitacao = solicitacao;
		this.pedidoVenda = pedidoVenda;
		this.dataEmisVenda = dataEmisVenda;
		this.dataEntrVenda = dataEntrVenda;
		this.cliente = cliente;
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
		this.cliPedCgcCli9 = cliPedCgcCli9;
		this.cliPedCgcCli4 = cliPedCgcCli4;
		this.cliPedCgcCli2 = cliPedCgcCli2;
	}
	
}
