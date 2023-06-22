package br.com.live.produto.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.SubColecao;

@Repository
public interface SubColecaoRepository extends JpaRepository<SubColecao, Long> {

	@Query("SELECT p FROM SubColecao p order by p.id")
	List<SubColecao> findAll();
	
}
