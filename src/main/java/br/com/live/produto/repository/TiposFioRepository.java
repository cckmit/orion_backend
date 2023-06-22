package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.TiposFio;

@Repository
public interface TiposFioRepository extends JpaRepository<TiposFio, Integer> {
	
	List<TiposFio> findAll();
	
	@Query("SELECT u FROM TiposFio u where u.id = :idTipo")
	TiposFio findByIdTipo(int idTipo);
	
	void deleteById(int id);
	
}