package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.OrcamentoLojaDreEntity;

@Repository
public interface OrcamentoLojaDreRepository extends JpaRepository<OrcamentoLojaDreEntity, Long> {

    @Query("SELECT NVL(MAX(u.id),0) + 1 FROM OrcamentoLojaDreEntity u")
    Long findNextId();

}
