package br.com.live.sistema.repository;

import br.com.live.sistema.entity.CustoOrcamentoProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustoOrcamentoProjetoRepository extends JpaRepository<CustoOrcamentoProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM CustoOrcamentoProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM CustoOrcamentoProjetoEntity a WHERE a.idProjeto = :idProjeto")
    List<CustoOrcamentoProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);
}
