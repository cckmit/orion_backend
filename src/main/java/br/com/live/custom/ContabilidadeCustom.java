package br.com.live.custom;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.model.ConsultaLanctoContabeis;
import br.com.live.model.Maquinas;

@Repository
public class ContabilidadeCustom {
	
	private JdbcTemplate jdbcTemplate;

	public ContabilidadeCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int findNextId() {
		int nextId = 0;
		
		String query = " SELECT NVL(max(a.id),0) + 1 FROM orion_cnt_010 a ";

		try {
			nextId = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			nextId = 0;
		}
		return nextId;
	}
	
	public int findNextNumLancto(int empresa, int exercicio) {
		int nextNumLancto;
		
		String query = " SELECT b.ultimo_lanc + 1 FROM cont_500 b "
				+ "      WHERE b.cod_empresa = " + empresa
				+ "      AND b.exercicio =  " + exercicio
				+ "      FOR UPDATE ";
		
		String queryUpdate = "UPDATE cont_500 SET ultimo_lanc = ? WHERE cod_empresa = ? AND exercicio = ?";
		
		try {
			nextNumLancto = jdbcTemplate.queryForObject(query, Integer.class);
			jdbcTemplate.update(queryUpdate, nextNumLancto, empresa, exercicio);
		} catch (Exception e) {
			nextNumLancto = 0;
		}	
		return nextNumLancto;
	}
	
	public int findEmpresa(int empresa) {
		int existsEmpresa;
		String query = " SELECT 1 FROM fatu_500 a WHERE a.codigo_empresa = " + empresa;

		try {
			existsEmpresa = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsEmpresa = 0;
		}
		return existsEmpresa;
	}
	
	public int findCentroCusto(int centroCusto) {
		int existsCCusto;
		String query = " SELECT 1 FROM basi_185 a WHERE a.centro_custo = " + centroCusto;

		try {
			existsCCusto = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsCCusto = 0;
		}
		return existsCCusto;
	}
	
	public int findCentroCustoInativo(int centroCusto) {
		int existsCCustoIn;
		String query = " SELECT 1 FROM basi_185 a WHERE a.centro_custo = " + centroCusto
				+ "    AND a.descricao NOT LIKE '%(IN)%' ";
		try {
			existsCCustoIn = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsCCustoIn = 0;
		}
		return existsCCustoIn;
	}
	
	public int findHistoricoContabil(int historicoContabil) {
		
		int existsHistContabil;
		String query = " SELECT 1 FROM cont_010 c WHERE c.codigo_historico = " + historicoContabil;

		try {
			existsHistContabil = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsHistContabil = 0;
		}
		return existsHistContabil;
	}
	
	public int findOrigem(int origem) {
		int existsOrigem;
		String query = " SELECT 1 FROM cont_050 a WHERE a.origem = " + origem;

		try {
			existsOrigem = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsOrigem = 0;
		}
		return existsOrigem;
	}
	
	public int findContaContabil(int contaReduzida) {
		int existsContaReduzida;
		String query = " SELECT 1 FROM cont_535 d WHERE d.cod_reduzido = " + contaReduzida 
				+ "      GROUP BY d.cod_reduzido ";

		try {
			existsContaReduzida = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existsContaReduzida = 0;
		}
		return existsContaReduzida;
	}
	
	public String findUserSystextil(int idUsuario) {
		
		String usuario = "";
		
		String query = " SELECT NVL(a.usuario_systextil, '') FROM orion_001 a WHERE a.id = " + idUsuario ;
		
		usuario = jdbcTemplate.queryForObject(query, String.class);
		
		return usuario;
	}
	
	public String findContaContabByContaRed(int contaReduzida) {
		
		String contaContabil = "";
		
		String query = " SELECT d.conta_contabil contaContabil FROM cont_535 d WHERE d.cod_reduzido = ? "
				+ "     AND d.data_cadastro = (SELECT MAX(g.data_cadastro) FROM cont_535 g WHERE g.cod_reduzido = ? ) ";
		
		contaContabil = jdbcTemplate.queryForObject(query, String.class, contaReduzida, contaReduzida);
		
		return contaContabil;
	}
	
