package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.UsuarioBi;

@Repository
public interface UsuarioBiRepository extends JpaRepository<UsuarioBi, Long> {
	
	@Query("SELECT u FROM UsuarioBi u order by u.codUsuario")
	List<UsuarioBi> findAll();
	
	@Query("SELECT u FROM UsuarioBi u where u.codUsuario = :codUsuario order by u.codUsuario")
	UsuarioBi findByCodUsuario(long codUsuario);
	
	void deleteBycodUsuario(long codUsuario);

}