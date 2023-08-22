package br.com.live.administrativo.repository;

import br.com.live.administrativo.entity.RespostaSoapNfsFranchisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RespostaSoapNfsFranchisingRepository extends JpaRepository<RespostaSoapNfsFranchisingEntity, Long> {

    @Query("SELECT COALESCE(MAX(a.numeroLote), 0) + 1 FROM RespostaSoapNfsFranchisingEntity a")
    Long findNextId();

    @Query("SELECT e FROM RespostaSoapNfsFranchisingEntity e WHERE e.codRetorno IS NULL AND e.descricaoRetorno IS NULL")
    List<RespostaSoapNfsFranchisingEntity> findProtocolosNaoProcessados();

    @Query("SELECT e FROM RespostaSoapNfsFranchisingEntity e WHERE e.numDuplicata = :numduplicata AND e.cnpjTomador = :cnpjTomador AND ROWNUM <= 1 ORDER BY e.dataRecebimento DESC")
    Optional<RespostaSoapNfsFranchisingEntity> findTopRespostaByDuplicataCliente(@Param("numduplicata") String numDuplicata, @Param("cnpjTomador") String cnpjTomador);
}
