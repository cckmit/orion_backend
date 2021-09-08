package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaDadosLancHoras;

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
		
		String query = " select a.id, a.tipo, a.sistema, a.situacao, a.titulo, c.nome usuarioAtribuido, a.data_prevista dataPrevista, a.tempo_estimado tempoEstimado, nvl(sum(b.tempo_gasto), 0) tempoGasto from orion_adm_001 a, orion_adm_002 b, orion_001 c "
				+ " where a.id = b.id_tarefa (+) "
				+ " and a.usuario_atribuido = " + idUsuario
				+ " and a.usuario_atribuido = c.id "
				+ " and a.usuario_atribuido = b.id_usuario (+) ";
				
		if (listarAbertos == true) {
			query += " and a.situacao = 0 ";
		}
				
				query += " group by a.id,a.tipo, a.sistema, a.situacao,a.titulo,c.nome,a.data_prevista, a.tempo_estimado ";
		
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
}
