package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ResultadosIndicadorMensal;

@Repository
public interface ResultadosIndicadorMensalRepository extends JpaRepository<ResultadosIndicadorMensal, String>{
	
	List<ResultadosIndicadorMensal> findAll();
	
	@Query("SELECT a FROM ResultadosIndicadorMensal a where a.ano = :ano and a.idIndicador = :idIndicador")
	List<ResultadosIndicadorMensal> findByAno(int ano, int idIndicador);

}
