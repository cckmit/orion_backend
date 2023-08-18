package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.CaixaPretaLocalConfeccao;

@Repository
public interface CaixaPretaLocalConfeccaoRepository extends JpaRepository<CaixaPretaLocalConfeccao, Integer>{
	
	List<CaixaPretaLocalConfeccao> findAll();
	
	CaixaPretaLocalConfeccao findByid(int id);
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM CaixaPretaLocalConfeccao a ")
	int findNextId();
	
	void deleteById(int id);

}
