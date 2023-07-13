package br.com.live.sistema.repository;

import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroTarefaAtividadeProjetoRepository extends JpaRepository<RegistroTarefaAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM RegistroTarefaAtividadeProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto ORDER BY a.dataInicio, a.horaInicio, a.id")
    List<RegistroTarefaAtividadeProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

    @Query("SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade ORDER BY a.dataInicio, a.horaInicio, a.id")
    List<RegistroTarefaAtividadeProjetoEntity> findAllByRegistroAtividade(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);
}
