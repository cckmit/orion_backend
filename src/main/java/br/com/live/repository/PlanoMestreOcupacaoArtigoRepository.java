package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PlanoMestreOcupacaoArtigo;

@Repository
public interface PlanoMestreOcupacaoArtigoRepository extends JpaRepository<PlanoMestreOcupacaoArtigo, Long> {
	
	List<PlanoMestreOcupacaoArtigo> findAll();			
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
