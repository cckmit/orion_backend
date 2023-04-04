package br.com.live.custom;

import br.com.live.model.ConsultaContaContabilConsiderarDre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContaContabilConsiderarDreCustom {

    private JdbcTemplate jdbcTemplate;

    public ContaContabilConsiderarDreCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaContaContabilConsiderarDre> findAllContaContabil (){
        String query = "SELECT a.cod_reduzido contaContabil, a.descricao descricaoContaContabil, b.gasto_variavel gastoVariavel, b.custo_ocupacao custoOcupacao, b.despesa_folha despesaFolha, b.despesa_geral despesaGeral, b.depreciacao depreciacao " +
                "FROM cont_535 a " +
                "LEFT JOIN orion_fin_010 b ON a.cod_reduzido = b.conta_contabil " +
                "WHERE A.cod_plano_cta = 2021";

        return jdbcTemplate.query(query, rs -> {
            List<ConsultaContaContabilConsiderarDre> resultList = new ArrayList<>();
            while (rs.next()) {
                ConsultaContaContabilConsiderarDre consultaConta = new ConsultaContaContabilConsiderarDre();
                consultaConta.setContaContabil(rs.getInt("contaContabil"));
                consultaConta.setDescricaoContaContabil(rs.getString("descricaoContaContabil"));
                consultaConta.setGastoVariavel(rs.getObject("gastoVariavel") != null ? rs.getBoolean("gastoVariavel") : false);
                consultaConta.setCustoOcupacao(rs.getObject("custoOcupacao") != null ? rs.getBoolean("custoOcupacao") : false);
                consultaConta.setDespesaFolha(rs.getObject("despesaFolha") != null ? rs.getBoolean("despesaFolha") : false);
                consultaConta.setDespesaGeral(rs.getObject("despesaGeral") != null ? rs.getBoolean("despesaGeral") : false);
                consultaConta.setDepreciacao(rs.getObject("depreciacao") != null ? rs.getBoolean("depreciacao") : false);
                resultList.add(consultaConta);
            }
            return resultList;
        });
    }
}
