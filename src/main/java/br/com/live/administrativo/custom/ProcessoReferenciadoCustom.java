package br.com.live.administrativo.custom;

import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.model.ConsultaNotaFiscal;

@Repository
public class ProcessoReferenciadoCustom {
    private JdbcTemplate jdbcTemplate;

    public ProcessoReferenciadoCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaNotaFiscal> findNotasProcessoReferenciado(String empresas, String naturezas, Date dataInicial) {
    	
    	System.out.println("series: " + empresas);
    	System.out.println("naturezas: " + naturezas);
    	System.out.println("dataInicial: " + dataInicial);
    	
    	String query = " select a.codigo_empresa codEmpresa, " 
        + " a.num_nota_fiscal notaFiscal, "
        + " a.serie_nota_fisc serieFiscal, "
        + " a.data_emissao dataEmissao, "
        + " a.cgc_9 cnpj9, "
        + " a.cgc_4 cnpj4, "
        + " a.cgc_2 cnpj2 "       
        + " from fatu_050 a "
        + " where a.codigo_empresa in (" + empresas + ") "
        + " and a.natop_nf_nat_oper in (" + naturezas + ") "
        + " and a.data_emissao >= ? "
        + " and not exists (select 1 from obrf_701 b "
        + " where b.cod_empresa = a.codigo_empresa "
        + " and b.num_nota = a.num_nota_fiscal "
        + " and b.ser_nota = a.serie_nota_fisc "
        + " and b.cod_part9 = a.cgc_9 "
        + " and b.cod_part4 = a.cgc_4 "
        + " and b.cod_part2 = a.cgc_2) ";
    	    	
    	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaNotaFiscal.class), dataInicial);
    }
    
    public void insereProcessoReferenciado(String registro, String numProcesso, int indProcesso, String regPai, int codEmpresa, int notaFiscal, String serieNota, int codCred, int numMes, int numAno, int cnpj9, int cnpj4, int cnpj2, int natBcCred, int indOrigCred) {
    	
    	String query = "insert into OBRF_701 (REG,NUM_PROC,IND_PROC,REG_PAI,COD_EMPRESA,NUM_NOTA," 
        + "SER_NOTA,COD_CRED,MES_APUR,ANO_APUR,COD_PART9,COD_PART4,COD_PART2,NAT_BC_CRED,IND_ORIG_CRED) "
        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    	jdbcTemplate.update(query, registro, numProcesso, indProcesso, regPai, codEmpresa, notaFiscal, serieNota, codCred, numMes, numAno, cnpj9, cnpj4, cnpj2, natBcCred, indOrigCred);    	
    }
    
    public void insereProcessoReferenciadoJudicialAdministrativo(String registro, String numProcesso, int indProcesso, String regPai, int codEmpresa, int notaFiscal, String serieNota, int codCred, int numMes, int numAno, int cnpj9, int cnpj4, int cnpj2, String idSecJudicial, String idVara, int indNat10acao, int indNat20acao, String descDecisaoJudicial, Date dtSentencaJudicial, Date dtDecisaoAdministrativa, int natBcCred, int indOrigCred, String regPai2) {
    	
    	String query = "insert into OBRF_702 (REG,REG_PAI_1,COD_EMPRESA,NUM_NOTA,SER_NOTA,COD_CRED,"
    	+ "IND_PROC,NUM_PROC,ID_SEC_JUD,ID_VARA,IND_NAT10_ACAO,IND_NAT20_ACAO,DESC_DEC_JUD,"
    	+ "DT_SENT_JUD,DT_DEC_ADM,MES_APUR,ANO_APUR,COD_PART9,COD_PART4,COD_PART2,NAT_BC_CRED,IND_ORIG_CRED,REG_PAI_2)"
    	+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	
    	jdbcTemplate.update(query, registro, regPai, codEmpresa, notaFiscal, serieNota, codCred, indProcesso, numProcesso, idSecJudicial, idVara, indNat10acao, indNat20acao, descDecisaoJudicial, dtSentencaJudicial, dtDecisaoAdministrativa, numMes, numAno, cnpj9, cnpj4, cnpj2, natBcCred, indOrigCred, regPai2); 
    }
    
}
