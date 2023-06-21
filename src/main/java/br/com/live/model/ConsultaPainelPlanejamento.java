package br.com.live.model;

public class ConsultaPainelPlanejamento {
	
	public String produto;
	public String descricao;
	public String periodo;
	public int ordemProd;
	public int ordemConf;
	public int qtdeOrdem;
	public int reservas;
	public float receber;
	public String deposito;
	public float estoque;
	public int pedido;
	public String cliente;
	public String embarquePrevisto;
	public float carteira;
	public String unidMedida;
	public float saldo;
	public String emissao;
	public String fornecedor;
	public String entregaPrevista;
	public int qtdePedida;
	
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public int getOrdemProd() {
		return ordemProd;
	}
	public void setOrdemProd(int ordemProd) {
		this.ordemProd = ordemProd;
	}
	public int getOrdemConf() {
		return ordemConf;
	}
	public void setOrdemConf(int ordemConf) {
		this.ordemConf = ordemConf;
	}
	public int getQtdeOrdem() {
		return qtdeOrdem;
	}
	public void setQtdeOrdem(int qtdeOrdem) {
		this.qtdeOrdem = qtdeOrdem;
	}
	public int getReservas() {
		return reservas;
	}
	public void setReservas(int reservas) {
		this.reservas = reservas;
	}
	public float getReceber() {
		return receber;
	}
	public void setReceber(float receber) {
		this.receber = receber;
	}
	public String getDeposito() {
		return deposito;
	}
	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}
	public float getEstoque() {
		return estoque;
	}
	public void setEstoque(float estoque) {
		this.estoque = estoque;
	}
	public int getPedido() {
		return pedido;
	}
	public void setPedido(int pedido) {
		this.pedido = pedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getEmbarquePrevisto() {
		return embarquePrevisto;
	}
	public void setEmbarquePrevisto(String embarquePrevisto) {
		this.embarquePrevisto = embarquePrevisto;
	}
	public float getCarteira() {
		return carteira;
	}
	public void setCarteira(float carteira) {
		this.carteira = carteira;
	}
	public String getUnidMedida() {
		return unidMedida;
	}
	public void setUnidMedida(String unidMedida) {
		this.unidMedida = unidMedida;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public String getEmissao() {
		return emissao;
	}
	public void setEmissao(String emissao) {
		this.emissao = emissao;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getEntregaPrevista() {
		return entregaPrevista;
	}
	public void setEntregaPrevista(String entregaPrevista) {
		this.entregaPrevista = entregaPrevista;
	}
	public int getQtdePedida() {
		return qtdePedida;
	}
	public void setQtdePedida(int qtdePedida) {
		this.qtdePedida = qtdePedida;
	}
	
	
	public ConsultaPainelPlanejamento() {
		
	}
	
	public ConsultaPainelPlanejamento(String produto, String descricao, String periodo, int ordemProd, int ordemConf,
			int qtdeOrdem, int reservas, float receber, String deposito, float estoque, int pedido, String cliente,
			String embarquePrevisto, float carteira, String unidMedida, float saldo, String emissao, String fornecedor,
			String entregaPrevista, int qtdePedida) {
		
		this.produto = produto;
		this.descricao = descricao;
		this.periodo = periodo;
		this.ordemProd = ordemProd;
		this.ordemConf = ordemConf;
		this.qtdeOrdem = qtdeOrdem;
		this.reservas = reservas;
		this.receber = receber;
		this.deposito = deposito;
		this.estoque = estoque;
		this.pedido = pedido;
		this.cliente = cliente;
		this.embarquePrevisto = embarquePrevisto;
		this.carteira = carteira;
		this.unidMedida = unidMedida;
		this.saldo = saldo;
		this.emissao = emissao;
		this.fornecedor = fornecedor;
		this.entregaPrevista = entregaPrevista;
		this.qtdePedida = qtdePedida;
	}

}
