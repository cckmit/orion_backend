package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.IndicadoresSemanal;

@Repository
public interface IndicadoresSemanalRepository extends JpaRepository<IndicadoresSemanal, String>{
	
	List<IndicadoresSemanal> findAll();
	
	@Query("SELECT b FROM IndicadoresSemanal b where b.idIndicador = :idIndicador AND b.mes = :mes AND b.ano = :ano")
	List<IndicadoresSemanal> findByMesAno(int idIndicador, String mes, int ano);
	
	void deleteById(String id);

}
