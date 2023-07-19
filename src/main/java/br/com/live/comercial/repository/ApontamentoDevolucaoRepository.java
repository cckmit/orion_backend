package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.ApontamentoDevolucao;

@Repository
public interface ApontamentoDevolucaoRepository extends JpaRepository<ApontamentoDevolucao, Integer>{
	
	List<ApontamentoDevolucao> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM ApontamentoDevolucao a")
	int findNextID();
	
	@Query("SELECT a FROM ApontamentoDevolucao a where a.id = :id")
	ApontamentoDevolucao findById(int id);
	
	void deleteById(int id);

}
