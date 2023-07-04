package br.com.live.sistema.repository;

import br.com.live.sistema.entity.TarefaConcluidaAtividadeProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaConcluidaAtividadeProjetoRepository extends JpaRepository<TarefaConcluidaAtividadeProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM TarefaConcluidaAtividadeProjetoEntity a ")
    long findNextId();

}
