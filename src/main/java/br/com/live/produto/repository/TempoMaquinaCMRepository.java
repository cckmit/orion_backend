package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.TempoMaquinaCM;

@Repository
public interface TempoMaquinaCMRepository extends JpaRepository<TempoMaquinaCM, Long> {
	
	@Query("SELECT t FROM TempoMaquinaCM t")
	List<TempoMaquinaCM> findAllTempMaq();
	
	@Query("SELECT t FROM TempoMaquinaCM t where t.id = :idTempoMaqCM")
	TempoMaquinaCM findByidTempoMaqCM(long idTempoMaqCM);

	@Query("SELECT t FROM TempoMaquinaCM t where t.grupo = :grupoMaquina and t.subgrupo = :subGrupoMaquina and t.medida = :medida")
	TempoMaquinaCM findByMaqSubMaquinaCM(String grupoMaquina, String subGrupoMaquina, float medida);
	
	void deleteById(long idTempoMaqCM);
}