package br.com.live.repository;

import br.com.live.entity.CentroCustoLoja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroCustoLojaRepository extends JpaRepository<CentroCustoLoja, Long> {

    @Query("SELECT nvl(max(p.id),0) + 1 FROM CentroCustoLoja p")
    Long findNextId();

}
