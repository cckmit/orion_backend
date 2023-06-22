package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.IndicadoresMensal;

@Repository
public interface IndicadoresMensalRepository extends JpaRepository<IndicadoresMensal, String>{
	
	List<IndicadoresMensal> findAll();
	
	@Query("SELECT a FROM IndicadoresMensal a where a.ano = :ano and a.idIndicador = :idIndicador")
	List<IndicadoresMensal> findByAno(int ano, int idIndicador);
	
	void deleteById(String id);

}
