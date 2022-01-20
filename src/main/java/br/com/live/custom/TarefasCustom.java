package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaDadosLancHoras;
import br.com.live.model.ConsultaGridTarefas;
import br.com.live.model.ConsultaHorasLancadas;
import br.com.live.model.ConsultaHorasTarefa;
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
	
	public List<ConsultaHorasTarefa> findAllLancamentosTarefa(int idTarefa) {
		List<ConsultaHorasTarefa> horasTarefa = null;
		
		String query = " select w.id, e.nome usuario, w.data_lancamento dataLancamento, w.descricao, w.tempo_gasto tempoGasto from orion_adm_002 w, orion_001 e "
				+ " where w.id_tarefa = " + idTarefa
				+ " and w.id_usuario = e.id "
				+ " order by w.data_lancamento ";
		
		try {
			horasTarefa = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHorasTarefa.class));
		} catch (Exception e) {
			horasTarefa = new ArrayList<ConsultaHorasTarefa>();
		}
		return horasTarefa;
	}
	
	public List<ConsultaGridTarefas> findAllTarefasGridConsulta(boolean listarAbertos) {
		List<ConsultaGridTarefas> gridTarefas = null;
		
		String query = " select d.id,d.sistema,d.origem,d.titulo,d.situacao,d.data_prevista dataPrevista, d.tempo_estimado tempoEstimado, e.nome usuarioSolicitante, f.nome usuarioAtribuido, nvl(sum(b.tempo_gasto), 0) horasLancadas"
				+ " from orion_adm_001 d, orion_001 e, orion_001 f, orion_adm_002 b "
				+ " where d.usuario_solicitante = e.id "
				+ " and d.usuario_atribuido = f.id "
				+ " and d.id = b.id_tarefa (+) ";
		
		if (listarAbertos == true) {
			query += " and d.situacao <> 1 ";
		}
		
		query += " group by d.id,d.origem, d.sistema, d.situacao, d.titulo, e.nome, d.data_prevista, d.tempo_estimado, f.nome "; 
		
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
}
