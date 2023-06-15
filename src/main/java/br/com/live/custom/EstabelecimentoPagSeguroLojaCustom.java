package br.com.live.custom;

import br.com.live.model.ConsultaEstabelecimentoPagSeguroLoja;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EstabelecimentoPagSeguroLojaCustom {

    private JdbcTemplate jdbcTemplate;

    public EstabelecimentoPagSeguroLojaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaEstabelecimentoPagSeguroLoja> findAllEstabelecimento(){

        String query = "SELECT c.ID_ESTABELECIMENTO idEstabelecimento, lpad(a.CGC_9,8,0) || LPAD(a.CGC_4,4,0) ||LPAD(a.CGC_2,2,0) cnpjLoja, a.FANTASIA_CLIENTE nomeLoja " +
                "FROM PEDI_010 a " +
                "LEFT JOIN ORION_FIN_085 c ON c.CNPJ_LOJA = lpad(a.cgc_9, 8, '0') || lpad(a.cgc_4, 4, '0') || lpad(a.cgc_2, 2, '0') " +
                "WHERE a.SITUACAO_CLIENTE = 1 " +
                "AND EXISTS ( " +
                "    SELECT 1 " +
                "    FROM PEDI_085 b " +
                "    WHERE b.TIPO_CLIENTE = a.TIPO_CLIENTE " +
                "    AND b.LIVE_AGRUP_TIPO_CLIENTE = 'Lojas Próprias' " +
                ")";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaEstabelecimentoPagSeguroLoja.class));
    }
}
