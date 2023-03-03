package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.FaturamentoLiveClothing;
import br.com.live.model.ConsultaTitulosBloqForn;
import br.com.live.model.ConsultaTpClienteXTabPreco;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class ComercialCustom {
	
	private JdbcTemplate jdbcTemplate;
	
	public ComercialCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void gravaEnvioProdEcommerce(String produto) {
		String query = "insert into ecom_005 (sku, disponivel, preco, controle, data) values (?, 0, 0, 3, sysdate)";		
		jdbcTemplate.update(query, produto);		
		query = " update ecom_005 set controle = 0 where ecom_005.controle = 3 ";		
		jdbcTemplate.update(query);				
	}
	
	public List<ConsultaTitulosBloqForn> findAllTitulosBloqForn() {
		String query = " select a.cnpj_fornecedor_9 || '.' || a.cnpj_fornecedor_4 || '.' || a.cnpj_fornecedor_2 id, a.cnpj_fornecedor_9 || '/' || lpad(a.cnpj_fornecedor_4,4,0) || '-' || lpad(a.cnpj_fornecedor_2,2,0) || ' - ' || b.nome_fornecedor fornecedor, a.data_bloqueio dataBloqueio, a.data_desbloqueio dataDesbloqueio, "
				+ " a.motivo from orion_com_250 a, supr_010 b "
				+ " where a.cnpj_fornecedor_9 = b.fornecedor9 "
				+ " and a.cnpj_fornecedor_4 = b.fornecedor4 "
				+ " and a.cnpj_fornecedor_2 = b.fornecedor2 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTitulosBloqForn.class));
	}
	
	public List<ConteudoChaveNumerica> findTipoCliente() {
		String query = "select a.tipo_cliente value, a.tipo_cliente || ' - ' || a.descr_tipo_clien label from PEDI_085 a "
				+ "     ORDER BY a.tipo_cliente ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findAllCatalogo(){
		String query = "SELECT a.cod_catalogo value, a.cod_catalogo || ' - ' || a.des_catalogo label FROM pedi_063 a "
				+ " ORDER BY a.cod_catalogo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findAllRelacionam(){
		String query = " SELECT a.id value,  c.des_catalogo || ' - ' || b.descr_tipo_clien label FROM orion_com_280 a, pedi_085 b, pedi_063 c "
				+ "  WHERE c.cod_catalogo = a.catalogo "
				+ "  AND b.tipo_cliente = a.tipo_cliente "
				+ "  ORDER BY a.id ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConsultaTpClienteXTabPreco> findByIdTpCliTabPrecoItem(String idCapa){
		String query = " SELECT a.id_item idItem, "
				+ "		  a.id_capa idCapa, "
				+ "       LPAD(a.col_tab_entr, 2, 0) || '.' || LPAD(a.mes_tab_entr, 2, 0) || '.' || LPAD(a.seq_tab_entr, 2, 0) || ' - ' || b.descricao tabela, "
				+ "       a.periodo_ini periodoInicial, "
				+ "       a.periodo_fim periodoFinal"
				+ "       FROM orion_com_281 a, pedi_090 b "
				+ "		  WHERE b.col_tabela_preco = a.col_tab_entr "
				+ "       AND b.mes_tabela_preco = a.mes_tab_entr "
				+ "       AND b.seq_tabela_preco = a.seq_tab_entr "
				+ "       AND a.id_capa = ? " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTpClienteXTabPreco.class), idCapa);
	}
	
	public ConsultaTpClienteXTabPreco findAllGridItem(String idCapa, long id){
		String query = " SELECT a.col_tab_entr || '.' || a.mes_tab_entr || '.' || a.seq_tab_entr tabela, "
				+ "       a.periodo_ini periodoInicial, "
				+ "       a.periodo_fim periodoFinal"
				+ "       FROM orion_com_281 a "
				+ "		  WHERE a.id_capa = ? "
				+ "       AND a.id_item = ? " ;
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTpClienteXTabPreco.class), idCapa, id);
	}
	
	public List<FaturamentoLiveClothing> findAllFatLiveClothing(){
		
		String query = "SELECT a.id id, "
				+ "		  a.loja loja, "
				+ "       a.data data, "
				+ "       a.quantidade quantidade, "
				+ "       a.tickets tickets, "
				+ "       a.conversao conversao, "
				+ "       a.valor_dolar valorDolar, "
				+ "       a.valor_real valorReal "
				+ "       FROM orion_com_300 a "
				+ "       ORDER BY a.data DESC ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(FaturamentoLiveClothing.class));
	}
	
}
