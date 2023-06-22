package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.AreaIndicador;

@Repository
public interface TipoIndicadorRepository extends JpaRepository<AreaIndicador, String>{
	
	List<AreaIndicador> findAll();
	
	@Query("SELECT a FROM AreaIndicador a where a.id = :id")
	AreaIndicador findByIdArea(String id);
	
	void deleteById(String id);
}
