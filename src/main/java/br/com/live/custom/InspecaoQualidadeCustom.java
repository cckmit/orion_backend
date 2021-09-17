package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.model.ConsultaInspecaoQualidLanctoMedidas;
import br.com.live.model.ConsultaInspecaoQualidLanctoPecas;
import br.com.live.model.MotivoRejeicao;
import br.com.live.model.TipoMedida;
import br.com.live.util.FormataCNPJ;

@Repository
public class InspecaoQualidadeCustom {

	private final JdbcTemplate jdbcTemplate;
	
	public InspecaoQualidadeCustom (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
	
	public List<TipoMedida> findTiposMedidasByReferencia(String referencia) {
		
		String query = " select b.codigo, b.descricao from basi_065 a, hdoc_001 b "
		+ " where a.referencia  = '" + referencia + "'"
		+ " and b.codigo = a.tipo_medida " 
		+ " and b.tipo = 64 "
		+ " and b.descricao like '%PEÇA PRONTA%' "
		+ " group by b.codigo, b.descricao "
		+ " order by b.codigo, b.descricao "; 

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(TipoMedida.class));		
	}
	
	public List<MotivoRejeicao> findAllMotivos() {
		
		String query = " select motivos.codigo_motivo codMotivo, motivos.desc_motivo descMotivo, motivos.codigo_estagio codEstagio, motivos.desc_estagio descEstagio "
		+ " from (select 0 codigo_motivo, 'SEM DEFEITO' desc_motivo, 0 codigo_estagio, '.' desc_estagio from dual "
		+ " union "
		+ " select efic_040.codigo_motivo, efic_040.descricao desc_motivo, efic_040.codigo_estagio, mqop_005.descricao desc_estagio " 
		+ " from efic_040, mqop_005 "
		+ " where efic_040.nivel = '1' "     
		+ " and efic_040.area_producao = 1 "       
		+ " and efic_040.cod_defeito_agrup not in (0,1) "
		+ " and mqop_005.codigo_estagio = efic_040.codigo_estagio "
		+ " group by efic_040.codigo_motivo, efic_040.descricao, efic_040.codigo_estagio, mqop_005.descricao) motivos "
		+ " order by motivos.codigo_motivo, motivos.desc_motivo, motivos.codigo_estagio, motivos.desc_estagio " ;     

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(MotivoRejeicao.class));
	}
	
	public List<InspecaoQualidadeLanctoMedida> findMedidasByReferenciaTamanhoTipo(String referencia, String tamanho, int tipoMedida) {
		
		String query = " select 0 idInspecao, 0 idLancamento, a.sequencia, a.descricao, a.medida medidaPadrao, a.tolerancia_max toleranciaMaxima, a.tolerancia_min toleranciaMinima "
		+ " from basi_065 a "
		+ " where a.referencia = '" + referencia + "'"
		+ " and a.tamanho = '" + tamanho + "'"
		+ " and a.tipo_medida = " + tipoMedida
		+ " order by a.sequencia ";  

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(InspecaoQualidadeLanctoMedida.class));				
	}
	
	public List<InspecaoQualidadeLanctoMedida> findMedidasByIdInspecaoIdLancamento(long idInspecao, long idLancamento) {
		
		String query = " select a.id_inspecao idInspecao, a.id_lancamento idLancamento, a.sequencia, a.descricao, a.medida_padrao medidaPadrao, a.toler_maxima toleranciaMaxima, a.toler_minima toleranciaMinima, a.medida_real medidaReal, variacao " 
		+ " from orion_052 a "
		+ " where a.id_inspecao = " + idInspecao
		+ " and a.id_lancamento = " + idLancamento
		+ " order by a.sequencia " ;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(InspecaoQualidadeLanctoMedida.class));				
	}
		
	public long findNextIdInspecao() {
		String query = " select nvl(max(id_inspecao),0) + 1 from orion_050 ";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public long findNextIdLancamentoPeca() {		
		String query = " select nvl(max(id_lancamento),0) + 1 from orion_051 ";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	public long findNextIdLancamentoMedida(long idInspecao) {					
		String query = " select nvl(max(a.id_lancamento),0) + 1 from orion_052 a where a.id_inspecao = " + idInspecao;
		return jdbcTemplate.queryForObject(query, Integer.class);
	}	

	public long findNextIdMedida(long idInspecao, long idLancamento, int sequencia) {
		
		long id;
		String query = "";
				
		try {
			query = " select a.id from orion_052 a " 
			+ " where a.id_inspecao = " + idInspecao
			+ " and a.id_lancamento = " + idLancamento 
			+ " and a.sequencia = " + sequencia ;

			id = jdbcTemplate.queryForObject(query, Integer.class);			
		} catch (Exception e) {
			query = " select nvl(max(a.id),0) + 1 from orion_052 a ";			
			id = jdbcTemplate.queryForObject(query, Integer.class);
		}		
		
		return id;
	}	
	
	public int getQtdeInspecionadaPeca(long idInspecao) {
		
		String query = " select nvl(sum(a.quantidade),0) from orion_051 a "
		+ " where a.id_inspecao = " + idInspecao;		
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int getQtdeInspecionadaMedida(long idInspecao) {		
		
		String query = " select count(*) from (select a.id_lancamento from orion_052 a " 
        + " where a.id_inspecao = " + idInspecao
        + " group by a.id_lancamento) lancamentos " ;
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public int getQtdeRejeitadaPeca(long idInspecao) {
		
		String query = " select nvl(sum(a.quantidade),0) from orion_051 a "
		+ " where a.id_inspecao = " + idInspecao
		+ "  and a.cod_motivo  > 0 " ;
		
		return jdbcTemplate.queryForObject(query, Integer.class);
	}	

	public int getQtdeRejeitadaMedida(long idInspecao) {
		
		String query = " select count(*) from (select a.id_lancamento from orion_052 a " 
		+ " where a.id_inspecao = " + idInspecao
		+ "	and (a.variacao > 0 and a.variacao > a.toler_minima) "
		+ "	group by a.id_lancamento "
		+ "	union "
		+ "	select a.id_lancamento from orion_052 a " 
		+ "	where a.id_inspecao = " + idInspecao
		+ "	and (a.variacao < 0 and (a.variacao * -1) > a.toler_maxima) "
		+ "	group by a.id_lancamento "
		+ "	) lancamentos ";
				  
		return jdbcTemplate.queryForObject(query, Integer.class);
	}	
	
	public List<ConsultaInspecaoQualidLanctoPecas> findLancamentoPecasByIdInspecao(long idInspecao) {
		
		List<ConsultaInspecaoQualidLanctoPecas> lancamentos;
		
		String query = " select a.id_lancamento id, a.cod_motivo || ' - ' || nvl(b.descricao, 'SEM DEFEITO') motivo, " 
	    + " b.codigo_estagio || ' - ' || c.descricao estagio, "  
	    + " a.quantidade, a.usuario, a.data_hora " 
	    + " from orion_051 a, efic_040 b, mqop_005 c "
	    + " where a.id_inspecao = " + idInspecao
	    + " and b.codigo_motivo  (+) = a.cod_motivo "
	    + " and c.codigo_estagio (+) = b.codigo_estagio "
	    + " order by a.id_lancamento " ;
		
		try {
			lancamentos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaInspecaoQualidLanctoPecas.class));
		} catch (Exception e) {
			lancamentos = new ArrayList<ConsultaInspecaoQualidLanctoPecas>();
		}
		
		return lancamentos;
	}
	
	public List<ConsultaInspecaoQualidLanctoMedidas> findLancamentoMedidasByIdInspecao(long idInspecao) {
		
		List<ConsultaInspecaoQualidLanctoMedidas> lancamentos;
				
		String query = " select orion_052.id_inspecao idInspecao, orion_052.id_lancamento idLancamento, max(orion_052.tipo_medida) tipoMedida, "      
	    + " max(nvl(lancamentos.qtde_fora_toler,0)) qtdeMedidaForaTolerancia, " 
	    + " max(orion_052.usuario) usuario, " 
	    + " max(orion_052.data_hora) dataHora "
	    + " from orion_052, (select agrup_lancamentos.id_inspecao, " 
	    + " agrup_lancamentos.id_lancamento, " 
	    + " sum(agrup_lancamentos.qtde_fora_toler) qtde_fora_toler "
	    + " from (select a.id_inspecao, a.id_lancamento, count(*) qtde_fora_toler from orion_052 a " 
	    + " where (a.variacao > 0 and a.variacao > a.toler_minima) "
	    + " group by a.id_inspecao, a.id_lancamento "
	    + " union all "
	    + " select a.id_inspecao, a.id_lancamento, count(*) qtde_fora_toler from orion_052 a " 
	    + " where (a.variacao < 0 and (a.variacao * -1) > a.toler_maxima) "
	    + " group by a.id_inspecao, a.id_lancamento) agrup_lancamentos "
	    + " group by agrup_lancamentos.id_inspecao, agrup_lancamentos.id_lancamento "
	    + " ) lancamentos "
	    + " where orion_052.id_inspecao = " + idInspecao
	    + " and lancamentos.id_inspecao (+) = orion_052.id_inspecao "
	    + " and lancamentos.id_lancamento (+)= orion_052.id_lancamento "
	    + " group by orion_052.id_inspecao, orion_052.id_lancamento "
		+ " order by orion_052.id_inspecao, orion_052.id_lancamento ";

		try {
			lancamentos = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaInspecaoQualidLanctoMedidas.class));
		} catch (Exception e) {
			lancamentos = new ArrayList<ConsultaInspecaoQualidLanctoMedidas>();
		}
		
		return lancamentos;
	}
	
	public String findTerceiroByOrdemPacoteEstagio(int ordemProducao, int ordemConfeccao, int estagio) {
		
		String terceiro;
		
		String query = " select lpad(nvl(b.cgcterc_forne9,''),8,0) || lpad(nvl(b.cgcterc_forne4,''),4,0) || lpad(nvl(b.cgcterc_forne2,''),2,0) || ' - ' || nvl(c.nome_fornecedor,'') terceiro " 
		+ " from pcpc_040 a, obrf_080 b, supr_010 c "
		+ "	where a.ordem_producao = " + ordemProducao
		+ "	and a.ordem_confeccao = " + ordemConfeccao
		+ " and a.codigo_estagio = " + estagio
		+ "	and b.numero_ordem = a.numero_ordem "
		+ "	and c.fornecedor9 = b.cgcterc_forne9 "
		+ "	and c.fornecedor4 = b.cgcterc_forne4 "
		+ "	and c.fornecedor2 = b.cgcterc_forne2 " ; 

		try {
			terceiro = jdbcTemplate.queryForObject(query, String.class);			
		} catch (Exception e) {			
			terceiro = "";
		}		
		
		if (terceiro.length() > 3) {
			String[] separaCNPJdaDescricao = terceiro.split("-");
			String cnpj = separaCNPJdaDescricao[0];
			String descricao = separaCNPJdaDescricao[1];		
			cnpj = FormataCNPJ.formatar(cnpj);		
			terceiro = cnpj + " " + descricao;		
		}
		
		return terceiro;
	}		
}
