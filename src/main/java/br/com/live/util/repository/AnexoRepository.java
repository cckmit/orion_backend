package br.com.live.util.repository;

import br.com.live.util.entity.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM Anexo a ")
    long findNextId();

    @Query("SELECT a FROM Anexo a WHERE a.chave = :chave ORDER BY a.dataRegistro")
    List<Anexo> findAllByChave(@Param("chave") String chave);

}
