package br.com.live.producao.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.bo.FormataParametrosPlanoMestre;
import br.com.live.producao.body.BodyParametrosPlanoMestre;
import br.com.live.producao.entity.PlanoMestre;
import br.com.live.producao.model.ConsultaItensPlanoMestre;
import br.com.live.producao.model.ConsultaItensTamPlanoMestre;
import br.com.live.producao.model.ConsultaPreOrdemProducao;
import br.com.live.producao.model.ConsultaProgramadoReferencia;
import br.com.live.producao.model.GradeDistribuicaoGrupoItem;
import br.com.live.producao.model.ProgramacaoPlanoMestre;
import br.com.live.produto.model.Produto;
import br.com.live.util.ConverteLista;

@Repository
public class PlanoMestreCustom {

	private JdbcTemplate jdbcTemplate;

	public PlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Produto> findProdutosByParameters(BodyParametrosPlanoMestre parametros) {

		System.out.println("findProdutosByParameters");

		FormataParametrosPlanoMestre parametrosFormatados = new FormataParametrosPlanoMestre(parametros);

		if (parametrosFormatados.getColecoes().equalsIgnoreCase("")
				&& parametrosFormatados.getPrevisoes().equalsIgnoreCase(""))
			return null;

		String query = " select b.nivel_estrutura nivel, b.grupo_estrutura grupo, b.subgru_estrutura sub, b.item_estrutura item, b.narrativa, ";

		query += " 0 qtdePrevisaoVendas " 
				+ " from basi_030 a, basi_010 b, basi_590 g, "
				+ " (select d.referencia grupo, nvl((select 1 from basi_140 c " 
				+ " where c.colecao = d.colecao "
				+ " and c.descricao_espanhol like '%COLECAO PERMANENTE%'),0) permanente " 
				+ " from basi_030 d "
				+ " where d.nivel_estrutura = '1') ver_permanente " 
				+ " where a.nivel_estrutura = '1' "
				+ " and b.nivel_estrutura = a.nivel_estrutura " 
				+ " and b.grupo_estrutura = a.referencia "
				+ " and b.item_ativo = 0 " 
				+ " and g.nivel (+) = b.nivel_estrutura "
				+ " and g.grupo (+) = b.grupo_estrutura "
				+ " and g.subgrupo (+) = b.subgru_estrutura "
				+ " and g.item (+) = b.item_estrutura "
				+ " and ver_permanente.grupo = a.referencia ";

		if (!parametrosFormatados.getColecoes().equalsIgnoreCase("")) {
			query += " and a.colecao in " + parametrosFormatados.getColecoes();
		}

		if (!parametrosFormatados.getLinhasProduto().equalsIgnoreCase("")) {
			query += " and a.linha_produto in " + parametrosFormatados.getLinhasProduto();
		}

		if (!parametrosFormatados.getArtigosProduto().equalsIgnoreCase("")) {
			query += " and a.artigo in " + parametrosFormatados.getArtigosProduto();
		}

		if (!parametrosFormatados.getArtigosCotas().equalsIgnoreCase("")) {
			query += " and a.artigo_cotas in " + parametrosFormatados.getArtigosCotas();
		}

		if (!parametrosFormatados.getEmbarques().equalsIgnoreCase("")) {
			query += " and g.grupo_embarque in " + parametrosFormatados.getEmbarques();
		}

		if (!parametrosFormatados.getProdutos().equalsIgnoreCase("")) {
			query += " and a.referencia in " + parametrosFormatados.getProdutos();
		}

		if (!parametrosFormatados.getCores().equalsIgnoreCase("")) {
			query += " and b.item_estrutura in " + parametrosFormatados.getCores();
		}

		if (!parametrosFormatados.getOrigemProdutos().equalsIgnoreCase("")) {
			query += " and b.origem_prod in " + parametrosFormatados.getOrigemProdutos();
		}

		if (!parametrosFormatados.getPublicosAlvo().equalsIgnoreCase("")) {
			query += " and exists (select 1 from basi_400 g " + " where g.grupo = a.referencia "
					+ " and g.codigo_informacao in " + parametrosFormatados.getPublicosAlvo()
					+ " and g.tipo_informacao = 9) ";
		}

		if (!parametrosFormatados.getSubColecoes().equalsIgnoreCase("")) {
			query += " and (ver_permanente.permanente = 1 and exists (select 1 from basi_631 m, basi_632 n "
					+ " where n.cd_agrupador (+) = m.cd_agrupador " + " and n.grupo_ref (+) = m.grupo_ref "
					+ " and m.cd_agrupador in " + parametrosFormatados.getSubColecoes()
					+ " and m.grupo_ref = b.grupo_estrutura " + " and n.subgrupo_ref = b.subgru_estrutura "
					+ " and n.item_ref = b.item_estrutura) or ver_permanente.permanente = 0) ";
		}

		if (!parametrosFormatados.getPrevisoes().equalsIgnoreCase("")) {
			query += " and exists (select 1 from orion_041 v where v.id_previsao_vendas in "
					+ parametrosFormatados.getPrevisoes() + " and v.grupo = b.grupo_estrutura "
					+ " and v.item = b.item_estrutura )";
		}

		System.out.println("Query ProdutoCompleto: " + query);

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
	}

