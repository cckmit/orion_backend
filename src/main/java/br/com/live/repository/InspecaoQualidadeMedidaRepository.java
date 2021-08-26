package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadeMedidaBKP;

@Repository
public interface InspecaoQualidadeMedidaRepository extends JpaRepository<InspecaoQualidadeMedidaBKP, Long> {
	
}
