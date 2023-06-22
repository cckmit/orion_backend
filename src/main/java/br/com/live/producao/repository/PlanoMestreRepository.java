package br.com.live.producao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.PlanoMestre;

@Repository
public interface PlanoMestreRepository extends JpaRepository<PlanoMestre, Long> {

	@Query("SELECT p FROM PlanoMestre p order by p.id desc")
	List<PlanoMestre> findAll();
		
	@Query("SELECT p FROM PlanoMestre p where p.id = :id")
	PlanoMestre findById(long id);	
}
