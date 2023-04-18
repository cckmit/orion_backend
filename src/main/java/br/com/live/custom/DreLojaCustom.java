package br.com.live.custom;

import br.com.live.entity.ConciliacaoLojaDreEntity;
import br.com.live.model.ConciliacaoLojaDreConsulta;
import br.com.live.model.OrcamentoLojaDre;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DreLojaCustom {

    private JdbcTemplate jdbcTemplate;

    public DreLojaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int findParametrosDreByMesAno(int mesDre, int anoDre){

        int encontrou = 0;

        String query="select 1 from orion_fin_020 a " +
                "where a.mes_dre = " + mesDre +
                " and a.ano_dre = " + anoDre;

        try {
            encontrou = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            encontrou = 0;
        }

        return encontrou;
    }

    public List<ConciliacaoLojaDreConsulta> findAllDreConciliacaoDreMesAno(int mesDre, int anoDre){
        String query = "select a.cnpj_loja cnpjLoja, b.fantasia_cliente nomeLoja, a.mes_dre mesDre, a.ano_dre anoDre, a.val_taxa_captura valTaxaCaptura, a.val_custo_antecipacao valCustoAntecipacao from orion_fin_025 a, pedi_010 b " +
                " where a.cnpj_loja = (b.cgc_9 ||'.'|| b.cgc_4 ||'.'|| b.cgc_2) " +
                " and a.mes_dre = ? " +
                " and a.ano_dre = ?";
        return jdbcTemplate.query(query, new Object[]{mesDre, anoDre}, BeanPropertyRowMapper.newInstance(ConciliacaoLojaDreConsulta.class));
    }

    public void deleteParametrosLojaByMesAno(int mesDre, int anoDre) {
        String queryDelete = " delete from orion_fin_025 a " +
                " where a.mes_dre = " + mesDre +
                " and a.ano_dre = " + anoDre;
        jdbcTemplate.update(queryDelete);
    }
}