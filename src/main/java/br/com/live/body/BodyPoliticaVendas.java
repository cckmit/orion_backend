package br.com.live.body;

import java.util.List;

import br.com.live.model.ConsultaPoliticaVendas;

public class BodyPoliticaVendas {
	
	public int id;
	public int tipo;
	public int formaPagamento;
	public int portador;
	public String cnpj;
	public int codFuncionario;
	public float descCapa;
	public int tipoPedido;
	public int depositoItens;
	public float descMaxCliente;
	public float comissao;
	public int condPgto;
	public int tipoCliente;
	public int naturezaOperacao;
	public float desconto;
	public String tabelaPreco;
	public String estacao;
	
	public List<ConsultaPoliticaVendas> tabImportarRegras;

}
