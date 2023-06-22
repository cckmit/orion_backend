package br.com.live.sistema.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.sistema.entity.PlanoNegocioEntity;

public interface PlanoNegocioRepository extends JpaRepository<PlanoNegocioEntity, Long>{

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM PlanoNegocioEntity a ")
    long findNextId();

}
