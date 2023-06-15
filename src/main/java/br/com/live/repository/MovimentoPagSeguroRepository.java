package br.com.live.repository;

import br.com.live.entity.MovimentoPagSeguroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoPagSeguroRepository extends JpaRepository<MovimentoPagSeguroEntity, Long> {

    @Query("SELECT NVL(MAX(u.id),0) + 1 FROM MovimentoPagSeguroEntity u")
    Long findNextId();
}
