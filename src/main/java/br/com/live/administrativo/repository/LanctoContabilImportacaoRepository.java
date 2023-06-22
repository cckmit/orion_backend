package br.com.live.administrativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.LancamentoContabeisImport;

@Repository
public interface LanctoContabilImportacaoRepository extends JpaRepository<LancamentoContabeisImport, String>{
	
	List<LancamentoContabeisImport> findAll();
	
	@Query(" SELECT c FROM LancamentoContabeisImport c where c.id = :id ")
	LancamentoContabeisImport findByIdLancto(int id);
	
	@Query(" SELECT d FROM LancamentoContabeisImport d where d.usuario = :usuario ")
	List<LancamentoContabeisImport> findByUser(String usuario);
	
	void deleteByUsuario(String usuario);
	
	void deleteById(int id);
}
