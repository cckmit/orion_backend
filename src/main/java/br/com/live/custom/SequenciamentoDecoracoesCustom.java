package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.OrdemProducaoEstagios;
import br.com.live.model.Produto;

@Repository
public class SequenciamentoDecoracoesCustom {

	private final JdbcTemplate jdbcTemplate;

	public SequenciamentoDecoracoesCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Produto> findReferenciasEmOrdensCentroDistrib() {	
		List<Produto> produtos;	
		String query = " select a.referencia_peca grupo, c.descr_referencia narrativa "  
		+ " from pcpc_020 a, basi_030 c "
		+ " where a.cod_cancelamento = 0 "
    	+ " and exists (select 1 from pcpc_040 b "
        + " where b.ordem_producao = a.ordem_producao " 
        + " and b.codigo_estagio in (9,31) " // CENTRO DISTRIBUIÇÃO - DECORAÇÕES
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and c.nivel_estrutura = '1' "
		+ " and c.referencia = a.referencia_peca "
		+ " group by a.referencia_peca, c.descr_referencia " 
		+ " order by a.referencia_peca, c.descr_referencia ";
		
		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto>();
		}		
		return produtos;
	}

	public List<OrdemProducaoEstagios> findProximosEstagiosDecoracoesOrdem(int ordemProducao) {
			
		List<OrdemProducaoEstagios> estagios;
						
		String query = "select a.ordem_producao ordemProducao, a.sequencia_estagio seqEstagio, a.codigo_estagio codEstagio, a.estagio_anterior codEstagioAnterior, a.estagio_depende codEstagioDepende, sum(a.qtde_a_produzir_pacote) qtdeAProduzir from pcpc_040 a "
		+ " where a.ordem_producao = ? "
		+ " and a.qtde_a_produzir_pacote > 0 "
		+ " and a.codigo_estagio in (select m.codigo_estagio from mqop_005 m where m.est_agrup_est = 10) "
		+ " group by a.ordem_producao, a.sequencia_estagio, a.codigo_estagio, a.estagio_anterior, a.estagio_depende "
		+ " order by a.ordem_producao, a.sequencia_estagio, a.codigo_estagio "; 
		
		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducaoEstagios.class));
		} catch (Exception e) {
			estagios = new ArrayList<OrdemProducaoEstagios>();
		}		
		return estagios;
	}	
	
}
