package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColetaPorArea;
@Repository
public interface LoteSugestaoColetaPorAreaRepository extends JpaRepository<LoteSugestaoColetaPorArea, Long> {

	@Query(" SELECT nvl(max(a.id),0) + 1 FROM LoteSugestaoColetaPorArea a ")
	long findNextId();

	@Query(" SELECT a FROM LoteSugestaoColetaPorArea a where a.idLote = :idLote")
	List<LoteSugestaoColetaPorArea> findAreasByLote(long idLote);	
	
	void deleteByIdLote(long idLote);
}
