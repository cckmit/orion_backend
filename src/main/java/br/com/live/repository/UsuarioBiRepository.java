package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.UsuarioBi;

@Repository
public interface UsuarioBiRepository extends JpaRepository<UsuarioBi, Long> {
	
	List<UsuarioBi> findAll();
	
	@Query("SELECT u FROM UsuarioBi u where u.codUsuario = :codUsuario")
	UsuarioBi findByCodUsuario(long codUsuario);
	
	void deleteBycodUsuario(long codUsuario);

}