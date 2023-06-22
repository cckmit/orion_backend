package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.InspecaoQualidadeLanctoMedida;

@Repository
public interface InspecaoQualidadeLanctoMedidaRepository extends JpaRepository<InspecaoQualidadeLanctoMedida, Long> {

	void deleteByIdInspecaoAndIdLancamento(long idInspecao, long IdLancamento);
	void deleteByIdLancamento(long IdLancamento);
	void deleteByIdInspecao(long idInspecao);
	
	@Query("SELECT i FROM InspecaoQualidadeLanctoMedida i where i.id = :id")
	InspecaoQualidadeLanctoMedida findById(long id);
}
