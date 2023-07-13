package br.com.live.comercial.body;

import java.util.Date;
import java.util.List;

import br.com.live.comercial.entity.MetasCategoria;
import br.com.live.comercial.entity.TpClienteXTabPreco;
import br.com.live.comercial.model.ConsultaPedidosPorCliente;
import br.com.live.comercial.model.DescontoClientesImportados;

public class BodyComercial {
	
	public int idTpCli;
	public int idRelac;
	public String id;
	public long idItem;
	public String idCapa;
	public int idCanal;
	public String produto;
	public String fornecedor;
	public String motivo;
	public boolean editMode;
	public String referencia;
	public String tamanho;
	public String cor;
	public long codEstacao;
	public int tipoMeta;
	public int catalogo;
	public int tipoCliente;
	public String tipoClienteLive;
	public int colTabEntr;
	public int mesTabEntr;
	public int seqTabEntr;
	public int numDias;
	public int numInterno;
	public String tabela;
	public Date periodoIni;
	public Date periodoFim;
	public int idFaturamento;
	public String loja;
	public String data;
	public int quantidade;
	public int tickets;
	public float conversao;
	public float valorDolar;
	public float valorReal;
	public String descricao;
	public String modalidade;
	public int represAntigo;
	public int represNovo;
	
	public List<MetasCategoria> tabImportar;
	
	public List<TpClienteXTabPreco> tabTpClienteTabPreco;

	public List<DescontoClientesImportados> listClientesDesconto;
	public List<ConsultaPedidosPorCliente> listPedidosSel;
	public String usuario;
	public String cnpjCliente;
	public String observacao;
}
	
