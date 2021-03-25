package br.com.live.custom;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.com.live.entity.CorProduto;
import br.com.live.entity.Embarque;
import br.com.live.entity.MarcacaoRisco;
import br.com.live.entity.Produto;
import br.com.live.entity.ProdutoReferCor;
import br.com.live.model.Alternativa;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.ArtigoProduto;
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

		String query = "select new br.com.live.entity.CorProduto (c.id, c.item, c.descricao) from CorProduto c ";
		String condicao = "where ";

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " exists ( select 1 from ProdutoReferCor p where p.colecao in " + filtro.getColecoes();
			query += " and p.item = c.item ) ";
			condicao = " and ";
		}

		if (!filtro.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " exists (select 1 from SubColecao s where s.colecao in " + filtro.getSubColecoes();
			query += " and s.item = c.item ) ";
		}

		query += " order by c.item ";

		System.out.println("CorProdutos - query: " + query);

		var q = manager.createQuery(query, CorProduto.class);

		return q.getResultList();
	}

	public List<Embarque> findAllEmbarques() {

		String query = "select new br.com.live.entity.Embarque (e.id, e.descricao) from Embarque e order by e.descricao";
		System.out.println("Embarque - query: " + query);

		var q = manager.createQuery(query, Embarque.class);

		return q.getResultList();
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

	public List<MarcacaoRisco> findMarcacoesByParameters(FiltroProduto filtro) {

		String query = "select new br.com.live.entity.MarcacaoRisco (m.id, m.grupo, m.sub, m.codigoRisco, m.alternativa, m.qtdeMarcacao, m.colecao) from MarcacaoRisco m ";
		String condicao = "where ";

		if (!filtro.getReferencias().equalsIgnoreCase("")) {
			query += condicao + " m.grupo in " + filtro.getReferencias();
			condicao = " and ";
		}

		if (!filtro.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " m.colecao in " + filtro.getColecoes();
			condicao = " and ";
		}

		System.out.println("MarcacoesRisco - query: " + query);

		var q = manager.createQuery(query, MarcacaoRisco.class);

		return q.getResultList();
	}

	public List<Alternativa> findAlternativasByCodigo(String grupo, String item) {

		String query = "select max(rownum) id, b.alternativa_item alternativa, nvl(c.descricao, ' ') descricao from basi_050 b, basi_070 c"
				+ " where b.nivel_item       = '1' "
		  + " and c.nivel (+) = b.nivel_item "
		  + " and c.grupo (+) = b.grupo_item "
		  + " and c.alternativa (+) = b.alternativa_item " 
		  + " and b.grupo_item = '" + grupo + "'" 
		  + " and (b.item_item = '" + item + "' or b.item_item = '000000') "
		+ " group by b.nivel_item, b.grupo_item, b.item_item, b.alternativa_item, c.descricao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Alternativa.class));
	}

	public List<Roteiro> findRoteirosByCodigo(String grupo, String item, int alternativa) {

		String query = "select m.numero_roteiro codigo from mqop_050 m"
				+ " where m.nivel_estrutura  = '1' "
				+ " and m.grupo_estrutura  = '" + grupo + "'"       
		        + " and m.numero_alternati = " + alternativa
		        + " group by m.numero_roteiro";     
		  
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Roteiro.class));
	}

	public AlternativaRoteiroPadrao findAlternativaRoteiroPadraoByCodigo(String grupo, String item) {

		String query = "select b.grupo_estrutura grupo, b.item_estrutura item, max(b.numero_alternati) alternativa, max(b.numero_roteiro) roteiro from basi_010 b"
				+ " where b.nivel_estrutura = '1' " + " and b.grupo_estrutura = '" + grupo + "'"
				+ " and b.item_estrutura = '" + item + "'" + " group by b.grupo_estrutura, b.item_estrutura ";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(AlternativaRoteiroPadrao.class));
	}

	public List<ArtigoProduto> findAllArtigosProduto() {

		String query = "select b.artigo id, b.descr_artigo descricao from basi_290 b"
				+ " where b.nivel_estrutura = '1' " + "   and b.descr_artigo <> '.' " + " order by b.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ArtigoProduto.class));
	}	 
}
