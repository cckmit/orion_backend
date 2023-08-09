package br.com.live.comercial.custom;

import java.util.ArrayList;
import java.util.List;

import br.com.live.comercial.model.CaixasEsteira;
import br.com.live.comercial.model.CapacidadesArtigoEndereco;
import br.com.live.comercial.model.CestoEndereco;
import br.com.live.comercial.model.ConsultaCaixasNoEndereco;
import br.com.live.comercial.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.comercial.model.ConsultaHistAuditoria;
import br.com.live.comercial.model.ConsultaMinutaTransporte;
import br.com.live.comercial.model.ConsultaNotasTagsDevolucao;
import br.com.live.comercial.model.ConsultaRegraPrioridadeTipoCliente;
import br.com.live.comercial.model.ConsultaTag;
import br.com.live.comercial.model.ConsultaTagsEReferenciasMapa;
import br.com.live.comercial.model.ConsultaTransportadora;
import br.com.live.comercial.model.ConsultaVariacaoArtigo;
import br.com.live.comercial.model.DadosModalEndereco;
import br.com.live.comercial.model.EnderecoCesto;
import br.com.live.comercial.model.EnderecoCount;
import br.com.live.comercial.model.ProdutoEnderecar;
import br.com.live.comercial.model.VolumeMinuta;
import br.com.live.producao.model.DadosTagProd;
import br.com.live.produto.model.Embarque;
import br.com.live.produto.model.Produto;
import br.com.live.sistema.entity.Usuario;
import br.com.live.sistema.repository.UsuarioRepository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

import javax.management.Query;
import javax.print.DocFlavor;

@Repository
public class ExpedicaoCustom {

	private JdbcTemplate jdbcTemplate;
	private UsuarioRepository usuarioRepository;

	public ExpedicaoCustom(JdbcTemplate jdbcTemplate, UsuarioRepository usuarioRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.usuarioRepository = usuarioRepository;
	}

