package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ProgramaBi;

@Repository
public interface ProgramaBiRepository extends JpaRepository<ProgramaBi, String> {
	
	List<ProgramaBi> findAll();
	
	@Query("SELECT u FROM ProgramaBi u where u.id = :idProgramaBi")
	ProgramaBi findByIdPrograma(String idProgramaBi);
	
	@Query("SELECT u FROM ProgramaBi u where u.id || u.descricao || u.areaModulo like '%:chavePesquisa%'")
	List<ProgramaBi> filtrarProgramas(String idProgramaBi);

}

