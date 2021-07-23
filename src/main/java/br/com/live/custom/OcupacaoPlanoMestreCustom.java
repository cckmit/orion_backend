package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.OcupacaoEstagioArtigo;
import br.com.live.model.OcupacaoPlanoMestre;

@Repository
public class OcupacaoPlanoMestreCustom {

	private JdbcTemplate jdbcTemplate;

	public OcupacaoPlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public OcupacaoEstagioArtigo findOcupacaoPlanoMestreByPeriodoEstagio(long idPlanoMestre, int periodoInicial, int periodoFinal, int estagio) {
		
		OcupacaoEstagioArtigo ocupacaoEstagio;
		
		String query = "select ocupacao.estagio, sum(ocupacao.qtde_pecas) qtdePecas, sum(ocupacao.qtde_minutos) qtdeMinutos "
		+ " from (select c.codigo_estagio estagio, a.grupo, a.item, a.qtde_programada qtde_pecas, a.qtde_programada * sum(c.minutos_homem) qtde_minutos " 
		        + " from orion_016 a, orion_017 b, mqop_050 c "
		        + " where a.num_plano_mestre = " + idPlanoMestre
		          + " and a.qtde_programada  > 0 "
		          + " and b.num_plano_mestre = a.num_plano_mestre "
		          + " and b.num_item_plano_mestre = a.id "
		          + " and b.periodo between " + periodoInicial + " and " + periodoFinal
		          + " and c.nivel_estrutura = '1' " 
		          + " and c.grupo_estrutura = a.grupo " 
		          + " and (c.item_estrutura = a.item or c.item_estrutura = '000000') " 
		          + " and c.numero_alternati = b.alternativa " 
		          + " and c.numero_roteiro = b.roteiro " 
		          + " and c.codigo_estagio = " + estagio
		          + " group by c.codigo_estagio, a.grupo, a.item, a.qtde_programada) ocupacao "  
	    + " group by ocupacao.estagio " ; 
		
		try {
			ocupacaoEstagio = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoEstagioArtigo.class));
		} catch (Exception e) {
			ocupacaoEstagio = new OcupacaoEstagioArtigo();
		}
		
		return ocupacaoEstagio; 
	}

	public OcupacaoEstagioArtigo findOcupacaoPlanoMestreArtigoByPeriodoEstagioArtigo(long idPlanoMestre, int periodoInicial, int periodoFinal, int estagio, int artigo) {
		
		OcupacaoEstagioArtigo ocupacaoArtigo;
				
		String query = "select ocupacao.estagio, ocupacao.artigo, sum(ocupacao.qtde_pecas) qtdePecas, sum(ocupacao.qtde_minutos) qtdeMinutos " 
		+ " from (select c.codigo_estagio estagio, d.artigo, a.grupo, a.item, a.qtde_programada qtde_pecas, a.qtde_programada * sum(c.minutos_homem) qtde_minutos "  
		          + " from orion_016 a, orion_017 b, mqop_050 c, basi_030 d " 
		         + " where a.num_plano_mestre = " + idPlanoMestre
		           + " and a.qtde_programada  > 0 "
		           + " and b.num_plano_mestre = a.num_plano_mestre "
		           + " and b.num_item_plano_mestre = a.id " 
		           + " and b.periodo between " + periodoInicial + " and " + periodoFinal
		           + " and c.nivel_estrutura = '1' " 
		           + " and c.grupo_estrutura = a.grupo "  
		           + " and (c.item_estrutura = a.item or c.item_estrutura = '000000') "  
		           + " and c.numero_alternati = b.alternativa "
		           + " and c.numero_roteiro = b.roteiro "
		           + " and c.codigo_estagio = " + estagio
		           + " and d.nivel_estrutura = '1' "
		           + " and d.referencia = a.grupo "
		           + " and d.artigo = " + artigo
		         + " group by c.codigo_estagio, d.artigo, a.grupo, a.item, a.qtde_programada) ocupacao "   
		  + " group by ocupacao.estagio, ocupacao.artigo " ;
		
		try {
			ocupacaoArtigo = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoEstagioArtigo.class));
		} catch (Exception e) {
			ocupacaoArtigo = new OcupacaoEstagioArtigo();
		}
		
		return ocupacaoArtigo; 
	}
	
	public OcupacaoEstagioArtigo findOcupacaoProgramadaByPeriodoEstagio(int periodoInicio, int periodoFim, int estagio) {
	
		OcupacaoEstagioArtigo ocupacaoEstagio;
		
		String query = "select ocupacao.estagio, sum(ocupacao.qtde_pecas) qtdePecas, sum(ocupacao.qtde_minutos) qtdeMinutos "
		+ " from (select c.codigo_estagio estagio, ordens.nivel, ordens.grupo, ordens.sub, ordens.item, ordens.qtde_pecas, "
		             + " ordens.alternativa, ordens.roteiro, ordens.qtde_pecas * sum(nvl(c.minutos_homem,0)) qtde_minutos "
		        + " from (select a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item, " 
		                     + " b.alternativa_peca alternativa, b.roteiro_peca roteiro, sum(a.qtde_a_produzir_pacote) qtde_pecas "
   		                + " from pcpc_040 a, pcpc_020 b "
		                + " where b.ordem_producao   = a.ordem_producao "		  
		                 + " and b.cod_cancelamento = 0 "
		                 + " and b.periodo_producao between " + periodoInicio + " and " + periodoFim 
		                 + " and a.proconf_nivel99  = '1' "
		                 + " and a.codigo_estagio   = " + estagio
		                 + " and a.qtde_a_produzir_pacote > 0 "
		               + " group by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.alternativa_peca, b.roteiro_peca) ordens, mqop_050 c "
		       + " where c.nivel_estrutura = ordens.nivel "  
		         + " and c.grupo_estrutura = ordens.grupo "
		         + " and (c.subgru_estrutura = ordens.sub or c.subgru_estrutura = '000') "
		         + " and (c.item_estrutura = ordens.item or c.item_estrutura = '000000') " 
		         + " and c.numero_alternati = ordens.alternativa "  
		         + " and c.numero_roteiro = ordens.roteiro "
		         + " and c.codigo_estagio = " + estagio
		+ " group by c.codigo_estagio, ordens.nivel, ordens.grupo, ordens.sub, ordens.item, ordens.qtde_pecas, ordens.alternativa, ordens.roteiro) ocupacao "
		+ " group by ocupacao.estagio ";

		try {
			ocupacaoEstagio = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoEstagioArtigo.class));
		} catch (Exception e) {
			ocupacaoEstagio = new OcupacaoEstagioArtigo();
		}
		
		return ocupacaoEstagio; 
	}	
	
	public OcupacaoEstagioArtigo findOcupacaoProgramadaArtigoByPeriodoEstagioArtigo(int periodoInicio, int periodoFim, int estagio, int artigo) {
		
		OcupacaoEstagioArtigo ocupacaoArtigo;		
		
		String query = "select ocupacao.estagio, f.artigo, sum(ocupacao.qtde_pecas) qtdePecas, sum(ocupacao.qtde_minutos) qtdeMinutos " 
		+ " from (select c.codigo_estagio estagio, ordens.nivel, ordens.grupo, ordens.sub, ordens.item, ordens.qtde_pecas, "
        + " ordens.alternativa, ordens.roteiro, ordens.qtde_pecas * sum(nvl(c.minutos_homem,0)) qtde_minutos "
        + " from (select a.proconf_nivel99 nivel, a.proconf_grupo grupo, a.proconf_subgrupo sub, a.proconf_item item, "  
                     + " b.alternativa_peca alternativa, b.roteiro_peca roteiro, sum(a.qtde_a_produzir_pacote) qtde_pecas " 
		   	    + " from pcpc_040 a, pcpc_020 b "
		        + " where b.ordem_producao   = a.ordem_producao " 		  
				+ "  and b.cod_cancelamento = 0 "
				+ "  and b.periodo_producao between " + periodoInicio + " and " + periodoFim 
				+ "  and a.proconf_nivel99  = '1' "
				+ "  and a.codigo_estagio   = " + estagio
				+ "  and a.qtde_a_produzir_pacote > 0 " 
		        + " group by a.proconf_nivel99, a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.alternativa_peca, b.roteiro_peca) ordens, mqop_050 c " 
		   + " where c.nivel_estrutura = ordens.nivel "  
		   + " and c.grupo_estrutura = ordens.grupo "
		   + " and (c.subgru_estrutura = ordens.sub or c.subgru_estrutura = '000') " 
		   + " and (c.item_estrutura = ordens.item or c.item_estrutura = '000000') " 
		   + " and c.numero_alternati = ordens.alternativa "  
		   + " and c.numero_roteiro = ordens.roteiro "
		   + " and c.codigo_estagio = " + estagio
		+ " group by c.codigo_estagio, ordens.nivel, ordens.grupo, ordens.sub, ordens.item, ordens.qtde_pecas, ordens.alternativa, ordens.roteiro) ocupacao, basi_030 f "     
		+ " where f.nivel_estrutura = '1' "
		+ " and f.referencia = ocupacao.grupo "
		+ " and f.artigo = " + artigo
		+ " group by ocupacao.estagio, f.artigo "; 

		try {
			ocupacaoArtigo = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoEstagioArtigo.class));
		} catch (Exception e) {
			ocupacaoArtigo = new OcupacaoEstagioArtigo();
		}
		
		return ocupacaoArtigo; 
	}	
	
	public OcupacaoPlanoMestre findOcupacaoCalculadaByPlanoEstagio(long idPlanoMestre, int estagio) {
	
		OcupacaoPlanoMestre ocupacao;
		
		String query = "select o.estagio, "
		   + " o.qtde_capacidade_pecas qtdeCapacidadePecas, " 
	       + " o.qtde_plano_pecas qtdePecasPlano, "
	       + " o.qtde_programado_pecas qtdePecasProgramado, " 
	       + " o.perc_ocupacao_pecas percOcupacaoPecas, "
	       + " o.qtde_falta_sobra_pecas qtdeSobraFaltaPecas, "       
	       + " o.qtde_capacidade_minutos qtdeCapacidadeMinutos, " 
	       + " o.qtde_plano_minutos qtdeMinutosPlano, " 
	       + " o.qtde_programado_minutos qtdeMinutosProgramado, " 
	       + " o.perc_ocupacao_minutos percOcupacaoMinutos, " 
	       + " o.qtde_falta_sobra_minutos qtdeSobraFaltaMinutos "
	    + " from orion_025 o "
	    + " where o.num_plano_mestre = " + idPlanoMestre
	    + " and o.estagio = " + estagio ;
		try {
			ocupacao = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OcupacaoPlanoMestre.class));
		} catch (Exception e) {
			ocupacao = new OcupacaoPlanoMestre();
		}
		
		return ocupacao;
	}

	
	public List<OcupacaoPlanoMestre> findOcupacaoCalculadaArtigoByPlanoEstagio(long idPlanoMestre, int estagio) {
		
		List<OcupacaoPlanoMestre> listOcupacaoArtigo;
		
		String query = "select o.estagio, o.artigo, b.descr_artigo descArtigo, "
		   + " o.qtde_capacidade_pecas qtdeCapacidadePecas, " 
	       + " o.qtde_plano_pecas qtdePecasPlano, "
	       + " o.qtde_programado_pecas qtdePecasProgramado, " 
	       + " o.perc_ocupacao_pecas percOcupacaoPecas, "
	       + " o.qtde_falta_sobra_pecas qtdeSobraFaltaPecas, "       
	       + " o.qtde_capacidade_minutos qtdeCapacidadeMinutos, " 
	       + " o.qtde_plano_minutos qtdeMinutosPlano, " 
	       + " o.qtde_programado_minutos qtdeMinutosProgramado, " 
	       + " o.perc_ocupacao_minutos percOcupacaoMinutos, " 
	       + " o.qtde_falta_sobra_minutos qtdeSobraFaltaMinutos "
	    + " from orion_026 o, basi_290 b "
	    + " where o.num_plano_mestre = " + idPlanoMestre
	    + " and o.estagio = " + estagio 
		+ " and b.artigo = o.artigo " ;
		
		try {
			listOcupacaoArtigo = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OcupacaoPlanoMestre.class));
		} catch (Exception e) {			
			OcupacaoPlanoMestre ocupacao = new OcupacaoPlanoMestre();
			listOcupacaoArtigo = new ArrayList<OcupacaoPlanoMestre>();
			listOcupacaoArtigo.add(ocupacao);			
		}
		
		return listOcupacaoArtigo; 				
	}
}
