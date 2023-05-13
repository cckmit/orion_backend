package br.com.live.custom;

import br.com.live.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.model.ConsultaControleRelaxadeira;
import br.com.live.model.ObterInfoRolos;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ControleRelaxadeiraCustom {

    private final JdbcTemplate jdbcTemplate;

    public ControleRelaxadeiraCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaControleRelaxadeira> consultaDadosGridRelaxade(int status) {
        String query = " select a.id, a.status, a.cod_rolo codRolo, a.cod_motivo || ' - ' || nvl((select max(e.DESCRICAO) from EFIC_040 e " +
                "                                                                                   where e.codigo_motivo (+) = a.cod_motivo " +
                "                                                                                       and e.area_producao in (2,4)), 'SEM DEFEITO') motivoRejeicao, " +
                " a.quantidade, a.perda_metros perdaMetros, " +
                " a.sit_sincronizacao sitSincronizacao, a.data_sincronizacao dataSincronizacao, a.cod_analista || ' - ' || nvl(max(c.nome), 'ANALISTA GENÉRICO') codAnalista from orion_cfc_310 a, efic_040 b, efic_050 c " +
                " where a.sit_sincronizacao <> " + status +
                " and c.cod_funcionario (+) = a.cod_analista " +
                " group by a.id, a.status, a.cod_rolo, a.quantidade, a.perda_metros, a.sit_sincronizacao, a.data_sincronizacao, a.cod_motivo, a.cod_analista ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaControleRelaxadeira.class));
    }

    public List<ConsultaControleRelaxadeira> consultaRelatorioRegistrosSincronizados(String dataInicio, String dataFim, int status) {
        String query = " select a.id, a.status, a.cod_rolo codRolo, a.cod_motivo || ' - ' || " +
                " nvl((select max(e.DESCRICAO) from EFIC_040 e " +
                "  where e.codigo_motivo (+) = a.cod_motivo " +
                "  and e.area_producao in (2,4)), 'SEM DEFEITO') motivoRejeicao, " +
                "  a.quantidade, a.perda_metros perdaMetros, " +
                " a.sit_sincronizacao sitSincronizacao, a.data_sincronizacao dataSincronizacao, " +
                " a.cod_analista || ' - ' || nvl(max(c.nome), 'ANALISTA GENERICO') codAnalista, " +
                " d.PANOACAB_NIVEL99 || '.' || d.PANOACAB_GRUPO || '.' || d.PANOACAB_SUBGRUPO || '.' || " +
                " d.PANOACAB_ITEM tecido, f.NARRATIVA descTecido, d.PESO_BRUTO pesoRolo " +
                " from orion_cfc_310 a, efic_040 b, efic_050 c, pcpt_020 d, basi_010 f " +
                " where a.SIT_SINCRONIZACAO = 1 " +
                " and c.cod_funcionario (+) = a.cod_analista " +
                " AND d.CODIGO_ROLO = a.COD_ROLO " +
                " AND f.NIVEL_ESTRUTURA = d.PANOACAB_NIVEL99 " +
                " AND f.GRUPO_ESTRUTURA = d.PANOACAB_GRUPO " +
                " AND f.SUBGRU_ESTRUTURA = d.PANOACAB_SUBGRUPO " +
                " AND f.ITEM_ESTRUTURA = d.PANOACAB_ITEM " +
                " AND a.DATA_SINCRONIZACAO BETWEEN to_date('" + dataInicio + "', 'dd-MM-yyyy') AND to_date('" + dataFim + "', 'dd-MM-yyyy') ";

                if (status == 0) {
                    query += " and a.STATUS IN (1,2) ";
                } else {
                    query += " and a.STATUS IN (" + status + ") ";
                }

                query += " group by a.id, a.status, a.cod_rolo, a.quantidade, a.perda_metros, " +
                " a.sit_sincronizacao, a.data_sincronizacao, a.cod_motivo, a.cod_analista, " +
                " d.PANOACAB_NIVEL99, d.PANOACAB_GRUPO, d.PANOACAB_SUBGRUPO, d.PANOACAB_ITEM, f.NARRATIVA, d.PESO_BRUTO ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaControleRelaxadeira.class));
    }

    public ObterInfoRolos obterInfoRolos(int codRolo) {
        String query = " SELECT m.PANOACAB_NIVEL99 nivel, m.PANOACAB_GRUPO grupo, m.PANOACAB_SUBGRUPO sub, m.PANOACAB_ITEM item, " +
                " m.PERIODO_PRODUCAO periodo, m.QTDE_QUILOS_ACAB qtdeKilos FROM pcpt_020 m " +
                " WHERE m.CODIGO_ROLO = " + codRolo;
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(ObterInfoRolos.class));
    }

    public void atualizaInformacoesRolo(int codAnalista, Date dataHoraBipagem, int codRolo) {
        String query = " UPDATE pcpt_020 A " +
                " SET a.CODIGO_ANALISTA_QUALIDADE = ?, " +
                " a.DATA_INSPECAO_QUALIDADE = ? " +
                " WHERE a.CODIGO_ROLO = ? ";
        jdbcTemplate.update(query, codAnalista, dataHoraBipagem, codRolo);
    }

    public void inserirInformacaoRejeicao(int codRolo, int codAnalista, Date dataHoraBipagem, String observacao) {
        try {
            String query = " INSERT INTO pcpt_022 (CODIGO_ROLO, USUARIO, DATA, HORA,OBSERVACAO) VALUES (? ,? , trunc(sysdate), ?, ?) ";
            jdbcTemplate.update(query, codRolo, codAnalista, dataHoraBipagem, observacao);
        } catch (Exception e) {
            String query = " UPDATE pcpt_022 SET USUARIO = ?, DATA = trunc(sysdate), HORA = ?, OBSERVACAO = ? where pcpt_022.codigo_rolo = ? ";
            jdbcTemplate.update(query, codAnalista, dataHoraBipagem, observacao, codRolo);
        }

    }

    public void inserirRejeicaoRolo(String nivel, String grupo, String sub, String item, int codMotivo, int periodo, int codRolo, float qtdeKilos, float qtdeDefeitos, Date dataHoraBipagem, int codAnalista, float perdaMetros) {
        String query = " INSERT INTO efic_100 (DATA_REJEICAO, PROD_REJ_NIVEL99, PROD_REJ_GRUPO, PROD_REJ_SUBGRUPO, PROD_REJ_ITEM, " +
                " CODIGO_ESTAGIO, CODIGO_MOTIVO, CLASSIFICACAO, PERIODO_PRODUCAO, NUMERO_ORDEM, QUANTIDADE, AREA_PRODUCAO, " +
                " QTDE_DEFEITOS, HORA_INICIO, ESTAGIO_DIGITACAO, CODIGO_ANALISTA, PERDA_METROS) VALUES (trunc(sysdate),?,?,?,?,63,?,0,?,?,?,2,?,?,76,?,?) ";
        jdbcTemplate.update(query, nivel, grupo, sub, item, codMotivo, periodo, codRolo, qtdeKilos, qtdeDefeitos, dataHoraBipagem, codAnalista, perdaMetros);
    }

    public List<Integer> obterRolosComDefeitoRegistrado() {
        String query = " select a.cod_rolo from orion_cfc_310 a " +
                " where a.cod_motivo > 0 " +
                " group by a.cod_rolo ";
        return jdbcTemplate.queryForList(query, Integer.class);
    }

    public List<String> obterDescMotivosRolo(int codRolo) {
        String query = " select a.cod_motivo || ' - ' || max(b.descricao) || ' - qtde ' || a.quantidade from orion_cfc_310 a, efic_040 b " +
                " where a.cod_rolo = " + codRolo +
                " and b.codigo_motivo = a.cod_motivo " +
                " group by a.cod_motivo, a.quantidade ";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public int obterMaiorAnalista(int codRolo) {
        String query = " select max(a.cod_analista) from orion_cfc_310 a " +
                " where a.cod_rolo = " + codRolo +
                " and a.cod_motivo > 0 ";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public Date obterMaiorDataBipagem(int codRolo) {
        String query = " select max(a.data_hora_bipagem) from orion_cfc_310 a " +
                " where a.cod_rolo = " + codRolo +
                " and a.cod_motivo > 0 ";
        return jdbcTemplate.queryForObject(query, Date.class);
    }
}
