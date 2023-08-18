package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.CaixaPretaMovimentacao;

@Repository
public interface CaixaPretaMovimentacaoRepository extends JpaRepository<CaixaPretaMovimentacao, Integer> {
	
	List<CaixaPretaMovimentacao> findAll();
	
	CaixaPretaMovimentacao findByid(int id);
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM CaixaPretaMovimentacao a ")
	int findNextId();

}
