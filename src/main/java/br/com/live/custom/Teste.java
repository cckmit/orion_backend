package br.com.live.custom;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Teste {
	
	private JdbcTemplate jdbcTemplate;

	public Teste (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String getDescricao() {
		
		System.out.println("INICIO - TESTE");
		
		String query = "select b.numero_alternati alternativa,  b.numero_roteiro roteiro from basi_010 b where b.nivel_estrutura = '1' and b.grupo_estrutura = '70018'";
		var rows = (List<Map<String, Object>>) jdbcTemplate.queryForList(query);
		
		rows.forEach(System.out::println);
		
		//System.out.println(resultado);
		System.out.println("FIM - TESTE");
		
		return null;
	}

}
