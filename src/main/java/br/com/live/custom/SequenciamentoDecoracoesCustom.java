package br.com.live.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.OrdemProducaoEstagios;
import br.com.live.model.Produto;

@Repository
public class SequenciamentoDecoracoesCustom {

	public static final String ESTAGIOS_DISTRIB_DECORACOES = "9,31";
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
        + " and b.codigo_estagio in (" + ESTAGIOS_DISTRIB_DECORACOES + ") " // CENTRO DISTRIBUIÇÃO - DECORAÇÕES
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

	public List<OrdemProducaoEstagios> findEstagiosDecoracoesOrdem(int ordemProducao) {
		List<OrdemProducaoEstagios> estagios;
		String query = "select a.ordem_producao ordemProducao, a.sequencia_estagio seqEstagio, a.codigo_estagio codEstagio, a.estagio_anterior codEstagioAnterior, a.estagio_depende codEstagioDepende, sum(a.qtde_a_produzir_pacote) qtdeAProduzir from pcpc_040 a "
		+ " where a.ordem_producao = ? "
		+ " and a.qtde_a_produzir_pacote > 0 "
		+ " and (a.codigo_estagio in (select m.codigo_estagio from mqop_005 m where m.est_agrup_est = 10) or a.codigo_estagio in ("+ ESTAGIOS_DISTRIB_DECORACOES +"))"
		+ " group by a.ordem_producao, a.sequencia_estagio, a.codigo_estagio, a.estagio_anterior, a.estagio_depende "
		+ " order by a.ordem_producao, a.sequencia_estagio, a.codigo_estagio "; 
		
		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducaoEstagios.class), ordemProducao);
		} catch (Exception e) {
			estagios = new ArrayList<OrdemProducaoEstagios>();
		}		
		return estagios;
	}		
	
	public String findEnderecoDistribuicao(int ordemProducao) {
		String query = " select max(e.box) endereco from dist_050 d, dist_052 e "
		+ " where d.ordem_producao = ? "
		+ " and e.endereco = d.endereco ";

		return jdbcTemplate.queryForObject(query, String.class, ordemProducao);
	}
	
	public Date findDataProducaoEstagioAnterior(int ordemProducao) {
		String query = "select max(z.data_producao) data_producao "
		+ " from pcpc_040 p, pcpc_045 z " 
		+ " where p.ordem_producao = ? "
		+ " and p.codigo_estagio in (" + ESTAGIOS_DISTRIB_DECORACOES + ")"
		+ " and p.qtde_em_producao_pacote > 0 "
		+ " and z.pcpc040_perconf = p.periodo_producao " 
		+ " and z.pcpc040_ordconf = p.ordem_confeccao "
		+ " and z.pcpc040_estconf = p.estagio_anterior ";
		
		return jdbcTemplate.queryForObject(query, Date.class);
	}
}
