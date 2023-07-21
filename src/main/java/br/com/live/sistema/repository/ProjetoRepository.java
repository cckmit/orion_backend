package br.com.live.sistema.repository;

import br.com.live.sistema.entity.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<ProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM ProjetoEntity a ")
    long findNextId();

    @Query("SELECT f FROM ProjetoEntity f ORDER BY f.codProjeto")
    List<ProjetoEntity> findAllOrderByProjeto();

}