	private String getQueryItensPorRefCor(String colecoes) {

		String query = " select a.id, a.NUM_PLANO_MESTRE idPlanoMestre, a.codigo, a.grupo, a.item, "
				+ " a.rank, a.QTDE_PREVISAO qtdePrevisao, a.QTDE_ESTOQUE qtdeEstoque, "
				+ " a.QTDE_DEM_PLANO1 qtdeDemPlano1, a.QTDE_DEM_PLANO2 qtdeDemPlano2, a.QTDE_DEM_PLANO3 qtdeDemPlano3, a.QTDE_DEM_PLANO4 qtdeDemPlano4, "
				+ " a.QTDE_DEM_PLANO5 qtdeDemPlano5, a.QTDE_DEM_PLANO6 qtdeDemPlano6, a.QTDE_DEM_PLANO7 qtdeDemPlano7, a.QTDE_DEM_PLANO8 qtdeDemPlano8, "
				+ " a.QTDE_PROC_PLANO1 qtdeProcPlano1, a.QTDE_PROC_PLANO2 qtdeProcPlano2, a.QTDE_PROC_PLANO3 qtdeProcPlano3, a.QTDE_PROC_PLANO4 qtdeProcPlano4, "
				+ " a.QTDE_PROC_PLANO5 qtdeProcPlano5, a.QTDE_PROC_PLANO6 qtdeProcPlano6, a.QTDE_PROC_PLANO7 qtdeProcPlano7, a.QTDE_PROC_PLANO8 qtdeProcPlano8, "
				+ " a.QTDE_SALDO_PLANO1 qtdeSaldoPlano1, a.QTDE_SALDO_PLANO2 qtdeSaldoPlano2, a.QTDE_SALDO_PLANO3 qtdeSaldoPlano3, a.QTDE_SALDO_PLANO4 qtdeSaldoPlano4, "
				+ " a.QTDE_SALDO_PLANO5 qtdeSaldoPlano5, a.QTDE_SALDO_PLANO6 qtdeSaldoPlano6, a.QTDE_SALDO_PLANO7 qtdeSaldoPlano7, a.QTDE_SALDO_PLANO8 qtdeSaldoPlano8, "
				+ " a.QTDE_DEM_ACUMULADO qtdeDemAcumulado, a.QTDE_PROC_ACUMULADO qtdeProcAcumulado, a.QTDE_SALDO_ACUMULADO qtdeSaldoAcumulado, "
				+ " a.QTDE_DEM_ACUM_PROG qtdeDemAcumProg, a.QTDE_PROC_ACUM_PROG qtdeProcAcumProg, a.QTDE_SALDO_ACUM_PROG qtdeSaldoAcumProg, "
				+ " a.QTDE_SUGESTAO qtdeSugestao, a.QTDE_EQUALIZADO_SUGESTAO qtdeEqualizadoSugestao, a.QTDE_DIF_SUGESTAO qtdeDiferencaSugestao, a.QTDE_PROGRAMADA qtdeProgramada, "

				+ " nvl((select 'S' from orion_030 o " + " where o.nivel = '1' " + " and o.grupo = a.grupo "
				+ " and o.item = a.item" + " and o.colecao in (" + colecoes + ")),'N') sugCancelProducao, "

				+ " nvl((select max(b.grupo_embarque) from basi_590 b " + " where b.nivel = '1' "
				+ " and b.grupo = a.grupo " + " and b.item = a.item " + " ),0) embarque " + " from orion_016 a ";
		
		return query;
	}

