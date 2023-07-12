package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.CanaisDeDistribuicao;

@Repository
public interface CanaisDeDistribuicaoRepository extends JpaRepository<CanaisDeDistribuicao, Integer>{
	
	List<CanaisDeDistribuicao> findAll();
	
	@Query("SELECT a FROM CanaisDeDistribuicao a where a.id = :id")
	CanaisDeDistribuicao findByIdCanal(int id);
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM CanaisDeDistribuicao a")
	int findNextID();
	
	void deleteById(int id);

}
