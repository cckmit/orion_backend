package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.Micromovimentos;

@Repository
public interface MicromovimentosRepository extends JpaRepository<Micromovimentos, String> {
	
	List<Micromovimentos> findAll();
	
	@Query("SELECT u FROM Micromovimentos u where u.id = :idMicroMov")
	Micromovimentos findByIdMicroMov(String idMicroMov);
	
	void deleteById(String idMicroMov);
}
