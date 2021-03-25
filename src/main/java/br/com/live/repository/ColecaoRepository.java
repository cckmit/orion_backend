package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Colecao;

@Repository
public interface ColecaoRepository extends JpaRepository<Colecao, Long> {
	
	List<Colecao> findAll();
	
}
