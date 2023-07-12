package br.com.live.sistema.repository;

import br.com.live.sistema.entity.AtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AtividadeProjetoRepository extends JpaRepository<AtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM AtividadeProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM AtividadeProjetoEntity a WHERE a.idProjeto = :idProjeto ORDER BY a.dataPrevInicio")
    List<AtividadeProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

}
