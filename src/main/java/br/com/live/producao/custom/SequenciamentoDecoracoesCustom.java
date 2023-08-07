package br.com.live.producao.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.producao.model.DadosSequenciamentoDecoracoes;
import br.com.live.producao.model.OrdemProducaoEstagio;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;

@Repository
public class SequenciamentoDecoracoesCustom {

	public static final int ESTAGIO_AGRUPADOR_DECORACOES = 10; 
	public static final String ESTAGIOS_DISTRIB_DECORACOES = "9,31"; 
	public static final double MINUTOS_PRODUCAO_DIA = 984; // Turno1: 496 + Turno2: 488 => 984 minutos 	
	public static final int ORDEM_CONFIRMAR = 0;
	public static final int ORDEM_CONFIRMADA = 1;
	public static final String CAMPOS_DADOS_SEQUENCIAMENTO = " a.id,a.sequencia seqPrioridade,a.ordem_producao ordemProducao,a.referencia referencia,b.descr_referencia descricaoReferencia,a.cores,a.quantidade,c.observacao,a.cod_estagio codEstagioProx,d.descricao descEstagioProx,a.estagios_agrupados estagiosAgrupados,a.endereco,a.data_entrada dataEntrada, (a.data_entrada - trunc(sysdate)) diasNaFase, a.tempo_unit tempoUnitario,a.tempo_total tempoTotal,a.data_inicio dataInicio,a.data_termino dataTermino,a.confirmado ";
	
	private final JdbcTemplate jdbcTemplate;
	private final ParametrosRepository parametrosRepository;

	public SequenciamentoDecoracoesCustom(JdbcTemplate jdbcTemplate, ParametrosRepository parametrosRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.parametrosRepository = parametrosRepository;
	}

	private String getEstagiosConfiguradosParaSequenciar() {
		Parametros parametros = parametrosRepository.findByIdParametro("SEQ-DECORACOES-ESTAGIOS-PARA-SEQUENCIAR");
		if (parametros != null) return parametros.getValorStr(); 
		return "";
	}
	
	public List<ConteudoChaveNumerica> findEstagiosSequenciamento() {		
		String query = "select m.codigo_estagio value, m.codigo_estagio || ' - ' || m.descricao label from mqop_005 m where m.codigo_estagio in ("+ getEstagiosConfiguradosParaSequenciar() +") order by m.codigo_estagio";
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
	}
	
	public List<ConteudoChaveAlfaNum> findReferenciasEmOrdensCentroDistrib() {				
		String query = " select a.referencia_peca value, a.referencia_peca || ' - ' || c.descr_referencia label "  
		+ " from pcpc_020 a, basi_030 c "
		+ " where a.cod_cancelamento = 0 "
    	+ " and exists (select 1 from pcpc_040 b "
        + " where b.ordem_producao = a.ordem_producao " 
        + " and b.codigo_estagio in (" + ESTAGIOS_DISTRIB_DECORACOES + ") " // CENTRO DISTRIBUIÇÃO - DECORAÇÕES
        + " and b.qtde_disponivel_baixa > 0) "
		+ " and c.nivel_estrutura = '1' "
		+ " and c.referencia = a.referencia_peca "
		+ " group by a.referencia_peca, c.descr_referencia " 
		+ " order by a.referencia_peca, c.descr_referencia ";		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
	}

