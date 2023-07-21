package br.com.live.sistema.repository;

import br.com.live.sistema.entity.FaseProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaseProjetoRepository extends JpaRepository<FaseProjetoEntity, Long> {

    @Query("SELECT f FROM FaseProjetoEntity f ORDER BY f.descricao")
    List<FaseProjetoEntity> findAllOrderByDescricao();

    @Query("SELECT nvl(max(a.id), 0) + 1 FROM FaseProjetoEntity a")
    long findNextId();
}