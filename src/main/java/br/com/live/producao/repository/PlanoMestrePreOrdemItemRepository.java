package br.com.live.producao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestrePreOrdemItem;

@Repository
public interface PlanoMestrePreOrdemItemRepository extends JpaRepository<PlanoMestrePreOrdemItem, Long> {
	
	List<PlanoMestrePreOrdemItem> findAll();
	
	@Query("SELECT p FROM PlanoMestrePreOrdemItem p where p.idOrdem = :idOrdem order by p.id")
	List<PlanoMestrePreOrdemItem> findByIdOrdem(long idOrdem);
	@Query("SELECT p FROM PlanoMestrePreOrdemItem p where p.idOrdem = :idOrdem and p.item = :item order by p.id")
	List<PlanoMestrePreOrdemItem> findByIdOrdemAndItem(long idOrdem, String item);
	@Query("SELECT p FROM PlanoMestrePreOrdemItem p where p.idOrdem = :idOrdem and p.sub = :sub and p.item = :item")
	PlanoMestrePreOrdemItem findByIdOrdemAndSubAndItem(long idOrdem, String sub, String item);
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
