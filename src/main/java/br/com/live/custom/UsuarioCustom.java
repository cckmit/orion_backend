package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaModulosUsuario;
import br.com.live.model.ConsultaProgramasUsuario;
import br.com.live.model.DadosUsuario;

@Repository
public class UsuarioCustom {

	private final JdbcTemplate jdbcTemplate;

	public UsuarioCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long findNextIdUsuario() {

		Integer idNextUsuario;

		String query = " select nvl(max(id),0) + 1 from orion_001 ";

		try {
			idNextUsuario = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			idNextUsuario = 0;
		}
		return (long) idNextUsuario;
	}
	
	public List <ConsultaProgramasUsuario> findProgramasUsuario(long idUsuario) {
		
		List <ConsultaProgramasUsuario> programas = null;
		
		String query = "select b.id_usuario idUsuario,c.id idPrograma,c.descricao,c.modulo,c.path from orion_003 b, orion_002 c where b.id_usuario =" + idUsuario + " and c.id = b.id_programa";
		
		try {
			programas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaProgramasUsuario.class));
		} catch (Exception e) {
			programas = new ArrayList<ConsultaProgramasUsuario>();
		}
		return programas;
	}
	
	public List <ConsultaModulosUsuario> findModulosUsuario(long idUsuario) {
		
		List <ConsultaModulosUsuario> modulos = null;
		
		String query = " select b.id_usuario idUsuario,c.modulo from orion_003 b, orion_002 c "
				+ " where b.id_usuario = " + idUsuario
				+ " and c.id = b.id_programa "
				+ " group by b.id_usuario, c.modulo "
				+ " order by c.modulo";
		
		try {
			modulos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaModulosUsuario.class));
		} catch (Exception e) {
			modulos = new ArrayList<ConsultaModulosUsuario>();
		}
		return modulos;
	}
	
	public DadosUsuario findDadosUsuario(long idUsuario){
		DadosUsuario  dados = new DadosUsuario();
		dados.setModulos(findModulosUsuario(idUsuario));
		dados.setProgramas(findProgramasUsuario(idUsuario));
		dados.setIdUsuario(idUsuario);
		
		return dados;
	}
	
	public String findPathPrograma(long idUsuario, String descricao) {
		
		String programaSel;
		
		String query = " SELECT max(a.path) FROM orion_002 a, orion_003 b where UPPER(a.descricao) like UPPER('%" + descricao + "%') "
				+ " and b.id_usuario = " + idUsuario
				+ " and a.id = b.id_programa";

		try {
			programaSel = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			programaSel = "";
		}
		return programaSel;
	}
	
}
