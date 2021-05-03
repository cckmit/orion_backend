package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Produto;
import br.com.live.entity.ProdutoReferCor;
import br.com.live.model.Alternativa;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.ArtigoCotas;
import br.com.live.model.ArtigoProduto;
import br.com.live.model.Colecao;
import br.com.live.model.CorProduto;
import br.com.live.model.Embarque;
import br.com.live.model.LinhaProduto;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.PublicoAlvo;
import br.com.live.model.Roteiro;
import br.com.live.util.FiltroProduto;

@Repository
public class ProdutoCustom {

	private final EntityManager manager;
	private final JdbcTemplate jdbcTemplate;

	public ProdutoCustom(EntityManager manager, JdbcTemplate jdbcTemplate) {
		this.manager = manager;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Produto> findProdutosByParameters(FiltroProduto filtro) {

		String query = "select new br.com.live.entity.Produto (p.id, p.grupo, p.descricao, p.colecao, p.permanente) from Produto p ";
		String condicao = "where ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " colecao in " + filtro.getColecoes();
			condicao = " and ";
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + "(p.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ filtro.getSubColecoes();
			query += " and s.grupo = p.grupo ) or p.permanente = 0)";
			condicao = " and ";
		}

		query += " order by p.grupo ";

		System.out.println("Produtos - query: " + query);

		var q = manager.createQuery(query, Produto.class);

		return q.getResultList();
	}

	public List<CorProduto> findCoresByParameters(FiltroProduto filtro) {
		
		List<CorProduto> cores;
		
		String query = "select max(rownum) id, c.item_estrutura item, c.descricao_15 descricao "
		+ " from basi_030 a, basi_010 c "
		+ " where a.nivel_estrutura = c.nivel_estrutura "
		  + " and a.referencia = c.grupo_estrutura "
		  + " and c.nivel_estrutura = '1' "
		  + " and c.item_ativo = 1 "
		  + " and c.descricao_15 <> '.' " ;
		  
		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += " and a.colecao in " + filtro.getColecoes();
		} 
		 
		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {			
			query += " and exists (select * from basi_632 f " 
				  + " where f.cd_agrupador in " + filtro.getSubColecoes()
		          + "   and f.item_ref = c.item_estrutura) " ;
		}
		
		query += " group by c.item_estrutura, c.descricao_15 "
		      + " order by c.item_estrutura, c.descricao_15 " ;
		
		System.out.println("CorProdutos - query: " + query);
		
