package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaConfigArtigos;

@Repository
public class SugestaoReservaMaterialCustom {

	public static final int ESTAGIO_ANALISE_MATERIAIS = 2;
	private final JdbcTemplate jdbcTemplate;

	public SugestaoReservaMaterialCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		
		List<Produto> produtos;
				
		String query = " select p.tecordco_nivel99 nivel, p.tecordco_grupo grupo, p.tecordco_subgrupo sub, p.tecordco_item item, m.narrativa "
		+ " from pcpc_032 p, basi_010 m "
		+ " where p.pcpc0302_orprocor in (select a.ordem_producao from pcpc_020 a, pcpc_040 b "
		+ " where a.cod_cancelamento = 0 "
		+ " and b.ordem_producao = a.ordem_producao "
		+ " and b.codigo_estagio = ? " // ANALISE DE TECIDO
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and m.nivel_estrutura = p.tecordco_nivel99 "
		+ " and m.grupo_estrutura = p.tecordco_grupo "
		+ " and m.subgru_estrutura = p.tecordco_subgrupo "
		+ " and m.item_estrutura = p.tecordco_item "
		+ " group by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa "
		+ " order by p.tecordco_nivel99, p.tecordco_grupo, p.tecordco_subgrupo, p.tecordco_item, m.narrativa ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class), ESTAGIO_ANALISE_MATERIAIS);
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
        + " and b.codigo_estagio = ? " // ANALISE DE TECIDO
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and c.nivel_estrutura = '1' "
		+ " and c.referencia = a.referencia_peca "
		+ " group by a.referencia_peca, c.descr_referencia " 
		+ " order by a.referencia_peca, c.descr_referencia ";
		
		try {
			produtos= jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class), ESTAGIO_ANALISE_MATERIAIS);
		} catch (Exception e) {
			produtos = new ArrayList<Produto> ();
		}
		
		return produtos;
	}

	public Double findQtdeReservadaByProduto(String nivel, String grupo, String sub, String item) {
				
		String query = " select nvl(sum(t.qtde_reservada),0) quantidade " 
		+ " from tmrp_041 t " 
		+ " where t.area_producao = 1 " 
		+ " and t.nivel_estrutura = '" + nivel + "' "
		+ " and t.grupo_estrutura = '" + grupo + "' " 
		+ " and t.subgru_estrutura = '" + sub + "' "
		+ " and t.item_estrutura = '" + item + "' "    
		+ " and not exists (select 1 from pcpc_040 p " 
		+ " where p.ordem_producao = t.nr_pedido_ordem "
		+ " and p.codigo_estagio in (1, 2) "
		+ " and p.qtde_disponivel_baixa > 0) ";
		
		return jdbcTemplate.queryForObject(query, Double.class);		
	}	
	
	public String findLembreteByOrdem(int idOrdem) {		
		String lembrete;
		String query = " select o.lembrete from orion_cfc_210 o where o.ordem_producao = ? ";
		
		try {
			lembrete = jdbcTemplate.queryForObject(query, String.class, idOrdem);
		} catch (Exception e) {
			lembrete = "";
		}
		
		return lembrete; 		
	}
	
	public void gravarMateriaisReservados(int idOrdem, String nivelMaterial, String grupoMaterial, String subMaterial, String itemMaterial, double quantidade) {		
		String id = idOrdem + "-" + nivelMaterial+ "." + grupoMaterial+ "." + subMaterial+ "." + itemMaterial;				
		String query = " insert into orion_cfc_200 (id, ordem_producao, nivel_material, grupo_material, sub_material, item_material, quantidade) values (?,?,?,?,?,?,?) "; 
		
		try {
			jdbcTemplate.update(query, id, idOrdem, nivelMaterial, grupoMaterial, subMaterial, itemMaterial, quantidade);
		} catch (Exception e) {
			query = " update orion_cfc_200 set quantidade ? where id = ? ";
			jdbcTemplate.update(query, quantidade, id);
		}
	}
	
	public void excluirMateriaisReservadosPorOrdem(int idOrdem) {
		String query = " delete from orion_cfc_200 where ordem_producao = ? ";
		jdbcTemplate.update(query, idOrdem);		
	}
	
	public void gravarLembrete(int idOrdem, String lembrete) {	
		String query = " insert into orion_cfc_210 (ordem_producao, lembrete) values (?,?) ";
		try {
			jdbcTemplate.update(query, idOrdem, lembrete);
		} catch (Exception e) {
			query = " update orion_cfc_210 o set o.lembrete = ? where o.ordem_producao = ? ";
			jdbcTemplate.update(query, lembrete, idOrdem);
		}		
	}		
	
	public void gravarConfigArtigos(int coluna, String descricao, int meta, String artigos, int metaMinutos) {
		String query = " insert into orion_cfc_215 (coluna, descricao, meta, artigos, meta_minutos) values (?,?,?,?,?) ";
		try {
			jdbcTemplate.update(query, coluna, descricao, meta, artigos, metaMinutos);
		} catch (Exception e) {
			query = " update orion_cfc_215 set descricao = ?, meta = ?, artigos = ?, meta_minutos = ? where coluna = ? ";
			jdbcTemplate.update(query, descricao, meta, artigos, metaMinutos, coluna);
		}				
	}	
	
	public List<SugestaoReservaConfigArtigos> findConfigArtigos() {				
		List<SugestaoReservaConfigArtigos> configArtigos = new ArrayList<SugestaoReservaConfigArtigos>();		
		String query = " select coluna, descricao, meta, artigos, meta_minutos metaMinutos from orion_cfc_215 ";		
		try {
			configArtigos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(SugestaoReservaConfigArtigos.class));
		} catch (Exception e) {
			configArtigos = new ArrayList<SugestaoReservaConfigArtigos>();
		}
		return configArtigos;		
	}		
}