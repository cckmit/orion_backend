package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.OperacaoXMicromovimentos;

@Repository
public interface OperXMicromvRepository extends JpaRepository<OperacaoXMicromovimentos, Integer> {
	List<OperacaoXMicromovimentos> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM OperacaoXMicromovimentos a")
	long findNextID();
	
	@Query("SELECT a FROM OperacaoXMicromovimentos a where a.id = :id")
	OperacaoXMicromovimentos findById(long id);
	
	@Query("SELECT a FROM OperacaoXMicromovimentos a where a.codOperacao = :codOperacao order by a.sequencia")
	List<OperacaoXMicromovimentos> findByCodOper(int codOperacao);
	
	void deleteByCodOperacao(int codOperacao);
	
	void deleteById(long id);

}
