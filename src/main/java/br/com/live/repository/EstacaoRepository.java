package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Estacao;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Integer> {
	
	List<Estacao> findAll();
	
	@Query("SELECT u FROM Estacao u where u.codEstacao = :codEstacao")
	Estacao findByCodEstacao(int codEstacao);
	
	void deleteByCodEstacao(int codEstacao);
}