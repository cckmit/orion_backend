package br.com.live.administrativo.custom;

import br.com.live.administrativo.model.ConsultaOrcamentoLojaDre;
import br.com.live.administrativo.model.DreLojaCalculo;
import br.com.live.administrativo.model.OrcamentoLojaDre;
import br.com.live.util.ConteudoChaveNumerica;
import org.aspectj.weaver.ast.Or;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrcamentoLojaDreCustom {

    private JdbcTemplate jdbcTemplate;

    public OrcamentoLojaDreCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaOrcamentoLojaDre> findAllOrcamentoLojaDre(){

        String query = "select a.cnpj_loja cnpjLoja, b.fantasia_cliente nomeLoja, a.ano_orcamento anoOrcamento from orion_fin_015 a, pedi_010 b " +
                "where a.cnpj_loja = (b.cgc_9 ||'.'|| b.cgc_4 ||'.'|| b.cgc_2) " +
                "group by a.cnpj_loja, b.fantasia_cliente, a.ano_orcamento";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaOrcamentoLojaDre.class));
    }

    public ConsultaOrcamentoLojaDre findOrcamentoLojaDre(String cnpjLoja, int anoOrcamento){

        String query = "select a.cnpj_loja cnpjLoja, b.fantasia_cliente nomeLoja, a.ano_orcamento anoOrcamento from orion_fin_015 a, pedi_010 b " +
                "where a.cnpj_loja = (b.cgc_9 ||'.'|| b.cgc_4 ||'.'|| b.cgc_2) " +
                "and a.cnpj_loja = ?" +
                "and a.ano_orcamento = ?" +
                "group by a.cnpj_loja, b.fantasia_cliente, a.ano_orcamento";

        try {
            return jdbcTemplate.queryForObject(query, new Object[] {cnpjLoja, anoOrcamento}, BeanPropertyRowMapper.newInstance(ConsultaOrcamentoLojaDre.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<OrcamentoLojaDre> findAllFieldsOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento) {

        String query = "SELECT a.id, a.cnpj_loja cnpjLoja, a.ano_orcamento anoOrcamento, a.mes_orcamento mesOrcamento, a.tipo_orcamento tipoOrcamento, a.propriedade propriedade, a.val_propriedade valPropriedade, a.seq_consulta seqConsulta " +
                " FROM orion_fin_015 a " +
                " WHERE a.cnpj_loja = ? " +
                " AND a.ano_orcamento = ? " +
                " AND a.propriedade IS NOT NULL " +
                " AND a.seq_consulta IS NOT NULL " +
                " ORDER BY a.seq_consulta";

        return jdbcTemplate.query(query, new Object[]{cnpjLoja, anoOrcamento}, BeanPropertyRowMapper.newInstance(OrcamentoLojaDre.class));
    }

    public void deleteOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento) {
        String queryDelete = " delete from orion_fin_015 a " +
                "where a.cnpj_loja = '" + cnpjLoja + "' " +
                "and a.ano_orcamento = " + anoOrcamento;
        jdbcTemplate.update(queryDelete);
    }

    public List<String> findAllSequenciaOrcamento(String cnpjLoja, int mesOrcamento, int anoOrcamento) {

        String query = "select a.seq_consulta seqConsulta from orion_fin_015 a " +
                " where a.cnpj_loja = '"+cnpjLoja+"' " +
                " and a.mes_orcamento = " + mesOrcamento +
                " and a.ano_orcamento = " + anoOrcamento +
                " and a.seq_consulta is not null " +
                " and a.tipo_orcamento = 1 " +
                " group by a.seq_consulta";

        return jdbcTemplate.queryForList(query, String.class);
    }

    public OrcamentoLojaDre findOrcamentoBySeqCnpjMesAno(String seqConsulta, String cnpjLoja, int mesOrcamento, int anoOrcamento){

        String query = "select a.seq_consulta seqConsulta, a.conta_contabil contaContabil, a.propriedade, a.val_propriedade valPropriedade from orion_fin_015 a " +
                "where a.cnpj_loja = ? " +
                "and a.ano_orcamento = ? " +
                "and a.mes_orcamento = ? " +
                "and a.seq_consulta = ? " +
                "and a.tipo_orcamento = ?";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{cnpjLoja, anoOrcamento, mesOrcamento, seqConsulta, 1}, BeanPropertyRowMapper.newInstance(OrcamentoLojaDre.class));
        } catch (EmptyResultDataAccessException e){
            return new OrcamentoLojaDre();
        }
    }

    public OrcamentoLojaDre findOrcamentoByContaContabilCnpjMesAno(int contaContabil, String cnpjLoja, int mesOrcamento, int anoOrcamento){

        String query = "select a.seq_consulta seqConsulta, a.conta_contabil contaContabil, a.propriedade, a.val_propriedade valPropriedade from orion_fin_015 a " +
                "where a.cnpj_loja = ? " +
                "and a.ano_orcamento = ? " +
                "and a.mes_orcamento = ? " +
                "and a.conta_contabil = ? " +
                "and a.tipo_orcamento = ?";

        try {
            return jdbcTemplate.queryForObject(query, new Object[] {cnpjLoja, anoOrcamento, mesOrcamento, contaContabil, 1}, BeanPropertyRowMapper.newInstance(OrcamentoLojaDre.class));
        } catch (EmptyResultDataAccessException e) {
            return new OrcamentoLojaDre();
        }
    }
}