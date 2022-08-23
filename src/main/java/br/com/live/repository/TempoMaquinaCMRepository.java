package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.TempoMaquinaCM;

@Repository
public interface TempoMaquinaCMRepository extends JpaRepository<TempoMaquinaCM, Long> {
	
	@Query("SELECT t FROM TempoMaquinaCM t")
	List<TempoMaquinaCM> findAllTempMaq();
	
	@Query("SELECT t FROM TempoMaquinaCM t where t.id = :idTempoMaqCM")
	TempoMaquinaCM findByidTempoMaqCM(long idTempoMaqCM);
	
	void deleteById(String idTempoMaqCM);
}