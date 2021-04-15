package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PlanoMestreOcupacaoEstagio;

@Repository
public interface PlanoMestreOcupacaoEstagioRepository extends JpaRepository<PlanoMestreOcupacaoEstagio, Long> {
	
	List<PlanoMestreOcupacaoEstagio> findAll();			
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
