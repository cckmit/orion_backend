package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.UsuarioPrograma;

@Repository
public interface UsuarioProgramaRepository extends JpaRepository<UsuarioPrograma, String> {
	
	List<UsuarioPrograma> findAll();
	
	@Query("SELECT p FROM UsuarioPrograma p where p.idUsuario = :idUsuario")
	List<UsuarioPrograma> findByIdUsuario(long idUsuario);
	
	void deleteByIdUsuario(long idUsuario);
}
