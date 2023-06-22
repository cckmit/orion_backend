package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestreParamProgItem;

@Repository
public interface PlanoMestreParamProgItemRepository extends JpaRepository<PlanoMestreParamProgItem, Long> {
		 
	@Query("SELECT p FROM PlanoMestreParamProgItem p where p.idPlanoMestre = :idPlanoMestre")
	List<PlanoMestreParamProgItem> findByIdPlanoMestre(long idPlanoMestre);
	
	@Query("SELECT p FROM PlanoMestreParamProgItem p where p.idItemPlanoMestre = :idItemPlanoMestre")
	PlanoMestreParamProgItem findByIdItemPlanoMestre(long idItemPlanoMestre);
		
	void deleteByIdPlanoMestre(long idPlanoMestre);
}