	public List<OrdemProducaoEstagio> findEstagiosDecoracoesOrdem(int ordemProducao, int estagioDistrib) {
		List<OrdemProducaoEstagio> estagios;
		String query = "select a.ordem_producao ordemProducao, a.seq_operacao seqEstagio, a.codigo_estagio codEstagio, a.estagio_anterior codEstagioAnterior, a.estagio_depende codEstagioDepende, sum(a.qtde_a_produzir_pacote) qtdeAProduzir from pcpc_040 a "
		+ " where a.ordem_producao = ? "
		+ " and a.qtde_a_produzir_pacote > 0 "
		+ " and (a.codigo_estagio in (select m.codigo_estagio from mqop_005 m where m.est_agrup_est = ?) or a.codigo_estagio = ?)"
		+ " and a.seq_operacao >= (select min(z.seq_operacao) from pcpc_040 z where z.ordem_producao = a.ordem_producao and z.codigo_estagio = ?) "
		+ " group by a.ordem_producao, a.seq_operacao, a.codigo_estagio, a.estagio_anterior, a.estagio_depende "
		+ " order by a.ordem_producao, a.seq_operacao, a.codigo_estagio "; 

		try {
			estagios = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(OrdemProducaoEstagio.class), ordemProducao, ESTAGIO_AGRUPADOR_DECORACOES, estagioDistrib, estagioDistrib);
		} catch (Exception e) {
			estagios = new ArrayList<OrdemProducaoEstagio>();
		}		
		return estagios;
	}		
	
	public boolean estagioDistribuicaoEmAberto(int ordemProducao, int codEstagio) {
		int encontrou;

		String query = "select 1 from pcpc_040 z "
		+ " where z.ordem_producao = ? "
		+ " and z.codigo_estagio in ("+ ESTAGIOS_DISTRIB_DECORACOES +") "
		+ " and z.qtde_em_producao_pacote > 0 "                            
		+ " and z.seq_operacao <= (select y.seq_operacao from pcpc_040 y "  
		+ " where y.ordem_producao = z.ordem_producao "
		+ " and y.ordem_confeccao = z.ordem_confeccao "
		+ " and y.codigo_estagio = ?) " 
		+ " and rownum = 1 ";

		try {
			encontrou = jdbcTemplate.queryForObject(query, Integer.class, ordemProducao, codEstagio);
		} catch (Exception e) {
			encontrou = 0;
		}
		
		return (encontrou == 1);		
	}
	
	public String findEnderecoDistribuicao(int ordemProducao) {
		String query = " select nvl(max(e.box),'') endereco from dist_050 d, dist_052 e "
		+ " where d.ordem_producao = ? "
		+ " and e.endereco = d.endereco ";

		return jdbcTemplate.queryForObject(query, String.class, ordemProducao);
	}
		
	public boolean isOrdemSequenciada(int ordemProducao, int codEstagio) {
		Integer count = 0;		
		String query = "select count(*) from orion_cfc_300 o where o.ordem_producao = ? and o.cod_estagio = ?";
		count = jdbcTemplate.queryForObject(query, Integer.class, ordemProducao, codEstagio);				
		return (count > 0);		
	}

	public DadosSequenciamentoDecoracoes findSequenciamentoDecoracoesById(int id) {
		String query = " select " + CAMPOS_DADOS_SEQUENCIAMENTO 
	    + " from orion_cfc_300 a, basi_030 b, pcpc_020 c, mqop_005 d "
	    + " where a.id = ? "	    
	    + " and b.nivel_estrutura = '1' "
	    + " and b.referencia = a.referencia "
	    + " and c.ordem_producao = a.ordem_producao "
	    + " and d.codigo_estagio = a.cod_estagio "	    
	    + " and exists (select 1 from pcpc_040 p "
	    + " where p.ordem_producao = a.ordem_producao "
        + " and p.codigo_estagio = a.cod_estagio "
        + " and p.qtde_a_produzir_pacote > 0) "                        
        + " order by a.sequencia" ;
		return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(DadosSequenciamentoDecoracoes.class), id);
	}
	
	public List<DadosSequenciamentoDecoracoes> findOrdensSequenciadas(int codEstagio) {
		String query = " select " + CAMPOS_DADOS_SEQUENCIAMENTO 
	    + " from orion_cfc_300 a, basi_030 b, pcpc_020 c, mqop_005 d "
	    + " where a.cod_estagio = ? "	    
	    + " and b.nivel_estrutura = '1' "
	    + " and b.referencia = a.referencia "
	    + " and c.ordem_producao = a.ordem_producao "
	    + " and d.codigo_estagio = a.cod_estagio "	    
	    + " and exists (select 1 from pcpc_040 p "
	    + " where p.ordem_producao = a.ordem_producao "
        + " and p.codigo_estagio = a.cod_estagio "
        + " and p.qtde_a_produzir_pacote > 0) "                        
        + " order by a.sequencia" ;
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(DadosSequenciamentoDecoracoes.class), codEstagio);
	}
	
	public String findDescEstagiosDecoracoesConcatenadosAProduzir(int ordemProducao, int ordemConfeccao) {
		String concatEstagios = "";
		List<String> listDescEstagios;
		
		String query = " select b.descricao from pcpc_040 a, mqop_005 b " 
		+ " and where a.ordem_producao = ? "
		+ " and a.ordem_confeccao = ? "
		+ " and a.qtde_a_produzir_pacote > 0 "    
		+ " and a.codigo_estagio in (select m.codigo_estagio from mqop_005 m where m.est_agrup_est = ?) "
		+ " and b.codigo_estagio = a.codigo_estagio "
		+ " group by a.sequencia_estagio, a.seq_operacao, a.codigo_estagio, b.descricao "
		+ " order by a.sequencia_estagio, a.seq_operacao, a.codigo_estagio, b.descricao ";
		
		try {
			listDescEstagios = jdbcTemplate.queryForList(query, String.class, ordemProducao, ordemConfeccao, ESTAGIO_AGRUPADOR_DECORACOES);
		} catch (Exception e) {
			listDescEstagios = new ArrayList<String>();
		}
		
		for (String estagio : listDescEstagios) {
			if (concatEstagios.isEmpty()) concatEstagios = estagio;
			else concatEstagios += estagio + " + "; 
		}
		
		return concatEstagios; 
	}

	public int findNextId() {
		String query="select nvl(max(id),0) + 1 from orion_cfc_300";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int findNextSeqProducao() {
		String query="select nvl(max(sequencia),0) + 1 from orion_cfc_300";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int findNextSeqProducaoByEstagio(int codEstagio) {		
		String query="select nvl(max(sequencia),0) from orion_cfc_300 where cod_estagio = ?";
		return jdbcTemplate.queryForObject(query, Integer.class, codEstagio);
	}

	public int findLastSeqProducaoByEstagioDesconsiderandoOrdens(int codEstagio, String ordens) {		
		String query="select nvl(max(sequencia),0) from orion_cfc_300 where cod_estagio = ? and ordem_producao not in("+ ordens +")";
		return jdbcTemplate.queryForObject(query, Integer.class, codEstagio);
	}
	
	public void saveSequenciamento(int id, int sequencia, int periodo, int ordemProducao, String referencia, String cores, int codEstagio, int quantidade, String estagiosAgrupados, String endereco, Date dataEntrada, double tempoUnit, double tempoTotal, Date dataInicio, Date dataTermino) {		
		String query = "insert into orion_cfc_300 (id, sequencia, periodo, ordem_producao, referencia, cores, cod_estagio, quantidade, estagios_agrupados, endereco, data_entrada, tempo_unit, tempo_total, data_inicio, data_termino) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(query, id, sequencia, periodo, ordemProducao, referencia, cores, codEstagio, quantidade, estagiosAgrupados, endereco, dataEntrada, tempoUnit, tempoTotal, dataInicio, dataTermino);
	}
	
	public void saveSequenciamento(int id, int sequencia, Date dataInicio, Date dataTermino) {
		String query = "update orion_cfc_300 set sequencia = ?, data_inicio = ?, data_termino = ? where id = ?";
		jdbcTemplate.update(query, sequencia, dataInicio, dataTermino, id);
	}
	
	public void saveSequenciamento(int id, int confirmado) {
		String query = "update orion_cfc_300 set confirmado = ? where id = ?";
		jdbcTemplate.update(query, confirmado, id);
	}
	
	public void deleteOrdemProducao(int id) {
		String query = "delete from orion_cfc_300 where id = ?";
		jdbcTemplate.update(query, id);
	}	
}
