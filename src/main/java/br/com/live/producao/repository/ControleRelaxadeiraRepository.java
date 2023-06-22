package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.ControleRelaxadeira;

import java.util.List;

@Repository
public interface ControleRelaxadeiraRepository extends JpaRepository<ControleRelaxadeira, String> {

    @Query("SELECT u FROM ControleRelaxadeira u where u.sitSincronizacao = 0")
    List<ControleRelaxadeira> findRegistrosParaSincronizar();

    void deleteById(String id);
}
