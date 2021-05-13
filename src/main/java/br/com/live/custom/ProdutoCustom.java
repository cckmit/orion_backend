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
import br.com.live.model.ConsultaDadosCompEstrutura;
import br.com.live.model.ConsultaDadosEstrutura;
import br.com.live.model.ConsultaDadosFilete;
import br.com.live.model.ConsultaDadosRoteiro;
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

		String query = "select nvl(basi_050.sequencia,0) " 
		+ " from basi_050 "
		+ " where basi_050.nivel_item = '1' " 
		+ " and basi_050.grupo_item       = '" + grupo + "'"
		+ " and (basi_050.item_item       = '" + item + "' or basi_050.item_item = '000000') "
		+ " and basi_050.alternativa_item = " + alternativa 
		+ " and basi_050.tecido_principal = 1 "
		+ " and rownum = 1 " 
		+ " order by basi_050.consumo desc ";

		Integer sequencia = 0;

		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}

		return (int) sequencia;
	}

	public int findMenorSeqRisco(String grupo, int alternativa) {
		
		String query = " select min(pcpc_200.ordem_estrutura) from pcpc_200 "
		+ " where pcpc_200.codigo_risco = 1 "
		+ " and pcpc_200.grupo_riscado = '00000' "                          
		+ " and pcpc_200.alternativa_item = " + alternativa
		+ " and pcpc_200.grupo_estrutura = '" + grupo + "'";                                                           

		Integer sequencia = 0;

		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}

		return (int) sequencia;
	}
		
	public int findQtdeMarcacoesTamanho(String grupo, String sub, int risco, int seqRisco, int alternativa) {
		
		int qtdeMarcacoes;
		
		String query = "select sum(pcpc_200.qtde_marcacoes) "
		+ " from pcpc_200 " 
		+ " where pcpc_200.codigo_risco = " + risco
		+ " and pcpc_200.grupo_estrutura = '" + grupo + "' " 
		+ " and pcpc_200.ordem_estrutura = " + seqRisco
		+ " and pcpc_200.alternativa_item = " + alternativa
		+ " and pcpc_200.tamanho = " + sub;
		
		try {
			qtdeMarcacoes = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeMarcacoes = 0;
		}
	
		return qtdeMarcacoes;
	}
	
	public List<MarcacaoRisco> findMarcacoesRisco(String grupo, int risco, int seqRisco, int alternativa) {

		String query = "select pcpc_200.grupo_estrutura grupo, pcpc_200.tamanho sub, pcpc_200.qtde_marc_proj quantidade "
				+ " from pcpc_200 " 
				+ " where pcpc_200.codigo_risco = " + risco
				+ " and pcpc_200.grupo_estrutura = '" + grupo + "' " 
				+ " and pcpc_200.ordem_estrutura  = " + seqRisco
				+ " and pcpc_200.alternativa_item = " + alternativa;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MarcacaoRisco.class));
	}

	public int findOrdemTamanho(String tamanho) {
		
		Integer ordemTamanho;
		
		String query = " select a.ordem_tamanho from basi_220 a where a.tamanho_ref = '" + tamanho + "'";
		
		try {
			ordemTamanho = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			ordemTamanho = 0;
		}

		return ordemTamanho;
	}	

	public int findLoteFabricacao(String grupo, String sub) {
		
		int loteFabricao;
		
		String query = " select basi_020.lote_fabr_pecas from basi_020 "            
		+ " where basi_020.basi030_nivel030 = '1' " 
        + " and basi_020.basi030_referenc = '" + grupo + "'"
        + " and basi_020.tamanho_ref      = '" + sub + "'" ;
	     
		try {
			loteFabricao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			loteFabricao = 0;
		}
	      
	    if (loteFabricao == 0) loteFabricao = 99999999;
	      
	    return loteFabricao;    		
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
	
	public boolean isProdutoComprado(String grupo) {
	      
		int encontrou = 0;
		
		String query = " select 1 from basi_030 "
	    + " where basi_030.nivel_estrutura = '1' "
	    + " and basi_030.referencia      = '" + grupo + "' " 
	    + " and basi_030.comprado_fabric = 1 ";

		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);	   	
	}
	
	public boolean existsEstrutura(String grupo, String sub, String item, int alternativa) {
	
		int encontrou = 0;
		
		String query = " select 1 from basi_050 "
	    + " where  basi_050.nivel_item = '1' "  
	    + " and  basi_050.grupo_item = '" + grupo + "'"
	    + " and (basi_050.sub_item = '" + sub + "' or basi_050.sub_item  = '000') "
	    + " and (basi_050.item_item = '" + item + "' or basi_050.item_item = '000000') "
	    + " and  basi_050.alternativa_item = " + alternativa
		+ " and rownum = 1 ";
	    
		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);	   	
	}
	
	public boolean existsRoteiro(String grupo, String sub, String item, int alternativa, int roteiro) {
		int encontrou = 0;
		
		String query = " select 1 from mqop_050 "
	    + " where mqop_050.nivel_estrutura = '1' "
	    + " and mqop_050.grupo_estrutura   = '" + grupo + "'"
	    + " and (mqop_050.subgru_estrutura = '" + sub + "' or mqop_050.subgru_estrutura = '000') "
	    + " and (mqop_050.item_estrutura   = '" + item + "' or mqop_050.item_estrutura   = '000000') "
	    + " and mqop_050.numero_alternati  = " + alternativa
	    + " and mqop_050.numero_roteiro    = " + roteiro 
	    + " and rownum = 1 ";
	    
		try {
			encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);	   	
	}
	
	private String[] getCodProdutoCadRoteiro(String grupo, String sub, String item, int alternativa, int roteiro) {
		
		String[] codProdutoRoteiro = new String[3];
		
		String grupoRoteiro = grupo;
		String subRoteiro = sub;
		String itemRoteiro = item;
		
	    String query = " select 1 from mqop_050 "
	    + " where mqop_050.nivel_estrutura = '1' "
	    + " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
	    + " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
	    + " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
	    + " and mqop_050.numero_alternati = " + alternativa
	    + " and mqop_050.numero_roteiro   = " + roteiro
	    + " and rownum = 1 ";
	    
		try {
			jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {

			itemRoteiro = "000000";
			
	        query = " select 1 from mqop_050 "
	        + " where mqop_050.nivel_estrutura = '1' "
		    + " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
		    + " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
		    + " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
		    + " and mqop_050.numero_alternati = " + alternativa
		    + " and mqop_050.numero_roteiro   = " + roteiro
		    + " and rownum = 1 ";

			try {
				jdbcTemplate.queryForObject(query, Integer.class);
			} catch (Exception f) {
				
				subRoteiro = "000";
				itemRoteiro = item;
				
		        query = " select 1 from mqop_050 "
		        + " where mqop_050.nivel_estrutura = '1' "
			    + " and mqop_050.grupo_estrutura  = '" + grupoRoteiro + "'"
			    + " and mqop_050.subgru_estrutura = '" + subRoteiro + "'"
			    + " and mqop_050.item_estrutura   = '" + itemRoteiro + "'"
			    + " and mqop_050.numero_alternati = " + alternativa
			    + " and mqop_050.numero_roteiro   = " + roteiro
			    + " and rownum = 1 ";
				
				try {
					jdbcTemplate.queryForObject(query, Integer.class);
				} catch (Exception g) {
					itemRoteiro = "000000";
				}
			}
		}
		
		codProdutoRoteiro[0] = grupoRoteiro;
		codProdutoRoteiro[1] = subRoteiro;
		codProdutoRoteiro[2] = itemRoteiro;
		
		return codProdutoRoteiro;
	}
	
	public List<ConsultaDadosRoteiro> findDadosRoteiro(String grupo, String sub, String item, int alternativa, int roteiro) {
		
		String[] strCodProdCadRoteiro = getCodProdutoCadRoteiro(grupo, sub, item, alternativa, roteiro);
		String grupoRot = strCodProdCadRoteiro[0];
		String subRot = strCodProdCadRoteiro[1];
		String itemRot = strCodProdCadRoteiro[2];

		String query = " select mqop_050.codigo_estagio estagio, mqop_050.codigo_operacao operacao, "
        + " mqop_050.centro_custo centroCusto, mqop_050.minutos, " 
        + " mqop_050.codigo_familia familia, mqop_050.seq_operacao seqOperacao, "
        + " mqop_050.sequencia_estagio seqEstagio, mqop_050.estagio_depende estagioDepende, "
        + " mqop_040.pede_produto pedeProduto, nvl((select 1 from mqop_045 "
        + " where mqop_045.codigo_operacao = mqop_050.codigo_operacao),0) tipoOperCmc "
        + " from mqop_040, mqop_050 "
        + " where mqop_040.codigo_operacao  = mqop_050.codigo_operacao "
        + " and mqop_050.nivel_estrutura = '1' "
        + " and mqop_050.grupo_estrutura = '" + grupoRot + "'" 
        + " and mqop_050.subgru_estrutura = '" + subRot + "'"
        + " and mqop_050.item_estrutura = '" + itemRot + "'"
        + " and mqop_050.numero_alternati = " + alternativa
        + " and mqop_050.numero_roteiro = " + roteiro
        + " order by mqop_050.seq_operacao ASC, "
        + " mqop_050.sequencia_estagio ASC, "
        + " mqop_050.estagio_depende ASC, "
        + " mqop_050.codigo_estagio ASC " ;
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosRoteiro.class));
	}
	
	public List<ConsultaDadosEstrutura> findDadosEstrutura(String grupo, String sub, String item, int alternativa) {
				
		String query = " select basi_050.sequencia, basi_050.qtde_camadas qtdeCamadas, "
        + " basi_050.sub_item subItem, basi_050.item_item itemItem, "                        
        + " basi_050.nivel_comp nivelComp, basi_050.grupo_comp grupoComp, "
        + " basi_050.sub_comp subComp, basi_050.item_comp itemComp, "
        + " basi_050.consumo, basi_050.alternativa_comp alternativaComp, "
        + " basi_050.percent_perdas percPerdas "
        + " from basi_050 "
        + " where  basi_050.nivel_item = '1' "
        + " and basi_050.grupo_item = '" + grupo + "'" 
        + " and (basi_050.sub_item = '" + sub + "' or basi_050.sub_item = '000') "
        + " and (basi_050.item_item = '" + item + "' or basi_050.item_item = '000000') "
        + " and basi_050.alternativa_item = " + alternativa
        + " and basi_050.nivel_comp       = '2' "                                                                                                  
        + " order by basi_050.sequencia ASC " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosEstrutura.class));
	}
	
	public ConsultaDadosCompEstrutura findDadosComponenteEstrutura(String grupo, String sub, String item, int sequencia, int alternativa){
				
		ConsultaDadosCompEstrutura dadosCompEstrutura = null;
		
		String query = " select basi_040.sub_comp sub, basi_040.consumo, basi_040.item_comp item, basi_040.alternativa_comp alternativa"
		+ " from basi_040 "
		+ " where basi_040.nivel_item = '1' "
		+ " and basi_040.grupo_item = '" + grupo + "'" 
		+ " and basi_040.sub_item = '" + sub + "'"
		+ " and basi_040.item_item = '" + item + "'"
		+ " and basi_040.sequencia = " + sequencia
		+ " and basi_040.alternativa_item = " + alternativa 
		+ " and rownum = 1 ";
		
		try {
			dadosCompEstrutura = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaDadosCompEstrutura.class));
		} catch (Exception e) {
			dadosCompEstrutura = new ConsultaDadosCompEstrutura();
		}
		
		return dadosCompEstrutura;
	}
	
	public ConsultaDadosFilete findDadosFileteEstrutura(String grupo, String sub, String item, int sequencia, int alternativa) {
		
		ConsultaDadosFilete dadosFilete = null;
		
		String query = " select basi_060.tipo_corte_peca tipoCorte, basi_060.comprimento_debrum comprimentoFilete, basi_060.largura_debrum larguraFilete" 
        + " from basi_060 "
        + " where  basi_060.grupo_estrutura = '" + grupo + "'" 
        + " and (basi_060.subgru_estrutura = '" + sub + "' or basi_060.subgru_estrutura = '000') "
        + " and (basi_060.item_estrutura = '" + item + "' or basi_060.item_estrutura   = '000000') "
        + " and  basi_060.alternativa_item = " + alternativa
        + " and  basi_060.sequencia = " + sequencia
        + " and rownum = 1 ";
		
		try {
			dadosFilete = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}
		
		return dadosFilete;
	}
	
	public ConsultaDadosFilete findDadosFileteRisco(String grupo, int risco, int sequencia, int alternativa) {
		
		ConsultaDadosFilete dadosFilete = null;
		
		String query = " select pcpc_200.largura larguraRisco"
		+ " from pcpc_200 "
        + " where pcpc_200.codigo_risco = " + risco
        + " and pcpc_200.grupo_estrutura = '" + grupo + "'"
        + " and pcpc_200.alternativa_item = " + alternativa
        + " and pcpc_200.ordem_estrutura = " + sequencia
        + " and pcpc_200.grupo_riscado = '00000' "
        + " and rownum = 1 ";
		
		try {
			dadosFilete = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}
		
		return dadosFilete;
	}
	
	public ConsultaDadosFilete findDadosFileteTecidos(String grupo, String sub) {
		
		ConsultaDadosFilete dadosFilete = null;
		       
        String query = " select basi_020.largura_1 larguraTecido, basi_020.tubular_aberto tubularAberto"
        + " from basi_020 "
        + " where basi_020.basi030_nivel030 = '1' "
        + " and basi_020.basi030_referenc = '" + grupo + "'"
        + " and basi_020.tamanho_ref      = '" + sub + "'" ;
		
		try {
			dadosFilete = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaDadosFilete.class));
		} catch (Exception e) {
			dadosFilete = new ConsultaDadosFilete();
		}
		
		return dadosFilete;
	}
	
}
