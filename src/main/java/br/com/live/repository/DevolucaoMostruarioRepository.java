package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.DevolucaoMostruario;

@Repository
public interface DevolucaoMostruarioRepository extends JpaRepository<DevolucaoMostruario, Integer>{
	
	List<DevolucaoMostruario> findAll();
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM DevolucaoMostruario a ")
	int findNextId();
	
	void deleteById(int id);

}
