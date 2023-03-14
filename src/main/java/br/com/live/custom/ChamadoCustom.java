package br.com.live.custom;

import br.com.live.model.ConsultaChamado;
import br.com.live.util.FormataData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ChamadoCustom {
    private JdbcTemplate jdbcTemplate;

    public ChamadoCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaChamado> findAllChamados() {

        String query = "SELECT o.cod_chamado codChamado, o.titulo_chamado tituloChamado, o.impacto, o.descricao_chamado descricaoChamado, " +
                "o.data_chamado dataChamado, o.nome_requerente nomeRequerente, o.data_analise dataAnalise, o.data_entrega_des dataEntregaDes, " +
                "o.data_entrega_usuario dataEntregaUsuario, t.nome nomeTecnico, o.cod_area codArea, o.cod_departamento codDepartamento, o.cod_setor codSetor, " +
                "y.descricao descricaoArea, u.descricao descricaoDepartamento, i.descricao descricaoSetor " +
                "FROM ORION_ADM_010 o " +
                "JOIN ORION_001 t ON o.cod_tecnico = t.id " +
                "JOIN ORION_IND_020 y ON o.cod_area = y.sequencia " +
                "JOIN ORION_IND_020 u ON o.cod_departamento = u.sequencia " +
                "JOIN ORION_IND_020 i ON o.cod_setor = i.sequencia " +
                "WHERE y.tipo = 2 " +
                "AND u.tipo = 3 " +
                "AND i.tipo = 4 " +
                "ORDER BY o.cod_chamado DESC";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaChamado.class));
    }

    public List<ConsultaChamado> findAllChamadosByData (String dataInicio, String dataFim) {

        String query = "SELECT o.cod_chamado codChamado, o.titulo_chamado tituloChamado, o.impacto, o.descricao_chamado descricaoChamado, " +
                "o.data_chamado dataChamado, o.nome_requerente nomeRequerente, o.data_analise dataAnalise, o.data_entrega_des dataEntregaDes, o.data_entrega_usuario dataEntregaUsuario, " +
                "t.nome nomeTecnico, o.cod_area codArea, o.cod_departamento codDepartamento, o.cod_setor codSetor, " +
                "y.descricao descricaoArea, u.descricao descricaoDepartamento, i.descricao descricaoSetor " +
                "FROM ORION_ADM_010 o " +
                "JOIN ORION_001 t ON o.cod_tecnico = t.id " +
                "JOIN ORION_IND_020 y ON o.cod_area = y.sequencia " +
                "JOIN ORION_IND_020 u ON o.cod_departamento = u.sequencia " +
                "JOIN ORION_IND_020 i ON o.cod_setor = i.sequencia " +
                "WHERE o.data_chamado BETWEEN ? AND ? " +
                "AND y.tipo = 2 " +
                "AND u.tipo = 3 " +
                "AND i.tipo = 4 " +
                "ORDER BY o.cod_chamado DESC";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaChamado.class), FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
    }

}