	public List<EnderecoCount> findReferenciaEnd(int codDeposito) {
		List<EnderecoCount> dadosEnd;

		String query = " select mapa.referencia, mapa.endereco, mapa.totalPecas, " +
				" CASE " +
				"  WHEN (mapa.totalPecas = 0 and mapa.totalPecas = d.quant_perc_0) THEN 1 " +
				"  WHEN (mapa.totalPecas >= d.quant_perc_1 and mapa.totalPecas <= d.quant_perc_40 and mapa.totalPecas > 0) THEN 2 " +
				"  WHEN (mapa.totalPecas >= d.quant_perc_41 and mapa.totalPecas <= d.quant_perc_94 and mapa.totalPecas > 0) THEN 3 " +
				"  WHEN ((mapa.totalPecas >= d.quant_perc_95 and mapa.totalPecas <= d.quant_perc_99) or (mapa.totalPecas > d.quant_perc_99 and d.quant_perc_99 > 0)) THEN 4 " +
				"    ELSE 1 " +
				"    END AS flagCestoCheio " +
				" from ( " +
				" select a.grupo, a.grupo || '.' || a.subgrupo || '.' || a.item referencia, a.endereco, NVL((select count(*) from pcpc_330 b " +
				"                                                                                     where b.endereco = a.endereco " +
				"                                                                                            and b.deposito = " + codDeposito +
				"                                                                                            and b.grupo = a.grupo " +
				"                                                                                            and b.subgrupo = a.subgrupo " +
				"                                                                                             and b.item = a.item),0) totalPecas " +
				" from estq_110 a " +
				" where a.nivel <> '0' " +
				" and a.grupo <> '00000' " +
				" and a.subgrupo <> '000' " +
				" and a.item <> '000000' " +
				" and a.deposito = " + codDeposito + ") mapa, basi_030 c, orion_120 d " +
				" where c.nivel_estrutura = '1' " +
				" and c.referencia = mapa.grupo " +
				" and d.artigo = c.artigo ";

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

		String query = " select a.grupo, a.subgrupo, a.item, a.endereco, b.colecao || ' - ' || c.descr_colecao colecao, nvl(d.qtde_estoque_atu, 0) saldo, min(nvl(e.grupo_embarque, 0)) embarque,"
				+ " (select count(*) from pcpc_330 w "
				+ " where w.deposito = " + deposito
				+ " and w.endereco = '" + endereco + "') quantEndereco "
				+ " from estq_110 a, basi_030 b, basi_140 c, estq_040 d, basi_590 e "
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
			dadosModal = jdbcTemplate.queryForObject(query,
					BeanPropertyRowMapper.newInstance(DadosModalEndereco.class));
		} catch (Exception e) {
			dadosModal = new DadosModalEndereco();
		}
		return dadosModal;
	}

	public void gravarEnderecos(int periodo, int ordemProducao, int ordemConfeccao, int sequencia, String endereco,
			String usuarioSystextil) {
		String queryUpdate = " update pcpc_330 "
				+ " set endereco = '" + endereco + "', live_user_endereco = '" + usuarioSystextil + "' "
				+ " where pcpc_330.periodo_producao = " + periodo
				+ " and pcpc_330.ordem_producao = " + ordemProducao
				+ " and pcpc_330.ordem_confeccao = " + ordemConfeccao
				+ " and pcpc_330.sequencia = " + sequencia;

		jdbcTemplate.update(queryUpdate);
	}
	
	public int validarEnderecoCorreto(int periodo, int ordemProducao, int ordermConfeccao, int sequencia, String endereco) {
		int enderecoCorreto = 0;
		
		String query = " select 1 from pcpc_330 c, estq_110 d "
				+ " where c.periodo_producao = " + periodo
				+ " and c.ordem_producao = " + ordemProducao
				+ " and c.ordem_confeccao = " + ordermConfeccao
				+ " and c.sequencia = " + sequencia
				+ " and d.deposito = 4 "
				+ " and d.nivel = c.nivel "
				+ " and d.grupo = c.grupo "
				+ " and d.subgrupo = c.subgrupo "
				+ " and d.item = c.item "
				+ " and d.endereco = '" + endereco + "'";
		
		try {
			enderecoCorreto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			enderecoCorreto = 0;
		}
		return enderecoCorreto;
	}
	
	public int validarSeExistemEnderecosParaTag(int periodo, int ordemProducao, int ordermConfeccao, int sequencia) {
		int enderecoCorreto = 0;
		
		String query = " select count(*) from pcpc_330 c, estq_110 d "
				+ " where c.periodo_producao = " + periodo
				+ " and c.ordem_producao = " + ordemProducao
				+ " and c.ordem_confeccao = " + ordermConfeccao
				+ " and c.sequencia = " + sequencia
				+ " and d.deposito = 4 "
				+ " and d.nivel = c.nivel "
				+ " and d.grupo = c.grupo "
				+ " and d.subgrupo = c.subgrupo "
				+ " and d.item = c.item ";
		
		try {
			enderecoCorreto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			enderecoCorreto = 0;
		}
		return enderecoCorreto;
	}

	public int verificarPecasNoEndereco(String endereco) {
		int quantEndereco = 0;

		String query = " select count(*) from estq_110 a " +
				" where a.endereco = '" + endereco + "'" +
				" and a.deposito = 4 ";
		try {
			quantEndereco = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			quantEndereco = 0;
		}
		return quantEndereco;
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

	public int findNextIdVariacao() {
		int nextId = 0;

		String query = " select nvl((max(b.id)),0)+1 from orion_exp_001 b ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public int validarPecaEmEstoque(int periodo, int ordemProducao, int ordemConfeccao, int sequencia) {
		int flagEstoque = 0;

		String query = " select 1 from pcpc_330 a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProducao
				+ " and a.ordem_confeccao = " + ordemConfeccao
				+ " and a.sequencia = " + sequencia
				+ " and a.estoque_tag <> 1 ";

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
	@Deprecated
	public void cleanEnderecos(int deposito) {
		String query = " delete from estq_110 "
				+ " where estq_110.deposito = ?";

		jdbcTemplate.update(query, deposito);
	}

	public void inserirEnderecosDeposito(int deposito, String endereco, String nivel, String grupo, String subGrupo,
			String item) {
		String query = " insert into estq_110 "
				+ " values (?,?,?,?,?,?)";

		jdbcTemplate.update(query, nivel, grupo, subGrupo, item, deposito, endereco);
	}

	public void updateEnderecosDeposito(int deposito, String endereco, String nivel, String grupo, String subGrupo,
			String item) {
		String query = " update estq_110 "
				+ " set nivel = ?, grupo = ?, subgrupo = ?, item = ? "
				+ " where estq_110.deposito = ? "
				+ " and estq_110.endereco = ? ";

		jdbcTemplate.update(query, nivel, grupo, subGrupo, item, deposito, endereco);
	}

	public List<ConsultaCapacidadeArtigosEnderecos> findArtigosEnderecos() {
		String query = " select a.artigo, a.descr_artigo descricao, nvl(b.quant_pecas_cesto, 0) quantPecCesto, " +
				" nvl(b.quant_perc_0, 0) perc0, nvl(b.quant_perc_1, 0) perc1, nvl(b.quant_perc_40, 0) perc40, nvl(b.quant_perc_41, 0) perc41, " +
				" nvl(b.quant_perc_94, 0) perc94, nvl(b.quant_perc_95, 0) perc95, nvl(b.quant_perc_99, 0) perc99 from basi_290 a, orion_120 b "
				+ " where b.artigo (+) = a.artigo ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaCapacidadeArtigosEnderecos.class));
	}

	public List<ConsultaCapacidadeArtigosEnderecos> findArtigosByListArtigos (List<ConteudoChaveNumerica> artigos) {
		String query = " select a.artigo, a.descr_artigo descricao, nvl(b.quant_pecas_cesto, 0) quantPecCesto, " +
				" nvl(b.quant_perc_0, 0) perc0, nvl(b.quant_perc_1, 0) perc1, nvl(b.quant_perc_40, 0) perc40, nvl(b.quant_perc_41, 0) perc41, " +
				" nvl(b.quant_perc_94, 0) perc94, nvl(b.quant_perc_95, 0) perc95, nvl(b.quant_perc_99, 0) perc99 from basi_290 a, orion_120 b "
				+ " where b.artigo (+) = a.artigo ";

				if (artigos.size() > 0) {
					query += " and b.artigo in (" + ConteudoChaveNumerica.parseValueToString(artigos) + ")";
				}

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
		String query = " select p.periodo_producao periodo, p.ordem_producao ordem, p.ordem_confeccao pacote, p.sequencia,"
				+ " p.periodo_producao || LPAD(p.ordem_producao, 9, 0) || LPAD(p.ordem_confeccao, 5, 0) || LPAD(p.sequencia, 4, 0) numeroTag from orion_131 p "
				+ " where p.numero_caixa = " + codCaixa;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosTagProd.class));
	}

	public void atualizarSituacaoEndereco(int periodo, int ordem, int pacote, int sequencia) {
		String query = " update pcpc_330 "
				+ " set endereco = 'ENDERECAR' "
				+ " where pcpc_330.periodo_producao = ? "
				+ " and pcpc_330.ordem_producao = ? "
				+ " and pcpc_330.ordem_confeccao = ? "
				+ " and pcpc_330.sequencia = ? ";

		jdbcTemplate.update(query, periodo, ordem, pacote, sequencia);
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
				+ " where h.grupo_estrutura || '.' || h.item_estrutura || '.' || h.subgru_estrutura in (" + referencias
				+ ")"
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
				+ "  and j.endereco like '" + bloco + String.format("%02d", corredor) + String.format("%02d", box)
				+ "%'"
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

	public List<ConsultaCaixasNoEndereco> findCaixas(String endereco) {

		List<ConsultaCaixasNoEndereco> caixas = null;

		String query = " SELECT p.numero_caixa numeroCaixa, "
				+ "		l.periodo_producao periodo, "
				+ "		l.ordem_producao ordemProducao, "
				+ "		l.ordem_confeccao pacote, "
				+ "		t.proconf_nivel99 || '.' || t.proconf_grupo || '.' || t.proconf_subgrupo || '.' || t.proconf_item sku "
				+ "   FROM orion_130 p, orion_131 l, pcpc_040 t "
				+ "   WHERE p.endereco = '" + endereco + "' "
				+ "   AND l.numero_caixa = p.numero_caixa "
				+ "   AND t.periodo_producao = l.periodo_producao "
				+ "   AND t.ordem_confeccao = l.ordem_confeccao "
				+ "   AND t.ordem_producao = l.ordem_producao "
				+ "   GROUP BY p.numero_caixa, l.periodo_producao, l.ordem_producao, l.ordem_confeccao, t.proconf_nivel99, "
				+ "            t.proconf_grupo, t.proconf_subgrupo, t.proconf_item ";
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
		String query = " SELECT c.endereco, count(*) quantEndereco FROM pcpc_330 a, estq_040 b, estq_110 c "
				+ " WHERE a.nivel = b.cditem_nivel99 "
				+ " and a.grupo = b.cditem_grupo "
				+ " and a.subgrupo = b.cditem_subgrupo "
				+ " and a.item = b.cditem_item "
				+ " and a.deposito = b.deposito "
				+ " and a.nivel = '" + nivel + "' "
				+ " and a.grupo = '" + grupo + "' "
				+ " and a.subgrupo = '" + subGrupo + "' "
				+ " and a.item = '" + item + "' "
				+ " and a.deposito = " + deposito
				+ " and a.estoque_tag = 1 "
				+ " and a.endereco is not null "
				+ " and a.nivel = c.nivel "
				+ " and a.grupo = c.grupo "
				+ " and a.subgrupo = c.subgrupo "
				+ " and a.item = c.item "
				+ " and a.deposito = c.deposito "
				+ " and a.endereco = c.endereco "
				+ " group by c.endereco "
				+ " order by c.endereco ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
	}

	public int obterQuantidadeEndereco(String endereco, String nivel, String grupo, String subGrupo, String item,
			int deposito) {
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
				+ " b.usuario_estq usuario, b.usuario_exped coletor, b.pedido_venda_new pedido, b.estoque_tag_new situacao, b.nome_programa programa, b.usuario_rede usuarioRede from pcpc_330_log b, estq_005 c, basi_205 d "
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

	public List<ConsultaVariacaoArtigo> findVariacaoArtigo() {
		String query = " select a.artigo id, a.descr_artigo descricao, nvl(b.variacao, 0) variacao from basi_290 a, orion_exp_001 b "
				+ " where b.id (+)= a.artigo "
				+ " order by a.artigo asc ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaVariacaoArtigo.class));
	}

	public List<ConteudoChaveAlfaNum> findAllTranspAsync(String leitor) {
		String query = " select c.fornecedor9 || '.' || c.fornecedor4 || '.' || c.fornecedor2 value, c.fornecedor9 || '/' || LPAD(c.fornecedor4,4,0) || '-' || c.fornecedor2 || ' - ' || c.nome_fornecedor label from supr_010 c "
				+ " where c.fornecedor9 || c.fornecedor4 || c.fornecedor2 || upper(c.nome_fornecedor) like '%"
				+ leitor.toUpperCase() + "%' "
				+ " and c.sit_fornecedor = 1 ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public List<ConteudoChaveNumerica> findAllPedidosAtacadoAsync(String leitor) {
		String query = " select a.pedido_venda value, a.pedido_venda label from pedi_100 a "
				+ " where a.pedido_venda like '%" + leitor + "%' "
				+ " and a.cod_rep_cliente <> 162 "
				+ " and rownum <= 200 "
				+ " order by a.pedido_venda ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConteudoChaveNumerica> findAllPedidosEcommerceAsync(String leitor) {
		String query = " select a.pedido_venda value, a.pedido_venda label from pedi_100 a "
				+ " where a.pedido_venda like '%" + leitor + "%' "
				+ " and a.cod_rep_cliente = 162 "
				+ " and rownum <= 200 "
				+ " order by a.pedido_venda ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConteudoChaveNumerica> findAllNotasAsync(String leitor) {
		String query = " select b.num_nota_fiscal value, b.num_nota_fiscal label from fatu_050 b "
				+ " where b.situacao_nfisc = 1 "
				+ " and b. num_nota_fiscal like '%" + leitor + "%'";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConsultaMinutaTransporte> findDadosMinutaEcommerce(String dataInicioBox, String dataFimBox,
			String horaInicio, String horaFim, int nota, String transportadora, String enderecos) {
		String query = " select a.num_nota_fiscal nota, a.serie_nota_fisc serie, a.data_emissao emissao, a.pedido_venda pedido, c.nome_cliente cliente, "
				+ " count(e.numero_volume) caixas, f.data_liberacao libPaypal, a.peso_bruto pesoBruto, a.valor_itens_nfis valorNota, g.cidade, g.estado, e.live_endereco_volume endereco "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e, expe_003 f, basi_160 g "
				+ "           where a.codigo_empresa = 1 "
				+ "           and a.cod_canc_nfisc = 0 "
				+ "           and a.pedido_venda = b.pedido_venda "
				+ "           and c.cgc_9 = b.cli_ped_cgc_cli9"
				+ "           and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ "           and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ "           and e.pedido_venda = a.pedido_venda "
				+ " 		  and e.nota_fiscal = a.num_nota_fiscal "
				+ "			  and e.serie_nota = a.serie_nota_fisc "
				+ "			  and e.cod_empresa = a.codigo_empresa "
				+ "           and e.local_caixa = 9 "
				+ "           and e.situacao_volume = 2 "
				+ "           and a.cod_rep_cliente = 162 "
				+ "           and f.cod_empresa (+) = a.codigo_empresa "
				+ "           and f.nota_fiscal (+) = a.num_nota_fiscal "
				+ " 		  and g.cod_cidade = c.cod_cidade ";
		if (!enderecos.equalsIgnoreCase("")) {
			query = query + " and e.live_endereco_volume in (" + enderecos + ") ";
		}
		if (!horaInicio.equals("")) {
			query = query + " and to_char(e.live_dt_hr_local_9, 'HH24:MI') between '" + horaInicio + "' and '" + horaFim
					+ "' ";
		}
		if (!dataInicioBox.equals("")) {
			query = query + " and trunc(e.live_dt_hr_local_9) between TO_DATE('" + dataInicioBox.replace("-", "/")
					+ "', 'DD/MM/YYYY') and TO_DATE('" + dataFimBox.replace("-", "/") + "', 'DD/MM/YYYY') ";
		}
		if (!transportadora.equals("")) {
			query = query + " and a.transpor_forne9 || '.' || a.transpor_forne4 || '.' || a.transpor_forne2 = '"
					+ transportadora + "' ";
		}
		if (nota > 0) {
			query = query + " and a.num_nota_fiscal = " + nota;
		}
		query = query
				+ " group by a.num_nota_fiscal, a.serie_nota_fisc, a.data_emissao, a.pedido_venda, c.nome_cliente, a.peso_bruto, a.valor_itens_nfis, f.data_liberacao, g.cidade, g.estado, e.live_endereco_volume "
				+ " order by a.num_nota_fiscal ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public List<ConsultaMinutaTransporte> findDadosMinutaAtacado(String dataEmiInicio, String dataEmiFim,
			String dataLibPaypalIni, String dataLibPaypalFim, int empresa, List<ConteudoChaveNumerica> localCaixa,
			String transportadora, int pedido, int nota, String horaLibPaypalIni, String horaLibPaupalFim) {
		String query = "";

		if (empresa == 1) {
			query = retornaQueryPedidosEmpresa1(dataEmiInicio, dataEmiFim, dataLibPaypalIni, dataLibPaypalFim, empresa,
					localCaixa,
					transportadora, pedido, nota, horaLibPaypalIni, horaLibPaupalFim);
		} else {
			query = retornaQueryPedidosEmpresa100(dataEmiInicio, dataEmiFim, dataLibPaypalIni, dataLibPaypalFim,
					localCaixa, transportadora, pedido, nota);
		}
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public ConsultaTransportadora findDadosTransportadora(String transportadora) {
		String query = " select a.nome_fornecedor nome, a.endereco_forne endereco, a.complemento, a.bairro, a.cep_fornecedor cep, b.cidade, b.estado from supr_010 a, basi_160 b "
				+ " where a.fornecedor9 || '.' || a.fornecedor4 || '.' || a.fornecedor2 = '" + transportadora + "' "
				+ " and b.cod_cidade = a.cod_cidade ";
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTransportadora.class));
	}

	public List<ConsultaMinutaTransporte> findVolumesSemLeituraEcom(String dataInicioBox, String dataFimBox, int nota,
			String transportadora) {
		String query = " select a.num_nota_fiscal nota, a.serie_nota_fisc serie, a.data_emissao emissao, a.pedido_venda pedido, c.nome_cliente cliente, e.numero_volume caixa, decode(e.local_caixa,0, '0 - NÃO ENDEREÇADO', 1,'1 - NO ENDEREÇO',2, '2 - COM NOTA EMITIDA', 3, "
				+ " '3 - INDEFINIDA', 6,'6 - LIST IMPRESSA/PRÉ FATURADA', 7,'7 - TRANSPORTADORA', 9,'9 - FATURADA') local "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e "
				+ " where a.codigo_empresa = 1 "
				+ " and a.pedido_venda = b.pedido_venda "
				+ " and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ " and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ " and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ " and e.pedido_venda = a.pedido_venda "
				+ " and e.nota_fiscal = a.num_nota_fiscal "
				+ " and e.local_caixa <> 9 "
				+ " and e.situacao_volume = 2 "
				+ " and a.cod_rep_cliente = 162 ";
		if (!dataInicioBox.equals("")) {
			query = query + " and trunc(e.live_dt_hr_local_9) between TO_DATE('" + dataInicioBox.replace("-", "/")
					+ "', 'DD/MM/YYYY') and TO_DATE('" + dataFimBox.replace("-", "/") + "', 'DD/MM/YYYY') ";
		}
		if (!transportadora.equals("")) {
			query = query + " and a.transpor_forne9 || '.' || a.transpor_forne4 || '.' || a.transpor_forne2 = '"
					+ transportadora + "' ";
		}
		if (nota > 0) {
			query = query + " and a.num_nota_fiscal = " + nota;
		}
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public List<ConsultaMinutaTransporte> findVolumesSemLeituraAtac(String dataEmiInicio, String dataEmiFim,
			int empresa, String transportadora, int pedido, int nota) {
		String query = " select a.num_nota_fiscal nota, a.serie_nota_fisc serie, a.data_emissao emissao, a.pedido_venda pedido, c.nome_cliente cliente, "
				+ " e.numero_volume caixa, decode(e.local_caixa,0, '0 - NÃO ENDEREÇADO', 1,'1 - NO ENDEREÇO',2, '2 - COM NOTA EMITIDA', 3,'3 - INDEFINIDA', 6,'6 - LIST IMPRESSA/PRÉ FATURADA', 7,'7 - TRANSPORTADORA', 9,'9 - FATURADA') local "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e "
				+ " where a.data_emissao between TO_DATE('" + dataEmiInicio.replace("-", "/")
				+ "', 'DD/MM/YYYY') and TO_DATE('" + dataEmiFim.replace("-", "/") + "', 'DD/MM/YYYY') "
				+ " and a.codigo_empresa in (" + empresa + ") "
				+ " and a.pedido_venda = b.pedido_venda "
				+ " and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 <> 35303139199 "
				+ " and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ " and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ " and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ " and e.pedido_venda = a.pedido_venda "
				+ " and e.nota_fiscal = a.num_nota_fiscal "
				+ " and e.situacao_volume = 2 "
				+ " and a.cod_rep_cliente <> 162 "
				+ " and e.local_caixa not in (9) ";

		if (empresa == 100) {
			query += " and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 = 35303139199 ";
		} else {
			query += " and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 <> 35303139199 ";
		}
		if (nota > 0) {
			query += " and a.num_nota_fiscal = " + nota;
		}
		if (pedido > 0) {
			query += " and a.pedido_venda = " + pedido;
		}
		if (!transportadora.equals("")) {
			query += " and a.transpor_forne9 || '.' || a.transpor_forne4 || '.' || a.transpor_forne2 = '"
					+ transportadora + "' ";
		}
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public String retornaQueryPedidosEmpresa1(String dataEmiInicio, String dataEmiFim, String dataLibPaypalIni,
			String dataLibPaypalFim, int empresa, List<ConteudoChaveNumerica> localCaixa,
			String transportadora, int pedido, int nota, String horaLibPaypalIni, String horaLibPaypalFim) {
		String filtroDataLib = "";

		if (!dataLibPaypalIni.equals("NaN-NaN-NaN")) {
			filtroDataLib = "and ((a.data_emissao between TO_DATE('" + dataEmiInicio.replace("-", "/")
					+ "', 'DD/MM/YYYY') " +
					" and TO_DATE('" + dataEmiFim.replace("-", "/") + "', 'DD/MM/YYYY')) or " +
					" (to_char(f.data_liberacao) between TO_DATE('" + dataLibPaypalIni.replace("-", "/")
					+ "', 'DD/MM/YYYY') " +
					" and TO_DATE('" + dataLibPaypalFim.replace("-", "/") + "', 'DD/MM/YYYY'))) ";
		} else {
			filtroDataLib = " and a.data_emissao between TO_DATE('" + dataEmiInicio.replace("-", "/")
					+ "', 'DD/MM/YYYY') " +
					" and TO_DATE('" + dataEmiFim.replace("-", "/") + "', 'DD/MM/YYYY') ";
		}

		String query = " select 1 empresa, minuta.nota, minuta.serie, minuta.emissao, minuta.pedido, minuta.cliente, minuta.caixas, minuta.libPaypal, "
				+ " minuta.pesoBruto, minuta.valorNota, minuta.cidade, minuta.estado from (";

		query += " select a.num_nota_fiscal nota, a.serie_nota_fisc serie, a.data_emissao emissao, a.pedido_venda pedido, c.nome_cliente cliente, "
				+ " count(e.numero_volume) caixas, f.data_liberacao libPaypal, a.peso_bruto pesoBruto, a.valor_itens_nfis valorNota, g.cidade, g.estado "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e, expe_003 f, basi_160 g "
				+ "     where a.pedido_venda = b.pedido_venda "
				+ " 		  and a.codigo_empresa in (" + empresa + ") "
				+ "           and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ "           and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ "           and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ "           and e.pedido_venda = a.pedido_venda "
				+ " 		  and e.nota_fiscal = a.num_nota_fiscal "
				+ " 		  and a.cod_canc_nfisc = 0 "
				+ "           and e.situacao_volume = 2 "
				+ "           and a.cod_rep_cliente <> 162 "
				+ "           and f.cod_empresa (+) = a.codigo_empresa "
				+ "           and f.nota_fiscal (+) = a.num_nota_fiscal "
				/*
				 * +
				 * " 		  and ((a.cond_pgto_venda in (200,67,267)) and (f.usuario_liberador in ('WEB.SOLANGE.O', 'WEB.JENEFER.T')) and ("
				 * +
				 * " 			  to_char(f.data_liberacao) between TO_DATE('" +
				 * dataLibPaypalIni.replace("-", "/") + "', 'DD/MM/YYYY') " +
				 * " 			  and TO_DATE('" + dataLibPaypalFim.replace("-", "/") +
				 * "', 'DD/MM/YYYY'))) "
				 */
				+ "  		  and g.cod_cidade = c.cod_cidade "
				+ " 		  and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 <> 35303139199 ";
		query += filtroDataLib;

		if (!horaLibPaypalIni.equals("") && !dataLibPaypalIni.equals("NaN-NaN-NaN")) {
			query += " and to_char(f.data_liberacao, 'HH24:MI') between '" + horaLibPaypalIni + "' and '"
					+ horaLibPaypalFim + "' ";
		}
		if (nota > 0) {
			query += " and a.num_nota_fiscal = " + nota;
		}
		if (pedido > 0) {
			query += " and a.pedido_venda = " + pedido;
		}
		if (localCaixa.size() > 0) {
			query += " and e.local_caixa in (" + ConteudoChaveNumerica.parseValueToString(localCaixa) + ") ";
		}
		if (!transportadora.equals("")) {
			query += " and a.transpor_forne9 || '.' || a.transpor_forne4 || '.' || a.transpor_forne2 = '"
					+ transportadora + "' ";
		}
		if (!dataLibPaypalIni.equals("NaN-NaN-NaN")) {
			query += " 		  and ((a.cond_pgto_venda in (200,67,267)) and (f.usuario_liberador in ('WEB.SOLANGE.O', 'WEB.JENEFER.T')) and ("
					+
					" 			  to_char(f.data_liberacao) between TO_DATE('" + dataLibPaypalIni.replace("-", "/")
					+ "', 'DD/MM/YYYY') " +
					" 			  and TO_DATE('" + dataLibPaypalFim.replace("-", "/") + "', 'DD/MM/YYYY'))) ";
		} else {
			query += " and ((a.cond_pgto_venda in (200,67,267)) and (f.usuario_liberador in ('WEB.SOLANGE.O', 'WEB.JENEFER.T'))) ";
		}
		query += " and not exists (select 1 from orion_exp_320 y " +
				"					where y.nota = a.num_nota_fiscal) ";

		query += " group by a.num_nota_fiscal, a.serie_nota_fisc, a.data_emissao, a.pedido_venda, c.nome_cliente, a.peso_bruto, a.valor_itens_nfis, f.data_liberacao, g.cidade, g.estado ";
		query += " UNION ";
		query += " select a.num_nota_fiscal nota, a.serie_nota_fisc serie, a.data_emissao emissao, a.pedido_venda pedido, c.nome_cliente cliente, "
				+ " count(e.numero_volume) caixas, f.data_liberacao libPaypal, a.peso_bruto pesoBruto, a.valor_itens_nfis valorNota, g.cidade, g.estado "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e, expe_003 f, basi_160 g "
				+ "     where a.pedido_venda = b.pedido_venda "
				+ " 		  and a.codigo_empresa in (" + empresa + ") "
				+ "           and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ "           and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ "           and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ "           and e.pedido_venda = a.pedido_venda "
				+ " 		  and e.nota_fiscal = a.num_nota_fiscal "
				+ "           and e.situacao_volume = 2 "
				+ "           and a.cod_rep_cliente <> 162 "
				+ "           and f.cod_empresa (+) = a.codigo_empresa "
				+ "           and f.nota_fiscal (+) = a.num_nota_fiscal "
				+ " 		  and a.cod_canc_nfisc = 0 "
				+ " 		  and g.cod_cidade = c.cod_cidade "
				+ " 		  and a.cond_pgto_venda not in (200,67,267) "
				+ " 		  and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 <> 35303139199 "
				+ "	 		  and a.data_emissao between TO_DATE('" + dataEmiInicio.replace("-", "/")
				+ "', 'DD/MM/YYYY') "
				+ " 		  and TO_DATE('" + dataEmiFim.replace("-", "/") + "', 'DD/MM/YYYY') ";
		if (nota > 0) {
			query += " and a.num_nota_fiscal = " + nota;
		}
		if (pedido > 0) {
			query += " and a.pedido_venda = " + pedido;
		}
		if (localCaixa.size() > 0) {
			query += " and e.local_caixa in (" + ConteudoChaveNumerica.parseValueToString(localCaixa) + ") ";
		}
		if (!transportadora.equals("")) {
			query += " and a.transpor_forne9 || '.' || a.transpor_forne4 || '.' || a.transpor_forne2 = '"
					+ transportadora + "' ";
		}
		query += " and not exists (select 1 from orion_exp_320 y " +
				"					where y.nota = a.num_nota_fiscal) ";

		query += " group by a.num_nota_fiscal, a.serie_nota_fisc, a.data_emissao, a.pedido_venda, c.nome_cliente, a.peso_bruto, a.valor_itens_nfis, f.data_liberacao, g.cidade, g.estado ";

		query += ") minuta order by minuta.nota ";

		System.out.println("query: " + query);

		return query;
	}

	public String retornaQueryPedidosEmpresa100(String dataEmiInicio, String dataEmiFim, String dataLibPaypalIni,
			String dataLibPaypalFim, List<ConteudoChaveNumerica> localCaixa,
			String transportadora, int pedido, int nota) {
		String filtroDataLib = "";

		filtroDataLib = " and ((a.data_emissao between TO_DATE('" + dataEmiInicio.replace("-", "/")
				+ "', 'DD/MM/YYYY') " +
				" and TO_DATE('" + dataEmiFim.replace("-", "/") + "', 'DD/MM/YYYY'))) ";

		String query = " select 100 empresa, minuta.nota, minuta.serie, minuta.emissao, minuta.pedido, minuta.cliente, minuta.caixas, minuta.libPaypal, "
				+ " minuta.pesoBruto, minuta.valorNota, minuta.cidade, minuta.estado from (";

		query += " select i.num_nota_fiscal nota, i.serie_nota_fisc serie, i.data_emissao emissao, a.pedido_venda pedido, " +
				" (SELECT p.NOME_CLIENTE  FROM fatu_050 v, PEDI_010 p " +
				" WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal " +
				" AND v.CODIGO_EMPRESA = 100 " +
				" AND p.CGC_9 = v.CGC_9 " +
				" AND p.CGC_4 = v.CGC_4 " +
				" AND p.CGC_2 = v.CGC_2) cliente, "
				+ " count(e.numero_volume) caixas, f.data_liberacao libPaypal, a.peso_bruto pesoBruto, "
				+ " (select w.valor_itens_nfis from fatu_050 w where w.num_nota_fiscal = i.num_nota_fiscal and w.codigo_empresa = 100) valorNota, "
				+ " (SELECT b.CIDADE  FROM fatu_050 v, PEDI_010 p, BASI_160 b "
				+ " WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal "
				+ " AND v.CODIGO_EMPRESA = 100 "
				+ " AND p.CGC_9 = v.CGC_9 "
				+ " AND p.CGC_4 = v.CGC_4 "
				+ " AND p.CGC_2 = v.CGC_2 "
				+ " AND b.COD_CIDADE = p.COD_CIDADE) cidade, "
				+ " (SELECT b.ESTADO  FROM fatu_050 v, PEDI_010 p, BASI_160 b "
				+ " WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal "
				+ " AND v.CODIGO_EMPRESA = 100 "
				+ " AND p.CGC_9 = v.CGC_9 "
				+ " AND p.CGC_4 = v.CGC_4 "
				+ " AND p.CGC_2 = v.CGC_2 "
				+ " AND b.COD_CIDADE = p.COD_CIDADE) estado "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e, expe_003 f, basi_160 g, obrf_782 h, obrf_831 i "
				+ "     where a.pedido_venda = b.pedido_venda "
				+ " 		  and a.codigo_empresa in (1) "
				+ "           and a.cod_canc_nfisc = 0 "
				+ "           and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ "           and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ "           and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ "           and e.pedido_venda = a.pedido_venda "
				+ "           and e.nota_fiscal = a.num_nota_fiscal "
				+ "           and e.situacao_volume = 2 "
				+ "           and a.cod_rep_cliente <> 162 "
				+ "           and f.cod_empresa (+) = a.codigo_empresa "
				+ "           and f.nota_fiscal (+) = a.num_nota_fiscal "
				+ " 		  and ((a.cond_pgto_venda in (200,67,267)) and (f.usuario_liberador in ('WEB.SOLANGE.O', 'WEB.JENEFER.T'))) "
				+ "  		  and g.cod_cidade = c.cod_cidade "
				+ " 		  and h.num_nota_fiscal = a.num_nota_fiscal "
				+ "			  and i.numero_solicitacao = h.numero_solicitacao "
				+ " 		  and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 = 35303139199 ";
		query += filtroDataLib;

		if (nota > 0) {
			query += " and i.num_nota_fiscal = " + nota;
		}
		if (pedido > 0) {
			query += " and a.pedido_venda = " + pedido;
		}
		if (localCaixa.size() > 0) {
			query += " and e.local_caixa in (" + ConteudoChaveNumerica.parseValueToString(localCaixa) + ") ";
		}
		if (!transportadora.equals("")) {
			query += " and exists (select 1 from fatu_050 w "
					+ "                  where w.num_nota_fiscal = i.num_nota_fiscal "
					+ "                  and w.codigo_empresa = 100 "
					+ "                  and w.transpor_forne9 || '.' || w.transpor_forne4 || '.' || w.transpor_forne2 = '"
					+ transportadora + "') ";
		}
		query += " and not exists (select 1 from orion_exp_320 y " +
				"					where y.nota = i.num_nota_fiscal) ";

		query += " group by i.num_nota_fiscal, i.serie_nota_fisc, i.data_emissao, a.pedido_venda, c.nome_cliente, a.peso_bruto, a.valor_itens_nfis, f.data_liberacao, g.cidade, g.estado ";
		query += " UNION ";
		query += " select i.num_nota_fiscal nota, i.serie_nota_fisc serie, i.data_emissao emissao, a.pedido_venda pedido, "
				+ " (SELECT p.NOME_CLIENTE  FROM fatu_050 v, PEDI_010 p "
				+ " WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal "
				+ " AND v.CODIGO_EMPRESA = 100 "
				+ " AND p.CGC_9 = v.CGC_9 "
				+ " AND p.CGC_4 = v.CGC_4 "
				+ " AND p.CGC_2 = v.CGC_2) cliente, "
				+ " count(e.numero_volume) caixas, f.data_liberacao libPaypal, a.peso_bruto pesoBruto, "
				+ " (select w.valor_itens_nfis from fatu_050 w where w.num_nota_fiscal = i.num_nota_fiscal and w.codigo_empresa = 100) valorNota, "
				+ " (SELECT b.CIDADE FROM fatu_050 v, PEDI_010 p, BASI_160 b "
				+ " WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal "
				+ " AND v.CODIGO_EMPRESA = 100 "
				+ " AND p.CGC_9 = v.CGC_9 "
				+ " AND p.CGC_4 = v.CGC_4 "
				+ " AND p.CGC_2 = v.CGC_2 "
				+ " AND b.COD_CIDADE = p.COD_CIDADE) cidade, "
				+ " (SELECT b.ESTADO FROM fatu_050 v, PEDI_010 p, BASI_160 b "
				+ " WHERE v.NUM_NOTA_FISCAL = i.num_nota_fiscal"
				+ " AND v.CODIGO_EMPRESA = 100 "
				+ " AND p.CGC_9 = v.CGC_9 "
				+ " AND p.CGC_4 = v.CGC_4 "
				+ " AND p.CGC_2 = v.CGC_2 "
				+ " AND b.COD_CIDADE = p.COD_CIDADE) estado "
				+ " from fatu_050 a, pedi_100 b, pedi_010 c, pcpc_320 e, expe_003 f, basi_160 g, obrf_782 h, obrf_831 i "
				+ "     where a.pedido_venda = b.pedido_venda "
				+ " 		  and a.codigo_empresa in (1) "
				+ "           and a.cod_canc_nfisc = 0 "
				+ "           and c.cgc_9 = b.cli_ped_cgc_cli9 "
				+ "           and c.cgc_4 = b.cli_ped_cgc_cli4 "
				+ "           and c.cgc_2 = b.cli_ped_cgc_cli2 "
				+ "           and e.pedido_venda = a.pedido_venda "
				+ "			  and e.nota_fiscal = a.num_nota_fiscal "
				+ "           and e.situacao_volume = 2 "
				+ "           and a.cod_rep_cliente <> 162 "
				+ "           and f.cod_empresa (+) = a.codigo_empresa "
				+ "           and f.nota_fiscal (+) = a.num_nota_fiscal "
				+ " 		  and g.cod_cidade = c.cod_cidade "
				+ "			  and h.num_nota_fiscal = a.num_nota_fiscal "
				+ "			  and i.numero_solicitacao = h.numero_solicitacao "
				+ " 		  and b.cli_ped_cgc_cli9 || b.cli_ped_cgc_cli4 || b.cli_ped_cgc_cli2 = 35303139199 ";
		query += filtroDataLib;

		if (nota > 0) {
			query += " and i.num_nota_fiscal = " + nota;
		}
		if (pedido > 0) {
			query += " and a.pedido_venda = " + pedido;
		}
		if (localCaixa.size() > 0) {
			query += " and e.local_caixa in (" + ConteudoChaveNumerica.parseValueToString(localCaixa) + ") ";
		}
		if (!transportadora.equals("")) {
			query += " and exists (select 1 from fatu_050 w "
					+ "                  where w.num_nota_fiscal = i.num_nota_fiscal "
					+ "                  and w.codigo_empresa = 100 "
					+ "                  and w.transpor_forne9 || '.' || w.transpor_forne4 || '.' || w.transpor_forne2 = '"
					+ transportadora + "') ";
		}

		//if (!dataLibPaypalIni.equals("NaN-NaN-NaN")) {
		//	query += " and to_char(f.data_liberacao) between TO_DATE('" + dataLibPaypalIni.replace("-", "/")
		//				+ "', 'DD/MM/YYYY') and TO_DATE('" + dataLibPaypalFim.replace("-", "/") + "', 'DD/MM/YYYY') ";
		//}

		query += " and not exists (select 1 from orion_exp_320 y " +
				"					where y.nota = i.num_nota_fiscal) ";
		query += " group by i.num_nota_fiscal, i.serie_nota_fisc, i.data_emissao, a.pedido_venda, c.nome_cliente, a.peso_bruto, a.valor_itens_nfis, f.data_liberacao, g.cidade, g.estado ";

		query += ") minuta order by minuta.nota ";

		return query;
	}

	public void changeEnderecoTAG(int periodo, int ordemProd, int pacote, int sequencia, String newEndereco,
			String usuarioSystextil) {
		String query = " update pcpc_330 a "
				+ " set endereco = ?, live_user_endereco = ? "
				+ " where a.periodo_producao = ? "
				+ " and a.ordem_producao = ? "
				+ " and a.ordem_confeccao = ? "
				+ " and a.sequencia = ? ";
		jdbcTemplate.update(query, newEndereco, usuarioSystextil, periodo, ordemProd, pacote, sequencia);
	}

	public int showCountPartsAllocation(String allocation) {
		int countParts = 0;

		String query = " select count(*) from pcpc_330 l "
				+ " where l.endereco = '" + allocation + "' ";
		try {
			countParts = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			countParts = 0;
		}
		return countParts;
	}

	public int findWarehouseTAG(int periodo, int ordemProd, int pacote, int sequencia) {
		int warehouse = 0;

		String query = " select a.deposito from pcpc_330 a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProd
				+ " and a.ordem_confeccao = " + pacote
				+ " and a.sequencia = " + sequencia;
		try {
			warehouse = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			warehouse = 0;
		}
		return warehouse;
	}

	public int findSituacaoTAG(int periodo, int ordemProd, int pacote, int sequencia) {
		int situacao = 0;

		String query = " select a.estoque_tag from pcpc_330 a "
				+ " where a.periodo_producao = " + periodo
				+ " and a.ordem_producao = " + ordemProd
				+ " and a.ordem_confeccao = " + pacote
				+ " and a.sequencia = " + sequencia;
		try {
			situacao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			situacao = 0;
		}
		return situacao;
	}
	
	public void clearAllocation(String oldAllocation, String usuarioSystextil) {
		
		String query = " UPDATE pcpc_330 a "
				+ "    SET endereco = ? , "
				+ "    live_user_endereco = ? "
				+ "    WHERE a.endereco = ? ";
				jdbcTemplate.update(query, "", usuarioSystextil, oldAllocation);
	}

	public List<ConsultaTag> findAllocations(String startAllocation, String endAllocation) {
		List<ConsultaTag> enderecos = new ArrayList<ConsultaTag>();

		String query = " select a.endereco from pcpc_330 a "
				+ " where a.endereco between '" + startAllocation + "' and '" + endAllocation + "' "
				+ " group by a.endereco ";
		try {
			enderecos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
		} catch (Exception e) {
			enderecos = new ArrayList<ConsultaTag>();
		}
		return enderecos;
	}

	public void clearAllocationEstq110(String oldAllocation, int deposito) {
		String query = " delete estq_110 a "
				+ " where a.endereco = ? "
				+ " and a.deposito = ? ";
		jdbcTemplate.update(query, oldAllocation, deposito);
	}

	public void insertProductInEstq110(String nivel, String grupo, String subGrupo, String item, int deposito,
			String endereco) {
		String query = " insert into estq_110 values (?, ?, ?, ?, ?, ?) ";
		try {
			jdbcTemplate.update(query, nivel, grupo, subGrupo, item, deposito, endereco);
		} catch (Exception e) {
			//System.out.println(e);
		}
	}

	public ConsultaTag findDataTAGNumber(int period, int order, int packageOrder, int sequence) {
		String query = " select a.nivel, a.grupo, a.subgrupo subGrupo, a.item, a.deposito from pcpc_330 a "
				+ " where a.periodo_producao = " + period
				+ " and a.ordem_producao = " + order
				+ " and a.ordem_confeccao = " + packageOrder
				+ " and a.sequencia = " + sequence;
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
	}

	public List<ConsultaTag> findMovsEnderecos(String endereco, String dataInicio, String dataFim, String usuario,
			String tipoMov, String numeroTag, int deposito) {
		List<ConsultaTag> historicoEnderecos = new ArrayList<ConsultaTag>();

		String query = " select hist.numeroTag, hist.produto, hist.data, hist.tipo, hist.usuario, hist.endereco, hist.deposito from ( ";
		query += " select a.periodo || lpad(a.ordem_producao, 9,0) || lpad(a.ordem_confeccao, 5,0) || lpad(a.sequencia, 4,0) numeroTag, "
				+ " a.nivel || '.' || a.grupo || '.' || a.subgrupo || '.' || a.item produto, a.data_hora data, a.tipo, a.usuario, "
				+ " CASE "
				+ "      WHEN a.endereco LIKE '%-0%' "
				+ "   THEN SUBSTR(a.endereco, 0, 7) || ' - Zerado' "
				+ " ELSE a.endereco "
				+ " END endereco, "
				+ "  CASE "
				+ "    WHEN a.endereco BETWEEN 'A010101' AND 'C999999' "
				+ "      THEN 4 "
				+ "    WHEN a.endereco BETWEEN 'E010101' AND 'R999999' "
				+ "      THEN 111 "
				+ "    WHEN a.endereco = 'ENDERECAR' "
				+ " 	 THEN 4 "
				+ "      ELSE 0 "
				+ "        END deposito "
				+ " from orion_exp_300 a "
				+ " where trunc(a.data_hora) between to_date('" + dataInicio + "' , 'dd-MM-yyyy') and to_date('"
				+ dataFim + "', 'dd-MM-yyyy') ";

		if (endereco != null && !endereco.equalsIgnoreCase("")) {
			query += " and SUBSTR(a.endereco, 0, 7) = '" + endereco + "' ";
		}
		if (usuario != null && !usuario.equalsIgnoreCase("")) {
			query += " and a.usuario in (" + usuario + ")";
		}
		if (tipoMov != null) {
			query += " and a.tipo = '" + tipoMov + "' ";
		}
		if (!numeroTag.equalsIgnoreCase("")) {
			query += " and a.periodo || lpad(a.ordem_producao, 9,0) || lpad(a.ordem_confeccao, 5,0) || lpad(a.sequencia, 4,0) = " + numeroTag;
		}
		query += " order by a.data_hora ";
		query += ") hist ";

		if (deposito == 4) {
			query += " where hist.deposito = 4 ";
		} else if (deposito == 111) {
			query += "where hist.deposito = 111 ";
		}

		if (tipoMov.equalsIgnoreCase("S")) {
			query += " UNION ALL ";
			query += " select c.periodo_producao || lpad(c.ordem_producao, 9,0) || lpad(c.ordem_confeccao, 5,0) || lpad(c.sequencia, 4,0) numeroTag, " +
					" c.nivel || '.' || c.grupo || '.' || c.subgrupo || '.' || c.item produto, b.data_montagem, 'S', c.usuario_exped, c.endereco, c.deposito " +
					" from pcpc_330 c, pcpc_320 b " +
					" where b.numero_volume = c.nr_volume ";

			if (usuario != null && !usuario.equalsIgnoreCase("")) {
				query += " and c.usuario_exped in (" + usuario + ")";
			}
			query += " and b.data_montagem between to_date('" + dataInicio + "', 'dd-MM-yyyy') and  to_date('" + dataFim + "', 'dd-MM-yyyy') " +
					" and not exists (select 1 from orion_exp_300 d " +
					"                       where d.periodo = c.periodo_producao " +
					"                       and d.ordem_producao = c.ordem_producao " +
					"                       and d.ordem_confeccao = c.ordem_confeccao " +
					"                       and d.sequencia = c.sequencia " +
					"                       and d.tipo = 'S')";
		}

		System.out.println("QUERY: " + query);
		try {
			historicoEnderecos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTag.class));
		} catch (Exception e) {
			historicoEnderecos = new ArrayList<ConsultaTag>();
		}
		return historicoEnderecos;
	}

	public int somaQuantidadeMovimentacoes(String endereco, String dataInicio, String dataFim, String usuario,
			String tipoMov, String numeroTag, int deposito) {
		int totalMov = 0;

		String query = " select sum(quantMov.quantidade) from ( " +
				"select count(*) quantidade, CASE " +
				"    WHEN a.endereco BETWEEN 'A010101' AND 'C999999' " +
				"      THEN 4 " +
				"    WHEN a.endereco BETWEEN 'E010101' AND 'U999999' " +
				"      THEN 111 " +
				"        WHEN a.endereco = 'ENDERECAR' " +
				"          THEN 4 " +
				"      ELSE  0 " +
				"        END deposito from orion_exp_300 a " +
				" where trunc(a.data_hora) between to_date('" + dataInicio + "' , 'dd-MM-yyyy') and to_date('" + dataFim + "', 'dd-MM-yyyy') ";

		if (endereco != null && !endereco.equalsIgnoreCase("")) {
			query += " and SUBSTR(a.endereco, 0, 7) = '" + endereco + "' ";
		}
		if (usuario != null && !usuario.equalsIgnoreCase("")) {
			query += " and a.usuario in (" + usuario + ")";
		}
		if (tipoMov != null) {
			query += " and a.tipo = '" + tipoMov + "' ";
		}
		if (!numeroTag.equalsIgnoreCase("")) {
			query += " and a.periodo || lpad(a.ordem_producao, 9,0) || lpad(a.ordem_confeccao, 5,0) || lpad(a.sequencia, 4,0) = " + numeroTag;
		}
		query += " group by a.endereco ";

		if (tipoMov.equalsIgnoreCase("S")) {
			query += " UNION ALL ";
			query += " select count(*), c.deposito" +
					" from pcpc_330 c, pcpc_320 b " +
					" where b.numero_volume = c.nr_volume ";

			if (usuario != null && !usuario.equalsIgnoreCase("")) {
				query += " and c.usuario_exped in (" + usuario + ")";
			}
			query += " and b.data_montagem between to_date('" + dataInicio + "', 'dd-MM-yyyy') and  to_date('" + dataFim + "', 'dd-MM-yyyy') " +
					" and not exists (select 1 from orion_exp_300 d " +
					"                       where d.periodo = c.periodo_producao " +
					"                       and d.ordem_producao = c.ordem_producao " +
					"                       and d.ordem_confeccao = c.ordem_confeccao " +
					"                       and d.sequencia = c.sequencia " +
					"                       and d.tipo = 'S') group by c.deposito ";
		}

		query += " ) quantMov ";

		if (deposito == 4) {
			query += " where quantMov.deposito = 4 ";
		} else if (deposito == 111) {
			query += " where quantMov.deposito = 111 ";
		}

		System.out.println("OUTRA QUERY: " + query);

		try {
			totalMov = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			totalMov = 0;
		}
		return totalMov;
	}

	public int validateOrdered(int volume) {
		int openOrClose = 0;

		String query = " select 1 from pcpc_320 a, pedi_100 b "
				+ " where a.pedido_venda = b.pedido_venda "
				+ " and a.numero_volume = " + volume
				+ " and b.situacao_venda = 10 "
				+ " group by 1 ";
		try {
			openOrClose = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			openOrClose = 0;
		}
		return openOrClose;
	}

	public int validateNotaFiscall(int volume, int notaFiscal) {
		int notaFiscalVolume = 0;

		String query = " select 1 from pcpc_320 a "
				+ " where a.numero_volume = " + volume
				+ " and a.nota_fiscal = " + notaFiscal;
		try {
			notaFiscalVolume = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			notaFiscalVolume = 0;
		}
		return notaFiscalVolume;
	}

	public void updateBox(int volume, String allocation) {
		String query = " update pcpc_320 a "
				+ " set live_endereco_volume = ? ,"
				+ " local_caixa = ? "
				+ " where a.numero_volume = ? ";
		jdbcTemplate.update(query, allocation, 9, volume);
	}

	public String validateVolumeIsAllocated(int volume) {
		String volumeAllocated = "";

		String query = " select a.live_endereco_volume from pcpc_320 a "
				+ " where a.numero_volume = " + volume
				+ " and (a.live_endereco_volume is not null) ";
		try {
			volumeAllocated = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			volumeAllocated = "";
		}
		return volumeAllocated;
	}

	public void cleanAllocationVolume(int volume) {
		String query = " update pcpc_320 a "
				+ " set live_endereco_volume = ? ,"
				+ " 	local_caixa = ? ,"
				+ "		live_dt_hr_local_9 = ?"
				+ " where a.numero_volume = ? ";
		jdbcTemplate.update(query, "", 7, null, volume);
	}

	public List<ConteudoChaveAlfaNum> findEnderecosVolumes(String leitor) {
		String query = " select a.live_endereco_volume value, a.live_endereco_volume label from pcpc_320 a "
				+ " where a.live_endereco_volume is not null "
				+ " and a.live_endereco_volume like '%" + leitor + "%' "
				+ " group by a.live_endereco_volume ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public String

	findTransportadoraNotaFiscal(String chaveNFe) {
		String transportadora = "";

		String query = " select fatu_050.transpor_forne9 || fatu_050.transpor_forne4 || fatu_050.transpor_forne2 from fatu_050 "
				+ " where fatu_050.numero_danf_nfe = '" + chaveNFe + "' ";
		try {
			transportadora = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			transportadora = "";
		}
		return transportadora;
	}

	public int countVolumeSemEndereco(String chaveNFe, String transportadora) {
		int countVolumes = 0;

		String query = " select count(*) from pcpc_320 a "
				+ " where a.local_caixa = 7 "
				+ " and a.data_montagem >= sysdate-1 "
				+ " and exists (select 1 from fatu_050 "
				+ "                            where fatu_050.numero_danf_nfe = '" + chaveNFe + "' "
				+ "                              and fatu_050.codigo_empresa = a.cod_empresa "
				+ "                              and fatu_050.transpor_forne9 || fatu_050.transpor_forne4 || fatu_050.transpor_forne2 = "
				+ transportadora + ")";
		try {
			countVolumes = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			countVolumes = 0;
		}
		return countVolumes;
	}

	public String findTransportadoraEndereco(String endereco) {
		String transpEndereco = "";

		String query = " select nvl(min(c.transpor_forne9) || min(c.transpor_forne4) || min(c.transpor_forne2), '') from pcpc_320 a, fatu_050 c "
				+ " where a.live_endereco_volume = '" + endereco + "' "
				+ " and c.num_nota_fiscal = a.nota_fiscal "
				+ " and c.serie_nota_fisc = a.serie_nota "
				+ " and c.codigo_empresa = a.cod_empresa ";
		try {
			transpEndereco = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			transpEndereco = "";
		}
		return transpEndereco;
	}

	public List<ConteudoChaveAlfaNum> findClientesAsync(String leitor) {
		String query = " select b.cgc_9 || b.cgc_4 || b.cgc_2 value, lpad(b.cgc_9, 9, 0) || lpad(b.cgc_4, 4, 0) || lpad(b.cgc_2,2,0) || ' - ' || b.nome_cliente label from pedi_010 b "
				+ " where b.cgc_9 || b.cgc_4 || b.cgc_2 || b.nome_cliente like '%" + leitor + "%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public List<ConteudoChaveAlfaNum> findRepresentanteAsync(String leitor) {
		String query = " select a.cod_rep_cliente value, a.cod_rep_cliente || ' - ' || a.nome_rep_cliente label from pedi_020 a "
				+ " where a.cod_rep_cliente || a.nome_rep_cliente like '%" + leitor + "%' ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public String findEnderecoItem(String nivel, String grupo, String sub, String item, int deposito) {
		String query = "select e.endereco from estq_110 e "
				+ " where e.nivel = ? "
				+ " and e.grupo = ? "
				+ " and e.subgrupo = ? "
				+ " and e.item = ? "
				+ " and e.deposito = ? ";

		return jdbcTemplate.queryForObject(query, String.class, nivel, grupo, sub, item, deposito);
	}

	public List<ConteudoChaveAlfaNum> findUsuariosEnderecamento() {
		String query = " select a.usuario value, a.usuario label from orion_exp_300 a " +
				"where a.usuario is not null " +
				"group by a.usuario ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public int findNextIdAuditoria() {
		int nextId = 0;

		String query = " select nvl(max(a.id),0) +1 from orion_exp_310 a ";
		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}

	public void gravarAuditoria(int codRua, int volume) {
		String query = " insert into orion_exp_310 values (?, ?, ?, sysdate) ";
		jdbcTemplate.update(query, findNextIdAuditoria(), codRua, volume);
	}

	public List<ConsultaHistAuditoria> findAuditoriaByDate(String dataInicio, String dataFim) {
		String query = " select a.id, a.codigo_rua rua, a.volume, a.data_auditoria dataAuditoria, b.local_caixa localCaixa from orion_exp_310 a, pcpc_320 b "
				+
				" where trunc(a.data_auditoria) between to_date('" + dataInicio + "' , 'dd-MM-yyyy') and to_date('"
				+ dataFim + "', 'dd-MM-yyyy') " +
				" and b.numero_volume = a.volume ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaHistAuditoria.class));
	}

	public List<Integer> findVolumesPedido(int pedido, int notaFiscal) {
		String query = " select a.numero_volume from pcpc_320 a "
				+ " where a.pedido_venda = " + pedido
				+ " and a.nota_fiscal = " + notaFiscal
				+ " and not exists ( select 1 from orion_exp_320 b "
				+ "                        where b.pedido = a.pedido_venda "
				+ "                        and b.volume = a.numero_volume) ";
		return jdbcTemplate.queryForList(query, Integer.class);
	}

	public long findNextIdVolumesMinuta() {
		int nextId = 0;

		String query = " select nvl(max(orion_exp_320.id),0) + 1 from orion_exp_320 ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}

		return (long) nextId;
	}

	public int findNextMinuta() {
		int nextMinuta = 0;

		String query = " select nvl(max(orion_exp_320.minuta),0) + 1 from orion_exp_320 ";
		try {
			nextMinuta = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextMinuta = 0;
		}
		return nextMinuta;
	}

	public List<ConteudoChaveNumerica> findListMinutasGeradas() {
		String query = " select a.minuta value, a.minuta label from orion_exp_320 a "
				+ " group by a.minuta "
				+ " order by a.minuta ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}

	public List<ConsultaMinutaTransporte> consultaRelatorioMinutas(int minuta, String dataInicio, String dataFim) {

		String query = " select minGeradas.minuta, minGeradas.tipoMinuta, minGeradas.nota, minGeradas.serie, minGeradas.pedido, nvl(minGeradas.status, 0) status, minGeradas.usuario, minGeradas.data from ";

		query += " ( ";
		query += " select a.minuta, a.tipo_minuta tipoMinuta, a.nota, a.serie, a.usuario, a.data_hora_geracao data, a.pedido, (select 1 from pcpc_320 b " +
				"                                                                        where b. pedido_venda = a.pedido " +
				" 																		 and b.nota_fiscal = a.nota " +
				"																		 and b.serie_nota = a.serie " +
				"                                                                        and b.local_caixa = 9 " +
				"                                                                        group by 1) status  from orion_exp_320 a " +
				" where trunc(a.data_hora_geracao) between to_date('" + dataInicio + "' , 'dd-MM-yyyy') and to_date('" + dataFim + "', 'dd-MM-yyyy') ";
		if (minuta > 0) {
			query += " and a.minuta = " + minuta;
		}
		query += " group by a.minuta, a.tipo_minuta, a.nota, a.serie, a.pedido, a.usuario, a.data_hora_geracao ";
		query += " order by a.minuta, a.nota ";
		query += " ) minGeradas ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public int validarVolumesMinuta(int minuta) {
		int localCaixa = 0;

		String query = " select min(b.local_caixa) from orion_exp_320 a, pcpc_320 b "
				+ " where b.numero_volume = a.volume "
				+ " and a.minuta = " + minuta;
		try {
			localCaixa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			localCaixa = 0;
		}
		return localCaixa;
	}

	public int verificaTodosVolumesAlocados(int minuta) {
		int localCaixa = 0;

		String query = " select b.local_caixa from orion_exp_320 a, pcpc_320 b "
				+ " where b.numero_volume = a.volume "
				+ " and a.minuta = " + minuta
				+ " group by b.local_caixa ";
		try {
			localCaixa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			localCaixa = 0;
		}
		return localCaixa;
	}

	public void alterarLocalCaixaVolume(int volume, int localCaixa) {
		String query = " update pcpc_320 "
				+ "			set local_caixa = ? "
				+ "			where pcpc_320.numero_volume = ? ";
		jdbcTemplate.update(query, localCaixa, volume);
	}

	public int validarVolumeDentroMinuta(int minuta, int volume) {
		int status = 0;

		String query = " select 1 from orion_exp_320 a "
				+ " where a.minuta = " + minuta
				+ " and a.volume = " + volume;
		try {
			status = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}

	public int totalCaixasMinuta(int minuta) {
		int totalCaixas = 0;

		String query = " select count(*) from orion_exp_320 a "
				+ " where a.minuta = " + minuta;
		try {
			totalCaixas = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			totalCaixas = 0;
		}
		return totalCaixas;
	}

	public int totalCaixasLidasDespacho(int minuta, int statusVolume) {
		int totalCaixasLidas = 0;

		String query = " select count(*) from orion_exp_320 a, pcpc_320 b " +
				" where a.minuta = " + minuta +
				" and b.numero_volume = a.volume " +
				" and b.local_caixa = " + statusVolume;
		try {
			totalCaixasLidas = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			totalCaixasLidas = 0;
		}

		return totalCaixasLidas;
	}

	public String obterTransportadoraMinuta(int minuta) {
		String query = " select a.transportadora || ' - ' || b.nome_fornecedor from orion_exp_320 a, supr_010 b "
				+ " where b.fornecedor9 || '.' || b.fornecedor4 || '.' || b.fornecedor2 = a.transportadora "
				+ " and a.minuta = " + minuta
				+ " group by a.transportadora, b.nome_fornecedor ";
		return jdbcTemplate.queryForObject(query, String.class);
	}

	public int obterLocalCaixaVolume(int volume) {
		int localCaixa = 0;

		String query = " select a.local_caixa from pcpc_320 a" +
				" where a.numero_volume = " + volume;
		try {
			localCaixa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			localCaixa = 0;
		}
		return localCaixa;
	}

	public List<Integer> obterCaixasNaEsteira(int caixaNaEsteira) {

		String query = " select a.numero_caixa caixa from orion_130 a "
				+ " where a.caixa_na_esteira = " + caixaNaEsteira
				+ " and a.situacao_caixa = 1 "
				+ " and a.tipo_caixa = 0 "
				+ " and a.endereco is null ";

		return jdbcTemplate.queryForList(query, Integer.class);
	}

	public String obterAreaCaixa(int caixa) {
		String area = "";
		String query = " select p.descricao from orion_131 n, pcpc_330 m, estq_110 o, orion_exp_350 p " +
				" where n.numero_caixa = " + caixa +
				" and m.periodo_producao = n.periodo_producao " +
				" and m.ordem_producao = n.ordem_producao " +
				" and m.ordem_confeccao = n.ordem_confeccao " +
				" and m.sequencia = n.sequencia " +
				" and o.nivel = m.nivel " +
				" and o.grupo = m.grupo " +
				" and o.subgrupo = m.subgrupo " +
				" and o.item = m.item " +
				" and o.endereco between p.endereco_inicial and p.endereco_final " +
				" group by p.descricao ";

		try {
			area = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			area = "SEM ÁREA";
		}
		return area;
	}

	public List<CaixasEsteira> obterCaixasSortidas() {
		List<CaixasEsteira> listCaixas = null;

		String query = " select a.numero_caixa caixa, 'SORTIDA' area, (select count(*) from orion_131 where orion_131.numero_caixa = a.numero_caixa) quantidade "
				+
				" from orion_130 a " +
				" where a.situacao_caixa = 1 " +
				" and a.tipo_caixa = 1 ";
		try {
			listCaixas = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CaixasEsteira.class));
		} catch (Exception e) {
			listCaixas = new ArrayList<>();
		}
		return listCaixas;
	}

	public int obterQuantidadePendente(int numeroCaixa) {
		int quantidade = 0;

		String query = " select count(*) from orion_131 a " +
				" where a.numero_caixa = " + numeroCaixa;

		try {
			quantidade = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			quantidade = 0;
		}
		return quantidade;
	}

	public List<ConsultaRegraPrioridadeTipoCliente> findAllRegrasTipoClientePedido() {
		List<ConsultaRegraPrioridadeTipoCliente> listRegras = null;
		
		String query = " select b.tipo_cliente id, b.tipo_cliente || ' - ' || b.descr_tipo_clien tipoCliente, a.prioridade from orion_exp_330 a, pedi_085 b "
				+ " where b.tipo_cliente = a.tipo_cliente "
				+ " order by a.prioridade";

		try {
			listRegras = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRegraPrioridadeTipoCliente.class));
		} catch (Exception e) {
			listRegras = new ArrayList<>();
		}
		return listRegras;
	}

	public int retornaNumeroNotaEmpresa1(int numeroNotaEmp100) {
		int numeroNotaEmp1 = 0;

		String query = " select n.num_nota_fiscal from obrf_831 m, obrf_782 n " +
				" where m.num_nota_fiscal = " + numeroNotaEmp100 +
				" and n.numero_solicitacao = m.numero_solicitacao ";
		try {
			numeroNotaEmp1 = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			numeroNotaEmp1 = 0;
		}
		return numeroNotaEmp1;
	}

	public int validaVolumeDevolucao(int volume) {
		int volumeDevolucao = 0;

		String query = "SELECT 1 FROM PCPC_320 a " +
				" WHERE a.NUMERO_VOLUME = " + volume +
				" AND EXISTS (SELECT 1 FROM EXPE_003 e WHERE e.NOTA_FISCAL = a.NOTA_FISCAL AND e.STATUS not in (0,3)) ";

		try {
			volumeDevolucao = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			volumeDevolucao = 0;
		}
		return volumeDevolucao;
	}

	public int validaVolumeCancelado(int volume) {
		int volumeCancelado = 0;

		String query = " SELECT a.COD_CANCELAMENTO FROM PCPC_320 a " +
				" WHERE a.NUMERO_VOLUME = " + volume;

		try {
			volumeCancelado = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			volumeCancelado = 0;
		}
		return volumeCancelado;
	}

	public List<ConsultaMinutaTransporte> findMinutaReimpressao(int minuta) {
		String query = " select a.minuta, a.nota, a.serie, max(b.data_emissao) emissao, a.cliente, a.cidade, a.estado, (select count(*) from orion_exp_320 w " +
				"                                  where w.pedido = a.pedido " +
				"                                   and w.nota = a.nota  " +
				"                                   and w.serie = a.serie " +
				"                                   and w.minuta = a.minuta) caixas, " +
				" a.peso_bruto pesoBruto, a.valor_nota valorNota from orion_exp_320 a, fatu_050 b " +
				" where a.minuta = " + minuta +
				" and b.num_nota_fiscal = a.nota " +
				" and b.serie_nota_fisc = a.serie " +
				" group by a.minuta, a.nota, a.serie, a.cliente, a.cidade, a.estado, a.peso_bruto, a.valor_nota, a.pedido " +
				" order by a.nota ";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaMinutaTransporte.class));
	}

	public String obterTransportadoraMinutaGerada(int minuta) {
		String transportadora = "";

		String query = " select a.transportadora from orion_exp_320 a " +
				" where a.minuta = " + minuta +
				" group by a.transportadora ";
		try {
			transportadora = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			transportadora = "";
		}
		return transportadora;
	}

	public List<VolumeMinuta> findVolumesMinutaPorNotaPedido(int codNotaFiscal, int codPedido) {
		String query = " select a.numero_volume numeroVolume, a.data_montagem dataMontagem, a.hora_montagem horaMontagem, a.cod_usuario codUsuario, a.local_caixa localCaixa from pcpc_320 a "
				+ " where a.nota_fiscal = " + codNotaFiscal
				+ " and a.pedido_venda = " + codPedido;
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(VolumeMinuta.class));
	}

	public int findQuantMovimentacoesEntradaDiaUsuario(int usuario, String tipoMov) {

		Usuario dadosUsuario = usuarioRepository.findByIdUsuario(usuario);

		String query = " select count(*) qtdPecaLidaDiaUsuario from orion_exp_300 o "
				+ " where o.tipo = '" + tipoMov + "' "
				+ " and o.usuario = '"+ dadosUsuario.usuarioSystextil + "' "
				+ " and trunc(o.data_hora) = trunc(sysdate)";

		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public int findQuantidadeCaixasNoeEndereco(String endereco) {
		int quantEndereco = 0;

		String query = " select count(*) from orion_130 b " +
				" where b.endereco = '" + endereco + "' ";
		try {
			quantEndereco = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			quantEndereco = 0;
		}
		return quantEndereco;
	}

	public void LimparCaixa(int codCaixa) {
		String query = " delete from orion_131 a where a.numero_caixa = ? ";
		jdbcTemplate.update(query, codCaixa);
	}

	public int verificaTodosVolumesAlocadosByNotaAndPedido(int notaFiscal, int pedido) {
		int status = 0;


		String query = " select 1 from pcpc_320 c " +
				" where c.pedido_venda = " + pedido +
				" and c.nota_fiscal = " + notaFiscal +
				" and c.local_caixa = 9 " +
				" group by 1 ";
		try {
			status = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}

	public CapacidadesArtigoEndereco findCapacidadePorArtigoByReferencia(String referencia) {
		String query = " select w.quant_pecas_cesto pecasCesto, w.quant_perc_0 quant0, w.quant_perc_1 quant1, w.quant_perc_40 quant40, w.quant_perc_41 quant41, w.quant_perc_94 quant94, " +
				" w.quant_perc_95 quant95, w.quant_perc_99 quant99 from basi_030 v, orion_120 w " +
				" where w.artigo (+) = v.artigo " +
				" and v.referencia = '" + referencia + "' " +
				" and v.nivel_estrutura = '1' ";
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(CapacidadesArtigoEndereco.class));
	}
	
	public List<ConteudoChaveNumerica> findMotivosDevolucao() {
		
		String query = " SELECT a.codigo_motivo value, a.descricao label FROM efic_040 a WHERE a.cod_iso = 'D' GROUP BY a.codigo_motivo, a.descricao ORDER BY a.descricao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public int findQtdePecasLidasByUsuarioNf(int usuario) {
		
		int qtde = 0;

		String query = " SELECT COUNT(a.id) FROM orion_exp_200 a "
				+ "    WHERE a.usuario = " + usuario + " "
				+ "    AND a.data = TO_DATE(sysdate) ";
		
		try {
			qtde = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtde = 0;
		}
		return qtde;
	}
	
	public String findDataHora() {
		
		String data = "";

		String query = " SELECT TO_CHAR(sysdate, 'DD/MM/YYYY-HH:MM:SS') FROM dual ";
		
		try {
			data = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			data = "";
		}
		return data;
	}
	
	public List<ConsultaNotasTagsDevolucao> findAllDevolucao() {
		
		String query = " SELECT a.id id, "
				+ "       TO_CHAR(a.data, 'DD/MM/YYYY') data, "
				+ "       a.hora hora, "
				+ "       b.nome usuario, "
				+ "       a.nf_devolucao nfDevolucao, "
				+ "       DECODE(a.tipo_devolucao, 1, 'Conserto', "
				+ "       DECODE(a.tipo_devolucao, 2, '1º Qualidade', "
				+ "       DECODE(a.tipo_devolucao, 3, '2º Qualidade'))) tipoDevolucao, "
				+ "       c.descricao motivo, "
				+ "       d.descricao transacao, "
				+ "       a.cod_caixa caixa, "
				+ "       a.cod_barras_tag codBarrasTag, "
				+ "  (SELECT f.nivel_estrutura || '.' || f.grupo_estrutura || '.' || f.subgru_estrutura || '.' || f.item_estrutura || ' - ' || f.narrativa  "
				+ "                       FROM pcpc_330 e, basi_010 f "
				+ "                       WHERE f.nivel_estrutura = e.nivel "
				+ "                       AND f.grupo_estrutura = e.grupo "
				+ "                       AND f.subgru_estrutura = e.subgrupo "
				+ "                       AND f.item_estrutura = e.item "
				+ "                       AND e.periodo_producao = LPAD(SUBSTR(cod_barras_tag, 1, 4),  4, 0) "
				+ "                       AND e.ordem_producao = LPAD(SUBSTR(cod_barras_tag, 5, 9),  9, 0) "
				+ "                       AND e.ordem_confeccao = LPAD(SUBSTR(cod_barras_tag, 14, 5), 5, 0) "
				+ "                       AND e.sequencia = LPAD(SUBSTR(cod_barras_tag, 19, 4), 4, 0) "
				+ "                       ) produto "
				+ "     FROM orion_exp_200 a, efic_050 b, efic_040 c, estq_005 d "
				+ "     WHERE b.cracha_funcionario = a.usuario "
				+ "     AND c.codigo_motivo = a.cod_motivo "
				+ "     AND d.codigo_transacao = a.cod_transacao ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaNotasTagsDevolucao.class));
	}
	
	public List<ConsultaNotasTagsDevolucao> findDadosFiltrados(String dataInicial, String dataFinal) {
		
		String query = " SELECT a.id id, "
				+ "       TO_CHAR(a.data, 'DD/MM/YYYY') data, "
				+ "       a.hora hora, "
				+ "       b.nome usuario, "
				+ "       a.nf_devolucao nfDevolucao, "
				+ "       DECODE(a.tipo_devolucao, 1, 'Conserto', "
				+ "       DECODE(a.tipo_devolucao, 2, '1º Qualidade', "
				+ "       DECODE(a.tipo_devolucao, 3, '2º Qualidade'))) tipoDevolucao, "
				+ "       c.descricao motivo, "
				+ "       d.descricao transacao, "
				+ "       a.cod_caixa caixa, "
				+ "       a.cod_barras_tag codBarrasTag, "
				+ "  (SELECT f.nivel_estrutura || '.' || f.grupo_estrutura || '.' || f.subgru_estrutura || '.' || f.item_estrutura || ' - ' || f.narrativa  "
				+ "                       FROM pcpc_330 e, basi_010 f "
				+ "                       WHERE f.nivel_estrutura = e.nivel "
				+ "                       AND f.grupo_estrutura = e.grupo "
				+ "                       AND f.subgru_estrutura = e.subgrupo "
				+ "                       AND f.item_estrutura = e.item "
				+ "                       AND e.periodo_producao = LPAD(SUBSTR(cod_barras_tag, 1, 4),  4, 0) "
				+ "                       AND e.ordem_producao = LPAD(SUBSTR(cod_barras_tag, 5, 9),  9, 0) "
				+ "                       AND e.ordem_confeccao = LPAD(SUBSTR(cod_barras_tag, 14, 5), 5, 0) "
				+ "                       AND e.sequencia = LPAD(SUBSTR(cod_barras_tag, 19, 4), 4, 0) "
				+ "                       ) produto "
				+ "     FROM orion_exp_200 a, efic_050 b, efic_040 c, estq_005 d "
				+ "     WHERE b.cracha_funcionario = a.usuario "
				+ "     AND c.codigo_motivo = a.cod_motivo "
				+ "     AND d.codigo_transacao = a.cod_transacao"
				+ "     AND TO_CHAR(a.data, 'DD/MM/YYYY') BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' ";
		System.out.println(query);
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaNotasTagsDevolucao.class));
	}
	
	public void updateDepositoByTag(String codBarrasTag, int transacao, int deposito) {
		
		String query = " UPDATE pcpc_330 a "
				+ "       SET a.deposito = " + deposito + ", "
				+ "       a.transacao_cardex = " + transacao + ", "
				+ "       a.data_cardex = TO_DATE(sysdate),  "
				+ "       a.estoque_tag = 1 "
				+ "		WHERE a.periodo_producao = LPAD(SUBSTR(" + codBarrasTag + ", 1, 4),  4, 0) "
				+ "		AND a.ordem_producao = LPAD(SUBSTR(" + codBarrasTag + ", 5, 9),  9, 0) "
				+ "		AND a.ordem_confeccao = LPAD(SUBSTR(" + codBarrasTag + ", 14, 5), 5, 0) "
				+ "		AND a.sequencia = LPAD(SUBSTR(" + codBarrasTag + ", 19, 4), 4, 0) " ;
		
		jdbcTemplate.update(query, codBarrasTag);
	}
	
	public List<ConsultaTagsEReferenciasMapa> findTagsPreEnderecadas() {
		
		String query = " SELECT o.NUMERO_TAG TAG, a.ENDERECO ENDERECO FROM orion_130 a, ORION_131 o "
				+ "		WHERE o.NUMERO_CAIXA = a.NUMERO_CAIXA "
				+ "		AND a.ENDERECO IS NOT NULL "
				+ "		GROUP BY o.NUMERO_TAG , a.ENDERECO " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTagsEReferenciasMapa.class));
	}
	
	public List<ConsultaTagsEReferenciasMapa> findRefeferenciasPreEnderecadas() {
		
		String query = " SELECT a.ENDERECO, p.GRUPO referencia FROM orion_130 a, ORION_131 o, PCPC_330 p "
				+ "	  WHERE o.NUMERO_CAIXA = a.NUMERO_CAIXA "
				+ "	  AND p.PERIODO_PRODUCAO  = o.PERIODO_PRODUCAO "
				+ "	  AND p.ORDEM_PRODUCAO = o.ORDEM_PRODUCAO  "
				+ "   AND p.ORDEM_CONFECCAO = o.ORDEM_CONFECCAO "
				+ "	  AND p.SEQUENCIA = o.SEQUENCIA "
				+ "	  AND a.ENDERECO IS NOT NULL "
				+ "   GROUP BY a.ENDERECO, p.GRUPO " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTagsEReferenciasMapa.class));
	}
	
	public List<ConsultaTagsEReferenciasMapa> findReferenciaByTag() {
		String query = " SELECT o.numero_tag tag, p.grupo referencia FROM orion_130 a, ORION_131 o, pcpc_330 p "
				+ " WHERE o.NUMERO_CAIXA = a.NUMERO_CAIXA "
				+ "  and p.periodo_producao = LPAD(SUBSTR(o.numero_tag, 1, 4),  4, 0) "
				+ "  AND p.ordem_producao = LPAD(SUBSTR(o.numero_tag, 5, 9),  9, 0) "
				+ "  AND p.ordem_confeccao = LPAD(SUBSTR(o.numero_tag, 14, 5), 5, 0) "
				+ "  AND p.sequencia = LPAD(SUBSTR(o.numero_tag, 19, 4), 4, 0) "
				+ "  AND a.ENDERECO IS NOT NULL "
				+ "  GROUP BY o.numero_tag, p.grupo ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTagsEReferenciasMapa.class));
	}
}