package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.InspecaoQualidadeLanctoMedida;

@Repository
public interface InspecaoQualidadeLanctoMedidaRepository extends JpaRepository<InspecaoQualidadeLanctoMedida, Long> {

	void deleteByIdLancamento(long IdLancamento);
	void deleteByIdInspecao(long idInspecao);
	
}
