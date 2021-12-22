package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import br.com.live.entity.EstacaoAgrupador;

@Repository
public interface EstacaoAgrupadorRepository extends JpaRepository<EstacaoAgrupador, String> {
	
	@Query("SELECT u FROM EstacaoAgrupador u where u.id = :idComposto")
	EstacaoAgrupador findByIdComposto(String idComposto);
	
	@Query("SELECT u FROM EstacaoAgrupador u where u.codEstacao = :codEstacao")
	List<EstacaoAgrupador> findByCodEstacao(long codEstacao);
	
	void deleteById(String id);
	
	void deleteByCodEstacao(long codEstacao);
}
