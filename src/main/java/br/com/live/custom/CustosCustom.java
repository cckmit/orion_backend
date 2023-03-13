package br.com.live.custom;

import br.com.live.model.CopiaFichaCustos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustosCustom {

    private final JdbcTemplate jdbcTemplate;

    public CustosCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CopiaFichaCustos> findProdutosByGrupo(String produto, String grupo) {
        String query = " select b.nivel_estrutura || '.' || b.grupo_estrutura || '.' || b.subgru_estrutura || '.' || b.item_estrutura produto from basi_010 b " +
                " where b.nivel_estrutura = '1' " +
                " and b.nivel_estrutura || '.' || b.grupo_estrutura || '.' || b.subgru_estrutura || '.' || b.item_estrutura <> '" + produto + "' " +
                " and b.grupo_estrutura = '" + grupo + "' " +
                " order by b.grupo_estrutura, b.subgru_estrutura, b.item_estrutura ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CopiaFichaCustos.class));
    }

    public List<CopiaFichaCustos> findDadosProdutoOrigemCopia(String produto, int empresa, int tipoParam) {
        String query = " select a.consumo, a.seq_parametro sequenciaParam, a.descr_parametro descParametro, a.valor_percentual valorPercentual, a.mes_referencia mesDestino, a.ano_referencia anoDestino from rcnb_030 a " +
                " where a.nivel_estrutura || '.' || a.grupo_estrutura || '.' || a.subgru_estrutura || '.' || a.item_estrutura = '" + produto + "' " +
                " and a.codigo_empresa = " + empresa +
                " and a.tipo_parametro = " + tipoParam;

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(CopiaFichaCustos.class));
    }

    public void  inserirParametrosFichaCustos(String nivel, String grupo, String subGrupo, String item, int empresa, int tipoParam, int mesDestino, int anoDestino, float consumo, String descParam, String valorPercentual, int seqParam) {

        String query = " insert into rcnb_030 (codigo_empresa, nivel_estrutura, grupo_estrutura, subgru_estrutura, item_estrutura, tipo_parametro, " +
                " seq_parametro, mes_referencia, ano_referencia, cnpj9, cnpj4, cnpj2, colecao, consumo, descr_parametro, valor_percentual) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        jdbcTemplate.update(query, empresa,nivel,grupo,subGrupo,item,tipoParam,seqParam,mesDestino,anoDestino,0,0,0,0,consumo,descParam,valorPercentual);
    }
}
