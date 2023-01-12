package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.IndicadoresDiario;

@Repository
public interface IndicadoresDiarioRepository extends JpaRepository<IndicadoresDiario, String> {
	
	List<IndicadoresDiario> findAll();
	
	@Query("SELECT c FROM IndicadoresDiario c where c.idIndicador = :idIndicador AND c.mes = :mes AND c.ano = :ano")
	List<IndicadoresDiario> findByDia(int idIndicador, String mes, int ano);

	void deleteById(String id);


}
