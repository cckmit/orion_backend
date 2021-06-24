package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Programa;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {
	
	List<Programa> findAll();
	
	@Query("SELECT u FROM Programa u where u.id = :idPrograma")
	Programa findByIdPrograma(long idPrograma);
	
}
