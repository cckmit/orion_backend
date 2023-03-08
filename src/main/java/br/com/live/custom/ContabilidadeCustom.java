package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.model.ConsultaLanctoContabeis;
import br.com.live.model.Maquinas;

@Repository
public class ContabilidadeCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ContabilidadeCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int findNextId() {
		int nextId = 0;
		
		String query = " select nvl(max(a.id),0) + 1 from orion_cnt_010 a ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}
	
	public int findNextLote() {
		int nextLote = 0;
		
		String query = " SELECT MAX(b.lote) FROM cont_600 b ";

		try {
			nextLote = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextLote = 0;
		}
		return nextLote;
	}
	
	public int findNextNumLancto() {
		int nextNumLancto;
		String query = " SELECT MAX(a.numero_lanc) FROM cont_600 a ";

		try {
			nextNumLancto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextNumLancto = 0;
		}
		return nextNumLancto;
	}
	
	public int findOrigem(int origem) {
		int existsOrigem;
		String query = " SELECT 1 FROM cont_050 a WHERE a.origem = " + origem;

		try {
			existsOrigem = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsOrigem = 0;
		}
		return existsOrigem;
	}
	
	public String findUserSystextil(int idUsuario) {
		
		String usuario = "";
		
		String query = " SELECT NVL(a.usuario_systextil, '') FROM orion_001 a WHERE a.id = " + idUsuario ;
		
		usuario = jdbcTemplate.queryForObject(query, String.class);
		
		return usuario;
	}
	
	public List<ConsultaLanctoContabeis> findAllLanctoContabeis() {
		String query = " select a.id id, "
				+ "       a.filial_lancto filialLancto, "
				+ "       a.exercicio exercicio, "
				+ "       a.origem origem, "
				+ "       a.conta_reduzida contaReduzida, "
				+ "       a.debito_credito debitoCredito, "
				+ "       a.valor_lancto valorLancto, "
				+ "       a.centro_custo centroCusto, "
				+ "       a.hist_contabil histContabil, "
				+ "       a.data_lancto dataLancto, "
				+ "       a.compl_histor1 complHistor1, "
				+ "       a.datainsercao datainsercao, "
				+ "       a.usuario usuario, "
				+ "       a.lote lote, "
				+ "       a.numero_lanc numeroLanc, "
				+ "       a.seq_lanc seqLanc, "
				+ "       a.periodo periodo "
				+ "       FROM orion_cnt_010 a ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaLanctoContabeis.class));
	}

}
