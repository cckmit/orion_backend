package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LinhaProduto;

@Repository
public interface LinhaProdutoRepository extends JpaRepository<LinhaProduto, Long> {

	@Query("SELECT p FROM LinhaProduto p order by p.id")
	List<LinhaProduto> findAll();
	
}
