package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.LoteSugestaoColetaPorColetor;
@Repository
public interface LoteSugestaoColetaPorColetorRepository extends JpaRepository<LoteSugestaoColetaPorColetor, Long> {
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM LoteSugestaoColetaPorColetor a ")
	long findNextId();
	
	void deleteByIdLoteArea(long IdLoteArea);
}
