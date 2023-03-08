package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LancamentoContabeisImport;

@Repository
public interface LanctoContabilImportacaoRepository extends JpaRepository<LancamentoContabeisImport, String>{
	
	List<LancamentoContabeisImport> findAll();
	
	@Query(" SELECT c FROM LancamentoContabeisImport c where c.id = :id ")
	LancamentoContabeisImport findByIdLancto(int id);
	
	void deleteById(int id);
}
