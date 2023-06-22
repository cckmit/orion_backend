package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.Estacao;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Integer> {
	
	@Query(" SELECT u FROM Estacao u order by u.codEstacao desc")
	List<Estacao> findAll();
	
	@Query("SELECT u FROM Estacao u where u.codEstacao = :codEstacao")
	Estacao findByCodEstacao(long codEstacao);
	
	void deleteByCodEstacao(long codEstacao);
}