package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import br.com.live.model.ConsultaRequisicaoTecidosItem;

@Repository
public class RequisicaoTecidosCustom {

	private final JdbcTemplate jdbcTemplate;

	public RequisicaoTecidosCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConsultaRequisicaoTecidosItem> findItensByIdRequisicao(long idRequisicao) {
		
		List<ConsultaRequisicaoTecidosItem> itens;
		
		String query = " select a.id, a.sequencia, a.nivel, a.grupo, a.sub, a.item, b.narrativa, a.alternativa, a.roteiro, a.quantidade " 
		+ " from orion_091 a, basi_010 b "
		+ " where b.nivel_estrutura = a.nivel "
		+ " and b.grupo_estrutura = a.grupo "
		+ " and b.subgru_estrutura = a.sub "
		+ " and b.item_estrutura = a.item "
		+ " and a.id_requisicao = " + idRequisicao
		+ " order by a.sequencia " 
		;
		
		try {
			itens = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRequisicaoTecidosItem.class));
		} catch (Exception e) {
			itens = new ArrayList<ConsultaRequisicaoTecidosItem> ();
		}

		return itens;
	}
	
}
