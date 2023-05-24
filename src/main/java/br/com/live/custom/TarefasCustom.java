package br.com.live.custom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.live.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasPorUsuario;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Repository
public class TarefasCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public TarefasCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int findNextIdTarefa() {
		Integer idNextTarefa;

		String query = " select nvl(max(a.id), 0)+1 from orion_adm_001 a ";
		try {
			idNextTarefa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idNextTarefa = 0;
		}
		return idNextTarefa;
	}
	
	public int findNextSequenciaLanc(int idTarefa) {
		Integer seqNextLancamento;

		String query = " select nvl(max(a.sequencia_lancamento), 0)+1 from orion_adm_002 a "
				+ " where a.id_tarefa = " + idTarefa;
		try {
			seqNextLancamento = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			seqNextLancamento = 0;
		}
		return seqNextLancamento;
	}
	
	public float findTotalHorasLancadasMes(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim) {
		float totalHoras = 0;
		
		String query = " select sum(a.tempo_gasto) totalHoras from orion_adm_002 a "
		 		+ " where a.id_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
		 		+ " and a.data_lancamento BETWEEN ? and ? ";
		 try {
			 totalHoras = jdbcTemplate.queryForObject(query, Float.class, FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
			} catch (Exception e) {
				totalHoras = 0;
			}
			return totalHoras;
	}
	
	public List<ConsultaDadosLancHoras> findAllTarefas(int idUsuario, boolean listarAbertos) {
		
		List <ConsultaDadosLancHoras> tarefas= null;
		
		String query = " select a.id, a.origem, a.sistema, a.situacao, a.titulo, c.nome usuarioAtribuido, d.nome usuarioSolicitante, a.data_prevista dataPrevista, a.tempo_estimado tempoEstimado, nvl(sum(b.tempo_gasto), 0) tempoGasto from orion_adm_001 a, orion_adm_002 b, orion_001 c, orion_001 d "
				+ " where a.id = b.id_tarefa (+) "
				+ " and a.usuario_atribuido = " + idUsuario
				+ " and a.usuario_atribuido = c.id "
				+ " and a.usuario_solicitante = d.id ";
				
		if (listarAbertos == true) {
			query += " and a.situacao <> 1 ";
		}
				query += " group by a.id,a.origem, a.sistema, a.situacao,a.titulo,c.nome,a.data_prevista, a.tempo_estimado, d.nome";
		
		try {
			tarefas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosLancHoras.class));
		} catch (Exception e) {
			tarefas = new ArrayList<ConsultaDadosLancHoras>();
		}
		return tarefas;		
	}
	
	public List<ConsultaDadosLancHoras> findAllTarefas(int idUsuario) {
		
		List <ConsultaDadosLancHoras> tarefas= null;
		
		String query = " select a.id, a.tipo, a.sistema, a.situacao, a.titulo, c.nome usuarioAtribuido, a.data_prevista dataPrevista, a.tempo_estimado tempoEstimado, nvl(sum(b.tempo_gasto), 0) tempoGasto from orion_adm_001 a, orion_adm_002 b, orion_001 c "
				+ " where a.id = b.id_tarefa (+) "
				+ " and a.usuario_atribuido = " + idUsuario
				+ " and a.usuario_atribuido = c.id "
				+ " and a.usuario_atribuido = b.id_usuario (+) "
				+ " group by a.id,a.tipo, a.sistema, a.situacao,a.titulo,c.nome,a.data_prevista, a.tempo_estimado ";
		
		try {
			tarefas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDadosLancHoras.class));
		} catch (Exception e) {
			tarefas = new ArrayList<ConsultaDadosLancHoras>();
		}
		return tarefas;		
	}

	public List<ConteudoChaveNumerica> findAllTarefasAtribuidas(int idUsuario, boolean listarAbertos) {

		List <ConteudoChaveNumerica> tarefas= null;

		String query = " select a.id value, a.titulo label from orion_adm_001 a"
				+ " where a.usuario_atribuido = " + idUsuario;

		if (listarAbertos == true) {
			query += " and a.situacao <> 1 ";
		}

		try {
			tarefas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
		} catch (Exception e) {
			tarefas = new ArrayList<ConteudoChaveNumerica>();
		}
		return tarefas;
	}
	
	public List<ConsultaHorasTarefa> findAllLancamentosTarefa(int idTarefa) {
		List<ConsultaHorasTarefa> horasTarefa = null;
		
		String query = " select w.id, e.nome usuario, w.data_lancamento dataLancamento, w.descricao, w.tempo_gasto tempoGasto from orion_adm_002 w, orion_001 e "
				+ " where w.id_tarefa = " + idTarefa
				+ " and w.id_usuario = e.id "
				+ " order by w.data_lancamento desc ";
		
		try {
			horasTarefa = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHorasTarefa.class));
		} catch (Exception e) {
			horasTarefa = new ArrayList<ConsultaHorasTarefa>();
		}
		return horasTarefa;
	}
	
	public List<ConsultaGridTarefas> findAllTarefasGridConsulta(boolean listarAbertos, int idUsuario) {
		List<ConsultaGridTarefas> gridTarefas = null;
		
		String query = " select d.id,d.sistema,d.origem,d.titulo,d.situacao,d.data_prevista dataPrevista, d.tempo_estimado tempoEstimado, e.nome usuarioSolicitante, f.nome usuarioAtribuido, nvl(sum(b.tempo_gasto), 0) horasLancadas"
				+ " from orion_adm_001 d, orion_001 e, orion_001 f, orion_adm_002 b "
				+ " where d.usuario_solicitante = e.id "
				+ " and d.usuario_atribuido = f.id "
				+ " and d.id = b.id_tarefa (+) ";
		
		if (listarAbertos == true) {
			query += " and d.situacao <> 1 ";
		}

		if (idUsuario != 0){
			query += " and d.usuario_atribuido = " + idUsuario;
		}
		
		query += " group by d.id,d.origem, d.sistema, d.situacao, d.titulo, e.nome, d.data_prevista, d.tempo_estimado, f.nome " +
				 " order by d.data_prevista DESC ";
		
		try {
			gridTarefas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGridTarefas.class));
		} catch (Exception e) {
			gridTarefas = new ArrayList<ConsultaGridTarefas>();
		}
		return gridTarefas;
	}
	
	public List<ConsultaHorasLancadas> findLancamentoHoras(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim) {
		String query = " select a.id, a.id_usuario || ' - ' || b.nome usuario, a.data_lancamento data, a.id_tarefa || ' - ' || c.titulo tarefa, c.situacao, c.origem, a.tempo_gasto tempo, c.sistema from orion_adm_002 a, orion_001 b, orion_adm_001 c "
				+ " where a.id_usuario = b.id "
				+ " and a.id_tarefa= c.id "
				+ " and a.id_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ")"
				+ " and a.data_lancamento BETWEEN ? and ? "
				+ " order by a.data_lancamento desc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHorasLancadas.class), FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
	}
	
	public List<ConsultaHorasLancadas> findTotalHorasOrigemLanc(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim) {
		String query = " select decode(b.origem, 1, 'PROJETO', 2, 'MELHORIA', 3, 'ATENDIMENTO', 4, 'ATIVIDADE ADM') origemStr, sum(a.tempo_gasto) horaTotalOrigem from orion_adm_002 a, orion_adm_001 b "
				+ " where a.id_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
				+ " and a.data_lancamento BETWEEN ? and ? "
				+ " and b.id = a.id_tarefa "
				+ " group by b.origem ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHorasLancadas.class), FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
	}
	
	public MetasPorUsuario findValoresMetasHorasUsuario(int codUsuario, int tipoMeta) {
		MetasPorUsuario dadosMetas = null;
		
		String query = " select a.id id, a.codigo_usuario codUsuario, a.tipo_meta tipoMeta, nvl(a.mes_1,0) mes1, nvl(a.mes_2,0) mes2, nvl(a.mes_3,0) mes3, nvl(a.mes_4,0) mes4, nvl(a.mes_5,0) mes5 , nvl(a.mes_6,0) mes6, nvl(a.mes_7,0) mes7, "
				+ " nvl(a.mes_8,0) mes8, nvl(a.mes_9,0) mes9, nvl(a.mes_10,0) mes10, nvl(a.mes_11,0) mes11, nvl(a.mes_12,0) mes12 from orion_004 a "
				+ " where a.codigo_usuario = " + codUsuario
				+ " and a.tipo_meta = " + tipoMeta;
		
		try {
			dadosMetas = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(MetasPorUsuario.class));
		} catch (Exception e) {
			dadosMetas = null;
		}
		return dadosMetas;
	}
	
	public List<ReturnTarefasPrincipais> obterTarefasPrincipais(List<ConteudoChaveNumerica> usuarios) {
		String query = " select h.id, decode(h.origem, 1, 'PROJETO', 2, 'MELHORIA', 3, 'ATENDIMENTO', 4, 'ATIVIDADE ADM') origem, h.titulo, sum(i.tempo_gasto) horas from orion_adm_001 h, orion_adm_002 i "
				+ " where h.usuario_atribuido in ( " + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
				+ " and h.tarefa_principal = 1 "
				+ " and i.id_tarefa = h.id "
				// + " and h.situacao = 0 "
				+ " group by h.id, h.origem, h.titulo "
				+ " order by h.id desc";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReturnTarefasPrincipais.class));
	}
	
	public float obterTotalHorasLancadas(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim) {
		float totalHoras = 0;
		
		String query = " select sum(b.tempo_gasto) from orion_adm_002 b "
				+ " where b.id_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
				+ " and b.data_lancamento BETWEEN ? and ? ";
		
		try {
			totalHoras = jdbcTemplate.queryForObject(query, Float.class, FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
		} catch (Exception e) {
			totalHoras = 0;
		}
		
		return totalHoras;
	}
	
	public float obterTotalHorasLancadasEfetivo(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim) {
		float totalHoras = 0;
		
		String query = " select sum(b.tempo_gasto) from orion_adm_002 b, orion_adm_001 a "
				+ " where b.id_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
				+ " and b.data_lancamento BETWEEN ? and ? "
				+ " and a.id = b.id_tarefa "
				+ " and a.origem in (1,2) ";
		
		try {
			totalHoras = jdbcTemplate.queryForObject(query, Float.class, FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
		} catch (Exception e) {
			totalHoras = 0;
		}
		
		return totalHoras;
	}
	
	public float obterMetaLancadoMesUsuarioByMes(List<ConteudoChaveNumerica> usuarios, String mes, int tipoMeta) {
		float totalMeta = 0;
		
		String query = " select nvl(sum(a.mes_" + mes + "),0) from orion_004 a "
				+ " where a.codigo_usuario in (" + ConteudoChaveNumerica.parseValueToString(usuarios) + ") "
				+ " and a.tipo_meta = " + tipoMeta;
		
		try {
			totalMeta = jdbcTemplate.queryForObject(query, Float.class);
		} catch (Exception e) {
			totalMeta = 0;
		}
		
		return totalMeta;
	}

	public List<ConsultaControleLancamentoHoras> consultaHorasLancadasDia (int idUsuario, String dataInicio, String dataFim){

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dataInicioIn = LocalDate.parse(dataInicio, inputFormatter);
		LocalDate dataFimIn = LocalDate.parse(dataFim, inputFormatter);

		String dataInicioConvert = dataInicioIn.format(outputFormatter);
		String dataFimConvert = dataFimIn.format(outputFormatter);

		List<ConsultaControleLancamentoHoras> consultaHorasList = null;

		String query = "SELECT " +
				"    totalHorasLancamentoDia.dataLancamento, " +
				"    NVL(SUM(oa.TEMPO_GASTO), 0) AS totalHorasLancamentoDia, " +
				" 	 CASE WHEN TO_CHAR(totalHorasLancamentoDia.dataLancamento, 'D') IN (1, 7) THEN 0 ELSE 1 END AS diaTrabalhado " +
				" FROM " +
				"    ( " +
				"        SELECT " +
				"            TRUNC(to_date('"+ dataInicioConvert +"', 'dd/mm/yyyy') + level - 1) AS dataLancamento " +
				"        FROM " +
				"            dual " +
				"        CONNECT BY " +
				"            level <= (to_date('"+ dataFimConvert +"', 'dd/mm/yyyy') - to_date('"+ dataInicioConvert +"', 'dd/mm/yyyy') + 1) + 1 " +
				"    ) totalHorasLancamentoDia " +
				" LEFT JOIN " +
				"    orion_adm_002 oa ON TRUNC(oa.data_lancamento) = totalHorasLancamentoDia.dataLancamento AND oa.id_usuario = " + idUsuario +
				" GROUP BY " +
				"    totalHorasLancamentoDia.dataLancamento " +
				" ORDER BY " +
				"    totalHorasLancamentoDia.dataLancamento DESC";

		try {
			consultaHorasList = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaControleLancamentoHoras.class));
		} catch (Exception e) {
			consultaHorasList = new ArrayList<ConsultaControleLancamentoHoras>();
		}

		return consultaHorasList;
	}

	public List<ConsultaHorasLancadas> consultaLancamentosDia(int idUsuario, String dataLancamento) {

		System.out.println(idUsuario);
		System.out.println(dataLancamento);

		String query = " select a.id, a.id_usuario || ' - ' || b.nome usuario, a.data_lancamento data, a.id_tarefa || ' - ' || c.titulo tarefa, c.situacao, c.origem, a.tempo_gasto tempo, c.sistema from orion_adm_002 a, orion_001 b, orion_adm_001 c "
				+ " where a.id_usuario = b.id "
				+ " and a.id_tarefa= c.id "
				+ " and a.id_usuario = " + idUsuario
				+ " and a.data_lancamento = to_date('" + dataLancamento + "', 'dd-mm-yyyy')"
				+ " order by a.data_lancamento desc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHorasLancadas.class));
	}
}
