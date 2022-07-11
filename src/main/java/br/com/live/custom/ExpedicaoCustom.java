package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.CestoEndereco;
import br.com.live.model.ConsultaCaixasNoEndereco;
import br.com.live.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.model.ConsultaTag;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.DadosTagProd;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCesto;
import br.com.live.model.EnderecoCount;
import br.com.live.model.Produto;
import br.com.live.model.ProdutoEnderecar;

@Repository
public class ExpedicaoCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ExpedicaoCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<EnderecoCount> findReferenciaEnd(int codDeposito) {
		List<EnderecoCount> dadosEnd;

		String query = " select a.grupo || '.' || a.subgrupo || '.' || a.item referencia, a.endereco from estq_110 a "
				+ " where a.nivel <> '0' "
				+ " and a.grupo <> '00000' "
				+ " and a.subgrupo <> '000' "
				+ " and a.item <> '000000' "
				+ " and a.deposito = " + codDeposito;
		
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
		
		String query = " select a.grupo, a.subgrupo, a.item, a.endereco, b.colecao || ' - ' || c.descr_colecao colecao, nvl(d.qtde_estoque_atu, 0) saldo, min(nvl(e.grupo_embarque, 0)) embarque from estq_110 a, basi_030 b, basi_140 c, estq_040 d, basi_590 e "
				+ " where a.deposito = " + deposito
				+ " and a.endereco = '" + endereco + "'"
				+ " and b.nivel_estrutura = '1' "
				+ " and b.referencia = a.grupo "
				+ " and c.colecao = b.colecao "
				+ " and d.cditem_nivel99 (+) = '1' "
				+ " and d.cditem_grupo (+) = a.grupo "
				+ " and d.cditem_subgrupo (+) = a.subgrupo "
				+ " and d.cditem_item (+) = a.item "
				+ " and d.deposito (+) = a.deposito "
				+ " and e.nivel (+) = '1' "
				+ " and e.grupo (+) = a.grupo "
				+ " and e.subgrupo (+) = a.subgrupo "
				+ " and e.item (+) = a.item "
				+ " group by a.endereco, a.grupo, a.subgrupo, a.item, b.colecao, c.descr_colecao, d.qtde_estoque_atu ";
		
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
	
	public int validarExistePecaCaixa(int numeroCaixa) {
		int temProduto = 0;
		
		String query = " select nvl(max(1),0) from orion_131 v "
				+ " where v.numero_caixa = " + numeroCaixa;
		
		try {
			temProduto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			temProduto = 0;
		}
				
		return temProduto;
	}
	
	public void cleanEnderecos(int deposito) {
		String query = " delete from estq_110 "
				+ " where estq_110.deposito = ?";
		
		jdbcTemplate.update(query, deposito);
	}
	
	public void inserirEnderecosDeposito(int deposito, String endereco, String nivel, String grupo, String subGrupo, String item) {
		String query = " insert into estq_110 "
		+ " values (?,?,?,?,?,?)";
		
		jdbcTemplate.update(query, nivel,grupo, subGrupo, item, deposito, endereco);
	}
	
	public void updateEnderecosDeposito(int deposito, String endereco, String nivel, String grupo, String subGrupo, String item) {
		String query = " update estq_110 "
				+ " set nivel = ?, grupo = ?, subgrupo = ?, item = ? "
				+ " where estq_110.deposito = ? "
				+ " and estq_110.endereco = ? ";
		
		jdbcTemplate.update(query, nivel,grupo, subGrupo, item, deposito, endereco);
	}
	
	public List<ConsultaCapacidadeArtigosEnderecos> findArtigosEnderecos() {
		String query = " select a.artigo, a.descr_artigo descricao, nvl(b.quant_pecas_cesto, 0) quantPecCesto, nvl(b.quant_perc_0, 0) perc0, nvl(b.quant_perc_1, 0) perc1, nvl(b.quant_perc_40, 0) perc40, nvl(b.quant_perc_41, 0) perc41, nvl(b.quant_perc_94, 0) perc94, nvl(b.quant_perc_95, 0) perc95, nvl(b.quant_perc_99, 0) perc99 from basi_290 a, orion_120 b "
				+ " where b.artigo (+) = a.artigo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaCapacidadeArtigosEnderecos.class));
	}
	
	public int validarCaixaAberta(int codUsuario) {
		int numeroCaixa = 0;
		
		String query = " select a.numero_caixa numeroCaixa from orion_130 a "
				+ " where a.situacao_caixa = 0 "
				+ " and a.usuario = " + codUsuario;
	
		try {
			numeroCaixa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			numeroCaixa = 0;
		}
		return numeroCaixa; 
	}
	
