package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.AreaIndicador;
import br.com.live.entity.DepartamentoIndicador;
import br.com.live.entity.GrupoIndicador;
import br.com.live.entity.IndicadoresDiario;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.IndicadoresSemanal;
import br.com.live.entity.ResultadosIndicadorDiario;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.entity.ResultadosIndicadorSemanal;
import br.com.live.entity.SetorIndicador;
import br.com.live.entity.UndMedidaIndicador;
import br.com.live.model.IndicadoresAdministrativos;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import static org.springframework.util.StringUtils.capitalize;

@Repository
public class IndicadoresCustom {
	
	private final JdbcTemplate jdbcTemplate;
	
	public IndicadoresCustom (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConteudoChaveAlfaNum> findAllIndicadores(String idUsuario){
		String query = " SELECT a.id value, a.nome_indicador || ' -  ' || " 
					+ "     DECODE(a.frequencia_monitoramento, 1, 'DIÁRIO', "
					+ " 	DECODE(a.frequencia_monitoramento, 2, 'SEMANAL', "
					+ " 	DECODE(a.frequencia_monitoramento, 3, 'MENSAL')))label FROM orion_ind_110 a, orion_ind_020 b, orion_001 c "
					+ " 	WHERE b.id = a.grupo_indicador "
		 			+ " 	AND c.id = a.responsavel_registro "
					+ " 	AND a.responsavel_registro = ? ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class), idUsuario);
	}
	
	public List<ConteudoChaveNumerica> findGrupo() {
		String query = " SELECT a.id value, a.id || ' - ' || a.descricao_grupo label FROM orion_ind_020 a GROUP BY a.id, a.descricao_grupo ORDER BY a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findArea() {
		String query = " SELECT a.id value, a.id || ' - ' || a.descricao_area label FROM orion_ind_030 a GROUP BY a.id, a.descricao_area ORDER BY a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findDepartamento() {
		String query = " SELECT a.id value, a.id || ' - ' || a.descricao_departamento label FROM orion_ind_040 a GROUP BY a.id, a.descricao_departamento ORDER BY a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findSetor() {
		String query = " SELECT a.id value, a.id || ' - ' || a.descricao_setor label FROM orion_ind_050 a GROUP BY a.id, a.descricao_setor ORDER BY a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findUsuarios() {
		String query = " SELECT a.id value, a.id || ' - ' || UPPER(a.nome) label FROM orion_001 a WHERE a.situacao = 1 GROUP BY a.id, a.nome ORDER BY a.id ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findUndMedida() {
		String query = " SELECT a.id value, a.id || ' - ' || UPPER(a.descricao_und_medida) label FROM orion_ind_120 a ORDER BY a.id";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public int findNextGrupoIndicador(){
		
		int nextgrupo = 0;
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_ind_020 a ";
		
		try {
			nextgrupo = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextgrupo = 0;
		}
		return (int) nextgrupo;
	}
	
	public int findNextAreaIndicador(){
		
		int nextArea = 0;
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_ind_030 a ";
		
		try {
			nextArea = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextArea = 0;
		}
		return (int) nextArea;
	}
	
	public int findNextDepIndicador(){
		
		int nextDep = 0;
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_ind_040 a ";
		
		try {
			nextDep = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextDep = 0;
		}
		return (int) nextDep;
	}
	
	public int findNextSetorIndicador(){
		
		int nextSetor = 0;
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_ind_050 a ";
		
		try {
			nextSetor = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextSetor = 0;
		}
		return (int) nextSetor;
	}
	
	public int findNextUndMedIndicador(){
		
		int nextUndMed = 0;
		String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_ind_120 a ";
		
		try {
			nextUndMed = jdbcTemplate.queryForObject(query, Integer.class);
			
		} catch (Exception e) {
			nextUndMed = 0;
		}
		return (int) nextUndMed;
	}
	
	public GrupoIndicador cadastrarGrupo(String descricaoGrupo) {
		
		int idGrupo = findNextGrupoIndicador();
		
		String query = " insert into orion_ind_020(id, descricao_grupo) values (?,?) "; 
		
		String desc = capitalize(descricaoGrupo);
		
		try {
			jdbcTemplate.update(query, idGrupo, desc);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public AreaIndicador cadastrarArea(String descricaoArea) {
		
		int idArea = findNextAreaIndicador();
		
		String query = " insert into orion_ind_030(id, descricao_area) values (?,?) "; 
		
		String desc = capitalize(descricaoArea);
		
		try {
			jdbcTemplate.update(query, idArea, desc);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public DepartamentoIndicador cadastrarDepartamento(String descricaoDepartamento) {
		
		int idDep = findNextDepIndicador();
		
		String query = " insert into orion_ind_040(id, descricao_departamento) values (?,?) "; 
		
		String desc = capitalize(descricaoDepartamento);
		
		try {
			jdbcTemplate.update(query, idDep, desc);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public UndMedidaIndicador cadastrarUndMedida(String descricaoUndMed) {
		
		int idUndMed = findNextUndMedIndicador();
		
		String query = " insert into orion_ind_120(id, descricao_und_medida) values (?,?) "; 
		
		String desc = capitalize(descricaoUndMed);
		
		try {
			jdbcTemplate.update(query, idUndMed, desc);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public SetorIndicador cadastrarSetor(String descricaoSetor) {
		
		int idSetor = findNextSetorIndicador();
		
		String query = " insert into orion_ind_050(id, descricao_setor) values (?,?) "; 
		
		String desc = capitalize(descricaoSetor);
		
		try {
			jdbcTemplate.update(query, idSetor, desc);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public List<IndicadoresMensal> findDadosAno(int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.ano, a.codigo, a.descricao, a.janeiro, a.fevereiro, a.marco, a.abril, a.maio, a.junho, a.julho, a.agosto, a.setembro, a.outubro, a.novembro, a.dezembro "
				+ "   FROM orion_ind_010 a WHERE a.ano = '" + ano + "'"
				+ "   AND a.id_indicador =  '" + idIndicador + "'"
				+ "   ORDER BY a.codigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(IndicadoresMensal.class));
	}
	
	public List<ResultadosIndicadorMensal> findResultadosMensal(int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.codigo || ' - (' || c.descricao_und_medida || ')' codigo, a.descricao, a.janeiro, a.fevereiro, a.marco, a.abril, a.maio, a.junho, "
				+ "   a.julho, a.agosto, a.setembro, a.outubro, a.novembro, a.dezembro "
				+ "   FROM orion_ind_060 a, orion_ind_110 b, orion_ind_120 c WHERE b.id = a.id_indicador "
				+ "   AND c.id = b.unidade_medida "
				+ "   AND a.ano = ? "
				+ "   AND a.id_indicador = ? ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResultadosIndicadorMensal.class), ano, idIndicador);
	}
	
	public List<IndicadoresSemanal> findDadosMesAno(String mes, int ano, int idIndicador) {
				
		String query = " SELECT a.id, a.mes, a.ano, a.variaveis, a.codigo, a.descricao, a.semana_1 semana1, a.semana_2 semana2, a.semana_3 semana3, a.semana_4 semana4, a.semana_5 semana5 FROM orion_ind_070 a "
				+ "   WHERE a.mes = '" + mes + "'"
				+ "   AND a.ano = " + ano  
				+ "   AND a.id_indicador = " + idIndicador
				+ "	  ORDER BY a.codigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(IndicadoresSemanal.class));
	}
	
	public List<ResultadosIndicadorSemanal> findResultadosSemanal(String mes, int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.codigo || ' - (' || c.descricao_und_medida || ')' codigo, a.descricao, a.semana_1 semana1, a.semana_2 semana2, a.semana_3 semana3, "
				+ "    a.semana_4 semana4, a.semana_5 semana5 "
				+ "    FROM orion_ind_080 a, orion_ind_110 b, orion_ind_120 c "
				+ "    WHERE b.id = a.id_indicador "
				+ "    AND c.id = b.unidade_medida "
				+ "    AND a.mes = '" + mes + "'"
				+ "    AND a.ano = " + ano
				+ "    AND a.id_indicador = " + idIndicador;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResultadosIndicadorSemanal.class));
	}

	public List<IndicadoresDiario> findDadosDia(String mes, int ano, int idIndicador) {
				
		String query = " SELECT a.id, a.mes, a.ano, a.variaveis, a.codigo, a.descricao, a.dia_1 dia1, a.dia_2 dia2, a.dia_3 dia3, a.dia_4 dia4, a.dia_5 dia5, "
		        + "   a.dia_6 dia6, a.dia_7 dia7, a.dia_8 dia8, a.dia_9 dia9, a.dia_10 dia10, a.dia_11 dia11, a.dia_12 dia12, a.dia_13 dia13, a.dia_14 dia14, a.dia_15 dia15, "
				+ "   a.dia_16 dia16, a.dia_17 dia17, a.dia_18 dia18, a.dia_19 dia19, a.dia_20 dia20, a.dia_21 dia21, a.dia_22 dia22, a.dia_23 dia23, a.dia_24 dia24, "
				+ "   a.dia_25 dia25, a.dia_26 dia26, a.dia_27 dia27, a.dia_28 dia28, a.dia_29 dia29, a.dia_30 dia30, a.dia_31 dia31 "
		        + "   FROM orion_ind_090 a "
				+ "   WHERE a.mes = '" + mes + "'"
				+ "   AND a.ano = " + ano  
				+ "   AND a.id_indicador = " + idIndicador
				+ "	  ORDER BY a.codigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(IndicadoresDiario.class));
	}

	public List<ResultadosIndicadorDiario> findResultadosDiarios(String mes, int ano, int idIndicador) {
		
		String query = " SELECT a.id, a.codigo || ' - (' || c.descricao_und_medida || ')' codigo, a.descricao, a.dia_1 dia1, a.dia_2 dia2, a.dia_3 dia3, a.dia_4 dia4, a.dia_5 dia5, "
				+ "    a.dia_6 dia6, a.dia_7 dia7, a.dia_8 dia8, a.dia_9 dia9, a.dia_10 dia10, a.dia_11 dia11, a.dia_12 dia12, a.dia_13 dia13, a.dia_14 dia14, a.dia_15 dia15, "
				+ "    a.dia_16 dia16, a.dia_17 dia17, a.dia_18 dia18, a.dia_19 dia19, a.dia_20 dia20, a.dia_21 dia21, a.dia_22 dia22, a.dia_23 dia23, a.dia_24 dia24, "
				+ "    a.dia_25 dia25, a.dia_26 dia26, a.dia_27 dia27, a.dia_28 dia28, a.dia_29 dia29, a.dia_30 dia30, a.dia_31 dia31 "
				+ "    FROM orion_ind_100 a, orion_ind_110 b, orion_ind_120 c "
				+ "    WHERE b.id = a.id_indicador "
				+ "    AND c.id = b.unidade_medida "
				+ "    AND a.mes = '" + mes + "'"
				+ "    AND a.ano = " + ano
				+ "    AND a.id_indicador = " + idIndicador;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ResultadosIndicadorDiario.class));
	}
	
	public IndicadoresAdministrativos findIndicadorSelect(long id) {
		
		String query = " SELECT c.nome_indicador, c.frequencia_monitoramento, c.variaveis FROM orion_ind_110 c WHERE c.id = ? ";
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(IndicadoresAdministrativos.class), id);
		
	}
	public List<String> findvariaveisMensaisById(int idIndicador, int ano) {
		
		String query = " SELECT c.codigo FROM orion_ind_010 c WHERE c.id_indicador = ? AND c.ano = ?";
		
		return jdbcTemplate.queryForList(query, String.class, idIndicador, ano);	
	}
	
	public List<String> findvariaveisSemanaisById(int idIndicador, String mes, int ano) {
		
		String query = " SELECT c.codigo FROM orion_ind_070 c WHERE c.id_indicador = " + idIndicador
				+ "    AND c.mes = '" + mes + "' " 
				+ "    AND c.ano = " + ano;
		
		return jdbcTemplate.queryForList(query, String.class);	
	}

	public List<String> findvariaveisDiariasById(int idIndicador, String mes, int ano) {
		
		String query = " SELECT c.codigo FROM orion_ind_090 c WHERE c.id_indicador = " + idIndicador
				+ "    AND c.mes = '" + mes + "' " 
				+ "    AND c.ano = " + ano;
		
		return jdbcTemplate.queryForList(query, String.class);	
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
	
	public String retornaValorSemanal(int idIndicador, String mes, int ano, String sem, String var) {
		
        String query = " SELECT a." + sem + " FROM orion_ind_070 a "
              +  " WHERE a.id_indicador = '" + idIndicador + "'"
              +  " AND a.mes = '" + mes + "'"
              +  " AND a.ano = '" + ano + "'"
              +  " AND a.codigo = '" + var + "'";
        
		return jdbcTemplate.queryForObject(query, String.class);
    }

	public String retornaValorDiaria(int idIndicador, String mes, int ano, String dias, String var) {
		
        String query = " SELECT a." + dias + " FROM orion_ind_090 a "
              +  " WHERE a.id_indicador = '" + idIndicador + "'"
              +  " AND a.mes = '" + mes + "'"
              +  " AND a.ano = '" + ano + "'"
              +  " AND a.codigo = '" + var + "'";
        
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
	
	public void updateResultSemanal(int idIndicador, double result, String sem, String mes, int ano) {
		
		
		String query = " UPDATE orion_ind_080 a SET a." + sem + " = ? WHERE a.id_indicador = ? AND a.mes = ? AND a.ano = ? "; 
		
		try {
			jdbcTemplate.update(query, result, idIndicador, mes, ano);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
    }

	public void updateResultDiario(int idIndicador, double result, String dias, String mes, int ano) {

		
		String query = " UPDATE orion_ind_100 a SET a." + dias + " = ? WHERE a.id_indicador = ? AND a.mes = ? AND a.ano = ? "; 
		
		try {
			jdbcTemplate.update(query, result, idIndicador, mes, ano);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
    }

}
