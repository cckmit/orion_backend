package br.com.live.custom;

import java.sql.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
			
		return idPacoteParam;
	}
	
	public void atualizarUltimoEstagioOrdem(int idOrdemProducao, int ultimoEstagio) {
				
		String query = " update pcpc_020 "
	    + " set pcpc_020.ultimo_estagio = " + ultimoEstagio         
	    + " where pcpc_020.ordem_producao = " + idOrdemProducao;

		jdbcTemplate.update(query);		
	}
	
	public void gravarCapa(int idOrdemProducao, String referencia, int periodo, int alternativa, int roteiro, int quantidade, String observacao) {
				
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
		
		jdbcTemplate.update(query, idOrdemProducao, periodo, 3, alternativa, roteiro, 0, quantidade, 0, 0, 2, dataProgramacao, referencia, 1, idOrdemProducao,observacao, "", 0, 0);
	}
	
	public void gravarTamanhoCor(int idOrdemProducao, String tamanho, String cor, int quantidade, int seqTamanho) {
		
		String query = " insert into pcpc_021 ( "
		+ " ordem_producao, tamanho, "
		+ " sortimento, quantidade, "
		+ " sequencia_tamanho) "
		+ " values (?, ?, ?, ?, ?) " ;
				       
		jdbcTemplate.update(query, idOrdemProducao, tamanho, cor, quantidade, seqTamanho);
	}

	public void gravarMarcacaoTamanho(int idOrdemProducao, String sub, int qtdeMarcacoes, int seqTamanho) {

		String query = " insert into pcpc_025 (ordem_producao, tamanho, qtde_marcacoes, sequencia_tamanho) "
	    + " values (?, ?, ?, ?) " ;

		jdbcTemplate.update(query, idOrdemProducao, sub, qtdeMarcacoes, seqTamanho);		
	}	
	
	public void gravarCapaEnfesto(int idOrdemProducao, String grupo, int seqEstrutura, int alternativa, int roteiro) {
		
		String query = "";
		
		query = " select 1 from pcpc_030 "
		+ " where ordem_producao = '" + idOrdemProducao
		+ " and ordem_estrutura = '" + seqEstrutura
		+ " and sequencia = 1 "	;	
		  
		try {
			jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			
			query = " insert into pcpc_030 ( "
				    + " ordem_producao, reforcor_nivel030, "
				    + " reforcor_referenc, ordem_estrutura, "
				    + " alternativa_peca, roteiro_peca, "
				    + " cod_risco_padrao, comprimento, "
				    + " qtde_programada, sequencia) " 
				    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";                 
				    
			jdbcTemplate.update(query, idOrdemProducao, '1', grupo, seqEstrutura, alternativa, roteiro, 0, 0.00, 0, 1);			
		}
	}
	
	public void gravarTecidosEnfesto( ) {
		
	}
	
	public void gravarMarcacoesEnfesto(int idOrdemProducao, String sub, int seqEstrutura, int qtdeMarcacoes) {
		
		String query = "";
				
		query = " select 1 from pcpc_035 "
		+ " where pcpc0305_orprocor = " + idOrdemProducao
		+ " and pcpc0305_seqorcor = " + seqEstrutura
		+ " and pcpc0305_sequenor = 1 " ;
		
		try {
			jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			
			query = " insert into pcpc_035 ( "       
		    + " pcpc0305_orprocor, pcpc0305_seqorcor, " 
		    + " tamanho_peca, qtde_marc_prog, "
		    + " pcpc0305_sequenor) "
		    + " values (?, ?, ?, ?, ?) ";
		    
			jdbcTemplate.update(query, idOrdemProducao, seqEstrutura, sub, qtdeMarcacoes, 1); 
		}
	}	
	
	public void gravarPacoteConfeccao(int periodo, int idPacote, int estagio, int idOrdemProducao, String grupo, String sub, String item, int quantidade, 
			int estagioAnterior, int familia, int seqOperacao, int estagioDepende, int seqEstagio) {
		
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
	
}
