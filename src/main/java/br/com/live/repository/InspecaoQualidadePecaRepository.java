package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadePeca;

@Repository
public interface InspecaoQualidadePecaRepository extends JpaRepository<InspecaoQualidadePeca, Long> {
	
	@Query("SELECT i FROM InspecaoQualidadePeca i where i.id = :id")
	public InspecaoQualidadePeca findById(long id);
	
}
