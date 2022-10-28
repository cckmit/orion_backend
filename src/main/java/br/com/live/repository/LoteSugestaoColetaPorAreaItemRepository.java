package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColetaPorAreaItem;
@Repository
public interface LoteSugestaoColetaPorAreaItemRepository extends JpaRepository<LoteSugestaoColetaPorAreaItem, Long> {

	@Query(" SELECT nvl(max(a.id),0) + 1 FROM LoteSugestaoColetaPorAreaItem a ")
	long findNextId();

	void deleteByIdLoteArea(long idLoteArea);	
}
