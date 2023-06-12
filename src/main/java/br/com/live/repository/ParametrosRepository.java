package br.com.live.repository;

import br.com.live.entity.Parametros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParametrosRepository extends JpaRepository<Parametros, String> {

    @Query("SELECT u FROM Parametros u where u.id = :idParametro")
    Parametros findByIdParametro(String idParametro);
}
