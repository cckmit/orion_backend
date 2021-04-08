package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.ProgramacaoPlanoMestre;

@Repository
public class PlanoMestreCustom {

	private JdbcTemplate jdbcTemplate;

	public PlanoMestreCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProgramacaoPlanoMestre> findProgramacaoIdByPlanoMestre(long idPlanoMestre) {

		String query = "select a.grupo, a.sub, a.item, a.qtde_programada, c.alternativa, c.roteiro, c.periodo from orion_015 a, orion_016 b, orion_017 c "
				+ " where a.num_plano_mestre = " + idPlanoMestre + " and b.num_plano_mestre = a.num_plano_mestre "
				+ " and b.grupo = a.grupo " + " and b.item = a.item " + " and c.num_plano_mestre = a.num_plano_mestre "
				+ " and c.num_item_plano_mestre = b.id ";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramacaoPlanoMestre.class));
	}

	public List<ConsultaPreOrdemProducao> findPreOrdensByIdPlanoMestre(long idPlanoMestre) {

		String query = "select a.id, a.referencia || ' - ' || b.descr_referencia referencia, a.periodo, "
				+ " a.alternativa || ' - ' || max(c.descricao) alternativa, a.roteiro, a.quantidade, "
				+ " a.deposito || ' - ' || max(d.descricao) deposito, a.observacao, max(a.ordem_gerada) ordemGerada "
				+ " from orion_020 a, basi_030 b, basi_070 c, basi_205 d "
				+ " where a.num_plano_mestre = " + idPlanoMestre
				+ " and b.nivel_estrutura = '1' "
				+ " and b.referencia = a.referencia "
				+ " and c.nivel (+) = '1' "
				+ " and c.grupo (+) = a.referencia "
				+ " and c.alternativa (+) = a.alternativa "
				+ " and d.codigo_deposito = a.deposito "
				+ " group by a.id, a.referencia, b.descr_referencia, a.periodo, a.alternativa, a.roteiro, a.deposito, a.observacao, a.quantidade "; 
				
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaPreOrdemProducao.class));
	}

	public int findQtdePecasProgByPreOrdens(List<Integer> preOrdens) {
		
		int qtdePecas = 0;
		
		String query = "select sum(a.quantidade) "
		             + " from orion_020 a "
		             + " where a.id in (" + parseToString(preOrdens) + ") ";
		
		try {
			qtdePecas = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdePecas = 0;
		}
		
		return qtdePecas;		
	}	
		
	public double findQtdeMinutosProgByPreOrdens(List<Integer> preOrdens) {
		
		double qtdeMinutos = 0.000;
		
		String query = " select sum(minutos.total_minutos) "
		+ " from (select a.referencia, b.sub, b.item, b.quantidade, sum(f.minutos_homem), b.quantidade * sum(f.minutos_homem) total_minutos "
		        + " from orion_020 a, orion_021 b, orion_016 c, orion_017 d, mqop_050 f "
		       + " where a.id in (" + parseToString(preOrdens) + ") " 
		         + " and b.num_id_ordem = a.id "
		         + " and c.num_plano_mestre = a.num_plano_mestre "
		         + " and c.grupo = a.referencia "
		         + " and c.item = b.item "
		         + " and d.num_item_plano_mestre = c.id "
		         + " and f.nivel_estrutura = '1' " 
		         + " and f.grupo_estrutura = a.referencia " 
		         + " and (f.item_estrutura = b.item or f.item_estrutura = '000000') " 
		         + " and f.numero_alternati = d.alternativa " 
		         + " and f.numero_roteiro = d.roteiro " 
		       + " group by a.referencia, b.sub, b.item, b.quantidade) minutos " ;

		try {
			qtdeMinutos = jdbcTemplate.queryForObject(query, Double.class);
		} catch (Exception e) {
			qtdeMinutos = 0.000;
		}
		
		return qtdeMinutos;
	}
	
	public int findQtdeReferenciasProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeReferencias = 0;
		
		String query = " select count(*) from (select a.referencia "
		             + " from orion_020 a "
		             + " where a.id in (" + parseToString(preOrdens) + ") "                 
		             + " group by a.referencia) referencias " ;

		try {
			qtdeReferencias = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeReferencias = 0;
		}
		
		return qtdeReferencias;
	}		
	
	public int findQtdeSKUsProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeSKUs = 0;
		
		String query = " select count(*) from (select a.referencia, b.sub, b.item "
                  + " from orion_020 a, orion_021 b "
                  + " where a.id in (" + parseToString(preOrdens) + ") "
                  + " and b.num_id_ordem = a.id "
                  + " group by a.referencia, b.sub, b.item) skus ";

		try {
			qtdeSKUs = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeSKUs = 0;
		}
		
		return qtdeSKUs;
	}		
		
	public int findQtdeLoteMedioProgByPreOrdens(List<Integer> preOrdens) {

		int qtdeLoteMedio = 0;
		
		String query = " select sum(a.quantidade) / count(*) "
         			 + "  from orion_020 a "
				     + " where a.id in (" + parseToString(preOrdens) + ") " ;

		try {
			qtdeLoteMedio = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			qtdeLoteMedio = 0;
		}
		
		return qtdeLoteMedio;
	}		
		
	public long findIDMaiorOrdemByPreOrdens(List<Integer> preOrdens) {

		long id = 0;
		
		String query = " select a.id "
				+ "  from orion_020 a "
				+ " where a.id in (" + parseToString(preOrdens) + ") "
				+ "   and a.quantidade = (select max(b.quantidade) from orion_020 b "
				+ "                        where b.id in (" + parseToString(preOrdens) + ")) "
				+ "   and rownum = 1 " ;				

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}
		
		return id;
	}		

	public long findIDMenorOrdemByPreOrdens(List<Integer> preOrdens) {

		long id = 0;
		
		String query = " select a.id "
				+ "  from orion_020 a "
				+ " where a.id in (" + parseToString(preOrdens) + ") "
				+ "   and a.quantidade = (select min(b.quantidade) from orion_020 b "
				+ "                        where b.id in (" + parseToString(preOrdens) + ")) "
				+ "   and rownum = 1 " ;				

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}
		
		return id;
	}		
	
	public int findMaxIdPreOrdem() {

		Integer id;

		String query = " select nvl(max(id),0) from orion_020 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return (int) id;
	}

	public int findMaxIdPreOrdemItem() {

		Integer id;

		String query = " select nvl(max(id),0) from orion_021 ";

		try {
			id = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			id = 0;
		}

		return (int) id;
	}

	private String parseToString(List<Integer> listaIDs) {
	
		String listaString = "";
		
		for (Integer id : listaIDs) {
			if (listaString.equalsIgnoreCase("")) listaString = Integer.toString(id);
			else listaString += ", " + id;			 			
		}		
					
		return listaString;
	}	
}