	public int findSubConta(int contaRezuzida) {
		
		int subConta = 0;
		
		String query = " SELECT d.exige_subconta FROM CONT_535 d WHERE d.cod_reduzido = ? and d.cod_plano_cta = 2021 ";
		
		try {
			subConta = jdbcTemplate.queryForObject(query, Integer.class, contaRezuzida);
		} catch (Exception e) {
			subConta = 0;
		}
		return subConta;
	}
	
	public int findCentroCustoByEmpresa(int centroCusto, int filial) {
		
		int centroCustoByEmpresa = 0;
		
		String query = " select 1 from basi_185 a WHERE a.centro_custo = ? AND a.local_entrega = ? ";
		
		try {
			centroCustoByEmpresa = jdbcTemplate.queryForObject(query, Integer.class, centroCusto, filial);
		} catch (Exception e) {
			centroCustoByEmpresa = 0;
		}

		return centroCustoByEmpresa;
	}
	
	public int findLoteExistenteParaParametros(int empresa, int exercicio, int origem, String dataLancto) {
		
		int existeLote = 0;
		
		String query = " SELECT NVL(MIN(a.lote), 0) FROM cont_520 a "
				+ "       WHERE a.cod_empresa = " + empresa
				+ "       AND a.exercicio = " + exercicio
				+ "       AND a.origem = " + origem
				+ "       AND a.situacao = 0 "
				+ "       AND TO_CHAR(TO_DATE(a.data_lote), 'DD/MM/YYYY') = '" + dataLancto + "'";
		
		try {
			existeLote = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			existeLote = 0;
		}
		return existeLote;
	}
	
	public int novoLote(int empresa, int exercicio, int origem, String dataLancto) {
		
		int loteNovo = 0;
		
		String query = " SELECT MAX(a.lote) + 1 FROM cont_520 a "
				+ "       WHERE a.cod_empresa = " + empresa
				+ "       AND a.exercicio = " + exercicio
				+ "       AND a.origem = " + origem;
		
		String queryInsert = "INSERT INTO cont_520(cod_empresa, exercicio, origem, lote, data_lote, situacao)VALUES(?, ?, ?, ?, ?, 0)";
		
		try {
			loteNovo = jdbcTemplate.queryForObject(query, Integer.class);
			jdbcTemplate.update(queryInsert, empresa, exercicio, origem, loteNovo, dataLancto);
						
		} catch (Exception e) {
			loteNovo = 0;
		}
		return loteNovo;
	}
	
