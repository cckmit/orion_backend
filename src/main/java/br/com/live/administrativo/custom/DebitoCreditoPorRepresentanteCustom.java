package br.com.live.administrativo.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.model.ConsultaDebCredPorRepresentante;
import br.com.live.util.ConteudoChaveAlfaNum;

@Repository
public class DebitoCreditoPorRepresentanteCustom {
	
    private final JdbcTemplate jdbcTemplate;

    public DebitoCreditoPorRepresentanteCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public String findUsuarioById(int id) {
    	
    	String usuario = "";
    	
    	String query = " SELECT c.id || '-' || c.nome FROM orion_001 c WHERE c.id = " + id;	
    	
    	try {
    		usuario = jdbcTemplate.queryForObject(query, String.class);			
			
		} catch (Exception e) {
			usuario = "";
		}
    	return usuario;
    	
    }
    
    public int findNextId() {
    	
    	int nextId = 0;
    	
    	String query = " SELECT NVL(MAX(a.id), 0) + 1 FROM orion_fin_040 a ";	
		
    	try {
		nextId = jdbcTemplate.queryForObject(query, Integer.class);			
			
		} catch (Exception e) {
			nextId = 0;
		}
    	return nextId;
    	
    }
    
    public List<ConteudoChaveAlfaNum> findRepresentantes(String representante) {
		
		String query = " SELECT a.cod_rep_cliente value, "
				+ "    a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label "
				+ "    FROM pedi_020 a WHERE a.tipo_repr = 3 "
				+ "    AND cod_rep_cliente || ' - ' || a.nome_rep_cliente LIKE '%" + representante.toUpperCase() + "%'";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		
	}
    
    public List<ConsultaDebCredPorRepresentante> findAllDebitoCredito(){
		
		List<ConsultaDebCredPorRepresentante> listLactoManuais = null;
		
		String query = "  SELECT f.id id, "
				+ "     f.data_lancto dataLancto, "
				+ "     f.campanha campanha, "
				+ "     DECODE(f.tipo, 1, 'Débito', 'Crédito') tipo, "
				+ "     f.representante || ' - ' || g.nome_rep_cliente representante, "
				+ "     LPAD(f.mes, 2, 0) || '/' || f.ano referencia, "
				+ "     f.observacao observacao, "
				+ "     f.valor valor, "
				+ "     f.usuario || '-' || h.nome usuario "
				+ "	FROM orion_fin_040 f, pedi_020 g, orion_001 h "
				+ "	WHERE g.cod_rep_cliente = f.representante "
				+ "	AND h.id = f.usuario "
				+ " ORDER BY data_lancto DESC";
		
		try {
			listLactoManuais = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaDebCredPorRepresentante.class));
		} catch (Exception e) {
			listLactoManuais = new ArrayList<ConsultaDebCredPorRepresentante>();
		}
		return listLactoManuais;
	}

}
