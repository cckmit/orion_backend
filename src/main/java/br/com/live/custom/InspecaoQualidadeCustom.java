package br.com.live.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidade;
import br.com.live.model.ConsultaInspecaoQualidLanctoPecas;
import br.com.live.model.MotivoRejeicao;

@Repository
public class InspecaoQualidadeCustom {

	private final JdbcTemplate jdbcTemplate;
	
	public InspecaoQualidadeCustom (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public List<MotivoRejeicao> findAllMotivos() {
		
		String query = " select motivos.codigo_motivo codMotivo, motivos.desc_motivo descMotivo, motivos.codigo_estagio codEstagio, motivos.desc_estagio descEstagio "
		+ " from (select 0 codigo_motivo, 'SEM DEFEITO' desc_motivo, 0 codigo_estagio, '.' desc_estagio from dual "
		+ " union "
		+ " select efic_040.codigo_motivo, efic_040.descricao desc_motivo, efic_040.codigo_estagio, mqop_005.descricao desc_estagio " 
		+ " from efic_040, mqop_005 "
		+ " where efic_040.nivel = '1' "     
		+ " and efic_040.area_producao = 1 "       
		+ " and mqop_005.codigo_estagio = efic_040.codigo_estagio "
		+ " group by efic_040.codigo_motivo, efic_040.descricao, efic_040.codigo_estagio, mqop_005.descricao) motivos "
		+ " order by motivos.codigo_motivo, motivos.desc_motivo, motivos.codigo_estagio, motivos.desc_estagio " ;     

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MotivoRejeicao.class));
	}
	
	public long findNextIdInspecao() {
		String query = " select nvl(max(id_inspecao),0) from orion_050 ";
		
		long id = jdbcTemplate.queryForObject(query, Integer.class);
		id++;

		return id;
	}
	
	public long findNextIdInspecaoLanctoPeca() {		
		String query = " select nvl(max(id_lancamento),0) from orion_051 ";
		
		long id = jdbcTemplate.queryForObject(query, Integer.class);
		id++;
		
		return id;
	}

	public int getQtdeInspecionadaPeca(long idInspecao) {
		
		String query = " select nvl(sum(a.quantidade),0) from orion_051 a "
		+ " where a.id_inspecao = " + idInspecao;		
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int getQtdeRejeitadaPeca(long idInspecao) {
		
		String query = " select nvl(sum(a.quantidade),0) from orion_051 a "
		+ " where a.id_inspecao = " + idInspecao
		+ "  and a.cod_motivo  > 0 " ;
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}	
	
	public List<ConsultaInspecaoQualidLanctoPecas> findLancamentoPecasByIdInspecao(long idInspecao) {
		
		List<ConsultaInspecaoQualidLanctoPecas> lancamentos;
		
		String query = " select a.id_lancamento id, a.cod_motivo || ' - ' || nvl(b.descricao, 'SEM DEFEITO') motivo, " 
	    + " b.codigo_estagio || ' - ' || c.descricao estagio, "  
	    + " a.quantidade, a.usuario, a.data_hora " 
	    + " from orion_051 a, efic_040 b, mqop_005 c "
	    + " where a.id_inspecao = " + idInspecao
	    + " and b.codigo_motivo  (+) = a.cod_motivo "
	    + " and c.codigo_estagio (+) = b.codigo_estagio "
	    + " order by a.id_lancamento " ;
		
		try {
			lancamentos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaInspecaoQualidLanctoPecas.class));
		} catch (Exception e) {
			lancamentos = new ArrayList<ConsultaInspecaoQualidLanctoPecas>();
		}
		
		return lancamentos;
	}
	
}
