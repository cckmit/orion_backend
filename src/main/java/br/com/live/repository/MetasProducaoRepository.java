package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasProducao;

@Repository
public interface MetasProducaoRepository extends JpaRepository<MetasProducao, String> {
	
	List<MetasProducao> findAll();
	
	@Query(" SELECT a FROM MetasProducao a where a.id = :id ")
	MetasProducao findByIdMeta(String id);
	
	void deleteById(String id);

}
