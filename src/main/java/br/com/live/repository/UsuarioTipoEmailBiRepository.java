package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.UsuarioTipoEmailBi;

@Repository
public interface UsuarioTipoEmailBiRepository extends JpaRepository<UsuarioTipoEmailBi, String> {
	
	List<UsuarioTipoEmailBi> findAll();
	
	@Query("SELECT p FROM UsuarioTipoEmailBi p where p.codUsuario = :codUsuario")
	List<UsuarioTipoEmailBi> findByIdUsuario(long codUsuario);

	@Query("SELECT p FROM UsuarioTipoEmailBi p where p.codUsuario = :codUsuario and p.idPrograma = :idPrograma")
	List<UsuarioTipoEmailBi> findByIdUsuarioIdPrograma(long codUsuario, String idPrograma);
	
	void deleteByCodUsuario(long codUsuario);	
	void deleteByIdPrograma(String idPrograma);
	void deleteByCodUsuarioAndIdPrograma(long codUsuario, String idPrograma);
}
