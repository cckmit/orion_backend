package br.com.live.administrativo.repository;

import br.com.live.administrativo.entity.LogTituloNfsFranchisingEntity;
import br.com.live.administrativo.entity.RespostaSoapNfsFranchisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LogTituloNfsFranchisingRepository extends JpaRepository<LogTituloNfsFranchisingEntity, Long> {

    @Query("SELECT NVL(MAX(u.id),0) + 1 FROM LogTituloNfsFranchisingEntity u")
    Long findNextId();

    @Query("SELECT e FROM LogTituloNfsFranchisingEntity e WHERE e.numDuplicata = :numduplicata AND e.cnpjTomador = :cnpjTomador AND ROWNUM <= 1 ORDER BY e.dataEnvio DESC")
    Optional<LogTituloNfsFranchisingEntity> findTopLogTituloByDuplicataCliente(@Param("numduplicata") String numDuplicata, @Param("cnpjTomador") String cnpjTomador);
}
