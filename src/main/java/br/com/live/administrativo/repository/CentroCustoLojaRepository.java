package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.CentroCustoLojaEntity;

@Repository
public interface CentroCustoLojaRepository extends JpaRepository<CentroCustoLojaEntity, Long> {

    @Query("SELECT nvl(max(p.id),0) + 1 FROM CentroCustoLojaEntity p")
    Long findNextId();

}
