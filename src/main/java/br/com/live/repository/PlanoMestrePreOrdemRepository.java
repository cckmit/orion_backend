package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.live.entity.PlanoMestrePreOrdem;

@Repository
public interface PlanoMestrePreOrdemRepository extends JpaRepository<PlanoMestrePreOrdem, Long> {
	
	List<PlanoMestrePreOrdem> findAll();
	
	@Query("SELECT p FROM PlanoMestrePreOrdem p where p.idPlanoMestre = :idPlanoMestre order by p.id")
	List<PlanoMestrePreOrdem> findByIdPlanoMestre(long idPlanoMestre);

	@Query("SELECT p FROM PlanoMestrePreOrdem p where p.id = :id")
	PlanoMestrePreOrdem findById(long id);	
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
