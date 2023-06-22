package br.com.live.comercial.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.ItemSugCancelProducao;

@Repository
public interface ItemSugCancelProducaoRepository extends JpaRepository<ItemSugCancelProducao, Long> {
	
	List<ItemSugCancelProducao> findAll();
	
}
