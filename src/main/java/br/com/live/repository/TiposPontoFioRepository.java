package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.TipoPontoFio;

@Repository
public interface TiposPontoFioRepository extends JpaRepository<TipoPontoFio, String> {
	
	List<TipoPontoFio> findAll();
	
	@Query("SELECT u FROM TipoPontoFio u where u.id = :id")
	TipoPontoFio findByIdTipoPontoFio(String id);

	@Query("SELECT u FROM TipoPontoFio u where u.idTipoPonto = :idTipoPonto")
	List<TipoPontoFio> findByIdTipoPonto(int idTipoPonto);
	
	void deleteById(String id);

    void deleteByIdTipoPonto(int idTipoPonto);
	
}