package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.UsuarioProgramaBi;

@Repository
public interface UsuarioProgramaBiRepository extends JpaRepository<UsuarioProgramaBi, String> {
	
	List<UsuarioProgramaBi> findAll();
	
	@Query("SELECT p FROM UsuarioProgramaBi p where p.codUsuario = :codUsuario")
	List<UsuarioProgramaBi> findByCodUsuario(long codUsuario);
	
	void deleteByCodUsuario(long codUsuario);
}
