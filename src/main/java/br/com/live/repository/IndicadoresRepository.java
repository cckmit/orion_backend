package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Indicadores;

@Repository
public interface IndicadoresRepository extends JpaRepository<Indicadores, Integer>{
	
	List<Indicadores> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM Indicadores a")
	long findNextID();
	
	@Query("SELECT a FROM Indicadores a where a.id = :id")
	Indicadores findByIdIndicador(long id);
	
	void deleteById(long id);

}
