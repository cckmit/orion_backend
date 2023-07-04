package br.com.live.sistema.repository;

import br.com.live.sistema.entity.TarefaTipoAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaTipoAtividadeProjetoRepository extends JpaRepository<TarefaTipoAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM TarefaTipoAtividadeProjetoEntity a ")
    long findNextId();

    @Query(" SELECT nvl(max(a.ordenacao),0) + 1 FROM TarefaTipoAtividadeProjetoEntity a WHERE a.idTipoAtividade = :idTipoAtividade ")
    int findNextOrdem(@Param("idTipoAtividade") Long idTipoAtividade);;

    @Query("SELECT a FROM TarefaTipoAtividadeProjetoEntity a WHERE a.idTipoAtividade = :idTipoAtividade ORDER BY a.ordenacao")
    List<TarefaTipoAtividadeProjetoEntity> findByTipoAtividadeId(@Param("idTipoAtividade") Long idTipoAtividade);

}
