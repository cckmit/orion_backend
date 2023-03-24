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

        String query = "SELECT o.cod_chamado codChamado, o.titulo_chamado tituloChamado, o.impacto, o.descricao_chamado descricaoChamado, o.nome_requerente nomeRequerente, " +
                "t.nome nomeTecnico, o.cod_area codArea, o.cod_departamento codDepartamento, o.cod_setor codSetor, " +
                "y.descricao descricaoArea, u.descricao descricaoDepartamento, i.descricao descricaoSetor, " +
                "o.data_inicio_triagem dataInicioTriagem, o.data_fim_triagem dataFimTriagem, " +
                "o.data_inicio_analise dataInicioAnalise, o.data_fim_analise dataFimAnalise, " +
                "o.data_inicio_aprov_escopo dataInicioAprovEscopo, o.data_fim_aprov_escopo dataFimAprovEscopo, " +
                "o.data_inicio_orcamento dataInicioOrcamento, o.data_fim_orcamento dataFimOrcamento, " +
                "o.data_inicio_fila_desenv_forn dataInicioFilaDesenvForn, o.data_fim_fila_desenv_forn dataFimFilaDesenvForn, " +
                "o.data_inicio_desenv_forn dataInicioDesenvForn, o.data_fim_desenv_forn dataFimDesenvForn, " +
                "o.data_inicio_fila_desenv_int dataInicioFilaDesenvInt, o.data_fim_fila_desenv_int dataFimFilaDesenvInt, " +
                "o.data_inicio_desenv_int dataInicioDesenvInt, o.data_fim_desenv_int dataFimDesenvInt, " +
                "o.data_inicio_qualidade_testes dataInicioQualidadeTestes, o.data_fim_qualidade_testes dataFimQualidadeTestes, " +
                "o.data_inicio_entrega dataInicioEntrega, o.data_fim_entrega dataFimEntrega " +
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

        String query = "SELECT o.cod_chamado codChamado, o.titulo_chamado tituloChamado, o.impacto, o.descricao_chamado descricaoChamado, o.nome_requerente nomeRequerente, " +
                "t.nome nomeTecnico, o.cod_area codArea, o.cod_departamento codDepartamento, o.cod_setor codSetor, " +
                "y.descricao descricaoArea, u.descricao descricaoDepartamento, i.descricao descricaoSetor, " +
                "o.data_inicio_triagem dataInicioTriagem, o.data_fim_triagem dataFimTriagem, " +
                "o.data_inicio_analise dataInicioAnalise, o.data_fim_analise dataFimAnalise, " +
                "o.data_inicio_aprov_escopo dataInicioAprovEscopo, o.data_fim_aprov_escopo dataFimAprovEscopo, " +
                "o.data_inicio_orcamento dataInicioOrcamento, o.data_fim_orcamento dataFimOrcamento, " +
                "o.data_inicio_fila_desenv_forn dataInicioFilaDesenvForn, o.data_fim_fila_desenv_forn dataFimFilaDesenvForn, " +
                "o.data_inicio_desenv_forn dataInicioDesenvForn, o.data_fim_desenv_forn dataFimDesenvForn, " +
                "o.data_inicio_fila_desenv_int dataInicioFilaDesenvInt, o.data_fim_fila_desenv_int dataFimFilaDesenvInt, " +
                "o.data_inicio_desenv_int dataInicioDesenvInt, o.data_fim_desenv_int dataFimDesenvInt, " +
                "o.data_inicio_qualidade_testes dataInicioQualidadeTestes, o.data_fim_qualidade_testes dataFimQualidadeTestes, " +
                "o.data_inicio_entrega dataInicioEntrega, o.data_fim_entrega dataFimEntrega " +
                "FROM ORION_ADM_010 o " +
                "JOIN ORION_001 t ON o.cod_tecnico = t.id " +
                "JOIN ORION_IND_020 y ON o.cod_area = y.sequencia " +
                "JOIN ORION_IND_020 u ON o.cod_departamento = u.sequencia " +
                "JOIN ORION_IND_020 i ON o.cod_setor = i.sequencia " +
                "WHERE o.data_inicio_triagem BETWEEN ? AND ? " +
                "AND y.tipo = 2 " +
                "AND u.tipo = 3 " +
                "AND i.tipo = 4 " +
                "ORDER BY o.cod_chamado DESC";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaChamado.class), FormataData.parseStringToDate(dataInicio), FormataData.parseStringToDate(dataFim));
    }

}
