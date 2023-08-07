package br.com.live.administrativo.repository;

import br.com.live.administrativo.entity.ImportacaoParametroCustoEntity;
import br.com.live.producao.entity.ConfiguracaoEstagios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportacaoParametroCustoRepository extends JpaRepository<ImportacaoParametroCustoEntity, String> {

    @Query("SELECT u FROM ImportacaoParametroCustoEntity u where u.usuario = :usuario")
    List<ImportacaoParametroCustoEntity> findByUsuario(int usuario);

    void deleteById(String id);

    void deleteByUsuario(int usuario);
}
