package br.com.live.administrativo.model;

import java.util.Date;
import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class ConsultaFechamentoComissoes {
	
	public int id;
	public int portador;
	public String dataEmissao;
	public String representante;
	public int tipoTitulo;
	public String cliente;
	public String titulo;
	public String vencimento;
	public float percComissao;
	public float valorEmAberto;
	public float valorComissao;
	public float mesAnterior;
	public float mesAtual;
	public float saldo;
	public String dataInicio;
	public String dataAnterior;
	public int historico;
	public String pedidoCliente;
	public int pedido;
	public int qtdeFaturada;
	public int docto;
	public int seq;
	public float valorDoc;
	public float percComPed;
	public int qtdeParcelas;
	public float totComissao;
	public String descricao;
	public String uf;
	public String regiao;
	public String linha;
	public float meta;
	public float vendas;
	public float porcAtingido;
	public float valor;
	public float porcLinha;
	public float valorProporcional;
	public float totalFaturado;
	public int notaSaida;
	public int notaEntrada;
	public int nfDevolucao;
	public float valorNf;
	public String motivo;
	public String dtEmissaoEntrada;
	public String dtEmissaoSaida;
	public String tipoFornecedor;
	public String dataLancto;
	public String campanha;
	public String tipo;	
	public float totalDebito;
	public float totalCredito;
	public String observacao;
	public float debito;
	public float credito;
	public String estacao;
	public String nivel;
	public String grupo;
	public String subGrupo;
	public String item;
	public String produto;
	public int tabCol;
	public int tabMes;
	public int tabSeq;
	public String tabPreco;
	public int quantidade;
	public int codRepresentante;
	public float precoUnt;
	public float total;
	public int linhaProduto;
	public int qtdeEnviada;
	public int qtdeDevolvida;
	public int diferenca;
	public float desc60Porcento;
	public float bonus30Porcento;
	public float bonus100Porcento;
	public float valorCobrado;
	public int numParcela;
	public int status;
	public Date dataInicial;
	public Date dataFinal;
	
	public List<ConteudoChaveNumerica> listRepresentante;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPortador() {
		return portador;
	}

	public void setPortador(int portador) {
		this.portador = portador;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public int getTipoTitulo() {
		return tipoTitulo;
	}

	public void setTipoTitulo(int tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public float getPercComissao() {
		return percComissao;
	}

	public void setPercComissao(float percComissao) {
		this.percComissao = percComissao;
	}

	public float getValorEmAberto() {
		return valorEmAberto;
	}

	public void setValorEmAberto(float valorEmAberto) {
		this.valorEmAberto = valorEmAberto;
	}

	public float getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(float valorComissao) {
		this.valorComissao = valorComissao;
	}

	public float getMesAnterior() {
		return mesAnterior;
	}

	public void setMesAnterior(float mesAnterior) {
		this.mesAnterior = mesAnterior;
	}

	public float getMesAtual() {
		return mesAtual;
	}

	public void setMesAtual(float mesAtual) {
		this.mesAtual = mesAtual;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataAnterior() {
		return dataAnterior;
	}

	public void setDataAnterior(String dataAnterior) {
		this.dataAnterior = dataAnterior;
	}

	public int getHistorico() {
		return historico;
	}

	public void setHistorico(int historico) {
		this.historico = historico;
	}

	public String getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public int getQtdeFaturada() {
		return qtdeFaturada;
	}

	public void setQtdeFaturada(int qtdeFaturada) {
		this.qtdeFaturada = qtdeFaturada;
	}

	public int getDocto() {
		return docto;
	}

	public void setDocto(int docto) {
		this.docto = docto;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public float getValorDoc() {
		return valorDoc;
	}

	public void setValorDoc(float valorDoc) {
		this.valorDoc = valorDoc;
	}

	public float getPercComPed() {
		return percComPed;
	}

	public void setPercComPed(float percComPed) {
		this.percComPed = percComPed;
	}

	public int getQtdeParcelas() {
		return qtdeParcelas;
	}

	public void setQtdeParcelas(int qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

	public float getTotComissao() {
		return totComissao;
	}

	public void setTotComissao(float totComissao) {
		this.totComissao = totComissao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public float getMeta() {
		return meta;
	}

	public void setMeta(float meta) {
		this.meta = meta;
	}

	public float getVendas() {
		return vendas;
	}

	public void setVendas(float vendas) {
		this.vendas = vendas;
	}

	public float getPorcAtingido() {
		return porcAtingido;
	}

	public void setPorcAtingido(float porcAtingido) {
		this.porcAtingido = porcAtingido;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getPorcLinha() {
		return porcLinha;
	}

	public void setPorcLinha(float porcLinha) {
		this.porcLinha = porcLinha;
	}

	public float getValorProporcional() {
		return valorProporcional;
	}

	public void setValorProporcional(float valorProporcional) {
		this.valorProporcional = valorProporcional;
	}

	public float getTotalFaturado() {
		return totalFaturado;
	}

	public void setTotalFaturado(float totalFaturado) {
		this.totalFaturado = totalFaturado;
	}

	public int getNotaSaida() {
		return notaSaida;
	}

	public void setNotaSaida(int notaSaida) {
		this.notaSaida = notaSaida;
	}

	public int getNotaEntrada() {
		return notaEntrada;
	}

	public void setNotaEntrada(int notaEntrada) {
		this.notaEntrada = notaEntrada;
	}

	public int getNfDevolucao() {
		return nfDevolucao;
	}

	public void setNfDevolucao(int nfDevolucao) {
		this.nfDevolucao = nfDevolucao;
	}

	public float getValorNf() {
		return valorNf;
	}

	public void setValorNf(float valorNf) {
		this.valorNf = valorNf;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDtEmissaoEntrada() {
		return dtEmissaoEntrada;
	}

	public void setDtEmissaoEntrada(String dtEmissaoEntrada) {
		this.dtEmissaoEntrada = dtEmissaoEntrada;
	}

	public String getDtEmissaoSaida() {
		return dtEmissaoSaida;
	}

	public void setDtEmissaoSaida(String dtEmissaoSaida) {
		this.dtEmissaoSaida = dtEmissaoSaida;
	}

	public String getTipoFornecedor() {
		return tipoFornecedor;
	}

	public void setTipoFornecedor(String tipoFornecedor) {
		this.tipoFornecedor = tipoFornecedor;
	}

	public String getDataLancto() {
		return dataLancto;
	}

	public void setDataLancto(String dataLancto) {
		this.dataLancto = dataLancto;
	}

	public String getCampanha() {
		return campanha;
	}

	public void setCampanha(String campanha) {
		this.campanha = campanha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public float getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(float totalDebito) {
		this.totalDebito = totalDebito;
	}

	public float getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(float totalCredito) {
		this.totalCredito = totalCredito;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public float getDebito() {
		return debito;
	}

	public void setDebito(float debito) {
		this.debito = debito;
	}

	public float getCredito() {
		return credito;
	}

	public void setCredito(float credito) {
		this.credito = credito;
	}

	public String getEstacao() {
		return estacao;
	}

	public void setEstacao(String estacao) {
		this.estacao = estacao;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public int getTabCol() {
		return tabCol;
	}

	public void setTabCol(int tabCol) {
		this.tabCol = tabCol;
	}

	public int getTabMes() {
		return tabMes;
	}

	public void setTabMes(int tabMes) {
		this.tabMes = tabMes;
	}

	public int getTabSeq() {
		return tabSeq;
	}

	public void setTabSeq(int tabSeq) {
		this.tabSeq = tabSeq;
	}

	public String getTabPreco() {
		return tabPreco;
	}

	public void setTabPreco(String tabPreco) {
		this.tabPreco = tabPreco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getCodRepresentante() {
		return codRepresentante;
	}

	public void setCodRepresentante(int codRepresentante) {
		this.codRepresentante = codRepresentante;
	}

	public float getPrecoUnt() {
		return precoUnt;
	}

	public void setPrecoUnt(float precoUnt) {
		this.precoUnt = precoUnt;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	public int getLinhaProduto() {
		return linhaProduto;
	}

	public void setLinhaProduto(int linhaProduto) {
		this.linhaProduto = linhaProduto;
	}

	public List<ConteudoChaveNumerica> getListRepresentante() {
		return listRepresentante;
	}

	public int getQtdeEnviada() {
		return qtdeEnviada;
	}

	public void setQtdeEnviada(int qtdeEnviada) {
		this.qtdeEnviada = qtdeEnviada;
	}

	public int getQtdeDevolvida() {
		return qtdeDevolvida;
	}

	public void setQtdeDevolvida(int qtdeDevolvida) {
		this.qtdeDevolvida = qtdeDevolvida;
	}

	public int getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(int diferenca) {
		this.diferenca = diferenca;
	}

	public float getDesc60Porcento() {
		return desc60Porcento;
	}

	public void setDesc60Porcento(float desc60Porcento) {
		this.desc60Porcento = desc60Porcento;
	}

	public float getBonus30Porcento() {
		return bonus30Porcento;
	}

	public void setBonus30Porcento(float bonus30Porcento) {
		this.bonus30Porcento = bonus30Porcento;
	}

	public float getBonus100Porcento() {
		return bonus100Porcento;
	}

	public void setBonus100Porcento(float bonus100Porcento) {
		this.bonus100Porcento = bonus100Porcento;
	}

	public float getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(float valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public void setListRepresentante(List<ConteudoChaveNumerica> listRepresentante) {
		this.listRepresentante = listRepresentante;
	}

	public int getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public ConsultaFechamentoComissoes() {
		
	}

	public ConsultaFechamentoComissoes(int id, int portador, String dataEmissao, String representante, int tipoTitulo,
			String cliente, String titulo, String vencimento, float percComissao, float valorEmAberto,
			float valorComissao, float mesAnterior, float mesAtual, float saldo, String dataInicio, String dataAnterior,
			int historico, String pedidoCliente, int pedido, int qtdeFaturada, int docto, int seq, float valorDoc,
			float percComPed, int qtdeParcelas, float totComissao, String descricao, String uf, String regiao,
			String linha, float meta, float vendas, float porcAtingido, float valor, float porcLinha,
			float valorProporcional, float totalFaturado, int notaSaida, int notaEntrada, int nfDevolucao,
			float valorNf, String motivo, String dtEmissaoEntrada, String dtEmissaoSaida, String tipoFornecedor,
			String dataLancto, String campanha, String tipo, float totalDebito, float totalCredito, String observacao,
			float debito, float credito, String estacao, String nivel, String grupo, String subGrupo, String item,
			String produto, int tabCol, int tabMes, int tabSeq, String tabPreco, int quantidade, int codRepresentante,
			float precoUnt, float total, int linhaProduto, int qtdeEnviada, int qtdeDevolvida, int diferenca, float desc60Porcento, float bonus30Porcento,
			float bonus100Porcento, float valorCobrado, List<ConteudoChaveNumerica> listRepresentante, int numParcela, int status, Date dataInicial, Date dataFinal) {
		super();
		this.id = id;
		this.portador = portador;
		this.dataEmissao = dataEmissao;
		this.representante = representante;
		this.tipoTitulo = tipoTitulo;
		this.cliente = cliente;
		this.titulo = titulo;
		this.vencimento = vencimento;
		this.percComissao = percComissao;
		this.valorEmAberto = valorEmAberto;
		this.valorComissao = valorComissao;
		this.mesAnterior = mesAnterior;
		this.mesAtual = mesAtual;
		this.saldo = saldo;
		this.dataInicio = dataInicio;
		this.dataAnterior = dataAnterior;
		this.historico = historico;
		this.pedidoCliente = pedidoCliente;
		this.pedido = pedido;
		this.qtdeFaturada = qtdeFaturada;
		this.docto = docto;
		this.seq = seq;
		this.valorDoc = valorDoc;
		this.percComPed = percComPed;
		this.qtdeParcelas = qtdeParcelas;
		this.totComissao = totComissao;
		this.descricao = descricao;
		this.uf = uf;
		this.regiao = regiao;
		this.linha = linha;
		this.meta = meta;
		this.vendas = vendas;
		this.porcAtingido = porcAtingido;
		this.valor = valor;
		this.porcLinha = porcLinha;
		this.valorProporcional = valorProporcional;
		this.totalFaturado = totalFaturado;
		this.notaSaida = notaSaida;
		this.notaEntrada = notaEntrada;
		this.nfDevolucao = nfDevolucao;
		this.valorNf = valorNf;
		this.motivo = motivo;
		this.dtEmissaoEntrada = dtEmissaoEntrada;
		this.dtEmissaoSaida = dtEmissaoSaida;
		this.tipoFornecedor = tipoFornecedor;
		this.dataLancto = dataLancto;
		this.campanha = campanha;
		this.tipo = tipo;
		this.totalDebito = totalDebito;
		this.totalCredito = totalCredito;
		this.observacao = observacao;
		this.debito = debito;
		this.credito = credito;
		this.estacao = estacao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.item = item;
		this.produto = produto;
		this.tabCol = tabCol;
		this.tabMes = tabMes;
		this.tabSeq = tabSeq;
		this.tabPreco = tabPreco;
		this.quantidade = quantidade;
		this.codRepresentante = codRepresentante;
		this.precoUnt = precoUnt;
		this.total = total;
		this.linhaProduto = linhaProduto;
		this.listRepresentante = listRepresentante;
		this.qtdeEnviada = qtdeEnviada;
		this.qtdeDevolvida = qtdeDevolvida;
		this.diferenca = diferenca;
		this.desc60Porcento = desc60Porcento;
		this.bonus30Porcento = bonus30Porcento;
		this.bonus100Porcento = bonus100Porcento;		
		this.valorCobrado = valorCobrado;
		this.numParcela = numParcela;
		this.status = status;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;		
	}
	
}
