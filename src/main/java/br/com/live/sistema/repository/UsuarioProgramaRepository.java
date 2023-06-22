package br.com.live.sistema.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.UsuarioPrograma;
import br.com.live.sistema.entity.UsuarioProgramaBi;

@Repository
public interface UsuarioProgramaRepository extends JpaRepository<UsuarioPrograma, String> {
	
	List<UsuarioPrograma> findAll();
	void deleteByIdUsuario(long idUsuario);
	void deleteByIdPrograma(long idPrograma);
	void deleteByIdUsuarioAndIdPrograma(long idUsuario, long idPrograma);
	
	@Query("SELECT p FROM UsuarioPrograma p where p.idUsuario = :idUsuario")
	List<UsuarioPrograma> findByIdUsuario(long idUsuario);

	@Query("SELECT p FROM UsuarioPrograma p where p.idUsuario = :idUsuario and p.idPrograma = :idPrograma")
	UsuarioPrograma findByIdUsuarioAndIdPrograma(long idUsuario, long idPrograma);

	@Query("SELECT p FROM UsuarioPrograma p where p.idPrograma = :idPrograma")
	List<UsuarioPrograma> findByIdPrograma(long idPrograma);
}