	public List<ConsultaLanctoContabeis> findAllLanctoContabeis(String usuario) {
		String query = " select a.id id, "
				+ "       a.filial_lancto filialLancto, "
				+ "       a.exercicio exercicio, "
				+ "       a.origem origem, "
				+ "       a.conta_reduzida contaReduzida, "
				+ "       a.debito_credito debitoCredito, "
				+ "       a.valor_lancto valorLancto, "
				+ "       a.centro_custo centroCusto, "
				+ "       a.hist_contabil histContabil, "
				+ "       a.data_lancto dataLancto, "
				+ "       a.compl_histor1 complHistor1, "
				+ "       a.datainsercao datainsercao, "
				+ "       a.usuario usuario, "
				+ "       a.lote lote, "
				+ "       a.numero_lanc numeroLanc, "
				+ "       a.seq_lanc seqLanc, "
				+ "       a.periodo periodo, "
				+ "       a.status status "
				+ "       FROM orion_cnt_010 a "
				+ "       WHERE a.usuario = '" + usuario + "'";

		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaLanctoContabeis.class));
	}
	
	public String findCriticasById(int id) {
		
		String criticas = "";
		
		String query = " SELECT a.criticas FROM orion_cnt_010 a WHERE a.id = " + id;
		
		try {
			criticas = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			criticas = "";
		}

		return criticas;
	}
	
	public void inserirLanctoContabilSystextil(int codMatriz, int filialLancto, int exercicio, int origem, String contaContabil, int contaReduzida, String debitoCredito,
			float valorLancto, int centroCusto, int histContabil, Date dataLancto, String complHistor1, Date datainsercao, String programa, String usuario, int lote,
			int numeroLanc, int seqLanc, int periodo, int status) {
		
		String query = " insert into cont_600(cod_empresa, exercicio, numero_lanc, seq_lanc, origem, lote, periodo, conta_contabil, conta_reduzida, centro_custo, debito_credito, "
				+ "         hist_contabil, compl_histor1, data_lancto, valor_lancto, filial_lancto, prg_gerador, usuario, datainsercao) "
				+ "         values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		jdbcTemplate.update(query, codMatriz, exercicio, numeroLanc, seqLanc, origem, lote, periodo, contaContabil, contaReduzida ,centroCusto, debitoCredito, histContabil, 
				complHistor1, dataLancto, valorLancto, filialLancto, programa, usuario, datainsercao);
		
	}
	
	public int findStatusByLancto(String usuario) {
		
		int status = 1;
		
		String query = " SELECT 1 FROM orion_cnt_010 x WHERE x.status = 1 AND x.usuario = ? GROUP BY x.status ";
				
		try {
			status = jdbcTemplate.queryForObject(query, Integer.class, usuario);
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}
	
	public float findTotalCredito(String usuario) {
		
		float valorCredito = 0;
		
		String query = " SELECT SUM(a.valor_lancto) FROM orion_cnt_010 a WHERE a.usuario = '" + usuario + "' AND a.debito_credito = 'C' ";
		
		try {
			valorCredito = jdbcTemplate.queryForObject(query, float.class);
		} catch (Exception e) {
			valorCredito = 0;			
		};		
		
		return valorCredito;
	}
	
	public float findTotalDebito(String usuario) {
		
		float valorDebito = 0;
		
		String query = " SELECT SUM(a.valor_lancto) FROM orion_cnt_010 a WHERE a.usuario = '" + usuario + "' AND a.debito_credito = 'D' ";

		try {
			valorDebito = jdbcTemplate.queryForObject(query, float.class);
		} catch (Exception e) {
			valorDebito = 0;			
		};	
		
		return valorDebito;
	}
	
	public List<ConsultaLanctoContabeis> findEmpresaExercicioPorUsuario(String usuario){
		
		String query = " SELECT b.codigo_matriz codEmpresa, "
				+ "       a.exercicio exercicio "
				+ "       FROM fatu_500 b, orion_cnt_010 a "
				+ "       WHERE a.filial_lancto = b.codigo_empresa "
				+ "       AND a.usuario = '" + usuario + "'"
				+ "       GROUP BY b.codigo_matriz, a.exercicio ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaLanctoContabeis.class));		
	}
	
	public List<ConsultaLanctoContabeis> findFiliaisPorEmpresaUsuario(String usuario, int codEmpresa){
		
		String query = " SELECT b.codigo_matriz matriz, "
				+ "       b.codigo_empresa filialLancto "
				+ "       FROM fatu_500 b, orion_cnt_010 a "
				+ "       WHERE a.filial_lancto = b.codigo_empresa "
				+ "       AND a.usuario = '" + usuario + "'"
				+ "       GROUP BY b.codigo_matriz, b.codigo_empresa ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaLanctoContabeis.class));		
	}
	
	public List<ConsultaLanctoContabeis> findByUserFilial(String usuario, int filialLancto, int exercicio){
		
		String query = " SELECT filial_lancto filialLancto, "
				+ "       exercicio exercicio, "
				+ "       origem origem, "
				+ "       conta_reduzida contaReduzida, "
				+ "       debito_credito debitoCredito, "
				+ "       valor_lancto valorLancto, "
				+ "       centro_custo centroCusto, "
				+ "       hist_contabil histContabil, "
				+ "       TO_CHAR(data_lancto, 'DD/MM/YYYY') dataLancto, "
				+ "       compl_histor1 complHistor1, "
				+ "       TO_CHAR(datainsercao, 'DD/MM/YYYY') datainsercao, "
				+ "       usuario, "
				+ "       lote, "
				+ "       numero_lanc numeroLanc, "
				+ "       seq_lanc seqLanc, "
				+ "       periodo, "
				+ "       status, "
				+ "       criticas "
				+ "     FROM orion_cnt_010 a "
				+ "		WHERE a.usuario = '" + usuario + "'"
				+ "		AND a.filial_lancto = " + filialLancto
				+ "		AND a.exercicio =  " + exercicio;
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaLanctoContabeis.class));			
	}

}
