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

    @Query("SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto ORDER BY a.id, a.descricao, a.dataInicio, a.horaInicio")
    List<RegistroTarefaAtividadeProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

    @Query("SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade ORDER BY a.id, a.descricao, a.dataInicio, a.horaInicio")
    List<RegistroTarefaAtividadeProjetoEntity> findAllByRegistroAtividade(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query(value = "SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade and a.dataInicio <> null and a.horaInicio <> null ORDER BY a.dataInicio ASC, a.horaInicio ASC")
    List<RegistroTarefaAtividadeProjetoEntity> findTarefaAtividadeMenorData(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query(value = "SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade and a.dataFim <> null and a.horaFim <> null ORDER BY a.dataFim DESC, a.horaFim DESC")
    List<RegistroTarefaAtividadeProjetoEntity> findTarefaAtividadeMaiorData(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query("SELECT SUM(a.custo) FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade")
    Double calcularCustoTotalTarefaAtividade(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query("SELECT SUM(a.tempoGasto) FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade")
    Double calcularTempoGastoTarefaAtividade(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query(value = "SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade and a.dataFim IS NOT NULL")
    List<RegistroTarefaAtividadeProjetoEntity> findTarefaAtividadeConcluida(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);

    @Query(value = "SELECT a FROM RegistroTarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idRegistroAtividade = :idRegistroAtividade and a.dataFim IS NULL")
    List<RegistroTarefaAtividadeProjetoEntity> findTarefaAtividadePendente(@Param("idProjeto") Long idProjeto, @Param("idRegistroAtividade") Long idRegistroAtividade);
}
