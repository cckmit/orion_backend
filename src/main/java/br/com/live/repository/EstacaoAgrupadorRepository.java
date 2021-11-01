package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.EstacaoAgrupador;

@Repository
public interface EstacaoAgrupadorRepository extends JpaRepository<EstacaoAgrupador, String> {
	
	@Query("SELECT u FROM EstacaoAgrupador u where u.id = :idComposto")
	EstacaoAgrupador findByIdComposto(String idComposto);
	
	void deleteById(String id);
}
