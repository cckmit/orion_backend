package br.com.live.sistema.repository;

import br.com.live.sistema.entity.TipoAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAtividadeProjetoRepository extends JpaRepository<TipoAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM TipoAtividadeProjetoEntity a ")
    long findNextId();
}
