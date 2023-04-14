package br.com.live.repository;

import br.com.live.entity.CentroCustoLojaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroCustoLojaRepository extends JpaRepository<CentroCustoLojaEntity, Long> {

    @Query("SELECT nvl(max(p.id),0) + 1 FROM CentroCustoLojaEntity p")
    Long findNextId();

}
