package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.EstacaoTabelaPreco;

@Repository
public interface EstacaoTabelaPrecoRepository extends JpaRepository<EstacaoTabelaPreco, String> {
	
	@Query("SELECT u FROM EstacaoTabelaPreco u where u.id = :id")
	EstacaoTabelaPreco findByIdComposto(String id);
	
	@Query("SELECT u FROM EstacaoTabelaPreco u where u.codEstacao = :codEstacao")
	List<EstacaoTabelaPreco> findByCodEstacao(long codEstacao);
	
	void deleteByCodEstacao(long codEstacao);
}
