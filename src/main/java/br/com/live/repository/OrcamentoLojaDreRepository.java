package br.com.live.repository;

import br.com.live.entity.OrcamentoLojaDreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoLojaDreRepository extends JpaRepository<OrcamentoLojaDreEntity, Long> {

    @Query("SELECT NVL(MAX(u.id),0) + 1 FROM OrcamentoLojaDreEntity u")
    Long findNextId();

}
