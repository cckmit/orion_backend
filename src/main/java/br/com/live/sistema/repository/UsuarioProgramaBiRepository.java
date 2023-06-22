package br.com.live.sistema.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.UsuarioProgramaBi;

@Repository
public interface UsuarioProgramaBiRepository extends JpaRepository<UsuarioProgramaBi, String> {
	
	List<UsuarioProgramaBi> findAll();
	
	@Query("SELECT p FROM UsuarioProgramaBi p where p.codUsuario = :codUsuario order by p.idPrograma")
	List<UsuarioProgramaBi> findByCodUsuario(long codUsuario);
	
	@Query("SELECT p FROM UsuarioProgramaBi p where p.codUsuario = :codUsuario and p.idPrograma = :idPrograma")
	UsuarioProgramaBi findByCodUsuarioAndIdPrograma(long codUsuario, String idPrograma);

	@Query("SELECT p FROM UsuarioProgramaBi p where p.idPrograma = :idPrograma")
	List<UsuarioProgramaBi> findByIdPrograma(String idPrograma);
	
	void deleteByCodUsuario(long codUsuario);
	void deleteByIdPrograma(String idPrograma);
	void deleteByCodUsuarioAndIdPrograma(long codUsuario, String idPrograma);
}