	public List<DadosTagProd> findDadosTagCaixas(int codCaixa) {
		String query = " select p.periodo_producao periodo, p.ordem_producao ordem, p.ordem_confeccao pacote, p.sequencia from orion_131 p "
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
	    + " group by a.numero_caixa, b.nivel, b.grupo, b.subgrupo, b.item, b.sequencia, b.deposito, b.endereco) tagsEnderecar, basi_220 tam "  
	    + " where tagsEnderecar.numero_caixa = " + codCaixa
	    + " and tam.tamanho_ref = tagsEnderecar.subgrupo "
	    + " group by tagsEnderecar.numero_caixa, tagsEnderecar.nivel, tagsEnderecar.grupo, tagsEnderecar.subgrupo, tagsEnderecar.item, tam.ordem_tamanho, tagsEnderecar.deposito " 
	    + " order by tagsEnderecar.numero_caixa, tagsEnderecar.nivel, tagsEnderecar.grupo, tagsEnderecar.item, tam.ordem_tamanho, tagsEnderecar.deposito ";       

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
			
			System.out.println(e.getMessage());
			
			cesto = null;
		}
		
		return cesto; 
	}
	
	public List<Produto> ordenarReferencias(String referencias) {
		List<Produto> produtos;
		
		String query = " select h.grupo_estrutura grupo, h.subgru_estrutura sub, h.item_estrutura item, h.grupo_estrutura || '.' || h.subgru_estrutura || '.' || h.item_estrutura referencia, h.grupo_estrutura || '.' || h.item_estrutura || '.' || h.subgru_estrutura id from basi_010 h, basi_220 p "
				+ " where h.grupo_estrutura || '.' || h.item_estrutura || '.' || h.subgru_estrutura in (" + referencias + ")"
				+ " and h.subgru_estrutura = p.tamanho_ref "
				+ " order by h.grupo_estrutura, h.item_estrutura, p.ordem_tamanho ";
		
		try {
			produtos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			produtos = new ArrayList<Produto>();
		}
		
		return produtos;
	}
	
	public String findEnderecoVazio(int deposito, String bloco, int corredor) {
		String enderecoLivre = "";
		
		String query = " select min(j.endereco) from estq_110 j "
				+ " where j.deposito = " + deposito
				+ " and j.endereco like '" + bloco + String.format("%02d", corredor) + "%' "
				+ " and j.nivel = '0' "
				+ " and j.grupo = '00000' "
				+ " and j.subgrupo = '000' "
				+ " and j.item = '000000' "
				+ " order by j.endereco ";

		try {
			enderecoLivre = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			enderecoLivre = null;
		}
		
		return enderecoLivre;
	}
	
	public int validaProdutoEnderecado(int deposito, String grupo, String subGrupo, String item) {
		int existeProd = 0;
		
		String query = " select 1 from estq_110 a "
				+ " and j.nivel = '1' "
				+ " and j.grupo = " + grupo
				+ " and j.subgrupo = " + subGrupo
				+ " and j.item = " + item;
		try {
			existeProd = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existeProd = 0;
		}
		
		return existeProd;
	}
	
	public List<EnderecoCesto> findCestosLivres(int deposito, String bloco, int corredor, int box, int ordenacao) {
		List<EnderecoCesto> enderecos = null;
		
		String query = " select blocos.cesto endereco from "
				+ " ( "
				+ "  select substr(j.endereco,0,8) cesto from estq_110 j "
				+ "  where j.deposito = " + deposito
				+ "  and j.endereco like '" + bloco + String.format("%02d", corredor) + String.format("%02d", box) + "%'" 
				+ "  and j.nivel = '0' "
				+ " ) blocos "
				+ " group by blocos.cesto ";
				
				if (ordenacao == 0) {
					query = query + " order by blocos.cesto desc ";
				} else {
					query = query + " order by blocos.cesto ";
				}
		try {
			enderecos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(EnderecoCesto.class));
		} catch (Exception e) {
			enderecos = new ArrayList<EnderecoCesto>();
		}
		
		return enderecos;
	}
	
	public List<ConsultaCaixasNoEndereco> findCaixas(String endereco){
		
		List<ConsultaCaixasNoEndereco> caixas = null;
		
		String query = " select p.numero_caixa numeroCaixa, l.periodo_producao periodo, l.ordem_producao ordemProducao, l.ordem_confeccao pacote, t.proconf_nivel99 || '.' || t.proconf_grupo || '.' || t.proconf_subgrupo || '.' || t.proconf_item sku from orion_130 p, orion_131 l, pcpc_040 t "
				+ " where p.endereco = '" + endereco + "' "
				+ " and l.numero_caixa = p.numero_caixa "
				+ " and t.periodo_producao = l.periodo_producao "
				+ " and t.ordem_confeccao = l.ordem_confeccao "
				+ " and t.ordem_producao = l.ordem_producao "
				+ " group by p.numero_caixa, l.periodo_producao, l.ordem_producao, l.ordem_confeccao, t.proconf_nivel99, t.proconf_grupo, t.proconf_subgrupo, t.proconf_item ";
		try {
			caixas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaCaixasNoEndereco.class));
		} catch (Exception e) {
			caixas = new ArrayList<ConsultaCaixasNoEndereco>();
		}
		
		return caixas;
	}
	
	public List<ConsultaCaixasNoEndereco> verificaCaixasNoEndereco() {
		List<ConsultaCaixasNoEndereco> caixas = null;
		
		String query = " select l.endereco endereco, count(*) quantidade from orion_130 l "
				+ " group by l.endereco ";
		
		try {
			caixas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaCaixasNoEndereco.class));
		} catch (Exception e) {
			caixas = new ArrayList<ConsultaCaixasNoEndereco>();
		}
		
		return caixas;
	}

	public List<DadosTagProd> obterTagsLidosCaixa(int numeroCaixa) {
		List<DadosTagProd> listTags = new ArrayList<DadosTagProd>();
		
		String query = " select v.periodo_producao periodo, v.ordem_producao ordem, v.ordem_confeccao pacote, v.sequencia from orion_131 v "
				+ " where v.numero_caixa = " + numeroCaixa;
		
		try {
			listTags = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosTagProd.class));
		} catch (Exception e) {
			listTags = new ArrayList<DadosTagProd>();
		}
		
		return listTags;
	}
	
	public void limparCaixa(int numeroCaixa) {
		String query = " delete from orion_131 a "
				+ " where a.numero_caixa = ? ";
		jdbcTemplate.update(query, numeroCaixa);
	}
	
	public void limparTagLidoCaixa(String numeroTag) {
		String query = " delete from orion_131 a "
				+ " where a.numero_tag = ? ";
		jdbcTemplate.update(query, numeroTag);
	}
	
	public List<ConsultaTag> obterEnderecos(int deposito, String nivel, String grupo, String subGrupo, String item) {
		String query = " SELECT a.endereco, count(*) quantEndereco FROM pcpc_330 a, estq_040 b "
				+ " WHERE a.nivel = b.cditem_nivel99 "
				+ " AND a.grupo = b.cditem_grupo "
				+ " AND a.subgrupo = b.cditem_subgrupo "
				+ " AND a.item = b.cditem_item "
				+ " AND a.deposito = b.deposito "
				+ " and a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.subgrupo = '" + subGrupo + "' "
				+ " and a.item = '" + item + "' "
				+ " and a.deposito = " + deposito
				+ " AND a.estoque_tag = 1 "
				+ " and a.endereco is not null "
				+ " group by a.endereco "
				+ " order by a.endereco ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
	}
	
	public int obterQuantidadeEndereco(String endereco, String nivel, String grupo, String subGrupo, String item, int deposito) {
		String query = " select count(*) quantEndereco from pcpc_330 b "
				+ " where b.endereco = '" + endereco + "' "
				+ " and b.estoque_tag = 1 "
				+ " and b.nivel = '" + nivel + "' "
				+ " and b.grupo = '" + grupo + "' "
				+ " and b.subGrupo = '" + subGrupo + "' "
				+ " and b.item = '" + item + "' "
				+ " and b.deposito = " + deposito;
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public List<ConsultaTag> findHistoricoTag(int periodo, int ordemProducao, int ordemConfeccao, int sequencia) {
		String query = " select rownum id, trunc(b.data_operacao) data, to_char(b.data_operacao, 'HH:MI') hora, b.data_operacao, b.transacao_ent_new transacao, c.descricao descTransacao, b.deposito_new deposito, d.descricao descDeposito, "
				+ " b.usuario_estq usuario, b.usuario_exped coletor from pcpc_330_log b, estq_005 c, basi_205 d "
				+ " where b.periodo_producao = " + periodo
				+ " and b.ordem_producao = " + ordemProducao
				+ " and b.ordem_confeccao = " + ordemConfeccao
				+ " and b.sequencia = " + sequencia
				+ " and c.codigo_transacao = b.transacao_ent_new "
				+ " and d.codigo_deposito = b.deposito_new "
				+ " order by b.data_operacao desc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
	}
	
	public String findProdutoByTag(int periodo, int ordemProducao, int ordemConfeccao, int sequencia) {
		String query = " select a.nivel || '.' || a.grupo || '.' || a.subgrupo || '.' || a.item produto from pcpc_330_log a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProducao
				+ " and a.ordem_confeccao = " + ordemConfeccao
				+ " and a.sequencia = " + sequencia
				+ " group by a.nivel, a.grupo, a.subgrupo, a.item ";
		return jdbcTemplate.queryForObject(query, String.class);
	}
}