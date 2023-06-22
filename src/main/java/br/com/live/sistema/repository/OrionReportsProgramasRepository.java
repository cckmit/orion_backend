package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.OrionReportsProgramas;

@Repository
public interface OrionReportsProgramasRepository extends JpaRepository<OrionReportsProgramas, String>{
	
	List<OrionReportsProgramas> findAll();

	@Query("SELECT a FROM OrionReportsProgramas a where a.id = :id")
	OrionReportsProgramas findByIdProgr(String id);
	
	void deleteById(String id);

}
