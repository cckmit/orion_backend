package br.com.live.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasDoOrcamento;
import br.com.live.model.ResumoOcupacaoCarteiraPorCanalVenda;
import br.com.live.repository.MetasDoOrcamentoRepository;
import br.com.live.util.FormataData;

@Repository
public class OcupacaoCarteiraCustom {

	public final static String OCUPACAO_EM_VALORES = "VALORES";
	public final static String OCUPACAO_EM_PECAS = "PECAS";
	public final static String OCUPACAO_EM_MINUTOS = "MINUTOS";
	public final static String NATUREZAS_FRANCHISING = "421,422,423,424";
	public final static int CLASSIFICACAO_DISPONIBILIDADE = 4;	
	public final static String MODALIDADE_ATACADO = "ATACADO";
	public final static String MODALIDADE_VAREJO = "VAREJO";
	public final static String META_ORCADA = "ORCADO";
	public final static String META_REALINHADA = "REALINHADO";
	
	private final MetasDoOrcamentoRepository metasDoOrcamentoRepository;	
	
	private JdbcTemplate jdbcTemplate;

	public OcupacaoCarteiraCustom(JdbcTemplate jdbcTemplate, MetasDoOrcamentoRepository metasDoOrcamentoRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.metasDoOrcamentoRepository = metasDoOrcamentoRepository;
	}

	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarCarteiraPorValor(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta) {
		
		System.out.println("consultarCarteiraPorValor");
		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanalVenda;
		
		String query = "select canais_valores.canal, sum(canais_valores.valor_liquido - ((canais_valores.valor_liquido * canais_valores.desconto_capa) / 100)) valorReal "
		+ " from ( "
		+ " select c.live_agrup_tipo_cliente canal, a.pedido_venda, LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, sum(pedi110.valor - ((pedi110.valor * pedi110.desconto) / 100))) valor_liquido, "
		+ "        live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3) desconto_capa "
		+ " from pedi_100 a, pedi_010 b, pedi_085 c, "
		+ " (select pedi_110.pedido_venda, ((pedi_110.qtde_pedida * pedi_110.valor_unitario)) valor, pedi_110.percentual_desc desconto, 'NORMAL' tipo " 
		+ " from pedi_110, pedi_080 "
		+ " where pedi_110.cod_cancelamento = 0 "
		+ " and pedi_110.cod_nat_op not in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")"  
		+ " and pedi_080.natur_operacao = pedi_110.cod_nat_op "
		+ " and pedi_080.estado_natoper = pedi_110.est_nat_op "
		+ " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1) "
		+ " union all "
		+ " select pedi_110.pedido_venda, ((pedi_110.qtde_pedida * pedi_110.valor_unitario) * 2) valor, pedi_110.percentual_desc desconto, 'FRANCHISING' tipo "
		+ " from pedi_110, pedi_080 "   
		+ " where pedi_110.cod_cancelamento = 0 "
		+ " and pedi_110.cod_nat_op in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")"
	    + " and pedi_080.natur_operacao = pedi_110.cod_nat_op "
	    + " and pedi_080.estado_natoper = pedi_110.est_nat_op "
	    + " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1)) pedi110 "
	    + " where a.data_entr_venda between ? and ? "
	    + " and a.cod_cancelamento = 0 ";
		
		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 

		query += "and a.tipo_pedido in (" + tipoPedido + ")"
		+ " and b.cgc_9 = a.cli_ped_cgc_cli9 "
		+ " and b.cgc_4 = a.cli_ped_cgc_cli4 "
		+ " and b.cgc_2 = a.cli_ped_cgc_cli2 "
		+ " and c.tipo_cliente = b.tipo_cliente "
		+ " and pedi110.pedido_venda = a.pedido_venda "  
		+ " and upper(c.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + OcupacaoCarteiraCustom.MODALIDADE_ATACADO + "') " // CONSIDERAR APENAS VALORES DE ATACADO 
		+ " group by c.live_agrup_tipo_cliente, a.pedido_venda, a.desconto1, a.desconto2, a.desconto3, a.codigo_moeda "
		+ " ) canais_valores "
		+ " group by canais_valores.canal ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorCanalVenda.class), dataInicio, dataFim);				
		} catch (Exception e) {			
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;		
	}
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarCarteiraConfirmarPorValor(int mes, int ano, String tipoPedido, int tipoClassificacao, int tipoMeta) {
		
		Date dataInicio = FormataData.getStartingDay(mes, ano);		
		Date dataFim = FormataData.getFinalDay(mes, ano);
		
		List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanalVenda;
		
		String query = " select canais_valores_integ.canal, sum(canais_valores_integ.valor_liquido - ((canais_valores_integ.valor_liquido * canais_valores_integ.desconto_capa) / 100)) valorConfirmar "
		+ " from ( "
		+ " select c.live_agrup_tipo_cliente canal, a.pedido_venda, LIVE_FN_CONVERTE_MOEDA(a.codigo_moeda, sum(inte110.valor - ((inte110.valor * inte110.desconto) / 100))) valor_liquido, "
		+ " (live_fn_calc_perc_desconto(a.desconto1, a.desconto2, a.desconto3)/100) desconto_capa "
		+ " from inte_100 a, pedi_010 b, pedi_085 c, "
		+ " (select inte_110.pedido_venda, (((inte_110.qtde_pedida / 100) * (inte_110.valor_unitario / 100))) valor, (inte_110.percentual_desc / 100) desconto, 'NORMAL' tipo " 
		+ " from inte_110, pedi_080 "
		+ " where inte_110.cod_nat_op not in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ") " 
	    + " and pedi_080.natur_operacao = inte_110.cod_nat_op "
	    + " and pedi_080.estado_natoper = inte_110.est_nat_op "
	    + " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1) "
	    + " union all "
	    + " select inte_110.pedido_venda, (((inte_110.qtde_pedida / 100) * (inte_110.valor_unitario / 100)) * 2) valor, (inte_110.percentual_desc / 100) desconto, 'FRANCHISING' tipo " 
	    + " from inte_110, pedi_080 "
	    + " where inte_110.cod_nat_op in (" + OcupacaoCarteiraCustom.NATUREZAS_FRANCHISING + ")" 
		+ " and pedi_080.natur_operacao = inte_110.cod_nat_op "
		+ " and pedi_080.estado_natoper = inte_110.est_nat_op "
		+ " and (pedi_080.faturamento = 1 or pedi_080.emite_duplicata = 1)) inte110 "		    
		+ " where a.data_entrega between ? and ? ";

		if (tipoClassificacao > 0)
			query += " and a.classificacao_pedido = " + tipoClassificacao; 
		
		query += " and a.tipo_pedido in (" + tipoPedido + ")" 
		+ " and b.cgc_9 = a.cliente9 " 
		+ " and b.cgc_4 = a.cliente4 " 
		+ " and b.cgc_2 = a.cliente2 " 
		+ " and c.tipo_cliente = b.tipo_cliente " 
		+ " and inte110.pedido_venda = a.pedido_venda "
		+ " and upper(c.live_agrup_tipo_cliente) in (select upper(orion_150.descricao) from orion_150 "
		+ " where orion_150.tipo_meta = " + tipoMeta   
		+ " and orion_150.ano = " + ano
		+ " and orion_150.modalidade = '" + OcupacaoCarteiraCustom.MODALIDADE_ATACADO + "') " // CONSIDERAR APENAS VALORES DE ATACADO
		+ " group by c.live_agrup_tipo_cliente, a.pedido_venda, a.desconto1, a.desconto2, a.desconto3, a.codigo_moeda "
		+ " ) canais_valores_integ group by canais_valores_integ.canal ";

		try {
			listResumoOcupacaoCarteiraPorCanalVenda = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResumoOcupacaoCarteiraPorCanalVenda.class), dataInicio, dataFim);	
		} catch (Exception e) {
			listResumoOcupacaoCarteiraPorCanalVenda = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		}
		
		return listResumoOcupacaoCarteiraPorCanalVenda;						
	}
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoEmValor(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoEmValor = null;
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmarEmValor = null;	

		// Carrega os valores apenas para atacado.
		if (tipoModalidade.equalsIgnoreCase(OcupacaoCarteiraCustom.MODALIDADE_ATACADO) ) {
			listOcupacaoEmValor = consultarCarteiraPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);
			listOcupacaoConfirmarEmValor = consultarCarteiraConfirmarPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);				
		}				
		return formatarResumo(mes, ano, tipoMeta, tipoModalidade, listOcupacaoEmValor, listOcupacaoConfirmarEmValor); 
	}	
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> formatarResumo(int mes, int ano, int tipoMeta, String tipoModalidade,  List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacao, List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmar) {
		Map<String, ResumoOcupacaoCarteiraPorCanalVenda> mapOcupacao = new HashMap<String, ResumoOcupacaoCarteiraPorCanalVenda>();
	    List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoPorCanal = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		List<MetasDoOrcamento> metasCadastradas = metasDoOrcamentoRepository.findByAnoAndTipoMetaAndModalidade(ano, tipoMeta, tipoModalidade);
	   
	    for (MetasDoOrcamento orcado : metasCadastradas) {
	    	ResumoOcupacaoCarteiraPorCanalVenda resumo = new ResumoOcupacaoCarteiraPorCanalVenda();
	    	resumo.setCanal(orcado.descricao);
	    	resumo.setValorOrcado(getValorOrcadoMes(mes, orcado));
	    	mapOcupacao.put(resumo.getCanal(), resumo);
	    }
		if (listOcupacao != null) {
			for (ResumoOcupacaoCarteiraPorCanalVenda ocupacao : listOcupacao) {
				ResumoOcupacaoCarteiraPorCanalVenda resumo = mapOcupacao.get(ocupacao.getCanal()); 	
				resumo.setValorReal(ocupacao.getValorReal());
			}		
		}
		
		if (listOcupacaoConfirmar != null) {
			for (ResumoOcupacaoCarteiraPorCanalVenda ocupacaoConfirmar : listOcupacaoConfirmar) {
				ResumoOcupacaoCarteiraPorCanalVenda resumo = mapOcupacao.get(ocupacaoConfirmar.getCanal()); 	
				resumo.setValorConfirmar(ocupacaoConfirmar.getValorConfirmar());
			}		
		}
		
		for (String canal : mapOcupacao.keySet()) {
			listResumoPorCanal.add(mapOcupacao.get(canal));
		}		
		return listResumoPorCanal;
	}
	
	private double getValorOrcadoMes(int mes, MetasDoOrcamento meta) {
    	if (mes == FormataData.JANEIRO) return meta.valorMes1;
    	else if (mes == FormataData.FEVEREIRO) return meta.valorMes2;
    	else if (mes == FormataData.MARCO) return meta.valorMes3;
    	else if (mes == FormataData.ABRIL) return meta.valorMes4;
    	else if (mes == FormataData.MAIO) return meta.valorMes5;
    	else if (mes == FormataData.JUNHO) return meta.valorMes6;
    	else if (mes == FormataData.JULHO) return meta.valorMes7;
    	else if (mes == FormataData.AGOSTO) return meta.valorMes8;
    	else if (mes == FormataData.SETEMBRO) return meta.valorMes9;
    	else if (mes == FormataData.OUTUBRO) return meta.valorMes10;
    	else if (mes == FormataData.NOVEMBRO) return meta.valorMes11;
    	return meta.valorMes12;
	}
	
	public List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoCarteiraPorCanal(String tipoOcupacao, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega, String tipoModalidade) {		
		String tipoPedido = "";
		int tipoClassificao = 0;
		int tipoMeta = 0;
		
		if (pedidosProgramados) tipoPedido = "0";   
		if (pedidosProntaEntrega) tipoPedido = tipoPedido.isEmpty() ? "1" : "0,1";
		if (tipoPedido.isEmpty()) tipoPedido = "9"; // inicializa com um tipo que não existe		
		if ((pedidosDisponibilidade)&(!pedidosProgramados)&(!pedidosProntaEntrega)) tipoClassificao = OcupacaoCarteiraCustom.CLASSIFICACAO_DISPONIBILIDADE;
		
		List<ResumoOcupacaoCarteiraPorCanalVenda> listDados = null;
		
		if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_VALORES)) {
			tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FATURAMENTO : MetasDoOrcamentoCustom.METAS_FATURAMENTO_REALINHADO;
			listDados = consultarOcupacaoEmValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_PECAS)) {
			tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS : MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS_REALINHADO;
			//listDados = consultarOcupacaoEmValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_MINUTOS)) {
			tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_MINUTOS : MetasDoOrcamentoCustom.METAS_FAT_EM_MINUTOS_REALINHADO;
			//listDados = consultarOcupacaoEmValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		}				
		
	    return listDados; 
	}		
}
