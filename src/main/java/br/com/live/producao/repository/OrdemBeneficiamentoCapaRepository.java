package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.producao.entity.OrdemBeneficiamentoCapa;

public interface OrdemBeneficiamentoCapaRepository extends JpaRepository<OrdemBeneficiamentoCapa, Integer> {
	
	List<OrdemBeneficiamentoCapa> findAll();
	
	@Query("SELECT a FROM OrdemBeneficiamentoCapa a where a.id = :idOrdemBenef")
	OrdemBeneficiamentoCapa findByIdOrdemBenef(long idOrdemBenef);
	
	void deleteById(long idOrdemBenef);

}
