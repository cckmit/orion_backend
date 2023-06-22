package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.MetasPorUsuario;

@Repository
public interface MetasPorUsuarioRepository extends JpaRepository<MetasPorUsuario, String> {
	
	List<MetasPorUsuario> findAll();
	
	@Query("SELECT u FROM MetasPorUsuario u where u.id = :idMeta")
	MetasPorUsuario findByIdMeta(String idMeta);
	
	void deleteById(String idMeta);
}