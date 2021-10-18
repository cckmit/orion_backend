package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.EstagiosParametros;

@Repository
public interface EstagiosParametrosRepository extends JpaRepository<EstagiosParametros, String> {
	
	@Query("SELECT u FROM EstagiosParametros u where u.anoCalendario = :anoCalendario and u.sequencia = :sequencia")
	EstagiosParametros findByAnoCalendarioSequencia(int anoCalendario, int sequencia);
	
	@Query("SELECT u FROM EstagiosParametros u where u.anoCalendario = :anoCalendario order by u.sequencia")
	List<EstagiosParametros> findByAnoCalendario(int anoCalendario);
	
	void deleteByAnoCalendario(int anoCalendario);
}