		try {
			cores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CorProduto.class));
		} catch (Exception e) {
			cores = new ArrayList<CorProduto> ();
		}
		
		return cores;
	}

	public List<Embarque> findAllEmbarques() {

		String query = "select min(rownum) id, b.codigo_cliente descricao from basi_010 b where b.codigo_cliente like '% EMBARQUE' "
				+ " group by b.codigo_cliente " + " order by b.codigo_cliente ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Embarque.class));
	}

	public List<ProdutoReferCor> findItensByParameters(FiltroProduto filtro) {

		String query = "select new br.com.live.entity.ProdutoReferCor (p.id, p.grupo, p.item, p.descricao, p.colecao, p.permanente, p.embarque, p.sugCancelProducao, p.alternativaPadrao, p.roteiroPadrao, p.riscoPadrao) from ProdutoReferCor p ";
		String condicao = "where ";

		if (!filtro.getReferencias().equalsIgnoreCase("")) {
			query += condicao + " p.grupo in " + filtro.getReferencias();
			condicao = " and ";
		}

		if (!filtro.getCores().equalsIgnoreCase("")) {
			query += condicao + " p.item in " + filtro.getCores();
			condicao = " and ";
		}

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " p.colecao in " + filtro.getColecoes();
			condicao = " and ";
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " (p.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ filtro.getSubColecoes();
			query += " and s.grupo = p.grupo ";
			query += " and s.item = p.item ) or p.permanente = 0) ";
		}

		query += " order by p.grupo, p.item ";

		System.out.println("ProdutoReferCor - query: " + query);

		var q = manager.createQuery(query, ProdutoReferCor.class);

		return q.getResultList();
	}

	public ProdutoReferCor findItemByCodigo(String grupo, String item) {
		String query = "select new br.com.live.entity.ProdutoReferCor (p.id, p.grupo, p.item, p.descricao, p.colecao, p.permanente, p.embarque, p.sugCancelProducao, p.alternativaPadrao, p.roteiroPadrao, p.riscoPadrao) from ProdutoReferCor p ";
		query += " where p.grupo = '" + grupo + "'";
		query += " and p.item = '" + item + "'";

		var q = manager.createQuery(query, ProdutoReferCor.class);

		return q.getSingleResult();
	}

	public int findRiscoPadraoByCodigo(String grupo) {

		String query = "select b.risco_padrao from basi_030 b where b.nivel_estrutura = '1' " + " and b.referencia = '"
				+ grupo + "'";

		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public List<Alternativa> findAlternativasByCodigo(String grupo, String item) {

		String query = "select max(rownum) id, b.alternativa_item alternativa, nvl(c.descricao, ' ') descricao from basi_050 b, basi_070 c"
				+ " where b.nivel_item       = '1' " + " and c.nivel (+) = b.nivel_item "
				+ " and c.grupo (+) = b.grupo_item " + " and c.alternativa (+) = b.alternativa_item "
				+ " and b.grupo_item = '" + grupo + "'" + " and (b.item_item = '" + item
				+ "' or b.item_item = '000000') "
				+ " group by b.nivel_item, b.grupo_item, b.item_item, b.alternativa_item, c.descricao "
				+ " order by b.alternativa_item, c.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Alternativa.class));
	}

	public List<Roteiro> findRoteirosByCodigo(String grupo, String item, int alternativa) {

		String query = "select m.numero_roteiro codigo from mqop_050 m" + " where m.nivel_estrutura  = '1' "
				+ " and m.grupo_estrutura  = '" + grupo + "'" + " and m.numero_alternati = " + alternativa
				+ " group by m.numero_roteiro" + " order by m.numero_roteiro";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Roteiro.class));
	}

	public AlternativaRoteiroPadrao findAlternativaRoteiroPadraoByCodigo(String grupo, String item) {

		String query = "select b.grupo_estrutura grupo, b.item_estrutura item, max(b.numero_alternati) alternativa, max(b.numero_roteiro) roteiro from basi_010 b"
				+ " where b.nivel_estrutura = '1' " + " and b.grupo_estrutura = '" + grupo + "'"
				+ " and b.item_estrutura = '" + item + "'" + " group by b.grupo_estrutura, b.item_estrutura ";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(AlternativaRoteiroPadrao.class));
	}

	public List<ArtigoCotas> findAllArtigosCotas() {

		String query = "select b.artigo_cotas id, b.descr_artigo descricao from basi_295 b "
				+ " where b.nivel_estrutura = '1' " + " and b.descr_artigo <> '.' " + " order by b.artigo_cotas ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoCotas.class));
	}

	public List<ArtigoProduto> findAllArtigosProduto() {

		String query = "select b.artigo id, b.descr_artigo descricao from basi_290 b"
				+ " where b.nivel_estrutura = '1' " + "   and b.descr_artigo <> '.' " + " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoProduto.class));
	}

	public List<Colecao> findAllColecoes() {

		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol not like '%COLECAO PERMANENTE%' "
				+ " and b.descricao_espanhol not like '%COLECAO ANTIGA%' " + " and b.colecao > 0 "
				+ " order by colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<Colecao> findAllColecoesPermanentes() {

		String query = " select b.colecao id, b.descr_colecao descricao from basi_140 b "
				+ " where b.descricao_espanhol like '%COLECAO PERMANENTE%' " + " and b.colecao > 0 "
				+ " order by colecao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Colecao.class));
	}

	public List<LinhaProduto> findAllLinhasProdutos() {

		String query = " select b.linha_produto id, b.descricao_linha descricao from basi_120 b "
				+ " where b.nivel_estrutura = '1' " + " order by b.linha_produto ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LinhaProduto.class));
	}

	public List<PublicoAlvo> findAllPublicosAlvos() {

		String query = " select h.codigo id, h.descricao from hdoc_001 h " + " where h.tipo = 9 "
				+ " order by h.codigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PublicoAlvo.class));
	}

	public int findSequenciaPrincipalRisco(String grupo, String item, int alternativa) {

		String query = "select nvl(basi_050.sequencia,0) " + " from basi_050 "
				+ " where basi_050.nivel_item       = '1' " + " and basi_050.grupo_item       = '" + grupo + "'"
				+ " and (basi_050.item_item       = '" + item + "' or basi_050.item_item = '000000') "
				+ " and basi_050.alternativa_item = " + alternativa + " and basi_050.tecido_principal = 1 "
				+ " and rownum = 1 " + " order by basi_050.consumo desc ";

		Integer sequencia = 0;

		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}

		return (int) sequencia;
	}

	public List<MarcacaoRisco> findMarcacoesRisco(String grupo, int risco, int seqRisco, int alternativa) {

		String query = "select pcpc_200.grupo_estrutura grupo, pcpc_200.tamanho sub, pcpc_200.qtde_marc_proj quantidade "
				+ " from pcpc_200 " + " where pcpc_200.codigo_risco     = " + risco
				+ " and pcpc_200.grupo_estrutura  = '" + grupo + "' " + " and pcpc_200.ordem_estrutura  = " + seqRisco
				+ " and pcpc_200.alternativa_item = " + alternativa;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MarcacaoRisco.class));
	}

	public boolean existsItem(String grupo, String item) {
		
		int encontrou = 0;
		
		String query = " select 1 from basi_010 a "
		+ " where a.nivel_estrutura = '1' "
		+ " and a.grupo_estrutura = '" + grupo + "'"
		+ " and a.item_estrutura = '" + item + "'"
		+ " and rownum = 1 ";
		
		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}
	
}
