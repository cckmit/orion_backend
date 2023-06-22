package br.com.live.util.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.util.entity.Parametros;

public interface ParametrosRepository extends JpaRepository<Parametros, String> {

    @Query("SELECT u FROM Parametros u where u.id = :idParametro")
    Parametros findByIdParametro(String idParametro);
}
