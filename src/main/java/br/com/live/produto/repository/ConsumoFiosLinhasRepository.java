package br.com.live.produto.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.ConsumoFiosLinhas;

@Repository
public interface ConsumoFiosLinhasRepository extends JpaRepository<ConsumoFiosLinhas, String> {
	
    @Query("SELECT u FROM ConsumoFiosLinhas u where u.id = :id")
	ConsumoFiosLinhas findConsumoById(String id);

	void deleteById(String id);
}