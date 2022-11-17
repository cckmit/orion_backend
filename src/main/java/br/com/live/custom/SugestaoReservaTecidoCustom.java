package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Produto;

@Repository
public class SugestaoReservaTecidoCustom {
	
	private final JdbcTemplate jdbcTemplate;

	public SugestaoReservaTecidoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		
		List<Produto> produtos;
				
		String query = " select p.tecordco_nivel99 nivel, p.tecordco_grupo grupo, p.tecordco_subgrupo sub, p.tecordco_item item, m.narrativa "
		+ " from pcpc_032 p, basi_010 m "
		+ " where p.pcpc0302_orprocor in (select a.ordem_producao from pcpc_020 a, pcpc_040 b "
		+ " where a.cod_cancelamento = 0 "
		+ " and b.ordem_producao = a.ordem_producao "
		+ " and b.codigo_estagio = 2 " // ANALISE DE TECIDO
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and m.nivel_estrutura = p.tecordco_nivel99 "
		+ " and m.grupo_estrutura = p.tecordco_grupo "
		+ " and m.subgru_estrutura = p.tecordco_subgrupo "
		+ " and m.item_estrutura = p.tecordco_item "
		+ " group by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa "
		+ " order by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto> ();
		}
		
		return produtos;
	}

	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		
		List<Produto> produtos;
	
		String query = " select a.referencia_peca grupo, c.descr_referencia narrativa "  
		+ " from pcpc_020 a, basi_030 c "
		+ " where a.cod_cancelamento = 0 "
    	+ " and exists (select 1 from pcpc_040 b "
        + " where b.ordem_producao = a.ordem_producao " 
        + " and b.codigo_estagio = 2 " // ANALISE DE TECIDO
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and c.nivel_estrutura = '1' "
		+ " and c.referencia = a.referencia_peca "
		+ " group by a.referencia_peca, c.descr_referencia " 
		+ " order by a.referencia_peca, c.descr_referencia ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto> ();
		}
		
		return produtos;
	}

	public Double findQtdeReservadaByProduto(String nivel, String grupo, String sub, String item) {
		
		String query = " select nvl(sum(reservado.quantidade),0) quantidade "
		+ " from ( "
		+ " select nvl(sum(t.qtde_reservada),0) quantidade "
   	    + " from tmrp_041 t "
		+ " where t.area_producao = 1 " 
		+ " and t.nivel_estrutura = '" + nivel + "' "
		+ " and t.grupo_estrutura = '" + grupo + "' " 
		+ " and t.subgru_estrutura = '" + sub + "' "
		+ " and t.item_estrutura = '" + item + "' "    
		+ " and not exists (select 1 from pcpc_040 p "  
		+ " where p.ordem_producao = t.nr_pedido_ordem "  
		+ " and p.codigo_estagio in (1, 2) " // PROGRAMACAO E ANALISE DE TECIDO 
		+ " and p.qtde_disponivel_baixa > 0) "
		+ " and not exists (select 1 from orion_cfc_200 o "
		+ " where o.ordem_producao = t.nr_pedido_ordem "		
		+ " and o.nivel_tecido = t.nivel_estrutura "
		+ " and o.grupo_tecido = t.grupo_estrutura "
		+ " and o.sub_tecido = t.subgru_estrutura "
	    + " and o.item_tecido = t.item_estrutura) "
		+ " union all "
		+ " select nvl(sum(t.quantidade),0) quantidade "
		+ " from orion_cfc_200 t "
		+ " where t.nivel_tecido = '" + nivel + "' "
		+ " and t.grupo_tecido = '" + grupo + "' "
		+ " and t.sub_tecido = '" + sub + "' "
		+ " and t.item_tecido = '" + item + "' "
		+ " and not exists (select 1 from pcpc_040 p "  
		+ " where p.ordem_producao = t.ordem_producao "  
		+ " and p.codigo_estagio in (1, 2) " // ANALISE DE TECIDO 
		+ " and p.qtde_disponivel_baixa > 0) "
		+ " and exists (select 1 from tmrp_041 o "
		+ " where o.area_producao = 1 "
		+ " and o.nr_pedido_ordem = t.ordem_producao "
		+ " and o.nivel_estrutura = t.nivel_tecido) "
		+ " ) reservado ";		
		
		return jdbcTemplate.queryForObject(query, Double.class);		
	}	
	
	public String findLembreteByOrdem(int idOrdem) {		
		String lembrete;
		String query = " select o.lembrete from orion_210 o where o.ordem_producao = ? ";
		
		try {
			lembrete = jdbcTemplate.queryForObject(query, String.class, idOrdem);
		} catch (Exception e) {
			lembrete = "";
		}
		
		return lembrete; 		
	}
	
	public void gravarTecidosReservados(int idOrdem, String nivelTecido, String grupoTecido, String subTecido, String itemTecido, double quantidade) {		
		String id = idOrdem + "-" + nivelTecido+ "." + grupoTecido+ "." + subTecido+ "." + itemTecido;				
		String query = " insert into orion_cfc_200 (id, ordem_producao, nivel_tecido, grupo_tecido, sub_tecido, item_tecido, quantidade) values (?,?,?,?,?,?,?) "; 
		
		try {
			jdbcTemplate.update(query, id, idOrdem, nivelTecido, grupoTecido, subTecido, itemTecido, quantidade);
		} catch (Exception e) {
			query = " update orion_cfc_200 set quantidade ? where id = ? ";
			jdbcTemplate.update(query, quantidade, id);
		}
	}
	
	public void excluirTecidosReservadosPorOrdem(int idOrdem) {
		String query = " delete from orion_cfc_200 where ordem_producao = ? ";
		jdbcTemplate.update(query, idOrdem);		
	}
	
	public void gravarLembrete(int idOrdem, String lembrete) {	
		String query = " insert into orion_210 (ordem_producao, lembrete) values (?,?) ";
		try {
			jdbcTemplate.update(query, idOrdem, lembrete);
		} catch (Exception e) {
			query = " update orion_210 o set o.lembrete = ? where o.ordem_producao = ? ";
			jdbcTemplate.update(query, lembrete, idOrdem);
		}		
	}		
}