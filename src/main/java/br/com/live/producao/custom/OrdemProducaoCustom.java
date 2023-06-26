package br.com.live.producao.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.DadosTagChina;
import br.com.live.producao.model.EstagioProducao;
import br.com.live.producao.model.OrdemConfeccao;
import br.com.live.producao.model.OrdemProducao;
import br.com.live.producao.model.OrdemProducaoItem;
import br.com.live.produto.service.ProdutoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class OrdemProducaoCustom {

	private JdbcTemplate jdbcTemplate;
	private final ProdutoService produtoService;  
	public final static int ESTAGIO_COSTURA = 20;  
	public final static int LINHA_FACCAO = 1001;
	
	public OrdemProducaoCustom(JdbcTemplate jdbcTemplate, ProdutoService produtoService) {
		this.jdbcTemplate = jdbcTemplate;
		this.produtoService = produtoService;
	}

	public int findNextIdOrdem() {

		Integer idOrdemProducaoParam = 0;
		Integer idUltimaOrdemProducao = 0;
		String query = "";
		
		query = " select nvl(empr_001.ordem_confeccao,0) from empr_001 ";

		try {
			idOrdemProducaoParam = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idOrdemProducaoParam = 0;
		}

		while (idUltimaOrdemProducao <= idOrdemProducaoParam) {
			query = " select SEQ_PCPC_020.nextval from dual ";
			try {
				idUltimaOrdemProducao = jdbcTemplate.queryForObject(query, Integer.class);
			} catch (Exception e) {
				idUltimaOrdemProducao = 0;
			}
		}

		query = " update empr_001 set empr_001.ordem_confeccao = " + idUltimaOrdemProducao;
		jdbcTemplate.update(query);
		
		System.out.println("findNextIdOrdem: " + idUltimaOrdemProducao);
		
		return idUltimaOrdemProducao;
	}
	
	private boolean verificaPacoteValido(int periodo, int idPacote) {
		
		if (idPacote == 0) return false;
		
		int encontrou;
		
        String query = " select 1 "
        + " from pcpc_040 "                          
        + " where pcpc_040.periodo_producao = " + periodo 
        + " and pcpc_040.ordem_confeccao = " + idPacote;

		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}
		
		System.out.println("verificaPacoteValido: " + (encontrou == 0));
		
		return (encontrou == 0);
	}
	
	private int findIdPacoteVago(int periodo) {
		
		int id;
		
		String query = " select min(pcpc_040.ordem_confeccao) + 1 "
		+ " from pcpc_040 "                          
		+ " where pcpc_040.periodo_producao = " + periodo 
		+ " and not exists (select 1 from pcpc_040 a "
		+ " where a.periodo_producao = " + periodo                       
		+ " and a.ordem_confeccao = (pcpc_040.ordem_confeccao + 1)) " ;
		
		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}
	
		if (!verificaPacoteValido(periodo, id)) id = 0;  
		
		System.out.println("findIdPacoteVago: " + id);
		
		return id;
	}
		
	public int findNextIdPacote(int periodo) {
		
		boolean atualizaUltimoPacote = true;
		Integer idPacoteParam = 0;		
		String query = "";
		
		query = " select pcpc_010.ultimo_numero from pcpc_010 "
	    + " where pcpc_010.area_periodo = 1 "
	    + " and pcpc_010.periodo_producao = " + periodo;
		
		try {
			idPacoteParam = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idPacoteParam = 0;
		}
		
		idPacoteParam++;
		
		if (idPacoteParam > 99999) atualizaUltimoPacote = false; 
		
		if (atualizaUltimoPacote) {		
			query = " update pcpc_010 "
	        + " set pcpc_010.ultimo_numero = " + idPacoteParam 
	        + " where pcpc_010.area_periodo = 1 "
	        + " and pcpc_010.periodo_producao = " + periodo; 
				
			jdbcTemplate.update(query);			
		} else {
			idPacoteParam = findIdPacoteVago(periodo);
		}		
		
		if (idPacoteParam == 0) idPacoteParam = 999999; // Se não encontrou um pacote valido retorna com 6 digitos para forçar erro de inserção.
		
		System.out.println("findNextIdPacote: " + idPacoteParam);
		
		return idPacoteParam;
	}
	
	public void atualizarUltimoEstagioOrdem(int idOrdemProducao, int ultimoEstagio) {
				
		String query = " update pcpc_020 "
	    + " set pcpc_020.ultimo_estagio = " + ultimoEstagio         
	    + " where pcpc_020.ordem_producao = " + idOrdemProducao;

		System.out.println("atualizarUltimoEstagioOrdem: " + idOrdemProducao + " -> " + ultimoEstagio);
		
		jdbcTemplate.update(query);		
	}
	
	public void gravarCapa(int idOrdemProducao, String referencia, int periodo, int alternativa, int roteiro, int quantidade, String observacao, String observacao2, int pedido, int colecao) {
		
		System.out.println("gravarCapa: " + idOrdemProducao + " -> " + referencia);
		
		Date dataProgramacao = new Date();
		
		String query = " insert into pcpc_020 ( " 
	    + " ordem_producao, periodo_producao, "             
	    + " tipo_programacao, alternativa_peca, "              
	    + " roteiro_peca, codigo_risco, "
	    + " qtde_programada, cod_cancelamento, "
	    + " usuario_cancelamento, situacao, "
	    + " data_programacao, referencia_peca, "
	    + " tipo_ordem, ordem_origem, "
	    + " observacao, observacao2, "
	    + " deposito_entrada, pedido_venda, "
	    + " live_colecao_plano_mestre) "  
	    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		
		jdbcTemplate.update(query, idOrdemProducao, periodo, 3, alternativa, roteiro, 0, quantidade, 0, 0, 2, dataProgramacao, referencia, 1, idOrdemProducao,observacao, observacao2, 0, pedido, colecao);
	}
	
	public void gravarTamanhoCor(int idOrdemProducao, String tamanho, String cor, int quantidade, int seqTamanho) {
		
		System.out.println("gravarTamanhoCor: " + idOrdemProducao + " -> tam: " + tamanho + " cor: " + cor);
		
		String query = " insert into pcpc_021 ( "
		+ " ordem_producao, tamanho, "
		+ " sortimento, quantidade, "
		+ " sequencia_tamanho) "
		+ " values (?, ?, ?, ?, ?) " ;
				       
		jdbcTemplate.update(query, idOrdemProducao, tamanho, cor, quantidade, seqTamanho);
	}

	public void gravarCapaEnfesto(int idOrdemProducao, String grupo, int seqEstrutura, int alternativa, int roteiro) {
		
		System.out.println("gravarCapaEnfesto: " + idOrdemProducao + " -> grupo: " + grupo + " seqEstrutura: " + seqEstrutura + " alternativa: " + alternativa + " roteiro: " + roteiro);
		
		String query = "";
		
		query = " select 1 from pcpc_030 "
		+ " where ordem_producao = " + idOrdemProducao
		+ " and ordem_estrutura = " + seqEstrutura
		+ " and sequencia = 1 "	;	
		  
		try {
			jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {

			query = " insert into pcpc_030 ( "
				    + " ordem_producao, reforcor_nivel030, "
				    + " reforcor_referenc, ordem_estrutura, "
				    + " alternativa_peca, roteiro_peca, "
				    + " sequencia ) " 
				    + " values (?, ?, ?, ?, ?, ?, ?) ";                 

			jdbcTemplate.update(query, idOrdemProducao, "1", grupo, seqEstrutura, alternativa, roteiro, 1);			
		}
	}
	
	public void gravarTecidosEnfesto(int idOrdemProducao, String item, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, int seqEstrutura, double qtdeKg, double qtdeMetros) {

		System.out.println("gravarTecidosEnfesto: " + idOrdemProducao + " -> seqEstrutura: " + seqEstrutura + " tecido: " + grupoTecido + "." + subTecido  + "." + itemTecido);
		
		String query = "";
		
		int encontrou = 0;
		
		query = " select 1 from pcpc_032 "
		+ " where pcpc0302_orprocor = " + idOrdemProducao
		+ " and pcpc0302_seqorcor = " + seqEstrutura
		+ " and pcpc0302_sequenor = 1 "
		+ " and sortimento_peca = '" + item + "'" 
		+ " and tecordco_subgrupo = '" + subTecido + "'" ;		
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
						
			query = " insert into pcpc_032 ( "          
		    + " pcpc0302_orprocor, pcpc0302_sequenor, "                                      
		    + " pcpc0302_seqorcor, sortimento_peca, "  
		    + " qtde_enfe_prog, qtde_kg_prog, "
		    + " qtde_sobra_falta, tecordco_nivel99, "
		    + " tecordco_grupo, tecordco_subgrupo, "
		    + " tecordco_item, qtde_metros) " 
		    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";                       
		               
			jdbcTemplate.update(query, idOrdemProducao, 1, seqEstrutura, item, 0, qtdeKg, 0, nivelTecido, grupoTecido, subTecido, itemTecido, qtdeMetros);
		}

		if (encontrou == 1) {
			
			query = " update pcpc_032 "
	        + " set pcpc_032.qtde_kg_prog = pcpc_032.qtde_kg_prog + " + qtdeKg + " ,"
	        + " pcpc_032.qtde_metros = pcpc_032.qtde_metros + " + qtdeMetros
	        + " where pcpc_032.pcpc0302_orprocor = " + idOrdemProducao
	        + " and pcpc_032.pcpc0302_sequenor = 1 "
	        + " and pcpc_032.pcpc0302_seqorcor = " + seqEstrutura
	        + " and pcpc_032.sortimento_peca = '" + item + "'"
	        + " and pcpc_032.tecordco_subgrupo = '" + subTecido + "'";
			
			jdbcTemplate.update(query); 
		}		
	}
	
	public void gravarPacoteConfeccao(int periodo, int idPacote, int estagio, int idOrdemProducao, String grupo, String sub, String item, int quantidade, 
			int estagioAnterior, int familia, int seqOperacao, int estagioDepende, int seqEstagio) {
		
		System.out.println("gravarPacoteConfeccao: " + idOrdemProducao + " -> idPacote: " + idPacote + " periodo:  " + periodo + " estagio: " + estagio + " quantidade: " + quantidade);
		
		String query = " insert into pcpc_040 ( "                
	    + " periodo_producao, ordem_confeccao, "
	    + " codigo_estagio, ordem_producao, "
	    + " proconf_nivel99, proconf_grupo, "
	    + " proconf_subgrupo, proconf_item, "
	    + " qtde_pecas_prog, estagio_anterior, " 
	    + " codigo_familia, situacao_ordem, "
	    + " qtde_programada, seq_operacao, "
	    + " estagio_depende, sequencia_estagio, "
	    + " executa_trigger) "
	    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		
		jdbcTemplate.update(query, periodo, idPacote, estagio, idOrdemProducao, "1", grupo, sub, item, quantidade, estagioAnterior, familia, 1, quantidade, seqOperacao, estagioDepende, seqEstagio, 3);
	}	
	
	public boolean isExistsApontProducao (int idOrdemProducao) {
		
		int encontrou = 0;
		
		String query = " select 1 from pcpc_045 " 
		+ " where ordem_producao = " + idOrdemProducao
		+ " and rownum = 1 " ;
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}

		return (encontrou == 1);
	}
	
	public void excluirOrdemProducao(int idOrdemProducao) {
		
		System.out.println("excluirOrdemProducao: " + idOrdemProducao);
		
		String query1 = " delete FROM pcpc_021 "
	    + " where pcpc_021.ordem_producao = " + idOrdemProducao;

		String query2 = " delete FROM pcpc_022 "
	    + " where pcpc_022.ordem_producao = " + idOrdemProducao;

		String query3 = " delete FROM pcpc_025 "
	    + " where pcpc_025.ordem_producao = " + idOrdemProducao; 
	     
		String query4 = " delete FROM pcpc_035 "
		+ " where pcpc0305_orprocor = " + idOrdemProducao;
	    
		String query5 = " delete FROM pcpc_032 "
		+ " where pcpc0302_orprocor = " + idOrdemProducao;
	      
		String query6 = " delete FROM pcpc_030 "
		+ " where pcpc_030.ordem_producao = " + idOrdemProducao;
	    
		String query7 = " update pcpc_040 "
		+ " set   pcpc_040.executa_trigger = 3 "
	    + " where pcpc_040.ordem_producao  = " + idOrdemProducao;	            
	    
		String query8 = " delete FROM pcpc_040 "
		+ " where pcpc_040.ordem_producao = " + idOrdemProducao;
 
		String query9 = " delete FROM pcpc_020 "
		+ " where pcpc_020.ordem_producao = " + idOrdemProducao;
		
		jdbcTemplate.update(query1);
		jdbcTemplate.update(query2);
		jdbcTemplate.update(query3);
		jdbcTemplate.update(query4);
		jdbcTemplate.update(query5);
		jdbcTemplate.update(query6);
		jdbcTemplate.update(query7);
		jdbcTemplate.update(query8);		
		jdbcTemplate.update(query9);
	}
	
	public List<EstagioProducao> findAllEstagios() {
		
		List<EstagioProducao> estagios;
		
		String query = " select m.codigo_estagio estagio, m.descricao, m.est_agrup_est estagioAgrupador from mqop_005 m  where m.codigo_estagio > 0 order by m.codigo_estagio ";
		
		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
		} catch (Exception e) {
			estagios = new ArrayList<EstagioProducao> ();
		}
		
		return estagios;
	}

	public List<EstagioProducao> findAllEstagiosDecoracaoOrdemProducao(int ordemProducao) {

		List<EstagioProducao> estagios;

		final int ESTAGIO_DEGORACAO = 1;

		String query = " select DISTINCT m.codigo_estagio estagio, m.descricao from mqop_005 m, pcpc_040 p " +
				"where m.codigo_estagio = p.codigo_estagio " +
				"and p.ordem_producao = " + ordemProducao +
				"and m.codigo_estagio > 0 " +
				"and m.live_estagio_decoracao = " + ESTAGIO_DEGORACAO +
				"order by m.codigo_estagio ";
		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));

		} catch (Exception e) {
			estagios = new ArrayList<EstagioProducao> ();
		}

		return estagios;
	}

	public EstagioProducao getEstagio(int codEstagio) {
		String query = " select m.codigo_estagio estagio, m.descricao, m.est_agrup_est estagioAgrupador from mqop_005 m  where m.codigo_estagio = ? order by m.codigo_estagio ";
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class), codEstagio);				
	}
	
	public OrdemConfeccao findOrdemConfeccaoByOrdProdPeriodoOrdConfec(int ordemProducao, int periodo, int ordemConfeccao) {
		
		OrdemConfeccao dadosOrdemConfeccao = new OrdemConfeccao();
		
		if ((ordemProducao == 0) && (periodo == 0) && (ordemConfeccao == 0)) return dadosOrdemConfeccao; 
		
		String query = " select a.ordem_producao ordemProducao, a.referencia_peca || ' - ' || c.descr_referencia referencia, a.periodo_producao periodo, a.qtde_programada qtdePecasProgramada, "
	    + " b.ordem_confeccao ordemConfeccao, b.proconf_subgrupo tamanho, b.proconf_item || ' - ' || d.descricao_15 cor, b.qtde_pecas_prog qtdePecas, "
        + " a.alternativa_peca nrAlternativa "				
	    + " from pcpc_020 a, pcpc_040 b, basi_030 c, basi_010 d "
	    + " where a.cod_cancelamento = 0 "
	    + " and b.ordem_producao   = a.ordem_producao ";

		if (ordemProducao > 0)
			query += " and b.ordem_producao = " + ordemProducao;  
		
		if (periodo > 0)
			query += " and b.periodo_producao = " + periodo;
		 
		if (ordemConfeccao > 0)
			query += " and b.ordem_confeccao = " + ordemConfeccao;
	  	  
		query += " and c.nivel_estrutura  = b.proconf_nivel99 "
        + " and c.referencia       = b.proconf_grupo "
		+ " and d.nivel_estrutura  = b.proconf_nivel99 "
		+ " and d.grupo_estrutura  = b.proconf_grupo "
		+ " and d.subgru_estrutura = b.proconf_subgrupo "
		+ " and d.item_estrutura   = b.proconf_item "
	    + " group by a.ordem_producao, a.referencia_peca, a.alternativa_peca, c.descr_referencia, a.periodo_producao, a.qtde_programada, "	    
	    + " b.ordem_confeccao, b.proconf_subgrupo, b.proconf_item, d.descricao_15, b.qtde_pecas_prog ";
		
		try {
			dadosOrdemConfeccao = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OrdemConfeccao.class));
		} catch (Exception e) {
			dadosOrdemConfeccao = new OrdemConfeccao();			
		}
		
		return dadosOrdemConfeccao;
	}
	
	public OrdemConfeccao findOrdemConfeccaoByOrdemProducaoConfeccao(int ordemProducao, int ordemConfeccao) {
		OrdemConfeccao dadosOrdemConfeccao;
				
		String query = " select a.ordem_producao ordemProducao, a.referencia_peca referencia, a.periodo_producao periodo, a.qtde_programada qtdePecasProgramada, "
	    + " b.ordem_confeccao ordemConfeccao, b.proconf_subgrupo tamanho, b.proconf_item cor, b.qtde_pecas_prog qtdePecas" 
	    + " from pcpc_020 a, pcpc_040 b "
	    + " where a.cod_cancelamento = 0 "
	    + " and b.ordem_producao   = a.ordem_producao "
	    + " and b.ordem_producao   = " + ordemProducao  
	    + " and b.ordem_confeccao  = " + ordemConfeccao	  	 
	    + " group by a.ordem_producao, a.referencia_peca, a.periodo_producao, a.qtde_programada, "	    
	    + " b.ordem_confeccao, b.proconf_subgrupo, b.proconf_item, b.qtde_pecas_prog ";
		
		try {
			dadosOrdemConfeccao = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OrdemConfeccao.class));
		} catch (Exception e) {
			dadosOrdemConfeccao = new OrdemConfeccao();			
		}
		
		return dadosOrdemConfeccao;		
	}
	
	public List<OrdemProducao> findAllTagsExportacaoChina()	{
		
		List<OrdemProducao> ordensProducao = null;
		
		String query = " select a.ordem_producao ordemProducao "
				+ " from pcpc_040 a, pcpc_020 b"
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and b.cod_cancelamento = 0 "
				+ " and a.qtde_a_produzir_pacote > 0 "
				+ " and a.codigo_estagio = 93"
				+ " group by a.ordem_producao ";
		
		try {
			ordensProducao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducao.class));
		} catch (Exception e) {
			ordensProducao = new ArrayList<OrdemProducao> ();			
		}
		
		return ordensProducao;		
	}
	
	public List<OrdemConfeccao> findAllOrdensConfeccao(int ordemProducao) {
		
		List<OrdemConfeccao> ordensConfeccao = null;
		
		String query = " select a.ordem_producao ordemProducao, a.periodo_producao periodo, a.ordem_confeccao ordemConfeccao, "
		+ " a.proconf_grupo referencia, a.proconf_subgrupo tamanho, a.proconf_item cor, a.qtde_pecas_prog qtdePecas "  
		+ " from pcpc_040 a, pcpc_020 b "
		+ " where b.ordem_producao = a.ordem_producao " 
		+ " and a.ordem_producao = " + ordemProducao
		+ " group by a.ordem_producao, a.periodo_producao, a.ordem_confeccao, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, a.qtde_pecas_prog ";
		
		try {
			ordensConfeccao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemConfeccao.class));
		} catch (Exception e) {
			ordensConfeccao = new ArrayList<OrdemConfeccao> ();			
		}
		
		return ordensConfeccao;			
	}
	
	public List<DadosTagChina> findDadosTagChina(String ordemProducao) {
		
		List<DadosTagChina> dadosTagsChina = null;
		
		String query = " select a.referencia_peca referencia, y.descr_tamanho tamanho, b.proconf_item || '-' || d.descricao_15 descCor, "
				+ " c.descr_referencia descReferencia, "
				+ " 'https://produto.liveoficial.com.br/' || b.proconf_grupo || '.' || b.proconf_item qrcode, "
				+ " d.codigo_barras codBarrasEan, "
				+ " b.proconf_item cor, "
				+ " nvl(c.descricao_tag2, ' ') tamanhoMedida, "
				+ " b.qtde_programada quantidade, "
				+ " inter_fn_get_val_unit_tab(27, 14, 14, b.proconf_nivel99, b.proconf_grupo, b.proconf_subgrupo, b.proconf_item) preco, "
				+ " lpad(b.periodo_producao,4,'0')||lpad(b.ordem_producao,9,'0')||lpad(b.ordem_confeccao,5,'0') codBarrasProd "
				+ " from pcpc_020 a, pcpc_040 b, basi_030 c, basi_010 d, basi_220 y "
				+ " where a.cod_cancelamento = 0 "
				+ " and b.ordem_producao = a.ordem_producao "
				+ " and b.ordem_producao in (" + ordemProducao + ")"
				+ " and b.codigo_estagio = a.ultimo_estagio "
				+ " and c.nivel_estrutura = b.proconf_nivel99 "
				+ " and c.referencia = b.proconf_grupo "
				+ " and d.nivel_estrutura = b.proconf_nivel99 "
				+ " and d.grupo_estrutura = b.proconf_grupo "
				+ " and d.subgru_estrutura = b.proconf_subgrupo "
				+ " and d.item_estrutura = b.proconf_item "
				+ " and y.tamanho_ref = b.proconf_subgrupo "
				+ " order by a.referencia_peca, b.proconf_subgrupo, b.proconf_item";
				
		try {
			dadosTagsChina = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosTagChina.class));
		} catch (Exception e) {
			dadosTagsChina = new ArrayList<DadosTagChina> ();
		}
		
		List<DadosTagChina> dadosTagsChinaFormatados = new ArrayList<DadosTagChina> ();	
		
		DadosTagChina construtorFormatado; 
		
		for (DadosTagChina tagAtual : dadosTagsChina) {

			List<String> bullets = new ArrayList<>();
			int seqTag = 0;
			String atributo = "";
			String bullet1 = "";
			String bullet2 = "";
			String bullet3 = "";
			String bullet4 = "";
			String bullet5 = "";
			String bullet6 = "";
			String bullet7 = "";

			atributo = findAtributo(tagAtual.getReferencia(), tagAtual.getTamanho(), tagAtual.getCor());
			bullets = produtoService.obterCaracteristicas("1", tagAtual.getReferencia(), tagAtual.getTamanho(), tagAtual.getCor());

			if (bullets.size() > 0) {
				if (bullets.size() >= 1) bullet1 = bullets.get(0);
				if (bullets.size() >= 2) bullet2 = bullets.get(1);
				if (bullets.size() >= 3) bullet3 = bullets.get(2);
				if (bullets.size() >= 4) bullet4 = bullets.get(3);
				if (bullets.size() >= 5) bullet5 = bullets.get(4);
				if (bullets.size() >= 6) bullet6 = bullets.get(5);
				if (bullets.size() >= 7) bullet7 = bullets.get(6);
			}

			while (seqTag < tagAtual.getQuantidade()) {
				seqTag++;
				
				String tamanhoAtual = tagAtual.getTamanho();
				String tamanhoMedida = "Tam: " + tagAtual.getTamanho();
				
				if (!tagAtual.getTamanhoMedida().equals(" ")) {
					tamanhoAtual = tagAtual.getTamanhoMedida();
					tamanhoMedida = " ";
				}
				
				construtorFormatado = new DadosTagChina(tagAtual.getReferencia(), tamanhoAtual, tagAtual.getCor(), tagAtual.getDescCor(), tagAtual.getDescReferencia(), tagAtual.getQrCode(), tagAtual.getCodBarrasEan(), 
						tagAtual.getQuantidade(), tagAtual.getPreco(), atributo, tagAtual.getCodBarrasProd(), seqTag, tamanhoMedida,
						bullet1, bullet2, bullet3, bullet4, bullet5, bullet6, bullet7);
				
				dadosTagsChinaFormatados.add(construtorFormatado);
			} 	
		}
		return dadosTagsChinaFormatados;
	}
	
	public String findAtributo(String referencia, String tamanho, String cor) {
		
		String atributo = "";
		int colecao = 0;
		String descPermanente = "";
		String descAtributo = "";
		String descCategoria = "";
		
		String query = " select basi_030.colecao "
				+ " from basi_030 "
				+ " where basi_030.nivel_estrutura = 1 "
				+ " and basi_030.referencia = '" + referencia + "'";
		
		try {
			colecao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			colecao = 0;
		}
		
		String queryBasi = " select basi_140.descricao_espanhol "
				+ " from basi_140 "
				+ " where basi_140.colecao = " + colecao;
	
		try {
			descPermanente = jdbcTemplate.queryForObject(queryBasi, String.class);
		} catch (Exception e) {
			descPermanente = "";
		}
		
		if ("COLECAO PERMANENTE".equals(descPermanente)) {
			
			String queryPermanente = "select basi_140.descricao_ingles"
                    + " from basi_140 "
                    + " where basi_140.colecao > 0 "
                    + " and basi_140.colecao = (select nvl(max(basi_632.cd_agrupador),0) "
                    + " from basi_632 "
                    + " where basi_632.grupo_ref = '" + referencia + "'"
                    + " and basi_632.subgrupo_ref = '" + tamanho + "'"
                    + " and basi_632.item_ref = '" + cor + "') ";
			
			try {
				descAtributo = jdbcTemplate.queryForObject(queryPermanente, String.class);
			} catch (Exception e) {
				descAtributo = "";
			}
					
		} else {
			
			String queryAtributo = " select basi_140.descricao_ingles "
					+ " from basi_140 "
					+ " where basi_140.colecao = " + colecao;
			
			try {
				descAtributo = jdbcTemplate.queryForObject(queryAtributo, String.class);
			} catch (Exception e) {
				descAtributo = "";
			}
		}
		
		try {
			atributo = descAtributo.substring(0, 4);
		} catch (Exception e) {
			atributo = descAtributo;
		}
		
		String sqlCategoria = " select a.conteudo_atributo from basi_544 a, basi_541 b "
                + " where b.codigo_atributo = a.codigo_atributo "
                + " and a.familia_atributo = '000001' "
                + " and a.codigo_grupo_atrib = 1 "
                + " and a.codigo_subgrupo_atrib = 1 "
                + " and SUBSTR(a.chave_acesso,2,5) = '" + referencia + "'"
                + " and a.codigo_atributo = 6 ";
		
		try {
			descCategoria = jdbcTemplate.queryForObject(sqlCategoria, String.class);
			
			if (descCategoria.length() > 8) descCategoria = descCategoria.substring(0, 8);
			
			atributo = atributo + " - " + descCategoria;
			
		} catch (Exception e) {
			descCategoria = "";
		}
		
		return atributo;
	}

	public String getCoresOrdem(int ordemProducao) {
		String cores = "";
		List<String> listaCodigoCores;
		
		String query = " select a.proconf_item from pcpc_040 a where a.ordem_producao = ? group by a.proconf_item ";
		
		listaCodigoCores = jdbcTemplate.queryForList(query, String.class, ordemProducao);
		
		for (String cor : listaCodigoCores) {
			if (cores.isEmpty()) cores = cor;
			else cores += "," + cor;
		}
		
		return cores;
	}	

	public List<OrdemProducaoItem> findItensByOrdemProducao(int ordemProducao) {
		
		String query = " select a.ordem_producao ordemProducao, a.referencia_peca referencia, a.alternativa_peca nrAlternativa, a.roteiro_peca nrRoteiro, b.proconf_subgrupo tamanho, b.proconf_item cor, sum(b.qtde_pecas_prog) qtdePecasProgramada, c.ordem_tamanho ordemTamanho " 
		+ " from pcpc_020 a, pcpc_040 b, basi_220 c " 
		+ " where a.ordem_producao = " + ordemProducao
		+ " and b.ordem_producao = a.ordem_producao "
		+ " and b.codigo_estagio = a.ultimo_estagio "
		+ " and c.tamanho_ref = b.proconf_subgrupo "
		+ " group by a.ordem_producao, a.referencia_peca, a.alternativa_peca, a.roteiro_peca, b.proconf_subgrupo, b.proconf_item, c.ordem_tamanho ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducaoItem.class));
	}

	public List<OrdemProducaoItem> findItensByOrdemProducaoAndCor(int ordemProducao, String cor) {

		String query = " select a.ordem_producao ordemProducao, a.referencia_peca referencia, a.alternativa_peca nrAlternativa, a.roteiro_peca nrRoteiro, b.proconf_subgrupo tamanho, b.proconf_item cor, sum(b.qtde_pecas_prog) qtdePecasProgramada " 
		+ " from pcpc_020 a, pcpc_040 b " 
		+ " where a.ordem_producao = " + ordemProducao
		+ " and b.ordem_producao = a.ordem_producao "
		+ " and b.proconf_item = '" + cor + "'"
		+ " and b.codigo_estagio = a.ultimo_estagio "
		+ " group by a.ordem_producao, a.referencia_peca, a.alternativa_peca, a.roteiro_peca, b.proconf_subgrupo, b.proconf_item ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducaoItem.class));
	}
	
	public List<OrdemProducao> findOrdensOrdenadasPorPrioridade(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String estagiosEmProducao, String embarques, String referencias, String estagios, String artigos, String tecidos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isOrdensSemTecido, boolean isPossuiAgrupadorEstamp) { 

		String query = " select pre_ordens_priorizadas.ordem_producao ordemProducao, "  
	    + " pre_ordens_priorizadas.periodo_producao periodo, "
	    + " pre_ordens_priorizadas.referencia, "
	    + " pre_ordens_priorizadas.descr_referencia descrReferencia, " 
	    + " pre_ordens_priorizadas.alternativa nrAlternativa, "
	    + " pre_ordens_priorizadas.roteiro nrRoteiro, "
	    + " pre_ordens_priorizadas.observacao, "
	    + " nvl(pre_ordens_priorizadas.data_embarque,sysdate+10000) dataEmbarque, " 
	    + " pre_ordens_priorizadas.quantidade qtdePecasProgramada, "
	    + " pre_ordens_priorizadas.qtde_estagio_critico qtdeEstagioCritico, " 
	    + " pre_ordens_priorizadas.tempo_producao_unit tempoProducaoUnit, "
	    + " pre_ordens_priorizadas.tempo_costura_unit tempoCosturaUnit "
	    + " from (select pre_ordens.ordem_producao, " 
	    + " pre_ordens.periodo_producao, "
	    + " pre_ordens.referencia, "
	    + " pre_ordens.descr_referencia, " 
	    + " pre_ordens.alternativa, "
	    + " pre_ordens.roteiro, " 
	    + " max(pre_ordens.observacao) observacao, "
	    + " min(pre_ordens.data_embarque) data_embarque, " 
	    + " max(pre_ordens.quantidade) quantidade, "
	    + " max(pre_ordens.qtde_estagio_critico) qtde_estagio_critico, " 
	    + " max(pre_ordens.tempo_producao) tempo_producao_unit, "
	    + " max(pre_ordens.tempo_costura) tempo_costura_unit "
	    + " from ( "
	    + " select a.ordem_producao, "  
  	    + " a.periodo_producao, "
        + " a.referencia, " 
        + " a.alternativa, " 
        + " a.roteiro, "
        + " max(a.observacao) observacao, "
        + " min(a.descr_referencia) descr_referencia, "  
        + " min(c.data_entrega) data_embarque, "
        + " a.sub, "
        + " a.item, "      
        + " max(a.quantidade) quantidade, " 
        + " (select count(*) from mqop_005 t "  
        + " where t.live_estagio_critico = 1 "
        + " and exists (select 1 from mqop_050 m "  
		+ " where m.nivel_estrutura = '1' "
        + " and m.grupo_estrutura = a.referencia "  
		+ " and (m.subgru_estrutura = a.sub or m.subgru_estrutura = '000') "  
		+ " and (m.item_estrutura = a.item or m.item_estrutura = '000000') "
		+ " and m.numero_alternati = a.alternativa "
		+ " and m.numero_roteiro = a.roteiro "
		+ " and m.codigo_estagio = t.codigo_estagio) " 
	    + " ) qtde_estagio_critico,  "
	    + " (select nvl(sum(m.minutos_homem),0) from mqop_050 m "  
	    + " where m.nivel_estrutura = '1' "  
	    + " and m.grupo_estrutura = a.referencia "  
	    + " and (m.subgru_estrutura = a.sub or m.subgru_estrutura = '000') "  
	    + " and (m.item_estrutura = a.item or m.item_estrutura = '000000') "  
	    + " and m.numero_alternati = a.alternativa "
	    + " and m.numero_roteiro = a.roteiro " 
	    + " ) tempo_producao, "
	    + " (select nvl(sum(m.minutos_homem),0) from mqop_050 m "  
	    + " where m.nivel_estrutura = '1' " 
	    + " and m.grupo_estrutura = a.referencia "  
	    + " and (m.subgru_estrutura = a.sub or m.subgru_estrutura = '000') "  
	    + " and (m.item_estrutura = a.item or m.item_estrutura = '000000') "  
	    + " and m.numero_alternati = a.alternativa " 
	    + " and m.numero_roteiro = a.roteiro "
	    + " and m.codigo_estagio = " + ESTAGIO_COSTURA 
	    + " ) tempo_costura "
	    + " from (select aa.ordem_producao, " 
	    + " aa.periodo_producao, "
	    + " aa.referencia_peca referencia, " 
	    + " aa.alternativa_peca alternativa, "
	    + " aa.roteiro_peca roteiro, "
	    + " cc.descr_referencia, bb.proconf_subgrupo sub, bb.proconf_item item, " 
	    + " aa.qtde_programada quantidade, "
	    + " aa.observacao "
	    + " from pcpc_020 aa, pcpc_040 bb, basi_030 cc "
	    + " where aa.cod_cancelamento = 0 "	    
		+ " and bb.ordem_producao = aa.ordem_producao "  
		+ " and bb.codigo_estagio = aa.ultimo_estagio "	    
	    + " and cc.nivel_estrutura = '1' "
	    + " and cc.referencia = aa.referencia_peca "
	    + " and exists (select 1 from pcpc_040 "
	    + " where pcpc_040.ordem_producao = aa.ordem_producao "	    
 	    + " and pcpc_040.codigo_estagio in (" + estagiosEmProducao + ")" 
	    + " and pcpc_040.qtde_disponivel_baixa > 0)";

		if (periodoInicial > 0 || periodoFinal > 0)
			query += " and aa.periodo_producao between " + periodoInicial + " and " + periodoFinal;

		if (!referencias.isEmpty())
			query += " and aa.referencia_peca in (" + referencias + ")";
		
		// Deve desconsiderar referências THERMO		
		query += " and aa.referencia_peca not like ('TM%')";
	                               
		if (!artigos.isEmpty()) 
			query += " and cc.artigo in (" + artigos + ")";                               
	                               
		if (!estagios.isEmpty())
			query +=" and exists (select 1 from pcpc_040 "
				  + " where pcpc_040.ordem_producao = aa.ordem_producao "
	              + " and pcpc_040.codigo_estagio in (" + estagios + "))";
		
		if (!tecidos.isEmpty())
			query += " and exists (select 1 from pcpc_032 w "
	              + " where w.pcpc0302_orprocor = aa.ordem_producao "
	              + " and w.tecordco_nivel99 || '.' || w.tecordco_grupo || '.' || w.tecordco_subgrupo || '.' || w.tecordco_item in (" + tecidos + "))";                               
	         
		if (isOrdensSemTecido)
			query += " and not exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = aa.ordem_producao ) ";
		else 
			query += " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = aa.ordem_producao ) ";
		
		if (isSomenteFlat)
			query += " and exists ( select 1 from mqop_050 m "  
	              + " where m.nivel_estrutura = '1' " 
	              + " and m.grupo_estrutura = aa.referencia_peca "  
	              + " and (m.subgru_estrutura = bb.proconf_subgrupo or m.subgru_estrutura = '000') "  
	              + " and (m.item_estrutura =  bb.proconf_item or m.item_estrutura = '000000') " 
	              + " and m.numero_alternati = aa.alternativa_peca "
	              + " and m.numero_roteiro = aa.roteiro_peca "
	              + " and m.codigo_operacao in (select y.codigo_operacao from mqop_040 y "
	              + " where y.nome_operacao like '%FLAT%')) ";
				
		if (isPossuiAgrupadorEstamp)
			query += " and (select count(*) "
				  + " from mqop_005 m " 
				  + " where m.est_agrup_est = 10 " // Estágios de estamparia
				  + " and exists (select 1 from pcpc_040 p " 
				  + " where p.ordem_producao = aa.ordem_producao "
				  + " and p.codigo_estagio = m.codigo_estagio)) > 2 ";				
		
		if (isDiretoCostura) 
			query += " and not exists ( select 1 from pcpc_040 pp "		 
			      + " where pp.ordem_producao = aa.ordem_producao "
		          + " and pp.codigo_estagio in (07,24,25,61,10,15,18,53,52,79)) "; 		 
		 
 		 query += " group by aa.ordem_producao, aa.periodo_producao, aa.referencia_peca, aa.alternativa_peca, " 
         + " aa.roteiro_peca, cc.descr_referencia, bb.proconf_subgrupo, bb.proconf_item, aa.qtde_programada, aa.observacao ) a, basi_590 c "
         + " where c.nivel (+) = '1' " 
         + " and c.grupo (+) = a.referencia "   
         + " and c.subgrupo (+) = a.sub " 
	     + " and c.item (+) = a.item " ;
	                         
	     if (!embarques.isEmpty())
	    	 query += " and c.grupo_embarque in (" + embarques + ")";
	                         
	     query += " group by a.ordem_producao, a.periodo_producao, a.referencia, a.alternativa, a.roteiro, a.sub, a.item ) pre_ordens "
	     + " group by pre_ordens.ordem_producao, pre_ordens.periodo_producao, pre_ordens.referencia, pre_ordens.descr_referencia , pre_ordens.alternativa, pre_ordens.roteiro " 
	     + " ) pre_ordens_priorizadas "; 
		
		String ordenacao = converteSelecaoCamposParaOrdenacao(camposSelParaPriorizacao);
		
		query += ordenacao;
		
		System.out.println(query);
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducao.class));
	}
	
	private String converteSelecaoCamposParaOrdenacao(List<String> ordenacao) {
		
		String order = "";
		
		if (ordenacao.size() == 0) order += "order by pre_ordens_priorizadas.data_embarque, pre_ordens_priorizadas.qtde_estagio_critico desc, pre_ordens_priorizadas.tempo_producao_unit desc, pre_ordens_priorizadas.quantidade desc";
		
		for (String campo : ordenacao) {
		
			if (!order.isEmpty()) order += ", ";
			else order = "order by ";
			
			//System.out.println("CAMPO -> " + campo);
			
			if (campo.equalsIgnoreCase("PERIODO_PRODUCAO")) order += "pre_ordens_priorizadas.periodo_producao asc";
			if (campo.equalsIgnoreCase("EMBARQUE")) order += "pre_ordens_priorizadas.data_embarque asc";
			if (campo.equalsIgnoreCase("ESTAGIOS_CRITICO")) order += "pre_ordens_priorizadas.qtde_estagio_critico desc";
			if (campo.equalsIgnoreCase("MAIOR_TEMPO_PROD")) order += "pre_ordens_priorizadas.tempo_producao_unit desc";
			if (campo.equalsIgnoreCase("MENOR_TEMPO_PROD")) order += "pre_ordens_priorizadas.tempo_producao_unit asc";
			if (campo.equalsIgnoreCase("MAIOR_TEMPO_COST")) order += "pre_ordens_priorizadas.tempo_costura_unit desc";
			if (campo.equalsIgnoreCase("MENOR_TEMPO_COST")) order += "pre_ordens_priorizadas.tempo_costura_unit asc";
			if (campo.equalsIgnoreCase("MAIOR_QTDE_PECAS")) order += "pre_ordens_priorizadas.quantidade desc";
			if (campo.equalsIgnoreCase("MENOR_QTDE_PECAS")) order += "pre_ordens_priorizadas.quantidade asc";

		}
		
		return order;
	}
	
	public void gravarProducaoEstagio(int ordemProducao, int periodo, int pacote, int estagio, int quantidade, int codUsuario, String usuarioSystextil) {			
		int sequencia;
		
		String query = " select nvl(max(a.sequencia),0) + 1 "
		+ " from pcpc_045 a "
		+ " where a.pcpc040_perconf = " + periodo
		+ " and a.pcpc040_ordconf = " + pacote
		+ " and a.pcpc040_estconf = " + estagio ;
		
		sequencia = jdbcTemplate.queryForObject(query, Integer.class);
				
		String queryInsert = " insert into pcpc_045 (pcpc040_ordconf, pcpc040_perconf, pcpc040_estconf, sequencia, data_producao, hora_producao, qtde_produzida, turno_producao, codigo_usuario, usuario_systextil, ordem_producao, executa_trigger) " 
		+ "	values (" + pacote + ", " + periodo + ", " + estagio + ", " + sequencia + ", " + " trunc(sysdate), to_date('16/11/1989 ' || to_CHAR(sysdate,'hh24:mi'),'dd/mm/yyyy hh24:mi'), "
		+ quantidade + ", 1, " + codUsuario + ", '" + usuarioSystextil + "', " + ordemProducao + ", 3) ";  				
		
		System.out.println("INSERT NA PCPC_045: " + queryInsert);
		
		jdbcTemplate.update(queryInsert);
	}	
	
	public int findQtdePecasApontadaNoDia(int codEstagio) {

		String query = " select nvl(sum(p.qtde_produzida),0) from pcpc_045 p "
		+ " where p.pcpc040_estconf = " + codEstagio  		
		+ " and p.data_producao = trunc(sysdate) "		
		+ " and exists (select 1 from pcpc_020 m "
		+ " where m.ordem_producao = p.ordem_producao "
		+ " and m.referencia_peca not like ('TM%')) "
        + " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = p.ordem_producao ) " ; 
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int findQtdePecasApontadaNoDiaPorArtigo(int codEstagio, boolean consideraArtigos, String artigos, Date dataInicial, Date dataFinal) {

		String considera = "in";
		
		if (!consideraArtigos) considera = "not in";
		
		String query = " select nvl(sum(p.qtde_produzida),0) from pcpc_045 p " 
		+ " where p.pcpc040_estconf = " + codEstagio
   	    + " and p.data_producao between ? and ? " 
		+ " and exists (select 1 from pcpc_020 m "
		+ " where m.ordem_producao = p.ordem_producao " 
		+ " and m.referencia_peca not like ('TM%')) "
		+ " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = p.ordem_producao) "
		+ " and exists (select 1 from pcpc_040 z, basi_030 v "
        + " where z.periodo_producao = p.pcpc040_perconf "
        + " and z.ordem_confeccao = p.pcpc040_ordconf "
        + " and z.codigo_estagio = p.pcpc040_estconf "
        + " and v.nivel_estrutura = z.proconf_nivel99 "
        + " and v.referencia = z.proconf_grupo "
        + " and v.artigo " + considera + " ( " + artigos + " )) ";
		
		return jdbcTemplate.queryForObject(query, Integer.class, dataInicial, dataFinal);
	}

	public double findQtdeMinutosApontadoNoDiaPorArtigo(int codEstagio, boolean consideraArtigos, String artigos, Date dataInicial, Date dataFinal) {

		String considera = "in";
		
		if (!consideraArtigos) considera = "not in";
		
		String query = "select nvl(sum(minutos_costura.total),0) total "
		+ " from ( "		
		+ " select nvl((select sum(m.minutos_homem) from mqop_050 m " 
		+ " where m.nivel_estrutura = z.proconf_nivel99 "
		+ " and m.grupo_estrutura = z.proconf_grupo "
		+ " and (m.subgru_estrutura = z.proconf_subgrupo or m.subgru_estrutura = '000') " 
        + " and (m.item_estrutura = z.proconf_item or m.item_estrutura = '000000') " 
        + " and m.numero_alternati = y.alternativa_peca "
        + " and m.numero_roteiro = y.roteiro_peca "
        + " and m.codigo_estagio = " + ESTAGIO_COSTURA + "),0) * nvl(sum(p.qtde_produzida),0) total "    
        + " from pcpc_045 p, pcpc_040 z, pcpc_020 y, basi_030 v " 
   	    + " where p.pcpc040_estconf = " + codEstagio
		+ " and p.data_producao between ? and ? "
		+ " and z.periodo_producao = p.pcpc040_perconf " 
		+ " and z.ordem_confeccao = p.pcpc040_ordconf " 
		+ " and z.codigo_estagio = p.pcpc040_estconf "
		+ " and y.ordem_producao = z.ordem_producao "
		+ " and v.nivel_estrutura = z.proconf_nivel99 " 
		+ " and v.referencia = z.proconf_grupo "
		+ " and exists (select 1 from pcpc_020 m " 
		+ " where m.ordem_producao = p.ordem_producao " 
		+ " and m.referencia_peca not like ('TM%')) "
		+ " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = p.ordem_producao) " 		   
		+ " and v.artigo " + considera + " ( " + artigos + " ) "
		+ " group by z.proconf_nivel99, z.proconf_grupo, z.proconf_subgrupo, z.proconf_item, y.alternativa_peca, y.roteiro_peca) minutos_costura";
		
		return jdbcTemplate.queryForObject(query, Double.class, dataInicial, dataFinal);
	}
	
	public int findQtdePecasFlatApontadaNoDia(int codEstagio, Date dataInicial, Date dataFinal) {
		
		String query = " select nvl(sum(p.qtde_produzida),0) from pcpc_045 p " 
		+ " where p.pcpc040_estconf = " + codEstagio
		+ " and p.data_producao between ? and ? " 
		+ " and exists (select 1 from pcpc_020 m " 
		+ " where m.ordem_producao = p.ordem_producao " 
		+ " and m.referencia_peca not like ('TM%')) "
		+ " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = p.ordem_producao) "
		+ " and exists (select 1 from pcpc_040 z, pcpc_020 v, mqop_050 m " 
		+ " where z.periodo_producao = p.pcpc040_perconf "
		+ " and z.ordem_confeccao = p.pcpc040_ordconf "
		+ " and z.codigo_estagio = p.pcpc040_estconf "                              
		+ " and v.ordem_producao = z.ordem_producao "                                               
		+ " and m.nivel_estrutura = '1' "
		+ " and m.grupo_estrutura = z.proconf_grupo "                
		+ " and (m.subgru_estrutura = z.proconf_subgrupo or m.subgru_estrutura = '000') " 
		+ " and (m.item_estrutura =  z.proconf_item or m.item_estrutura = '000000') "
		+ " and m.numero_alternati = v.alternativa_peca "
		+ " and m.numero_roteiro = v.roteiro_peca "
		+ " and m.codigo_operacao in (select y.codigo_operacao from mqop_040 y " 
		+ " where y.nome_operacao like '%FLAT%')) ";
				
		return jdbcTemplate.queryForObject(query, Integer.class, dataInicial, dataFinal);
	}

	public double findQtdeMinutosFlatApontadaNoDia(int codEstagio, Date dataInicial, Date dataFinal) {
		
		String query = " select nvl(sum(minutos_costura.total),0) total "
		+ " from ( "
		+ " select nvl((select sum(m.minutos_homem) from mqop_050 m " 
		+ " where m.nivel_estrutura = z.proconf_nivel99 "
		+ " and m.grupo_estrutura = z.proconf_grupo "
		+ " and (m.subgru_estrutura = z.proconf_subgrupo or m.subgru_estrutura = '000') " 
		+ " and (m.item_estrutura = z.proconf_item or m.item_estrutura = '000000') " 
		+ " and m.numero_alternati = y.alternativa_peca "
		+ " and m.numero_roteiro = y.roteiro_peca "
		+ " and m.codigo_estagio = " + ESTAGIO_COSTURA + "),0) * nvl(sum(p.qtde_produzida),0) total "    
		+ " from pcpc_045 p, pcpc_040 z, pcpc_020 y, basi_030 v " 
		+ " where p.pcpc040_estconf = " + codEstagio
		+ " and p.data_producao between ? and ? " 
		+ " and z.periodo_producao = p.pcpc040_perconf " 
		+ " and z.ordem_confeccao = p.pcpc040_ordconf " 
		+ " and z.codigo_estagio = p.pcpc040_estconf " 
		+ " and y.ordem_producao = z.ordem_producao "
		+ " and v.nivel_estrutura = z.proconf_nivel99 " 
		+ " and v.referencia = z.proconf_grupo " 
		+ " and exists (select 1 from pcpc_020 m " 
		+ " where m.ordem_producao = p.ordem_producao " 
		+ " and m.referencia_peca not like ('TM%')) " 
		+ " and exists (select 1 from pcpc_032 w where w.pcpc0302_orprocor = p.ordem_producao) " 
		+ " and exists (select 1 from mqop_050 m " 
		+ " where m.nivel_estrutura = z.proconf_nivel99 "
		+ " and m.grupo_estrutura = z.proconf_grupo "
		+ " and (m.subgru_estrutura = z.proconf_subgrupo or m.subgru_estrutura = '000') " 
		+ " and (m.item_estrutura = z.proconf_item or m.item_estrutura = '000000') " 
		+ " and m.numero_alternati = y.alternativa_peca "
		+ " and m.numero_roteiro = y.roteiro_peca "
		+ " and m.codigo_operacao in (select w.codigo_operacao from mqop_040 w where w.nome_operacao like '%FLAT%')) "    
		+ " group by z.proconf_nivel99, z.proconf_grupo, z.proconf_subgrupo, z.proconf_item, y.alternativa_peca, y.roteiro_peca) minutos_costura ";
		
		return jdbcTemplate.queryForObject(query, Double.class, dataInicial, dataFinal);
	}	
	
	public int findUltimaSeqPrioridadeDia() {
	
		String query = " select nvl(max(a.live_seq_dt_liberacao),0) last_seq "
		+ " from pcpc_020 a "
		+ " where trunc(a.live_dt_liberacao) = trunc(sysdate) "
		+ " and not exists (select 1 from pcpc_040 b " 
		+ " where b.ordem_producao = a.ordem_producao "
		+ " and b.codigo_estagio = 2 " // ANALISE DE TECIDO
		+ " and b.qtde_disponivel_baixa > 0) "; 
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}	
	
	public void gravarSeqPrioridadeDia(int ordemProducao, int sequencia) {
		
		String query = " update pcpc_020 a "
		+ " set a.live_dt_liberacao = sysdate, "
		+ " a.live_seq_dt_liberacao = " + sequencia
		+ " where a.ordem_producao = " + ordemProducao;
		
		jdbcTemplate.update(query);		
	}
	
	public void gravarObservacao(int ordemProducao, String observacao) {		
		String query = " update pcpc_020 a set a.observacao = ? where a.ordem_producao = ? ";
		jdbcTemplate.update(query, observacao, ordemProducao);		
	}
	
	public List<ConteudoChaveAlfaNum> findOrdensForAsync(int estagio, String searchVar) { 
		String query = " select a.ordem_producao value, a.ordem_producao label "
				+ " from pcpc_040 a, pcpc_020 b "
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and b.cod_cancelamento = 0 "
				+ " and (a.qtde_a_produzir_pacote > 0 or a.qtde_conserto > 0) "
				+ " and a.ordem_producao like '%" + searchVar + "%' "
				+ " and rownum <= 100 ";

				if (estagio > 0 ) query += " and a.codigo_estagio = " + estagio;

				query += " group by a.ordem_producao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConteudoChaveAlfaNum> findOrdensForAsyncComEstagio(List<ConteudoChaveAlfaNum> estagio, String searchVar) { 
		String query = " select a.ordem_producao value, a.ordem_producao label "
				+ " from pcpc_040 a, pcpc_020 b "
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and b.cod_cancelamento = 0 "
				+ " and (a.qtde_a_produzir_pacote > 0 or a.qtde_conserto > 0) "
				+ " and a.ordem_producao like '%" + searchVar + "%' "
				+ " and rownum <= 100 "
				+ " and a.codigo_estagio IN ( " + ConteudoChaveAlfaNum.parseValueToString(estagio) + " ) "
				+ " group by a.ordem_producao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public List<ConteudoChaveAlfaNum> findOrdensForAsyncEstagioDecoracao(String searchVar) {

		String query = " select a.ordem_producao value, a.ordem_producao label "
				+ " from pcpc_040 a, pcpc_020 b "
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and b.cod_cancelamento = 0 "
				+ " and a.qtde_a_produzir_pacote > 0 "
				+ " and a.ordem_producao like '%" + searchVar + "%' "
				+ " and a.codigo_estagio IN ( select codigo_estagio from mqop_005 where live_estagio_decoracao = 1 ) "
				+ " and rownum <= 100 "
				+ " group by a.ordem_producao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public int getQtdeAProduzirEstagio(int ordemProducao, int codEstagio) {		
		String query = " select nvl(sum(p.qtde_a_produzir_pacote),0) "
		+ " from pcpc_040 p "
		+ " where p.ordem_producao = ? "
		+ " and p.codigo_estagio = ? " ;
		return jdbcTemplate.queryForObject(query, Integer.class, ordemProducao, codEstagio);				
	}
	
	public int getQtdeAProduzirEstagio(int ordemProducao, int ordemConfeccao, int codEstagio) {		
		String query = " select nvl(sum(p.qtde_a_produzir_pacote),0) "
		+ " from pcpc_040 p "
		+ " where p.ordem_producao = ? "
		+ " and p.ordem_confeccao = ? "
		+ " and p.codigo_estagio = ? " ;
		return jdbcTemplate.queryForObject(query, Integer.class, ordemProducao, ordemConfeccao, codEstagio);
	}
	
	public boolean existsEstagioOrdemProducao(int ordemProducao, int codEstagio) {
		
		int encontrou = 0;
		
		String query = "SELECT 1 FROM pcpc_040 p "
				+ "		WHERE p.ordem_producao = ? " 
				+ "		AND p.codigo_estagio = ? "
				+ "     AND ROWNUM = 1";
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class, ordemProducao, codEstagio);
		} catch (Exception e) {
			encontrou = 0;
		}
		return (encontrou == 1);
		
	}
	
	public List<ConteudoChaveNumerica> findFamiliasProducao() {
		String query = " select p.codigo_familia value, p.codigo_familia label from pcpc_600 p " 
		+ " group by p.codigo_familia "
		+ " order by p.codigo_familia "; 
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findFaccoes() {
		String query = " select a.divisao_producao value, a.divisao_producao || ' - ' || a.descricao label from basi_180 a "
		+ " where a.tipo_linha = ? "
		+ " and a.faccionista9 > 0 "
		+ " order by a.divisao_producao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class), LINHA_FACCAO);
	}

	public List<ConteudoChaveNumerica> findPedidosOrdens() {		
		String query = " select p.pedido_venda value, p.pedido_venda label from pcpc_020 p "
		+ " where p.cod_cancelamento = 0 "
		+ " and p.pedido_venda > 0 "		  
		+ " and exists (select 1 from pcpc_040 m "
		+ " where m.ordem_producao = p.ordem_producao "
		+ " and m.codigo_estagio = p.ultimo_estagio "
		+ " and m.qtde_a_produzir_pacote > 0) "		  
		+ " group by p.pedido_venda "
		+ " order by p.pedido_venda ";		
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	
}