	public List<ConsultaItensPlanoMestre> findItensPorRefCorByIdPlanoMestre(long idPlanoMestre) {

		String colecoes = getColecoesPlanoMestre(idPlanoMestre);

		String query = getQueryItensPorRefCor(colecoes);
		query += " where a.num_plano_mestre = " + idPlanoMestre;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaItensPlanoMestre.class));
	}

	public ConsultaItensPlanoMestre findItensPorRefCorByIdPlanoMestreGrupoItem(long idPlanoMestre, String grupo,
			String item) {

		String colecoes = getColecoesPlanoMestre(idPlanoMestre);

		String query = getQueryItensPorRefCor(colecoes);

		query += " where a.num_plano_mestre = " + idPlanoMestre;
		query += "	and a.grupo = '" + grupo + "'";
		query += " and a.item = '" + item + "'";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaItensPlanoMestre.class));
	}

	public List<ConsultaItensTamPlanoMestre> findItensPorTamByIdPlanoMestreGrupoItem(long idPlanoMestre, String grupo,
			String item, String colecoes) {

		String query = " select rownum id, a.num_plano_mestre idPlanoMestre, a.grupo, a.item, c.ordem_tamanho ordem, b.tamanho_ref sub, "
				+ " nvl((select m.qtde_estoque " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeEstoque, "
				+ " nvl((select m.qtde_dem_acum_prog " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeDemanda, "
				+ " nvl((select m.qtde_proc_acum_prog " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeProcesso, "
				+ " nvl((select m.qtde_saldo_acum_prog " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeSaldo, "
				+ " nvl((select m.qtde_sugestao " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeSugestao, "
				+ " nvl((select m.qtde_equalizado_sugestao " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeEqualizado, "
				+ " nvl((select m.qtde_dif_sugestao " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeDiferenca, "
				+ " nvl((select m.qtde_programada " + " from orion_015 m "
				+ " where m.num_plano_mestre = a.num_plano_mestre " + " and m.nivel = '1' " + " and m.grupo = a.grupo "
				+ " and m.sub = b.tamanho_ref " + " and m.item = a.item),0) qtdeProgramada "
				+ " from orion_016 a, basi_020 b, basi_220 c, "
				+ " (select d.referencia grupo, nvl((select 1 from basi_140 c " + " where c.colecao = d.colecao "
				+ " and c.descricao_espanhol like '%COLECAO PERMANENTE%'),0) permanente " + " from basi_030 d "
				+ " where d.nivel_estrutura = '1') ver_permanente " + " where b.basi030_nivel030 = '1' "
				+ " and b.basi030_referenc = a.grupo " + " and c.tamanho_ref = b.tamanho_ref "
				+ " and a.num_plano_mestre = " + idPlanoMestre + " and a.grupo = '" + grupo + "'" + " and a.item = '"
				+ item + "'" + " and ver_permanente.grupo = a.grupo ";

		if (!colecoes.equalsIgnoreCase("")) {
			query += " and (ver_permanente.permanente = 1 and exists (select 1 from basi_631 m, basi_632 n "
					+ " where n.cd_agrupador (+) = m.cd_agrupador " + " and n.grupo_ref (+) = m.grupo_ref "
					+ " and m.cd_agrupador in (" + colecoes + ") " // colecao informada + colecao da previsao de vendas
					+ " and m.grupo_ref = a.grupo "
					+ " and n.subgrupo_ref = b.tamanho_ref) or ver_permanente.permanente = 0) ";
		}

		query += " order by a.num_plano_mestre, a.grupo, a.item, c.ordem_tamanho ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaItensTamPlanoMestre.class));
	}

	public List<ConsultaProgramadoReferencia> findProgramacaoPorReferenciaByIdPlanoMestre(long idPlanoMestre) {

		String query = " select o.grupo, sum(o.qtde_programada) quantidade" + " from orion_015 o "
				+ " where o.num_plano_mestre = " + idPlanoMestre + " group by o.grupo " + " order by o.grupo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaProgramadoReferencia.class));
	}

	public List<GradeDistribuicaoGrupoItem> findDemandaByIdPlanoMestreGrupo(long idPlanoMestre, String grupo) {

		String query = " select o.grupo, o.item, sum(o.qtde_dem_acum_prog) qtdeItem, "
				+ "	(select sum(g.qtde_dem_acum_prog) from orion_015 g " + "	where g.num_plano_mestre = "
				+ idPlanoMestre + "	and g.grupo = o.grupo) qtdeGrupo " + " from orion_015 o "
				+ " where o.num_plano_mestre = " + idPlanoMestre + " and o.grupo = '" + grupo + "' "
				+ " group by o.grupo, o.item " + " order by o.grupo, o.item ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GradeDistribuicaoGrupoItem.class));
	}

	public List<GradeDistribuicaoGrupoItem> findSugestaoByIdPlanoMestreGrupo(long idPlanoMestre, String grupo) {

		String query = " select o.grupo, o.item, sum(o.qtde_sugestao) qtdeItem, "
				+ " (select sum(g.qtde_sugestao) from orion_015 g " + " where g.num_plano_mestre = " + idPlanoMestre
				+ " and g.grupo = o.grupo) qtdeGrupo " + " from orion_015 o " + " where o.num_plano_mestre = "
				+ idPlanoMestre + " and o.grupo = '" + grupo + "' " + " group by o.grupo, o.item "
				+ " order by o.grupo, o.item ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GradeDistribuicaoGrupoItem.class));
	}

	public List<GradeDistribuicaoGrupoItem> findDistribuicaoPadraoCorByIdPlanoMestreGrupo(long idPlanoMestre,
			String grupo) {

		String query = " select a.grupo_estrutura grupo, a.item_estrutura item, sum(a.distribuicao_cor) qtdeItem, "
				+ "	(select nvl(sum(b.distribuicao_cor),0) " + "	from basi_010 b "
				+ "	where b.nivel_estrutura = '1' " + "	and b.grupo_estrutura = a.grupo_estrutura "
				+ "	and b.item_estrutura in (select g.item from orion_016 g " + "	where g.num_plano_mestre = "
				+ idPlanoMestre + "	and g.grupo = a.grupo_estrutura)) qtdeGrupo " + "	from orion_016 o, basi_010 a "
				+ "	where o.num_plano_mestre = " + idPlanoMestre + " and o.grupo = '" + grupo + "'"
				+ "	and a.nivel_estrutura = '1' " + "	and a.grupo_estrutura = o.grupo "
				+ "	and a.item_estrutura = o.item " + "	group by a.grupo_estrutura, a.item_estrutura "
				+ "	order by a.grupo_estrutura, a.item_estrutura ";

		System.out.println("query: " + query);

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(GradeDistribuicaoGrupoItem.class));
	}

	public List<ProgramacaoPlanoMestre> findProgramacaoByIdPlanoMestre(long idPlanoMestre) {

		String query = "select a.grupo, a.sub, a.item, a.qtde_programada, c.alternativa, c.roteiro, c.periodo from orion_015 a, orion_016 b, orion_017 c "
				+ " where a.num_plano_mestre = " + idPlanoMestre + " and b.num_plano_mestre = a.num_plano_mestre "
				+ " and b.grupo = a.grupo " + " and b.item = a.item " + " and c.num_plano_mestre = a.num_plano_mestre "
				+ " and c.num_item_plano_mestre = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramacaoPlanoMestre.class));
	}

	public List<ConsultaPreOrdemProducao> findPreOrdensByIdPlanoMestre(long idPlanoMestre) {

		String query = "select a.id, a.referencia || ' - ' || b.descr_referencia referencia, a.periodo, "
				+ " a.alternativa || ' - ' || max(c.descricao) alternativa, a.roteiro, a.quantidade, a.status, max(a.mensagem_gravacao) mensagemGravacaoOrdem, "
				+ " a.deposito || ' - ' || max(d.descricao) deposito, a.observacao, max(a.ordem_gerada) ordemGerada "
				+ " from orion_020 a, basi_030 b, basi_070 c, basi_205 d " + " where a.num_plano_mestre = "
				+ idPlanoMestre + " and b.nivel_estrutura = '1' " + " and b.referencia = a.referencia "
				+ " and c.nivel (+) = '1' " + " and c.grupo (+) = a.referencia "
				+ " and c.alternativa (+) = a.alternativa " + " and d.codigo_deposito = a.deposito "
				+ " group by a.id, a.referencia, b.descr_referencia, a.periodo, a.alternativa, a.roteiro, a.deposito, a.observacao, a.quantidade, a.status ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPreOrdemProducao.class));
	}
		
	public int findQtdePecasProgByPreOrdens(List<Integer> preOrdens) {

		int qtdePecas = 0;

		String query = "select sum(a.quantidade) " + " from orion_020 a " + " where a.id in ("
				+ ConverteLista.converteListIntToStr(preOrdens) + ") ";

		try {
			qtdePecas = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdePecas = 0;
		}

		return qtdePecas;
	}

	public double findQtdeMinutosProgByPreOrdens(List<Integer> preOrdens) {

		double qtdeMinutos = 0.000;

		String query = " select sum(minutos.total_minutos) "
				+ " from (select a.referencia, b.sub, b.item, b.quantidade, sum(f.minutos_homem), b.quantidade * sum(f.minutos_homem) total_minutos "
				+ " from orion_020 a, orion_021 b, orion_016 c, orion_017 d, mqop_050 f " + " where a.id in ("
				+ ConverteLista.converteListIntToStr(preOrdens) + ") " + " and b.num_id_ordem = a.id "
				+ " and c.num_plano_mestre = a.num_plano_mestre " + " and c.grupo = a.referencia "
				+ " and c.item = b.item " + " and d.num_item_plano_mestre = c.id " + " and f.nivel_estrutura = '1' "
				+ " and f.grupo_estrutura = a.referencia "
				+ " and (f.item_estrutura = b.item or f.item_estrutura = '000000') "
				+ " and f.numero_alternati = d.alternativa " + " and f.numero_roteiro = d.roteiro "
				+ " and f.codigo_estagio = 20 " 
				+ " group by a.referencia, b.sub, b.item, b.quantidade) minutos ";

		try {
			qtdeMinutos = jdbcTemplate.queryForObject(query, Double.class);
		} catch (Exception e) {
			qtdeMinutos = 0.000;
		}

		return qtdeMinutos;
	}
	
	public int findQtdeReferenciasProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeReferencias = 0;

		String query = " select count(*) from (select a.referencia " + " from orion_020 a " + " where a.id in ("
				+ ConverteLista.converteListIntToStr(preOrdens) + ") " + " group by a.referencia) referencias ";

		try {
			qtdeReferencias = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeReferencias = 0;
		}

		return qtdeReferencias;
	}

	public int findQtdeSKUsProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeSKUs = 0;

		String query = " select count(*) from (select a.referencia, b.sub, b.item " + " from orion_020 a, orion_021 b "
				+ " where a.id in (" + ConverteLista.converteListIntToStr(preOrdens) + ") " + " and b.num_id_ordem = a.id "
				+ " group by a.referencia, b.sub, b.item) skus ";

		try {
			qtdeSKUs = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeSKUs = 0;
		}

		return qtdeSKUs;
	}

	public int findQtdeLoteMedioProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeLoteMedio = 0;

		String query = " select sum(a.quantidade) / count(*) " + "  from orion_020 a " + " where a.id in ("
				+ ConverteLista.converteListIntToStr(preOrdens) + ") ";

		try {
			qtdeLoteMedio = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeLoteMedio = 0;
		}

		return qtdeLoteMedio;
	}

	public long findIDMaiorOrdemByPreOrdens(List<Integer> preOrdens) {

		long id = 0;

		String query = " select a.id " + "  from orion_020 a " + " where a.id in (" + ConverteLista.converteListIntToStr(preOrdens) + ") "
				+ "   and a.quantidade = (select max(b.quantidade) from orion_020 b "
				+ "                        where b.id in (" + ConverteLista.converteListIntToStr(preOrdens) + ")) " + "   and rownum = 1 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return id;
	}

	public long findIDMenorOrdemByPreOrdens(List<Integer> preOrdens) {

		long id = 0;

		String query = " select a.id " + "  from orion_020 a " + " where a.id in (" + ConverteLista.converteListIntToStr(preOrdens) + ") "
				+ "   and a.quantidade = (select min(b.quantidade) from orion_020 b "
				+ "                        where b.id in (" + ConverteLista.converteListIntToStr(preOrdens) + ")) " + "   and rownum = 1 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return id;
	}

	public List<PlanoMestre> findAllPlanosMestreComPreOrdensNaoGeradas() {
	
		String query = " select a.num_plano_mestre id, a.descricao, a.data, a.situacao, a.usuario from orion_010 a " 
		+ " where a.data > sysdate - 30 "
		+ " and a.situacao in (0,1) "
		+ " and exists (select 1 from orion_020 b " 
		+ " where b.num_plano_mestre = a.num_plano_mestre " 
		+ " and b.ordem_gerada = 0) "
		+ " order by a.num_plano_mestre desc ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PlanoMestre.class));
	}
	
	public List<Produto> findAllReferenciasByPlanoMestre(String planosMestres) {
		
		List<Produto> referencias; 
		
		String query = " select a.grupo, b.descr_referencia narrativa from orion_016 a, basi_030 b "
		+ " where a.num_plano_mestre in (" + planosMestres + ") "
		+ " and b.nivel_estrutura = '1' "
		+ " and b.referencia = a.grupo "
		+ " group by a.grupo, b.descr_referencia "  
		+ " order by a.grupo, b.descr_referencia " ;
		
		try {
			referencias = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			referencias = new ArrayList<Produto>();
		}
		
		return referencias; 
	}
	
	public int findNextIdPreOrdem() {
		String query = " select id_orion_020.nextval from dual ";
		return (int) jdbcTemplate.queryForObject(query, Integer.class);
	}

	public int findNextIdPreOrdemItem() {
		String query = " select id_orion_021.nextval from dual ";
		return (int) jdbcTemplate.queryForObject(query, Integer.class);
	}

	private String getColecoesPlanoMestre(long idPlanoMestre) {

		String query = "";
		String colecoes = "";
		String colecoesPermanentes = "";
		String previsoes = "";
		List<Integer> colecoesPrevisao;

		query = " select m.colecoes from orion_011 m where m.num_plano_mestre = " + idPlanoMestre;

		try {
			colecoes = jdbcTemplate.queryForObject(query, String.class);
			if (colecoes.equalsIgnoreCase(""))
				colecoes = "0";
		} catch (Exception e) {
			colecoes = "0";
		}

		query = " select m.colecoes_permanentes from orion_011 m where m.num_plano_mestre = " + idPlanoMestre;

		try {
			colecoesPermanentes = jdbcTemplate.queryForObject(query, String.class);
			if (colecoesPermanentes.equalsIgnoreCase(""))
				colecoesPermanentes = "0";
		} catch (Exception e) {
			colecoesPermanentes = "0";
		}

		colecoes += "," + colecoesPermanentes;

		query = " select m.previsoes from orion_011 m where m.num_plano_mestre = " + idPlanoMestre;

		try {
			previsoes = jdbcTemplate.queryForObject(query, String.class);
			if (previsoes.equalsIgnoreCase(""))
				previsoes = "0";
		} catch (Exception e) {
			previsoes = "0";
		}

		query = " select a.colecao from orion_040 a where a.id in ( " + previsoes + " ) ";

		try {
			colecoesPrevisao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Integer.class));
		} catch (Exception e) {
			colecoesPrevisao = new ArrayList<Integer>();
		}

		if (!colecoesPrevisao.isEmpty())
			colecoes += "," + ConverteLista.converteListIntToStr(colecoesPrevisao);

		return colecoes;
	}

	public int findNextIdPlanoMestre() {
		String query = " select id_orion_010.nextval from dual ";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public int findNextIdPlanoMestreItens() {
		String query = " select id_orion_015.nextval from dual ";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int findNextIdPlanoMestreItemPorCor() {
		String query = " select id_orion_016.nextval from dual ";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public int findColecaoProdutoByRangeColecoes(String nivel, String grupo, String sub, String item, String colecoes) {
		
		// Localiza pelo produto completo
		String query = " select nvl(max(o.colecao),0) colecao "
		+ " from orion_vi_itens_x_colecoes o "
		+ " where o.nivel = '" + nivel + "' "
		+ " and o.referencia = '" + grupo + "'"
  	    + " and o.tamanho = '" + sub + "'"
  	    + " and o.cor = '" + item + "'"
		+ " and o.colecao not in (select basi_140.colecao from basi_140 "
        + " where basi_140.descricao_espanhol like '%PERMANENTE%')";
		
		if ((colecoes != null) && (!colecoes.isEmpty()))
			query += " and o.colecao in (" + colecoes + ")";
		
		int colecao = jdbcTemplate.queryForObject(query, Integer.class);
		
		// Caso não encontre, localiza apenas pela referência
		if (colecao == 0) {
			query = " select nvl(max(o.colecao),0) colecao "
			+ " from orion_vi_itens_x_colecoes o "
			+ " where o.nivel = '" + nivel + "' "
			+ " and o.referencia = '" + grupo + "'"
			+ " and o.colecao not in (select basi_140.colecao from basi_140 "
	        + " where basi_140.descricao_espanhol like '%PERMANENTE%')";

			if ((colecoes != null) && (!colecoes.isEmpty()))
				query += " and o.colecao in (" + colecoes + ")";
			
			colecao = jdbcTemplate.queryForObject(query, Integer.class);
		}
					
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
}