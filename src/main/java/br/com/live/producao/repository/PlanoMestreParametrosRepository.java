package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestreParametros;

@Repository
public interface PlanoMestreParametrosRepository extends JpaRepository<PlanoMestreParametros, Long> {
		 
	@Query("SELECT p FROM PlanoMestreParametros p where p.idPlanoMestre = :idPlanoMestre")
	PlanoMestreParametros findByIdPlanoMestre(long idPlanoMestre);
	
}
