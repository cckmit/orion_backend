package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Agrupador;

@Repository
public interface AgrupadorRepository extends JpaRepository<Agrupador, Integer> {
	
	@Query("SELECT u FROM Agrupador u where u.codAgrupador = :codAgrupador")
	Agrupador findByCodAgrupador(int codAgrupador);
}
