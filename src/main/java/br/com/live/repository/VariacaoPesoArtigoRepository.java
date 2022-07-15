package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.VariacaoPesoArtigo;

@Repository
public interface VariacaoPesoArtigoRepository extends JpaRepository<VariacaoPesoArtigo, Long> {
	
	List<VariacaoPesoArtigo> findAll();
	
	@Query("SELECT p FROM VariacaoPesoArtigo p where p.id = :idVariacao")
	VariacaoPesoArtigo findByIdVariacao(long idVariacao);
	
	void deleteById(long id);	
}