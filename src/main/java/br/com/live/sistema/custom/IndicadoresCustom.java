package br.com.live.sistema.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.AreaIndicador;
import br.com.live.sistema.entity.Indicadores;
import br.com.live.sistema.entity.IndicadoresMensal;
import br.com.live.sistema.entity.ResultadosIndicadorMensal;
import br.com.live.sistema.entity.ResultadosIndicadorSemanal;
import br.com.live.sistema.model.ConsultaIndicadores;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class IndicadoresCustom {
	
	private final JdbcTemplate jdbcTemplate;
	
	public IndicadoresCustom (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConsultaIndicadores> findAllIndicadores(int idUsuario){
		
		String query = " SELECT a.id id, "
				+ "       a.nome_indicador nome, "
				+ "       (SELECT b.descricao FROM orion_ind_020 b WHERE b.tipo = 1 AND b.sequencia = a.grupo_indicador) grupo, "
				+ "       (SELECT b.descricao FROM orion_ind_020 b WHERE b.tipo = 2 AND b.sequencia = a.area) area, "
				+ "       (SELECT b.descricao FROM orion_ind_020 b WHERE b.tipo = 3 AND b.sequencia = a.departamento) dpto, "
				+ "       (SELECT b.descricao FROM orion_ind_020 b WHERE b.tipo = 4 AND b.sequencia = a.setor) setor, "
				+ "       (SELECT b.descricao FROM orion_ind_020 b WHERE b.tipo = 5 AND b.sequencia = a.unidade_medida) undMedida, "
				+ "       a.fonte_dados fonteDados, "
				+ "       a.polaridade polaridade, "
				+ "       c.nome respPublic, "
				+ "       a.observacao obs, "
				+ "       a.formula_calculo formula, "
				+ "       a.situacao situacao,"
				+ "       a.diretoria diretoria "
				+ "   FROM orion_ind_110 a, orion_001 c "
				+ "   WHERE c.id = a.responsavel_publicacao "
				+ "   AND a.responsavel_registro = " + idUsuario
				+ "   ORDER BY a.nome_indicador ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaIndicadores.class));
	}
	
	public List<ConteudoChaveNumerica> findArea(int tipo) {
		String query = " SELECT a.sequencia value, "
				+ "	   	 a.descricao label "
				+ "      FROM orion_ind_020 a "
				+ "      WHERE a.tipo = '" + tipo + "'"
				+ "      ORDER BY a.descricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAreaByTipo(int tipo) {
		String query = " SELECT a.sequencia value, a.descricao label "
				+ "      FROM orion_ind_020 a "
				+ "      WHERE a.tipo = ? "
				+ "		 ORDER BY a.descricao ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class), tipo);
	}
	
	public List<ConteudoChaveNumerica> findUsuarios() {
		String query = " SELECT a.id value, UPPER(a.nome) label FROM orion_001 a WHERE a.situacao = 1 GROUP BY a.id, a.nome ORDER BY a.nome ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<IndicadoresMensal> findDadosAno(int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.ano, a.codigo, a.variaveis, a.descricao, a.janeiro, a.fevereiro, a.marco, a.abril, a.maio, a.junho, a.julho, a.agosto, a.setembro, a.outubro, a.novembro, a.dezembro "
				+ "   FROM orion_ind_010 a WHERE a.ano = '" + ano + "'"
				+ "   AND a.id_indicador =  '" + idIndicador + "'"
				+ "   ORDER BY a.codigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(IndicadoresMensal.class));
	}
	
	public List<ResultadosIndicadorMensal> findResultadosMensal(int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.codigo, a.descricao, a.janeiro, a.fevereiro, a.marco, a.abril, a.maio, a.junho, a.julho, a.agosto, a.setembro, a.outubro, "
				+ "       a.novembro, a.dezembro "
				+ "      FROM orion_ind_060 a, orion_ind_110 b "
				+ "      WHERE b.id = a.id_indicador "
				+ "      AND a.ano = ? "
				+ "      AND a.id_indicador = ? ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResultadosIndicadorMensal.class), ano, idIndicador);
	}
	
	public Indicadores findIndicadorSelect(long id) {
		
		String query = " SELECT a.id id, "
				+ "       a.nome_indicador nomeIndicador, "
				+ "       a.grupo_indicador grupoIndicador, "
				+ "       a.area area, "
				+ "       a.departamento departamento, "
				+ "       a.setor setor, "
				+ "       a.gestor_avaliado gestorAvaliado, "
				+ "       a.unidade_medida unidadeMedida, "
				+ "       a.frequencia_monitoramento frequenciaMonitoramento, "
				+ "       a.fonte_dados fonteDados, "
				+ "       a.polaridade polaridade, "
				+ "       a.responsavel_registro responsavelRegistro, "
				+ "       a.responsavel_publicacao responsavelPublicacao, "
				+ "       a.observacao observacao, "
				+ "       a.variaveis variaveis, "
				+ "       a.formula_calculo formulaCalculo, "
				+ "       a.situacao situacao, "
				+ "       a.diretoria "
				+ "   FROM orion_ind_110 a "
				+ "   WHERE a.id = ? ";
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Indicadores.class), id);
		
	}
	public List<String> findvariaveisMensaisById(int idIndicador, int ano) {
		
		String query = " SELECT c.codigo FROM orion_ind_010 c WHERE c.id_indicador = ? AND c.ano = ?";
		
		return jdbcTemplate.queryForList(query, String.class, idIndicador, ano);	
	}
	
	public String findFormula(int idIndicador) {
		
		String query = " SELECT c.formula_calculo FROM orion_ind_110 c WHERE c.id = " + idIndicador;	
		
		return jdbcTemplate.queryForObject(query, String.class);
	}
	
	public String retornaValorMensal(int idIndicador, String mes, int ano, String var) {
		
        String query = " SELECT a." + mes + " FROM orion_ind_010 a " +
                " WHERE a.id_indicador = " + idIndicador +
				" AND a.ano = " + ano +
                " AND a.codigo = '" + var + "'";
        
		return jdbcTemplate.queryForObject(query, String.class);
    }
	
	public void updateResultMensal(int idIndicador, double result, String mes, int ano) {
		
		
		String query = " UPDATE orion_ind_060 a SET a." + mes + " = ? WHERE a.id_indicador = ? AND a.ano = ? "; 
		
		try {
			jdbcTemplate.update(query, result, idIndicador, ano);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
    }
	
	public int findNextSequencia(int tipo) {

		int proxSeq = 0;

		String query = " SELECT NVL(max(a.sequencia),0) + 1 FROM orion_ind_020 a "
				+ "		WHERE a.tipo = '" + tipo + "'";

		try {
			proxSeq = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxSeq = 0;
		}
		return proxSeq;
	}
	
	public AreaIndicador findTipoById(String id) {
		
		String query = " SELECT c.id, c.tipo, c.sequencia, c.descricao FROM orion_ind_020 c WHERE c.id = '" + id + "'";
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(AreaIndicador.class));
		
	}
	
	public void deleteValoresMensais(int idIndicador) {
		
		String queryMensal = " DELETE FROM orion_ind_010 WHERE id_indicador = ? ";
		
		try {
			jdbcTemplate.update(queryMensal, idIndicador);
				
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void deleteResultadosMensais(int idIndicador) {
		
		String queryResultMensal = " DELETE FROM orion_ind_060 WHERE id_indicador = ? ";
		
		try {
			jdbcTemplate.update(queryResultMensal, idIndicador);
				
		} catch (Exception e) {
			System.out.println(e);
		} 
	}

}
