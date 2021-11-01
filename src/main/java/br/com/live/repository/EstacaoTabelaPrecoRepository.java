package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.EstacaoTabelaPreco;

@Repository
public interface EstacaoTabelaPrecoRepository extends JpaRepository<EstacaoTabelaPreco, String> {
	
	@Query("SELECT u FROM EstacaoTabelaPreco u where u.id = :id")
	EstacaoTabelaPreco findByIdComposto(String id);
	
	void deleteByCodEstacao(int codEstacao);
}
