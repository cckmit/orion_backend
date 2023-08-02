package br.com.live.sistema.repository;

import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroAtividadeProjetoRepository extends JpaRepository<RegistroAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM RegistroAtividadeProjetoEntity a")
    long findNextId();

    @Query("SELECT a FROM RegistroAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto ORDER BY a.descricao, a.dataInicio, a.horaInicio")
    List<RegistroAtividadeProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

    @Query("SELECT a FROM RegistroAtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto and a.idAtividade = :idAtividade ORDER BY a.descricao, a.dataInicio, a.horaInicio")
    Optional<RegistroAtividadeProjetoEntity> findByIdProjetoIdAtividade(@Param("idProjeto") Long idProjeto, @Param("idAtividade") Long idAtividade);
}
