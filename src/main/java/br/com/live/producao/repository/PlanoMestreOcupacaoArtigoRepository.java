package br.com.live.producao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestreOcupacaoArtigo;

@Repository
public interface PlanoMestreOcupacaoArtigoRepository extends JpaRepository<PlanoMestreOcupacaoArtigo, Long> {
	
	List<PlanoMestreOcupacaoArtigo> findAll();			
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
