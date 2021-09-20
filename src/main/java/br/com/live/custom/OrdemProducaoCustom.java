package br.com.live.custom;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.DadosTagChina;
import br.com.live.model.EstagioProducao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;

@Repository
public class OrdemProducaoCustom {

	private JdbcTemplate jdbcTemplate;

	public OrdemProducaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
		
		return idUltimaOrdemProducao;
	}
	
	public int findNextIdPacote(int periodo) {
		
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
		
		query = " update pcpc_010 "
        + " set pcpc_010.ultimo_numero = " + idPacoteParam 
        + " where pcpc_010.area_periodo = 1 "
        + " and pcpc_010.periodo_producao = " + periodo; 
			
		jdbcTemplate.update(query);
			
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
	
	public void gravarCapa(int idOrdemProducao, String referencia, int periodo, int alternativa, int roteiro, int quantidade, String observacao, String observacao2) {
		
		System.out.println("gravarCapa: " + idOrdemProducao + " -> " + referencia);
		
		Date dataProgramacao = Date.valueOf(new Date(System.currentTimeMillis()).toString());
		
		String query = " insert into pcpc_020 ( " 
	    + " ordem_producao, periodo_producao, "             
	    + " tipo_programacao, alternativa_peca, "              
	    + " roteiro_peca, codigo_risco, "
	    + " qtde_programada, cod_cancelamento, "
	    + " usuario_cancelamento, situacao, "
	    + " data_programacao, referencia_peca, "
	    + " tipo_ordem, ordem_origem, "
	    + " observacao, observacao2, "
	    + " deposito_entrada, pedido_venda) "  
	    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " ;
		
		jdbcTemplate.update(query, idOrdemProducao, periodo, 3, alternativa, roteiro, 0, quantidade, 0, 0, 2, dataProgramacao, referencia, 1, idOrdemProducao,observacao, observacao2, 0, 0);
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
		
		System.out.println("gravarTecidosEnfesto: " + idOrdemProducao + " -> idPacote: " + idPacote + " periodo:  " + periodo + " quantidade: " + quantidade);
		
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
		
		String query = " select m.codigo_estagio estagio, m.descricao from mqop_005 m  where m.codigo_estagio > 0 order by m.codigo_estagio ";
		
		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EstagioProducao.class));
		} catch (Exception e) {
			estagios = new ArrayList<EstagioProducao> ();
		}
		
		return estagios;
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
		
		String query = " select a.ordem_confeccao ordemConfeccao "
				+ " from pcpc_040 a, pcpc_020 b "
				+ " where b.ordem_producao = a.ordem_producao "
				+ " and a.ordem_producao = " + ordemProducao
				+ " group by a.ordem_confeccao ";
		
		try {
			ordensConfeccao = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemConfeccao.class));
		} catch (Exception e) {
			ordensConfeccao = new ArrayList<OrdemConfeccao> ();			
		}
		
		return ordensConfeccao;	
		
	}
	
	public List<DadosTagChina> findDadosTagChina(int ordemProducao, String ordensConfeccao) {
		
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
				+ " and b.ordem_producao = " + ordemProducao;
				
				if (!ordensConfeccao.equals("")) query += " and b.ordem_confeccao in (" + ordensConfeccao + ") ";
				
				query += " and b.codigo_estagio = a.ultimo_estagio "
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
			
			int seqTag = 0;
			String atributo = "";
			
			atributo = findAtributo(tagAtual.getReferencia(), tagAtual.getTamanho(), tagAtual.getCor());
			
			while (seqTag < tagAtual.getQuantidade()) {
				seqTag++;
				
				String tamanhoAtual = tagAtual.getTamanho();
				String tamanhoMedida = "Tam: " + tagAtual.getTamanho();
				
				if (!tagAtual.getTamanhoMedida().equals(" ")) {
					tamanhoAtual = tagAtual.getTamanhoMedida();
					tamanhoMedida = " ";
				}
				
				construtorFormatado = new DadosTagChina(tagAtual.getReferencia(), tamanhoAtual, tagAtual.getCor(), tagAtual.getDescCor(), tagAtual.getDescReferencia(), tagAtual.getQrCode(), tagAtual.getCodBarrasEan(), 
						tagAtual.getQuantidade(), tagAtual.getPreco(), atributo, tagAtual.getCodBarrasProd(), seqTag, tamanhoMedida);
				
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
			
			descCategoria = descCategoria.substring(0, 8);
			atributo = atributo + " - " + descCategoria;
			
		} catch (Exception e) {
			descCategoria = "";
		}
		
		return atributo;
	}
	
	
}
