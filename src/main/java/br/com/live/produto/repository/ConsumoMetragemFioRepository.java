package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.ConsumoMetragemFio;

@Repository
public interface ConsumoMetragemFioRepository extends JpaRepository<ConsumoMetragemFio, String> {
	
    @Query("SELECT u FROM ConsumoMetragemFio u where u.id = :id")
	ConsumoMetragemFio findConsumoMetragemFioById(String id);

	@Query("SELECT u FROM ConsumoMetragemFio u where u.idReferencia = :idReferencia")
	List<ConsumoMetragemFio> findConsumoMetragemFioByIdReferencia(String idReferencia);

	void deleteById(String id);
}