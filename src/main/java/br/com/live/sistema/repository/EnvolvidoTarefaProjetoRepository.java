package br.com.live.sistema.repository;

import br.com.live.sistema.entity.EnvolvidoTarefaProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvolvidoTarefaProjetoRepository extends JpaRepository<EnvolvidoTarefaProjetoEntity, String> {
    @Query("SELECT a FROM EnvolvidoTarefaProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade and a.idRegistroTarefaAtividade = :idRegistroTarefaAtividade")
    List<EnvolvidoTarefaProjetoEntity> findEnvolvidoByTarefaAtividadeProjeto(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade, @Param("idRegistroTarefaAtividade") Long idRegistroTarefaAtividade);
}
