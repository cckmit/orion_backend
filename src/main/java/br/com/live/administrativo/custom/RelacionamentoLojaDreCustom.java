package br.com.live.administrativo.custom;

import br.com.live.administrativo.model.ConsultaRelacionamentoLojaDre;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelacionamentoLojaDreCustom {

    private JdbcTemplate jdbcTemplate;

    public RelacionamentoLojaDreCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConteudoChaveAlfaNum> findAllLojas() {

        String query = "select (pe.cgc_9 ||'.'|| pe.cgc_4 ||'.'|| pe.cgc_2) value, pe.fantasia_cliente label  from pedi_010 pe " +
                "where pe.tipo_cliente in (17, 33) " +
                "and pe.situacao_cliente = 1 ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }

    public List<ConteudoChaveNumerica> findAllCentroCustos() {

        String query = "select ba.centro_custo value, ba.descricao label from basi_185 ba";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
    }

    public ConteudoChaveAlfaNum findSupervisorLoja(String cnpjLoja){

        String query = "select a.cnpj_supervisor value, sp.nome_fornecedor label from orion_fin_001 a, supr_010 sp " +
                "where a.cnpj_supervisor = (sp.fornecedor9 ||'.'|| sp.fornecedor4 ||'.'|| sp.fornecedor2) ";

        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }

    public List<ConteudoChaveAlfaNum> findAllSupervisores() {

        String query = "select (sp.fornecedor9 ||'.'|| sp.fornecedor4 ||'.'|| sp.fornecedor2) value, sp.nome_fornecedor label from supr_010 sp " +
                "where sp.tipo_fornecedor = 97 ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }

    public List<ConteudoChaveAlfaNum> findAllSupervisorConfiggurado() {

        String query = "select (sp.fornecedor9 ||'.'|| sp.fornecedor4 ||'.'|| sp.fornecedor2) value, sp.nome_fornecedor label from supr_010 sp " +
                "where sp.tipo_fornecedor = 97 ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
    }

    public List<ConsultaRelacionamentoLojaDre> findAllLojasConfiguradas() {
        String query = "SELECT " +
                "    a.id, " +
                "    a.cnpj_loja AS cnpjLoja, " +
                "    c.fantasia_cliente AS nomeLoja, " +
                "    b.cnpj_supervisor AS cnpjSupervisor, " +
                "    d.nome_fornecedor AS nomeSupervisor," +
                "    (e.centro_custo || ' - ' || e.descricao) AS centroCustos " +
                "FROM " +
                "    orion_fin_005 a, " +
                "    orion_fin_001 b, " +
                "    pedi_010 c, " +
                "    supr_010 d, " +
                "    basi_185 e " +
                "WHERE " +
                "    a.cnpj_loja = b.cnpj_loja " +
                "    AND a.cnpj_loja = (c.cgc_9 ||'.'|| c.cgc_4 ||'.'|| c.cgc_2) " +
                "    AND b.cnpj_supervisor = (d.fornecedor9 ||'.'|| d.fornecedor4 ||'.'|| d.fornecedor2) " +
                "    AND a.centro_custo = e.centro_custo " +
                "GROUP BY " +
                "    a.id, " +
                "    a.cnpj_loja, " +
                "    c.fantasia_cliente, " +
                "    b.cnpj_supervisor, " +
                "    d.nome_fornecedor, " +
                "    e.centro_custo, " +
                "    e.descricao";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaRelacionamentoLojaDre.class));
    }

    public List<ConteudoChaveNumerica> findAllCentroCustoLoja (String cnpjLoja){

        String query = "select a.centro_custo value, b.descricao label from orion_fin_005 a, basi_185 b " +
                " where a.cnpj_loja = '" + cnpjLoja + "' " +
                " and a.centro_custo = b.centro_custo ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
    }

    public List<ConteudoChaveNumerica> findAllCentroCustoLojaId (String cnpjLoja){

        String query = "select a.id value, (a.centro_custo || '-' || b.descricao) label from orion_fin_005 a, basi_185 b " +
                " where a.cnpj_loja = '" + cnpjLoja + "' " +
                " and a.centro_custo = b.centro_custo ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
    }

    public boolean existsCentroCustoLoja (String cnpjLoja, long centroCusto){

        int encontrou = 0;

        String query = "select 1 from orion_fin_005 a" +
                " where a.cnpj_loja = '" + cnpjLoja + "' " +
                " and a.centro_custo = "+ centroCusto;

        try {
            encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            encontrou = 0;
        }

        return (encontrou==1);
    }

    public boolean existsLojaConfigurada (String cnpjLoja){

        int encontrou = 0;

        String query = "select 1 from orion_fin_001 a" +
                " where a.cnpj_loja = '"+cnpjLoja+"'";
        try {
            encontrou = (int) jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            encontrou = 0;
        }

        return (encontrou==1);
    }

    public List<ConteudoChaveNumerica> obterListaCentroCustoLoja (String cnpjLoja){
        String query = "select a.centro_custo value from orion_fin_005 a " +
                " where a.cnpj_loja = '" +cnpjLoja+ "'";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
    }
}