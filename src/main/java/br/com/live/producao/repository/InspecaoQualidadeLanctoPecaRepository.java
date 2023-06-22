package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.InspecaoQualidadeLanctoPeca;

@Repository
public interface InspecaoQualidadeLanctoPecaRepository extends JpaRepository<InspecaoQualidadeLanctoPeca, Long> {
	
	void deleteByIdInspecao(long idInspecao);
	
}
