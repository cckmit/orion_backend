package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaConsumoMetragem;
import br.com.live.model.ConsultaOperacaoXMicromovimentos;
import br.com.live.model.ConsultaTabelaConsumo;
import br.com.live.model.ConsultaTempoMaquinaCM;
import br.com.live.model.ConsultaTipoPonto;
import br.com.live.model.ConsultaTipoPontoFio;
import br.com.live.model.ConsultaTiposFio;
import br.com.live.model.Maquinas;
import br.com.live.model.Operacao;
import br.com.live.model.OptionProduto;
import br.com.live.model.Produto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class EngenhariaCustom {

	private JdbcTemplate jdbcTemplate;

	public EngenhariaCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int findNewIdMarcas() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_080 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findNewIdTipos() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_081 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findIdNewTipoPonto() {

		int proxCod = 0;

		String query = " select nvl(max(id),0) +1 from orion_082 ";

		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findIdNewSequenciaPontoFio(int idTipoPonto) {

		int proxCod = 0;

		String query = " select nvl(max(sequencia),0) +1 from orion_083 a" + " where a.id_tipo_ponto = " + idTipoPonto;
		try {
			proxCod = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxCod = 0;
		}
		return proxCod;
	}

	public int findProxSequenciaTipoPonto(int idTipoPonto, String referencia) {
		int proxSeq = 0;

		String query = " select nvl(max(a.sequencia), 0)+ 1 from orion_084 a "
		+ " where a.referencia = '" + referencia + "'"
		+ " and a.id_tipo_ponto = " + idTipoPonto;

		try {
			proxSeq = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proxSeq = 0;
		}

		return proxSeq;
	}

	public List<ConsultaTiposFio> findAllTiposFio() {
		String query = " select a.id, a.descricao, a.titulo, a.centim_cone centimetrosCone, a.centim_cone2 centimetrosCone2, a.centim_cone3 centimetrosCone3 from orion_081 a ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTiposFio.class));
	}

	public List<Maquinas> findAllMaquinas() {
		String query = " select a.grupo_maquina || '.' || b.subgrupo_maquina codigo, a.nome_grupo_maq || ' ' || b.nome_sbgrupo_maq descricao from mqop_010 a, mqop_020 b "
				+ " where a.grupo_maquina = b.grupo_maquina " + " and a.grupo_maquina not like '%.%' "
				+ " order by a.grupo_maquina, b.subgrupo_maquina ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Maquinas.class));
	}

	public List<Maquinas> findAllMaquinasEstamparia() {
		String query = " select a.grupo_maquina || '.' || b.subgrupo_maquina codigo, a.nome_grupo_maq || ' - ' || b.nome_sbgrupo_maq descricao"
		+ " from mqop_010 a, mqop_020 b, mqop_030 c "
		+ " where b.grupo_maquina = a.grupo_maquina "
		+ " and c.maq_sub_grupo_mq = b.grupo_maquina "
		+ " and c.maq_sub_sbgr_maq = b.subgrupo_maquina "
		+ " and c.letra_agrup = 'E' "
		+ " group by a.grupo_maquina, b.subgrupo_maquina, a.nome_grupo_maq, b.nome_sbgrupo_maq "
		+ " order by a.grupo_maquina, b.subgrupo_maquina ";		
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Maquinas.class));
	}		
	
	public List<OptionProduto> findAllFios() {
		String query = " select n.basi030_nivel030 || '.' || n.basi030_referenc || '.' || n.tamanho_ref codigo, b.descr_referencia || ' ' || n.descr_tam_refer descricao from basi_020 n, basi_030 b "
				+ " where n.basi030_nivel030 = '9' " + " and b.nivel_estrutura = n.basi030_nivel030 "
				+ " and b.referencia = n.basi030_referenc " + " and b.conta_estoque = 63 ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OptionProduto.class));
	}

	public List<ConsultaTipoPontoFio> findTipoPontoFio(int idTipoPonto) {
		String query = " select a.id, a.descricao, a.sequencia, a.consumo_fio consumoFio "
				+ " from orion_083 a "
				+ " where a.id_tipo_ponto = " + idTipoPonto;

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPontoFio.class));
	}

	public ConsultaTipoPonto findTipoPonto(int idTipoPonto) {
		String query = " select l.id, l.descricao from orion_082 l "
				+ " where l.id = " + idTipoPonto;

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConsultaTipoPonto.class));
	}

	public List<ConsultaTabelaConsumo> findConsumoByReferencia(String referencia) {
		String query = " select a.id, a.id_tipo_ponto tipoPonto, a.comprimento_costura comprimentoCostura, b.descricao from orion_084 a, orion_082 b "
				+ " where a.referencia = '" + referencia + "'" + " and a.id_tipo_ponto = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTabelaConsumo.class));
	}

	public OptionProduto findReferenciaById(String referencia) {
		String query = " select v.referencia codigo, v.descr_referencia descricao from basi_030 v "
				+ " where v.nivel_estrutura = '1' "
				+ " and v.referencia = '" + referencia + "'";

		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(OptionProduto.class));
	}

	public List<ConsultaTabelaConsumo> findAllReferenciasSalvas() {
		String query = " select o.referencia id, d.descr_referencia descricao from orion_084 o, basi_030 d "
				+ " where d.referencia (+) = o.referencia group by o.referencia, d.descr_referencia";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTabelaConsumo.class));
	}

	public void deleteConsumo(String referencia, int tipoPonto) {
		String queryDelete = " delete from  orion_085 u where u.referencia = '" + referencia + "'"
				+ " and u.id_tipo_ponto = " + tipoPonto;

		jdbcTemplate.update(queryDelete);
	}

	public void deleteConsumoByIdReferencia(String idReferencia) {
		String queryDelete = " delete from orion_085 u where u.id_referencia = '" + idReferencia + "'";

		jdbcTemplate.update(queryDelete);
	}

	public List<ConsultaConsumoMetragem> ConsultaConsumoMetragem(String idConsumo) {
		String query = " select n.id, n.sequencia, n.pacote, n.observacao, n.metragem_costura_cm consumoFio, n.id_tipo_fio tipoFio, p.descricao descTipoFio, n.metragem_total metragemTotal, n.metragem_um metragemUm, o.descricao descOperacao from orion_085 n, orion_082 m, orion_083 o, orion_081 p "
				+ " where n.id_referencia = '" + idConsumo + "'"
				+ " and m.id = n.id_tipo_ponto "
				+ " and o.id_tipo_ponto = m.id "
				+ " and p.id (+) = n.id_tipo_fio "
				+ " and n.sequencia = o.sequencia ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaConsumoMetragem.class));
	}

	public List<ConsultaConsumoMetragem> ConsultaResumoPorReferencia(String referencia) {
		String query = " select p.descricao descTipoFio, SUM(b.metragem_total) metragemTotal, SUM(b.metragem_um) metragemUm from orion_085 b, orion_082 m, orion_083 o, orion_081 p "
				+ " where m.id = b.id_tipo_ponto "
				+ " and o.id_tipo_ponto = m.id "
				+ " and p.id = b.id_tipo_fio "
				+ " and b.referencia = '" + referencia + "'"
				+ " and b.sequencia = o.sequencia "
				+ " group by p.descricao ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaConsumoMetragem.class));
	}

	public List<OptionProduto> findOptionsTiposFio() {
		String query = "select a.id codigo, a.descricao from orion_081 a "
				+ " union "
				+ " select 0 codigo, 'NENHUM' descricao from dual ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OptionProduto.class));
	}
	
	public List<ConteudoChaveAlfaNum> findOptiosGrupo() {
		String query = "SELECT a.grupo_maquina || '.' || a.subgrupo_maquina || ' - ' || a.nome_sbgrupo_maq || ' ' || b.nome_grupo_maq LABEL, "
				+ "a.grupo_maquina || '.' || a.subgrupo_maquina VALUE FROM mqop_020 a, mqop_010 b "
				+ "WHERE b.grupo_maquina = a.grupo_maquina";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}
	
	public List<ConsultaTempoMaquinaCM> findAllTempoMaquinaCM(){
		
		String query = "select a.id, a.grupo, b.nome_grupo_maq nomeGrupoMaq, a.subgrupo, c.nome_sbgrupo_maq nomeSbgrupoMaq, a.medida, a.tempo, c.interferencia "
			+ " FROM orion_eng_240 a, mqop_010 b, mqop_020 c "
			+ " WHERE b.grupo_maquina = a.grupo "
			+ " AND c.grupo_maquina = a.grupo "
			+ " AND c.subgrupo_maquina = a.subgrupo "
			+ " ORDER BY a.grupo, a.subgrupo";
	
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaTempoMaquinaCM.class));
	}
	
	public long nextId() {
	
		String query = "select NVL((MAX(a.id)), 0) + 1 from orion_eng_240 a";
				
		return jdbcTemplate.queryForObject(query, Long.class);
	}
	
	public Operacao findOperacaoByCodOperacao (int operacao) {
	
		String query = " select a.codigo_operacao codigo,a.nome_operacao descricao,a.grupo_maquinas grupoMaquina,a.sub_maquina subMaquina,b.nome_grupo_maq || ' ' || c.nome_sbgrupo_maq descMaquina,c.interferencia "
				+ " from mqop_040 a, mqop_010 b, mqop_020 c "
				+ " where a.codigo_operacao = " + operacao  
				+ " and b.grupo_maquina (+) = a.grupo_maquinas "
				+ " and c.grupo_maquina (+) = a.grupo_maquinas "
				+ " and c.subgrupo_maquina (+) = a.sub_maquina ";
		
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Operacao.class));
	}
	
	public List<ConteudoChaveNumerica> findAllCodOperacao(String codOp) {
		String query = "select a.codigo_operacao value, a.codigo_operacao || ' - ' || a.nome_operacao label from mqop_040 a "
				+ " where a.codigo_operacao || a.nome_operacao LIKE '%" + codOp + "%'"
				+ " order by a.codigo_operacao";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConsultaOperacaoXMicromovimentos> findAllOperacaoXMicromv(int operacao){
			
		String query = "SELECT a.id, "
				+ " a.sequencia, "
				+ " a.tipo, "
				+ " DECODE(a.tipo, 1, b.id || ' - ' || b.descricao,c.grupo || '.' || c.subgrupo || ' - ' || d.nome_grupo_maq || ' ' || e.nome_sbgrupo_maq || ' - MEDIDA: ' || c.medida || ' CM') informacao, "
				+ " NVL(DECODE(a.tipo, 1, b.tempo, c.tempo), 0) tempo, "
				+ " NVL(DECODE(a.tipo, 1, b.interferencia, e.interferencia), 0) interferencia, "
				+ " a.id_micromovimento idMicromovimento, "
				+ " a.id_tempo_maquina idTempoMaquina, "
				+ " NVL(DECODE(a.tipo, 1, ((b.interferencia / 100) * b.tempo) + b.tempo, ((e.interferencia / 100) * c.tempo) + c.tempo), 0) tempo_normal "
				+ " FROM orion_eng_260 a, orion_eng_230 b, orion_eng_240 c, mqop_010 d, mqop_020 e "
				+ " WHERE a.cod_operacao = " + operacao
				+ " AND b.id (+) = a.id_micromovimento "
				+ " AND c.id (+) = a.id_tempo_maquina "
				+ " AND d.grupo_maquina (+) = c.grupo "
				+ " AND e.grupo_maquina (+) = c.grupo "
				+ " AND e.subgrupo_maquina (+) = c.subgrupo "
				+ " ORDER BY a.sequencia ";
	
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaOperacaoXMicromovimentos.class));
	}
	
	public boolean existsMicromovimento(String idMicromovimento) {
		int encontrou = 0;
		
		String query = " SELECT 1 FROM orion_eng_260 b WHERE b.id_micromovimento = ? ";
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class, idMicromovimento);
		} catch (Exception e) {
			encontrou = 0;
		}
		
		return (encontrou == 1);		
	}
	
	public boolean existsTempoMaquina(long idtempoMaquina) {
		int encontrou = 0;
		
		String query = " SELECT 1 FROM orion_eng_260 b WHERE b.id_tempo_maquina = ? ";
		
		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class, idtempoMaquina);
		} catch (Exception e) {
			encontrou = 0;
		}
		
		return (encontrou == 1);		
	}
	
	public int findNextSequecia(int operacao) {
		int sequencia = 0;
		
		String query = "select NVL(MAX(a.sequencia), 0) + 5 from orion_eng_260 a "
				+ " where a.cod_operacao = " + operacao;
		try {
			sequencia = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			sequencia = 0;
		}
		return sequencia;
	}
	
	public void atualizarTempoHomem(int operacao, float tempoTotal) {
		
		String query = " UPDATE mqop_040 a SET a.tempo_homem = ? "
				+ " WHERE a.codigo_operacao = ? ";		
		
		jdbcTemplate.update(query, tempoTotal, operacao);
	}
	
	public float findInterferenciaTempoMaq(String grupoMaquina, String subGrupoMaquina) {
		float interferencia = 0;
		
		String query = " SELECT m.interferencia FROM mqop_020 m "
				+ " WHERE m.grupo_maquina = ? "
				+ " AND m.subgrupo_maquina = ? ";
		
		interferencia = jdbcTemplate.queryForObject(query, Float.class, grupoMaquina, subGrupoMaquina);
		
		return interferencia;
	}

	public float findTempoTotalOperacao(int codOp) {
		float tempoTotal = 0;
		
		String query = " SELECT n.tempo_homem FROM mqop_040 n WHERE n.codigo_operacao = ? ";
		
		tempoTotal = jdbcTemplate.queryForObject(query, Float.class, codOp);
		
		return tempoTotal;
	}
	
	public float findTempoNormalOperacao(int codOp) {
		float tempoNormal = 0;
		String query = "SELECT NVL(SUM(DECODE(a.tipo, 1, ((b.interferencia / 100) * b.tempo) + b.tempo, ((e.interferencia / 100) * c.tempo) + c.tempo)), 0) tempo_total "
				+ "  FROM orion_eng_260 a, orion_eng_230 b, orion_eng_240 c, mqop_020 e "
				+ "  WHERE a.cod_operacao = " + codOp
				+ "  AND b.id (+) = a.id_micromovimento "
				+ "  AND c.id (+) = a.id_tempo_maquina "
				+ "  AND e.grupo_maquina (+) = c.grupo "
				+ "  AND e.subgrupo_maquina (+) = c.subgrupo ";
		
		tempoNormal = jdbcTemplate.queryForObject(query, Float.class);
		
		return tempoNormal;
	}
	
	public List<Produto> findProdutosEnviarFichaDigital() {
		List<Produto> listaProdutos = null;
		
		String query = " select a.referencia , b.descr_referencia narrativa "
		+ " from quantich.ftecnica_referencia_integra a, basi_030 b "
		+ " where b.nivel_estrutura = a.nivel "
  	    + " and b.referencia = a.referencia "
  	    + " and a.flag_integra = 0 "
		+ " order by a.referencia "
  	    ;
		
		try {
			listaProdutos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Produto.class));
		} catch (Exception e) {
			listaProdutos = new ArrayList<Produto>();
		}
		
		return listaProdutos;
	}
	
	public void atualizarFichaDigital(String referencia) {		
		String query = " update quantich.ftecnica_referencia_integra a set a.flag_integra = 1 "
		+ " where nivel = '1' "
		+ " and referencia = ? ";
		
		jdbcTemplate.update(query, referencia);
	}
}
