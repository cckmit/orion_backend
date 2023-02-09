package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.entity.OrdemBeneficiamentoItem;

public interface OrdemBeneficiamentoItemRepository extends JpaRepository<OrdemBeneficiamentoItem, Integer>{
	
	List<OrdemBeneficiamentoItem> findAll();
	
	@Query("SELECT a FROM OrdemBeneficiamentoItem a where a.id = :id")
	OrdemBeneficiamentoItem findById(String id);
	
	void deleteById(String id);
	void deleteByUsuario(String ususario);

}
