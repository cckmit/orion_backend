package br.com.live.custom;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasDoOrcamento;
import br.com.live.model.DadosOcupacaoCarteiraPorCanaisVenda;
import br.com.live.repository.MetasDoOrcamentoRepository;
import br.com.live.util.FormataData;

@Repository
public class OcupacaoCarteiraCustom {

	private final static int ESTAGIO_MINUTOS = 20;
	private final MetasDoOrcamentoRepository metasDoOrcamentoRepository;
	
	private JdbcTemplate jdbcTemplate;

	public OcupacaoCarteiraCustom(JdbcTemplate jdbcTemplate, MetasDoOrcamentoRepository metasDoOrcamentoRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.metasDoOrcamentoRepository = metasDoOrcamentoRepository;
	}

	public List<DadosOcupacaoCarteiraPorCanaisVenda> consultarDadosPorCanal(int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega, String tipoCarteira) {
		
		String tipoPedido = "";
		
		if (pedidosProgramados) tipoPedido = "0";   
		if (pedidosProntaEntrega) tipoPedido = tipoPedido.isEmpty() ? "1" : "0,1";
		if (tipoPedido.isEmpty()) tipoPedido = "9"; // inicializa com um tipo que não existe		

		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		String query = "select dados.canal,  sum(dados.valor) valor, sum(dados.quantidade) quantidade, sum(dados.minutos) minutos,"
	    + " sum(dados.valor_conf) valorConferir, sum(dados.quantidade_conf) quantidadeConferir, sum(dados.minutos_conf) minutosConferir "
	    + " from ( "
	    + " select canais.canal, sum(canais.valor) valor, sum(canais.quantidade) quantidade, sum(canais.qtde_minutos) minutos, "
	    + " 0 valor_conf, 0 quantidade_conf, 0 minutos_conf "
	    + " from ( "
	    + " select pedidos.pedido_venda, pedidos.canal, pedidos.qtde_itens quantidade, "
	    + " pedidos.valor_itens - (pedidos.valor_itens * (pedidos.percentual_desconto / 100)) valor, "
	    + " pedidos.qtde_minutos "       
	    + " from ( "
	    + " select a.pedido_venda, c.live_agrup_tipo_cliente canal, live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3) percentual_desconto, "
	    + " (SELECT NVL(ROUND(SUM(((z.qtde_pedida) * LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, z.valor_unitario)) - ( ((z.qtde_pedida)* LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, z.valor_unitario)) * (z.percentual_desc / 100) )),2),0) " 
	    + " FROM pedi_110 z " 
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " AND z.cod_cancelamento = 0 "
	    + " ) valor_itens, "
	    + " (SELECT SUM(z.qtde_pedida)"
	    + " FROM pedi_110 z "
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " AND z.cod_cancelamento = 0 "
	    + " ) qtde_itens, "
	    + " (SELECT NVL(SUM(z.qtde_pedida * (SELECT NVL(MAX(o.tempo),0) FROM orion_vi_itens_x_tempo_estagio o " 
	    + " WHERE o.nivel = z.cd_it_pe_nivel99 "
	    + " AND o.grupo = z.cd_it_pe_grupo "
	    + " AND o.estagio = ? )),0) "
	    + " FROM pedi_110 z "
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " AND z.cod_cancelamento = 0 "
	    + " ) qtde_minutos "
	    + " from pedi_100 a, pedi_010 b, pedi_085 c "
	    + " where a.cod_cancelamento = 0 "
	    + " and a.data_emis_venda between ? and ? "; 
	    	    
	    if (pedidosDisponibilidade) query += " and a.classificacao_pedido = 4 "; 
			    
	    query += " and a.tipo_pedido in (" + tipoPedido + ")"	    
	    + " and b.cgc_9 = a.cli_ped_cgc_cli9 "
	    + " and b.cgc_4 = a.cli_ped_cgc_cli4 "
	    + " and b.cgc_2 = a.cli_ped_cgc_cli2 "
	    + " and c.tipo_cliente = b.tipo_cliente "
	    + " and upper(c.live_agrup_tipo_cliente) in (select upper(o.descricao) from orion_150 o "
	    + " where o.tipo_meta = 2 "
	    + " and o.ano = ? "
	    + " and o.modalidade = ?) "
		+ " ) pedidos " 
		+ " ) canais "
		+ " group by canais.canal "

		+ " UNION "

		+ " select canais.canal, 0 valor, 0 quantidade, 0 minutos, "
	    + " sum(canais.valor) valor_conf, sum(canais.quantidade) quantidade_conf, sum(canais.qtde_minutos) minutos_conf "
	    + " from ( "
	    + " select pedidos.pedido_venda, pedidos.canal, pedidos.qtde_itens quantidade, "
	    + " pedidos.valor_itens - (pedidos.valor_itens * (pedidos.percentual_desconto / 100)) valor, "
	    + " pedidos.qtde_minutos "
	    + " from ( "
	    + " select a.pedido_venda, c.live_agrup_tipo_cliente canal, live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3) percentual_desconto , "       
	    + " (SELECT NVL(ROUND(SUM(((z.qtde_pedida/100) * LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, (z.valor_unitario/100))) - ( ((z.qtde_pedida/100)* LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, (z.valor_unitario/100))) * ((z.percentual_desc/100) / 100) )),2),0) " 
	    + " FROM inte_110 z " 
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " ) valor_itens, "
	    + " (SELECT SUM(z.qtde_pedida/100) "
	    + " FROM inte_110 z "
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " ) qtde_itens, "
	    + " (SELECT NVL(SUM((z.qtde_pedida/100) * (SELECT NVL(MAX(o.tempo),0) FROM orion_vi_itens_x_tempo_estagio o " 
	    + " WHERE o.nivel = z.item_nivel99 "
	    + " AND o.grupo = z.item_grupo "
	    + " AND o.estagio = ? )),0)  "
	    + " FROM inte_110 z " 
	    + " WHERE z.pedido_venda = a.pedido_venda " 
	    + " ) qtde_minutos "
	    + " from inte_100 a, pedi_010 b, pedi_085 c "
	    + " where a.data_emis_venda between ? and ? " 
	    + " and a.tipo_pedido in (" + tipoPedido + ")" 
	    + " and b.cgc_9 = a.cliente9 "
	    + " and b.cgc_4 = a.cliente4 "
	    + " and b.cgc_2 = a.cliente2 "
	    + " and c.tipo_cliente = b.tipo_cliente "
	    + " and upper(c.live_agrup_tipo_cliente) in (select upper(o.descricao) from orion_150 o " 
	    + " where o.tipo_meta = 2 "
	    + " and o.ano = ? "
	    + " and o.modalidade = ?) "
	    + " ) pedidos  "
	    + " ) canais "
	    + " group by canais.canal "
	    + " ) dados "
	    + " group by dados.canal "
	    + " order by dados.canal ";
	    
	    List<DadosOcupacaoCarteiraPorCanaisVenda> dadosPorCanais = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosOcupacaoCarteiraPorCanaisVenda.class), ESTAGIO_MINUTOS, dataInicio, dataFim, ano, tipoCarteira, ESTAGIO_MINUTOS, dataInicio, dataFim, ano, tipoCarteira); 
	    atualizarValoresOrcados(tipoOrcamento, mes, ano, dadosPorCanais);
	    
	    return dadosPorCanais; 
	}	
	
	private void atualizarValoresOrcados (String tipoOrcamento, int mes, int ano, List<DadosOcupacaoCarteiraPorCanaisVenda> dadosPorCanais) {
	    int tipoMetaParaValor = tipoOrcamento.equalsIgnoreCase("ORCADO") ? 2 : 3; 
	    int tipoMetaParaQtdes = 4;
	    int tipoMetaParaMinutos = 5;
	    double valorOrcado = 0;
	    double quantidadeOrcado = 0;
	    double minutosOrcado = 0;

		for (DadosOcupacaoCarteiraPorCanaisVenda dadosCanal : dadosPorCanais) {
	    	MetasDoOrcamento metaValor = metasDoOrcamentoRepository.findByDescricaoAndAnoAndTipoMeta(dadosCanal.getCanal(), ano, tipoMetaParaValor);
	    	MetasDoOrcamento metaQuantidade = metasDoOrcamentoRepository.findByDescricaoAndAnoAndTipoMeta(dadosCanal.getCanal(), ano, tipoMetaParaQtdes);
	    	MetasDoOrcamento metaMinutos = metasDoOrcamentoRepository.findByDescricaoAndAnoAndTipoMeta(dadosCanal.getCanal(), ano, tipoMetaParaMinutos);
	    	
	    	if (mes == FormataData.JANEIRO) {
	    	    valorOrcado = metaValor.valorMes1;
	    	    quantidadeOrcado = metaQuantidade.valorMes1;
	    	    minutosOrcado = metaMinutos.valorMes1;
	    	} else if (mes == FormataData.FEVEREIRO) {
	    	    valorOrcado = metaValor.valorMes2;
	    	    quantidadeOrcado = metaQuantidade.valorMes2;
	    	    minutosOrcado = metaMinutos.valorMes2;
	    	} else if (mes == FormataData.MARCO) {
	    	    valorOrcado = metaValor.valorMes3;
	    	    quantidadeOrcado = metaQuantidade.valorMes3;
	    	    minutosOrcado = metaMinutos.valorMes3;
	    	} else if (mes == FormataData.ABRIL) {
	    	    valorOrcado = metaValor.valorMes4;
	    	    quantidadeOrcado = metaQuantidade.valorMes4;
	    	    minutosOrcado = metaMinutos.valorMes4;
	    	} else if (mes == FormataData.MAIO) {
	    	    valorOrcado = metaValor.valorMes5;
	    	    quantidadeOrcado = metaQuantidade.valorMes5;
	    	    minutosOrcado = metaMinutos.valorMes5;
	    	} else if (mes == FormataData.JUNHO) {
	    	    valorOrcado = metaValor.valorMes6;
	    	    quantidadeOrcado = metaQuantidade.valorMes6;
	    	    minutosOrcado = metaMinutos.valorMes6;
	    	} else if (mes == FormataData.JULHO) {
	    	    valorOrcado = metaValor.valorMes7;
	    	    quantidadeOrcado = metaQuantidade.valorMes7;
	    	    minutosOrcado = metaMinutos.valorMes7;
	    	} else if (mes == FormataData.AGOSTO) {
	    	    valorOrcado = metaValor.valorMes8;
	    	    quantidadeOrcado = metaQuantidade.valorMes8;
	    	    minutosOrcado = metaMinutos.valorMes8;
	    	} else if (mes == FormataData.SETEMBRO) {
	    	    valorOrcado = metaValor.valorMes9;
	    	    quantidadeOrcado = metaQuantidade.valorMes9;
	    	    minutosOrcado = metaMinutos.valorMes9;
	    	} else if (mes == FormataData.OUTUBRO) {
	    	    valorOrcado = metaValor.valorMes10;
	    	    quantidadeOrcado = metaQuantidade.valorMes10;
	    	    minutosOrcado = metaMinutos.valorMes10;
	    	} else if (mes == FormataData.NOVEMBRO) {
	    	    valorOrcado = metaValor.valorMes11;
	    	    quantidadeOrcado = metaQuantidade.valorMes11;
	    	    minutosOrcado = metaMinutos.valorMes11;
	    	} else if (mes == FormataData.DEZEMBRO) {
	    	    valorOrcado = metaValor.valorMes12;
	    	    quantidadeOrcado = metaQuantidade.valorMes12;
	    	    minutosOrcado = metaMinutos.valorMes12;
	    	}	    	
	    	dadosCanal.setValorOrcado(valorOrcado);
	    	dadosCanal.setQuantidadeOrcado(quantidadeOrcado);
	    	dadosCanal.setMinutosOrcado(minutosOrcado);
	    }	
	}
}
