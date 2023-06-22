package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.LoteSugestaoColetaPorAreaItem;

import java.util.List;

@Repository
public interface LoteSugestaoColetaPorAreaItemRepository extends JpaRepository<LoteSugestaoColetaPorAreaItem, Long> {

	@Query(" SELECT nvl(max(a.id),0) + 1 FROM LoteSugestaoColetaPorAreaItem a ")
	long findNextId();

	List<LoteSugestaoColetaPorAreaItem> findByIdLoteArea(long idLoteArea);

	void deleteByIdLoteArea(long idLoteArea);	
}
