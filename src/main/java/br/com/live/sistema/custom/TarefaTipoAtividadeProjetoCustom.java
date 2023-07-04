package br.com.live.sistema.custom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TarefaTipoAtividadeProjetoCustom {

    private final JdbcTemplate jdbcTemplate;

    public TarefaTipoAtividadeProjetoCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
