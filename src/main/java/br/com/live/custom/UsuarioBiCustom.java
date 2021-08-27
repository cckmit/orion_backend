package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.FiltroProgramaBi;

@Repository
public class UsuarioBiCustom {

	private final JdbcTemplate jdbcTemplate;

	public UsuarioBiCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long findNextIdUsuario() {

		Integer idNextUsuario;

		String query = " select nvl(max(cod_usuario),0) + 1 from orion_bi_003 ";

		try {
			idNextUsuario = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idNextUsuario = 0;
		}
		return (long) idNextUsuario;
	}
	
	public void deleteTiposEmailProgramaUsuario(long codUsuario, String idPrograma) {
		
		String query = " delete from orion_bi_005 a"
				+ " where a.cod_usuario = " + codUsuario
				+ " and a.id_programa = '" + idPrograma + "'";

	    jdbcTemplate.update(query);
	}
	
	public void deleteTiposEmailProgramaUsuarioId(long codUsuario, String idPrograma, String idTipoEmail) {
		
		String query = " delete from orion_bi_005 a"
				+ " where a.cod_usuario = " + codUsuario
				+ " and a.id_programa = '" + idPrograma + "'"
				+ " and a.id_tipo_email = '" + idTipoEmail + "'";

	    jdbcTemplate.update(query);
	}
	
	public List<FiltroProgramaBi> filtrarProgramas(String chavePesquisa) {
		
		System.out.println("chegou: " + chavePesquisa);
		
		String query = " select a.id, a.descricao, a.area_modulo areaModulo, a.atividade, a.ferramenta, a.frequencia, a.planilha, a.extrator, a.help from orion_bi_001 a" 
				+ " where a.id || a.descricao || a.area_modulo like '%" + chavePesquisa + "%'";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(FiltroProgramaBi.class));
	}
	
	public boolean existsUsuario(long idUsuario, String usuario)	{
		
		int encontrou;
		
		String query = " select 1 from orion_bi_003 a ";
		query += " where a.usuario = '" + usuario + "'";		

		if (idUsuario > 0) query += " and a.cod_usuario <> " + idUsuario;	
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			encontrou = 0;
		}
		
		return (encontrou == 1);
	}
	
	
}