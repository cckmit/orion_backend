package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.TipoPonto;

@Repository
public interface TiposPontoRepository extends JpaRepository<TipoPonto, Integer> {
	
	List<TipoPonto> findAll();
	
	@Query("SELECT u FROM TipoPonto u where u.id = :idTipoPonto")
	TipoPonto findByIdTipoPonto(int idTipoPonto);
	
	void deleteById(int id);
	
}