package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.AgrupadorColecao;

@Repository
public interface AgrupadorColecaoRepository extends JpaRepository<AgrupadorColecao, String> {
	
	@Query("SELECT u FROM AgrupadorColecao u where u.codAgrupador = :codAgrupador")
	AgrupadorColecao findByCodEstacao(int codAgrupador);
	
	@Query("SELECT u FROM AgrupadorColecao u where u.id = :idComposto")
	AgrupadorColecao findByIdComposto(String idComposto);
	
	void deleteById(String id);
}
