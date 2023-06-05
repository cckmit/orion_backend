package br.com.live.model;

public class ConsultaPoliticaVendas {
	
	public int id;
	public int tipo;
	public int formaPagamento;
	public int portador;
	public int cnpj9;
	public int cnpj4;
	public int cnpj2;
	public int codFuncionario;
	public float descCapa;
	public int tipoPedido;
	public int depositoItens;
	public float descMaxCliente;
	public float comissao;
	public int condPagamento;
	public int naturezaOp;
	public float desconto;
	public int tipoCliente;
	public int tabCol;
	public int tabMes;
	public int tabSeq;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public int getPortador() {
		return portador;
	}
	public void setPortador(int portador) {
		this.portador = portador;
	}
	public int getCnpj9() {
		return cnpj9;
	}
	public void setCnpj9(int cnpj9) {
		this.cnpj9 = cnpj9;
	}
	public int getCnpj4() {
		return cnpj4;
	}
	public void setCnpj4(int cnpj4) {
		this.cnpj4 = cnpj4;
	}
	public int getCnpj2() {
		return cnpj2;
	}
	public void setCnpj2(int cnpj2) {
		this.cnpj2 = cnpj2;
	}
	public int getCodFuncionario() {
		return codFuncionario;
	}
	public void setCodFuncionario(int codFuncionario) {
		this.codFuncionario = codFuncionario;
	}
	public float getDescCapa() {
		return descCapa;
	}
	public void setDescCapa(float descCapa) {
		this.descCapa = descCapa;
	}
	public int getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(int tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public int getDepositoItens() {
		return depositoItens;
	}
	public void setDepositoItens(int depositoItens) {
		this.depositoItens = depositoItens;
	}
	public float getDescMaxCliente() {
		return descMaxCliente;
	}
	public void setDescMaxCliente(float descMaxCliente) {
		this.descMaxCliente = descMaxCliente;
	}
	public float getComissao() {
		return comissao;
	}
	public void setComissao(float comissao) {
		this.comissao = comissao;
	}
	public int getCondPagamento() {
		return condPagamento;
	}
	public void setCondPagamento(int condPagamento) {
		this.condPagamento = condPagamento;
	}
	public int getNaturezaOp() {
		return naturezaOp;
	}
	public void setNaturezaOp(int naturezaOp) {
		this.naturezaOp = naturezaOp;
	}
	public float getDesconto() {
		return desconto;
	}
	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
	public int getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
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
	
	public ConsultaPoliticaVendas() {
		
	}
	public ConsultaPoliticaVendas(int id, int tipo, int formaPagamento, int portador, int cnpj9, int cnpj4, int cnpj2,
			int codFuncionario, float descCapa, int tipoPedido, int depositoItens, float descMaxCliente, float comissao,
			int condPagamento, int naturezaOp, float desconto, int tipoCliente, int tabCol, int tabMes, int tabSeq) {
		
		this.id = id;
		this.tipo = tipo;
		this.formaPagamento = formaPagamento;
		this.portador = portador;
		this.cnpj9 = cnpj9;
		this.cnpj4 = cnpj4;
		this.cnpj2 = cnpj2;
		this.codFuncionario = codFuncionario;
		this.descCapa = descCapa;
		this.tipoPedido = tipoPedido;
		this.depositoItens = depositoItens;
		this.descMaxCliente = descMaxCliente;
		this.comissao = comissao;
		this.condPagamento = condPagamento;
		this.naturezaOp = naturezaOp;
		this.desconto = desconto;
		this.tipoCliente = tipoCliente;
		this.tabCol = tabCol;
		this.tabMes = tabMes;
		this.tabSeq = tabSeq;
	}
	
}
