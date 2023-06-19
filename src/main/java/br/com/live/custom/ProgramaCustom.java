package br.com.live.custom;

import java.util.List;
import br.com.live.model.ProgramaConsulta;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramaCustom {

    private final JdbcTemplate jdbcTemplate;

    public ProgramaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProgramaConsulta> findProgramasByListaModulosAndUsuarios(String listaModulos, String listaUsuarios) {

       String query = "SELECT a.id, a.descricao, a.modulo FROM orion_002 a, orion_003 b " +
               " WHERE a.id = b.id_programa ";
                if (!listaModulos.equals("")) query = query + " AND a.modulo IN ( " + listaModulos + " ) ";
                if (!listaUsuarios.equals("")) query = query + " AND b.ID_USUARIO IN ( " + listaUsuarios + " ) ";
                query = query + " GROUP BY a.id, a.descricao, a.modulo " +
                        " ORDER BY a.id";

       return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ProgramaConsulta.class));
    }
}
