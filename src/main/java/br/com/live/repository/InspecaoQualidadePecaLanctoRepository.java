package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadePecaLancto;

@Repository
public interface InspecaoQualidadePecaLanctoRepository extends JpaRepository<InspecaoQualidadePecaLancto, Long> {
	
}
