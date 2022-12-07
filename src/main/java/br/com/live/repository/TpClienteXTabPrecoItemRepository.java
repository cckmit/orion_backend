package br.com.live.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.TpClienteXTabPrecoItem;

@Repository
public interface TpClienteXTabPrecoItemRepository extends JpaRepository<TpClienteXTabPrecoItem, Long>{
	
	List<TpClienteXTabPrecoItem> findAll();
	
	@Query(" SELECT a FROM TpClienteXTabPrecoItem a where a.idItem = :idItem ")
	TpClienteXTabPrecoItem findByIdTpCliTabPrecoItem(long idItem);
	
	@Query(" SELECT a FROM TpClienteXTabPrecoItem a where a.idItem <> :idItem and a.idCapa = :idCapa and ((:dtInicio between a.periodoIni and a.periodoFim) or (:dtFinal between a.periodoIni and a.periodoFim)) and rownum = 1")
	TpClienteXTabPrecoItem findTabByData(long idItem, String idCapa, Date dtInicio, Date dtFinal);
	
	@Query(" SELECT NVL(MAX(a.idItem), 0) + 1 FROM TpClienteXTabPrecoItem a ")
	long findNextId();
	
	void deleteById(long idItem);
	
	void deleteByIdCapa(String idCapa);
	
}
