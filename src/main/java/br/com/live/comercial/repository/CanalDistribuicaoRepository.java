package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.CanalDistribuicao;

@Repository
public interface CanalDistribuicaoRepository extends JpaRepository<CanalDistribuicao, Integer>{
	
	List<CanalDistribuicao> findAll();
	
	@Query("SELECT a FROM CanalDistribuicao a where a.id = :id")
	CanalDistribuicao findByIdCanal(int id);
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM CanalDistribuicao a")
	int findNextID();
	
	void deleteById(int id);

}
