package br.com.live.administrativo.custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MovimentoPagSeguroCustom {

    private JdbcTemplate jdbcTemplate;

    public MovimentoPagSeguroCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsMovimentoPagSeguroImportado(String estabelecimento, String codTransacaoPagamento){

        boolean existMovimentoPagSeguro;

        String query = " SELECT 1 FROM orion_fin_080 " +
                "WHERE estabelecimento = " + estabelecimento +
                "AND codigo_transacao = " + codTransacaoPagamento;
        try {
            existMovimentoPagSeguro = Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, boolean.class));
        } catch (Exception e) {
            existMovimentoPagSeguro = false;
        }

        return existMovimentoPagSeguro;
    }

    public boolean existsRelacionamentoEstabelecimentoPagSeguroLoja(String estabelecimento){

        boolean existRelacionamento;

        String query = " SELECT 1 FROM orion_fin_085 " +
                "WHERE id_estabelecimento = " + estabelecimento;
        try {
            existRelacionamento = Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, boolean.class));
        } catch (Exception e) {
            existRelacionamento = false;
        }

        return existRelacionamento;
    }
}
