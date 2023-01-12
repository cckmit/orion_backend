package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.entity.ResultadosIndicadorDiario;

public interface ResultadosIndicadorDiarioRepository extends JpaRepository<ResultadosIndicadorDiario, String>{
	
	List<ResultadosIndicadorDiario> findAll();
	
	@Query("SELECT c FROM ResultadosIndicadorDiario c where c.idIndicador = :idIndicador AND c.mes = :mes AND c.ano = :ano")
	List<ResultadosIndicadorDiario> findByDia(int idIndicador, String mes, int ano);

}
