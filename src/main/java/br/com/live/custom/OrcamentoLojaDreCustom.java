package br.com.live.custom;

import br.com.live.model.OrcamentoLojaDre;
import br.com.live.model.ConsultaOrcamentoLojaDre;
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

    public List<OrcamentoLojaDre> findAllFieldsOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento) {

        String query = "SELECT a.id, a.cnpj_loja cnpjLoja, a.ano_orcamento anoOrcamento, a.mes_orcamento mesOrcamento, a.tipo_orcamento tipoOrcamento, a.propriedade propriedade, a.val_propriedade valPropriedade, a.seq_consulta seqConsulta " +
                " FROM orion_fin_015 a " +
                " WHERE a.cnpj_loja = ? " +
                " AND a.ano_orcamento = ? " +
                " AND a.propriedade IS NOT NULL " +
                " ORDER BY CAST(REGEXP_SUBSTR(a.seq_consulta, '\\d+') AS NUMBER), " +
                "         REGEXP_SUBSTR(a.seq_consulta, '\\D+(\\d+)$', 1, 1, NULL, 1) NULLS FIRST, " +
                "         a.seq_consulta";

        return jdbcTemplate.query(query, new Object[]{cnpjLoja, anoOrcamento}, BeanPropertyRowMapper.newInstance(OrcamentoLojaDre.class));
    }

    public void deleteOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento) {
        String queryDelete = " delete from orion_fin_015 a " +
                "where a.cnpj_loja = '" + cnpjLoja + "' " +
                "and a.ano_orcamento = " + anoOrcamento;
        jdbcTemplate.update(queryDelete);
    }
}
