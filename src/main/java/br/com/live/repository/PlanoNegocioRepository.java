package br.com.live.repository;


import br.com.live.entity.PlanoNegocioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlanoNegocioRepository extends JpaRepository<PlanoNegocioEntity, Long>{

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM PlanoNegocioEntity a ")
    long findNextId();

}
