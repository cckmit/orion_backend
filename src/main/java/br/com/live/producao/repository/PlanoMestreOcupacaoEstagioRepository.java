package br.com.live.producao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestreOcupacaoEstagio;

@Repository
public interface PlanoMestreOcupacaoEstagioRepository extends JpaRepository<PlanoMestreOcupacaoEstagio, Long> {
	
	List<PlanoMestreOcupacaoEstagio> findAll();			
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
