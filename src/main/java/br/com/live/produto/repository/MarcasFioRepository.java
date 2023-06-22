package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.MarcasFio;

@Repository
public interface MarcasFioRepository extends JpaRepository<MarcasFio, Integer> {
	
	List<MarcasFio> findAll();
	
	@Query("SELECT u FROM MarcasFio u where u.id = :idMarcas")
	MarcasFio findByIdMarcas(int idMarcas);
	
	void deleteById(int id);
	
}