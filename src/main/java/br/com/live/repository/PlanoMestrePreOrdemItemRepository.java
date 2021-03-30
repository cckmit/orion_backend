package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.live.entity.PlanoMestrePreOrdemItem;

@Repository
public interface PlanoMestrePreOrdemItemRepository extends JpaRepository<PlanoMestrePreOrdemItem, Long> {
	
	List<PlanoMestrePreOrdemItem> findAll();
	
	@Query("SELECT p FROM PlanoMestrePreOrdemItem p where p.idOrdem = :idOrdem order by p.id")
	List<PlanoMestrePreOrdemItem> findByIdOrdem(long idOrdem);

	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
