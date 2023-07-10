package br.com.live.sistema.repository;

import br.com.live.sistema.entity.AprovadorProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprovadorProjetoRepository extends JpaRepository<AprovadorProjetoEntity, String> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM AprovadorProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM AprovadorProjetoEntity a WHERE a.idProjeto = :idProjeto")
    List<AprovadorProjetoEntity> findAprovadorByIdProjeto(@Param("idProjeto") Long idProjeto);

}
