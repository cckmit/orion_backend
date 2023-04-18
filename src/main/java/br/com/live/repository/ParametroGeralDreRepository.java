package br.com.live.repository;

import br.com.live.entity.ParametroGeralDreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroGeralDreRepository extends JpaRepository<ParametroGeralDreEntity, Long> {

    @Query("SELECT nvl(max(a.id),0) + 1 FROM ParametroGeralDreEntity a")
    long findNextID();

}
