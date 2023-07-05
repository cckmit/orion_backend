package br.com.live.sistema.repository;

import br.com.live.sistema.entity.FuncaoPessoaProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncaoPessoaProjetoRepository extends JpaRepository<FuncaoPessoaProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM FuncaoPessoaProjetoEntity a ")
    long findNextId();

}
