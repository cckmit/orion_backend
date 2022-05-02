package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.AcertoCalculoDepreciacao;

@Repository
public class AcertoCalculoDepreciacaoCustom {

	private JdbcTemplate jdbcTemplate;

	public AcertoCalculoDepreciacaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<AcertoCalculoDepreciacao> findBensCalculoDepreciacao(String bens) {

		String query = " select a.id id, a.code code, a.sequence sequence, min(b.ano) ano  from patr_020 a, patr_050 b  where b.bens_id = a.id  and a.code in (" + bens + ") "
				+ " group by a.id, a.code, a.sequence ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(AcertoCalculoDepreciacao.class));
	}

	public AcertoCalculoDepreciacao findMesCalculoDepreciacaoBens(int code, int sequence, int ano) {

		String query = " select min(a.mes) mes " + " from patr_050 a " + " where a.bens_id = " + code + " and a.ano = "
				+ ano + " and a.bens_sequence = " + sequence;
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(AcertoCalculoDepreciacao.class));
	}

	public int validarMesJaInserido(int ano, int mes, int bensId) {

		int existeMes = 0;

		String query = " select 1 from patr_050 j " + " where j.ano = " + ano + "  and j.mes = " + mes + " and j.bens_id = " + bensId
				+ "  and rownum = 1 ";

		try {
			existeMes = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existeMes = 0;
		}

		return existeMes;
	}

	public void inserirDepreciacaoMes(int id, int ano, int mes, int code, int sequence) {
		
		String query = " insert into patr_050 values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		jdbcTemplate.update(query, id, ano, mes, 999, 0, 0, code, sequence, 0 ,0);
	}

	public int findNextIdMes() {

		int nextId = 0;

		String query = " select max(patr_050.id)+1 id from patr_050 ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}

		return nextId;
	}
}
