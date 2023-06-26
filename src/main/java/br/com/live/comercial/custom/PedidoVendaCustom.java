package br.com.live.comercial.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.model.PedidoVenda;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class PedidoVendaCustom {

	private JdbcTemplate jdbcTemplate;

	public PedidoVendaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<PedidoVenda> findOrigensPedido() {
		String query = " select a.origem id, a.descr_origem descricao, nvl(b.perc_comissao, 0) percComissao from pedi_115 a, orion_095 b "
				+ " where b.origem (+) = a.origem "
				+ " order by a.origem ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(PedidoVenda.class));
	}
	
	public void deleteAllOrigens() {
		String query = " delete from orion_095 ";
		
		jdbcTemplate.update(query);
	}
	
	public PedidoVenda findPedidoVenda(int pedidoVenda) {			

		PedidoVenda pedidoEncontrado = null;
		
		String query = "select a.pedido_venda id, "
        + " a.pedido_venda pedidoVenda, "
        + " a.num_periodo_prod periodo, "
        + " a.valor_total_pedi valor, "
        + " lpad(a.cli_ped_cgc_cli9,9,0) || lpad(a.cli_ped_cgc_cli4,4,0) || lpad(a.cli_ped_cgc_cli2,2,0) cnpjCpfCliente, "
        + " b.nome_cliente descCliente, "
        + " a.data_emis_venda dataEmissao, "
        + " a.data_entr_venda dataEmbarque, "
        + " c.live_agrup_tipo_cliente canal "       
        + " from pedi_100 a, pedi_010 b, pedi_085 c "
        + " where b.cgc_9 = a.cli_ped_cgc_cli9 "
        + " and b.cgc_4 = a.cli_ped_cgc_cli4 "
        + " and b.cgc_2 = a.cli_ped_cgc_cli2 "
        + " and c.tipo_cliente = b.tipo_cliente "
        + " and a.pedido_venda = ? " ;		
		
		try {
			pedidoEncontrado = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(PedidoVenda.class), pedidoVenda);
		} catch (Exception e) {
			pedidoEncontrado = findPedidoVendaAConfirmar(pedidoVenda);
		}
		
		return pedidoEncontrado; 		
	}	

	public List<ConteudoChaveNumerica> findPedidos(int searchPedido) {
		String query = " select p.pedido_venda value, p.pedido_venda label "
		+ " from pedi_100 p "
		+ " where p.situacao_venda <> 10 " 
	    + " and p.cod_cancelamento = 0 "
	    + " and p.pedido_venda like '%" + searchPedido + "%'"
	    + " and rownum <= 50 ";
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveNumerica> findNumerosInternos() {
		String query = " select p.numero_controle value, p.numero_controle label "
		+ " from pedi_100 p "
		+ " where p.situacao_venda <> 10 " 
		+ " and p.cod_cancelamento = 0 "
		+ " and p.numero_controle > 0 "
		+ " group by p.numero_controle ";  		
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	private PedidoVenda findPedidoVendaAConfirmar(int pedidoVenda) {			

		PedidoVenda pedidoEncontrado = null;
		
		String query = "select a.pedido_venda id, "
		+ " a.pedido_venda pedidoVenda, "
		+ " 0 periodo, "
		+ " 0 valor, "
		+ " lpad(a.cliente9,9,0) || lpad(a.cliente4,4,0) || lpad(a.cliente2,2,0) cnpjCpfCliente, "
		+ " b.nome_cliente descCliente, "
		+ " a.data_emis_venda dataEmissao, "
		+ " a.data_entrega dataEmbarque, "
		+ " c.live_agrup_tipo_cliente canal "
		+ " from inte_100 a, pedi_010 b, pedi_085 c "
		+ " where b.cgc_9 = a.cliente9 "
		+ " and b.cgc_4 = a.cliente4 "
		+ " and b.cgc_2 = a.cliente2 "
		+ " and c.tipo_cliente = b.tipo_cliente "
		+ " and a.pedido_venda = ? ";
	  
		try {
			pedidoEncontrado = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(PedidoVenda.class), pedidoVenda);
		} catch (Exception e) {
			pedidoEncontrado = null;
		}
		
		return pedidoEncontrado;		
	}	
	
	public int findSugestaoLocked(int codigoEmpresa) {

		Integer sugestao;

		String query = " select k.nr_sugestao from fatu_503 k "
				+ " where k.sugestao_libera = 1 "
				+ " and k.codigo_empresa = " + codigoEmpresa;

		try {
			sugestao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sugestao = 0;
		}
		return sugestao;
	}
	
	public void liberarSugestao(int numeroSugestao) {
		String query = " update fatu_503 "
				+ "set sugestao_libera = 0"
				+ "where nr_sugestao = " + numeroSugestao;
		jdbcTemplate.update(query);
	}
	
	public List<ConteudoChaveNumerica> findSituacoes() {
		List<ConteudoChaveNumerica> situacoes = new ArrayList<ConteudoChaveNumerica>();
		situacoes.add(new ConteudoChaveNumerica(0, "Liberado"));
		situacoes.add(new ConteudoChaveNumerica(5, "Suspenso"));
		situacoes.add(new ConteudoChaveNumerica(9, "Parcial"));
		situacoes.add(new ConteudoChaveNumerica(15, "Nota Fiscal Cancelada"));		
		return situacoes;
	}
}
