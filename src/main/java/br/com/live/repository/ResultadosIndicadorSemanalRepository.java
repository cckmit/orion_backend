package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ResultadosIndicadorSemanal;

@Repository
public interface ResultadosIndicadorSemanalRepository extends JpaRepository<ResultadosIndicadorSemanal, String>{
	
	List<ResultadosIndicadorSemanal> findAll();
	
	@Query("SELECT b FROM ResultadosIndicadorSemanal b where b.mes = :mes and b.ano = :ano and b.idIndicador = :idIndicador")
	List<ResultadosIndicadorSemanal> findByMesAno(String mes, int ano, int idIndicador);

}
