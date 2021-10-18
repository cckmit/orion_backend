package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ParametrosCalendario;

@Repository
public interface ParametrosCalendarioRepository extends JpaRepository<ParametrosCalendario, Integer> {
	
	@Query("SELECT u FROM ParametrosCalendario u where u.anoCalendario = :anoCalendario")
	ParametrosCalendario findByAnoCalendario(int anoCalendario);
	
	void deleteByAnoCalendario(int anoCalendario);
}