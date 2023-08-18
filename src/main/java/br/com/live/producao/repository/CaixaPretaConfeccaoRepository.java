package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.CaixaPretaConfeccao;

@Repository
public interface CaixaPretaConfeccaoRepository extends JpaRepository<CaixaPretaConfeccao, Integer>{
	
	List<CaixaPretaConfeccao> findAll();
	
	@Query(" SELECT u FROM CaixaPretaConfeccao u where u.id = :idCaixa ")
	CaixaPretaConfeccao findByidCaixa(int idCaixa);
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM CaixaPretaConfeccao a ")
	int findNextId();
	
	void deleteById(int id);

}
