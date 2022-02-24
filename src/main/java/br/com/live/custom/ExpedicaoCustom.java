package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CestoEndereco;
import br.com.live.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.DadosTagProd;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;
import br.com.live.model.ProdutoEnderecar;

@Repository
public class ExpedicaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ExpedicaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<EnderecoCount> findReferenciaEnd(int codDeposito) {
		List<EnderecoCount> dadosEnd;

		String query = " select count(*) totalPecas, a.grupo || '.' || a.subgrupo || '.' || a.item referencia, a.endereco from pcpc_330 a "
				+ " where a.deposito = " + codDeposito
				+ " and a.estoque_tag = 1 "
				+ " group by a.endereco, a.grupo, a.subgrupo, a.item, a.endereco ";
		
		try {
			dadosEnd = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EnderecoCount.class));
		} catch (Exception e) {
			dadosEnd = new ArrayList<EnderecoCount>();
		}
		
		return dadosEnd;
	}
	
	public Embarque findEmbarque(int periodo, int ordem, int pacote) {
		Embarque dadosEmbarque;
		
		String query = " select a.proconf_grupo grupo, a.proconf_subgrupo subgrupo, a.proconf_item item, b.narrativa descricao, SUBSTR(min(c.grupo_embarque),LENGTH(min(c.grupo_embarque))-1,2) grupoEmbarque, min(c.data_entrega) dataEmbarque, 1 existeEmbarque from pcpc_040 a, basi_010 b, basi_590 c "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordem
				+ " and a.ordem_confeccao = " + pacote
				+ " and b.nivel_estrutura = '1' "
				+ " and b.grupo_estrutura = a.proconf_grupo "
				+ " and b.subgru_estrutura = a.proconf_subgrupo "
				+ " and b.item_estrutura = a.proconf_item "
				+ " and c.nivel = '1' "
				+ " and c.grupo = a.proconf_grupo "
				+ " and c.subgrupo = a.proconf_subgrupo "
				+ " and c.item = a.proconf_item "
				+ " group by a.proconf_grupo, a.proconf_subgrupo, a.proconf_item, b.narrativa, c.grupo_embarque, c.data_entrega ";
		
		try {
			dadosEmbarque = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Embarque.class));
		} catch (Exception e) {
			dadosEmbarque = new Embarque();
		}
		return dadosEmbarque;
	}
	
	public DadosModalEndereco findDadosModalEndereco(int deposito, String endereco) {
		DadosModalEndereco dadosModal;
		
		String query = " select a.grupo, a.subgrupo, a.item, a.endereco, b.colecao || ' - ' || c.descr_colecao colecao, d.qtde_estoque_atu saldo, nvl(e.grupo_embarque, 0) embarque from pcpc_330 a, basi_030 b, basi_140 c, estq_040 d, basi_590 e "
				+ " where a.deposito = " + deposito
				+ " and a.estoque_tag = 1 "
				+ " and a.endereco = '" + endereco + "'"
				+ " and b.nivel_estrutura = '1' "
				+ " and b.referencia = a.grupo "
				+ " and c.colecao = b.colecao "
				+ " and d.cditem_nivel99 = '1' "
				+ " and d.cditem_grupo = a.grupo "
				+ " and d.cditem_subgrupo = a.subgrupo "
				+ " and d.cditem_item = a.item "
				+ " and d.deposito = " + deposito
				+ " and e.nivel (+) = '1' "
				+ " and e.grupo (+) = a.grupo "
				+ " and e.subgrupo (+) = a.subgrupo "
				+ " and e.item (+) = a.item "
				+ " group by a.endereco, a.grupo, a.subgrupo, a.item, b.colecao, c.descr_colecao, d.qtde_estoque_atu, e.grupo_embarque ";
		
		try {
			dadosModal = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(DadosModalEndereco.class));
		} catch (Exception e) {
			dadosModal = new DadosModalEndereco();
		}
		return dadosModal;
	}
	
	public void gravarEnderecos(int periodo, int ordemProducao, int ordemConfeccao, int sequencia, String endereco) {
		String queryUpdate = " update pcpc_330 "
				+ " set endereco = '" + endereco + "'"
				+ " where pcpc_330.periodo_producao = " + periodo
				+ " and pcpc_330.ordem_producao = " + ordemProducao
				+ " and pcpc_330.ordem_confeccao = " + ordemConfeccao
				+ " and pcpc_330.sequencia = " + sequencia;

		jdbcTemplate.update(queryUpdate);
	}
	
	public String validarGravacaoEndereco(int periodo, int ordemProducao, int ordemConfeccao, int sequencia) {
		String endereco = "";
		
		String query = " select nvl(a.endereco, '') from pcpc_330 a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProducao
				+ " and a.ordem_confeccao = " + ordemConfeccao
				+ " and a.sequencia = " + sequencia;
		
		try {
			endereco = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			endereco = "";
		}
		return endereco;
	}
	
	public int validarPecaEmEstoque(int periodo, int ordemProducao, int ordemConfeccao, int sequencia) {
		int flagEstoque = 0;
		
		String query = " select 1 from pcpc_330 a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProducao
				+ " and a.ordem_confeccao = " + ordemConfeccao
				+ " and a.sequencia = " + sequencia
				+ " and a.estoque_tag = 4 ";
	
		try {
			flagEstoque = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			flagEstoque = 0;
		}
		return flagEstoque; 
	}
	
	public void cleanEnderecos(int deposito) {
		String query = " delete from estq_110 "
				+ " where estq_110.deposito = ?";
		
		jdbcTemplate.update(query, deposito);
	}
	
	public void inserirEnderecosDeposito(int deposito, String endereco) {
		String query = " insert into estq_110 "
		+ " values (?,?,?,?,?,?)";
		
		jdbcTemplate.update(query, 0,00000, 000, 000000, deposito, endereco);
	}
	
	public List<ConsultaCapacidadeArtigosEnderecos> findArtigosEnderecos() {
		String query = " select a.artigo, a.descr_artigo descricao, nvl(b.quant_pecas_cesto, 0) quantPecCesto, nvl(b.quant_perc_0, 0) perc0, nvl(b.quant_perc_1, 0) perc1, nvl(b.quant_perc_40, 0) perc40, nvl(b.quant_perc_41, 0) perc41, nvl(b.quant_perc_94, 0) perc94, nvl(b.quant_perc_95, 0) perc95, nvl(b.quant_perc_99, 0) perc99 from basi_290 a, orion_120 b "
				+ " where b.artigo (+) = a.artigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaCapacidadeArtigosEnderecos.class));
	}
	
	public int validarCaixaAberta() {
		int numeroCaixa = 0;
		
		String query = " select a.numero_caixa numeroCaixa from orion_130 a "
				+ " where a.situacao_caixa = 0 ";
	
		try {
			numeroCaixa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			numeroCaixa = 0;
		}
		return numeroCaixa; 
	}
	
	public List<DadosTagProd> findDadosTagCaixas(int codCaixa) {
		String query = " select p.periodo_producao periodo, p.ordem_producao ordem, p.ordem_cofeccao pacote, p.sequencia from orion_131 p "
				+ "where p.numero_caixa = " + codCaixa;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosTagProd.class));
	}
	
	public void atualizarSituacaoEndereco(int periodo, int ordem, int pacote, int sequencia) {
		String query = " update pcpc_330 "
				+ " set endereco = 'ENDERECAR' "
				+ " where pcpc_330.periodo_producao = ? "
				+ " and pcpc_330.ordem_producao = ? "
				+ " and pcpc_330.ordem_confeccao = ? "
				+ " and pcpc_330.sequencia = ? ";
		
		jdbcTemplate.update(query, periodo,ordem, pacote, sequencia);
	}
	
	public List<ProdutoEnderecar> findProdutosEnderecar(int codCaixa) {
		
		List<ProdutoEnderecar> produtos = null;
		
		String query = " select tagsEnderecar.numero_caixa caixa, " 
	    + " tagsEnderecar.nivel, " 
	    + " tagsEnderecar.grupo referencia, " 
        + " tagsEnderecar.subgrupo tamanho, "
	    + " tagsEnderecar.item cor, "
	    + " tagsEnderecar.deposito, "
	    + " count(*) qtdeEnderecar, "
	    + " count(*) - sum(tagsEnderecar.enderecar) qtdeEnderecada "
	    + " from (select a.numero_caixa, b.nivel, b.grupo, b.subgrupo, b.item, b.sequencia, b.deposito, b.endereco, decode(b.endereco,'ENDERECAR',1,0) enderecar "
	    + " from orion_131 a, pcpc_330 b "
	    + " where b.periodo_producao = a.periodo_producao "
	    + " and b.ordem_producao = a.ordem_producao "
	    + " and b.ordem_confeccao = a.ordem_cofeccao "
	    + " and b.sequencia = a.sequencia "
	    + " group by a.numero_caixa, b.nivel, b.grupo, b.subgrupo, b.item, b.sequencia, b.deposito, b.endereco) tagsEnderecar "  
	    + " where tagsEnderecar.numero_caixa = " + codCaixa
	    + " group by tagsEnderecar.numero_caixa, tagsEnderecar.nivel, tagsEnderecar.grupo, tagsEnderecar.subgrupo, tagsEnderecar.item, tagsEnderecar.deposito " ;       

		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProdutoEnderecar.class));
		} catch (Exception e) {
			produtos = new ArrayList<ProdutoEnderecar>();
		}
				
		return produtos;
	}
	
	public CestoEndereco findEnderecoCesto(String nivel, String referencia, String tamanho, String cor, int deposito) {
		
		CestoEndereco cesto;
		
		String query = " select enderecos.endereco, enderecos.qtde_capacidade qtdeCapacidade, enderecos.qtde_ocupada qtdeOcupada " 
		+ " from (select m.nivel, m.grupo, m.subgrupo, m.item, m.deposito, m.endereco, "
		+ " (select p.quant_pecas_cesto "
		+ " from basi_030 o, orion_120 p "
		+ " where o.nivel_estrutura = m.nivel "
		+ " and o.referencia = m.grupo "
		+ " and p.artigo = o.artigo) qtde_capacidade, "
		+ " (select count(*) "
		+ " from pcpc_330 n "
		+ " where n.estoque_tag = 1 "
		+ " and n.endereco = m.endereco) qtde_ocupada "
		+ " from estq_110 m) enderecos "
		+ " where enderecos.nivel = '" + nivel + "'" 
		+ " and enderecos.grupo = '" + referencia + "'"
		+ " and enderecos.subgrupo = '" + tamanho + "'"
		+ " and enderecos.item = '" + cor + "'"
		+ " and enderecos.deposito = " + deposito;

		try {
			cesto = jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(CestoEndereco.class));
		} catch (Exception e) {
			cesto = null;
		}
		
		return cesto; 
	}
}
