package br.com.live.sistema.repository;

import br.com.live.sistema.entity.TarefaAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaAtividadeProjetoRepository extends JpaRepository<TarefaAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM TarefaAtividadeProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM TarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto ORDER BY a.id, a.descricao")
    List<TarefaAtividadeProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

    @Query("SELECT a FROM TarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idAtividade = :idAtividade ORDER BY a.id, a.descricao")
    List<TarefaAtividadeProjetoEntity> findAllByAtividade(@Param("idProjeto") Long idProjeto, @Param("idAtividade") Long idAtividade);

    @Query("SELECT COALESCE(SUM(a.tempoPrevisto), 0) FROM TarefaAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idAtividade = :idAtividade")
    Double calcularTempoPrevistoTarefaAtividade(@Param("idProjeto") Long idProjeto, @Param("idAtividade") Long idAtividade);
}
