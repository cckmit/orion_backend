package br.com.live.custom;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.EstoqueProduto;
import br.com.live.model.Deposito;
import br.com.live.util.FormataParametrosPlanoMestre;
import br.com.live.util.ParametrosPlanoMestre;

@Repository
public class EstoqueProdutoCustom {

	private final EntityManager manager;
	private final JdbcTemplate jdbcTemplate;

	public EstoqueProdutoCustom(EntityManager manager, JdbcTemplate jdbcTemplate) {
		this.manager = manager;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<EstoqueProduto> findByParameters(ParametrosPlanoMestre parametros) {

		String query = "select new br.com.live.entity.EstoqueProduto (e.id, e.nivel, e.grupo, e.sub, e.item, e.deposito, e.quantidade, e.colecao, e.linha, e.artigo, e.artigoCotas, e.origem, e.permanente, e.embarque) from EstoqueProduto e ";
		String condicao = "where ";

		FormataParametrosPlanoMestre parametrosFormatados = new FormataParametrosPlanoMestre(parametros);

		if (!parametrosFormatados.consideraDepositos())
			return null;

		if (!parametrosFormatados.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " e.colecao in " + parametrosFormatados.getColecoes();
			condicao = " and ";
		}

		if (!parametrosFormatados.getLinhasProduto().equalsIgnoreCase("")) {
			query += condicao + " e.linha in " + parametrosFormatados.getLinhasProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosProduto().equalsIgnoreCase("")) {
			query += condicao + " e.artigo in " + parametrosFormatados.getArtigosProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosCotas().equalsIgnoreCase("")) {
			query += condicao + " e.artigoCotas in " + parametrosFormatados.getArtigosCotas();
			condicao = " and ";
		}

		if (!parametrosFormatados.getDepositos().equalsIgnoreCase("")) {
			query += condicao + " e.deposito in " + parametrosFormatados.getDepositos();
			condicao = " and ";			
		}		
		
		if (!parametrosFormatados.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " (e.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ parametrosFormatados.getSubColecoes();
			query += " and s.grupo = e.grupo ";
			query += " and s.sub = e.sub ";
			query += " and s.item = e.item ) or e.permanente = 0) ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getPublicosAlvo().equalsIgnoreCase("")) {
			query += condicao + " exists (select 1 from PublicoAlvoProduto pa where pa.grupo = e.grupo ";
			query += " and pa.codigo in " + parametrosFormatados.getPublicosAlvo() + " ) ";
			condicao = " and ";
		}		
		
		if (!parametrosFormatados.getEmbarques().equalsIgnoreCase("")) {
			query += condicao + " e.embarque in " + parametrosFormatados.getEmbarques();
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getProdutos().equalsIgnoreCase("")) {
			query += condicao + " e.grupo in " + parametrosFormatados.getProdutos();
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getCores().equalsIgnoreCase("")) {
			query += condicao + " e.item in " + parametrosFormatados.getCores();
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getOrigemProdutos().equalsIgnoreCase("")) {
			query += condicao + " e.origem in " + parametrosFormatados.getOrigemProdutos();
			condicao = " and ";
		}
					
		if (!parametrosFormatados.consideraSemEstoque()) {
			query += condicao + " e.quantidade > 0.00 ";
		}

		System.out.println("Estoques - query: " + query);

		var q = manager.createQuery(query, EstoqueProduto.class);

		return q.getResultList();
	}

	public List<Deposito> findAllDepositos() {
		
		String query = " select b.codigo_deposito id, b.descricao from basi_205 b "
		+ " where b.descricao like 'DEP N1%' "
		  + " and b.tipo_volume in (0,1) "
		  + " and b.considera_tmrp = 1 "
		+ " order by b.codigo_deposito " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Deposito.class));		
	}
	
}
