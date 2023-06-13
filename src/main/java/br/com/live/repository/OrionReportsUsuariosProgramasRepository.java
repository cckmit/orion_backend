package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.OrionReportsUsuarioProgramas;
import br.com.live.entity.UsuarioProgramaBi;

@Repository
public interface OrionReportsUsuariosProgramasRepository extends JpaRepository<OrionReportsUsuarioProgramas, String>{
	
	List<OrionReportsUsuarioProgramas> findAll();

	@Query("SELECT p FROM OrionReportsUsuarioProgramas p where p.idUsuario = :idUsuario order by p.idPrograma")
	List<OrionReportsUsuarioProgramas> findByCodUsuario(int idUsuario);
	
	@Query("SELECT p FROM OrionReportsUsuarioProgramas p where p.idUsuario = :idUsuario and p.idPrograma = :idPrograma")
	OrionReportsUsuarioProgramas findByIdUsuarioAndIdPrograma(int idUsuario, String idPrograma);
	
	void deleteById(String id);
	void deleteByIdUsuarioAndIdPrograma(int idUsuario, String idPrograma);

}
