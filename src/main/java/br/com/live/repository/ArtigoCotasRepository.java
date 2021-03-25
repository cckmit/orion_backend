package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ArtigoCotas;

@Repository
public interface ArtigoCotasRepository extends JpaRepository<ArtigoCotas, Long> {

	@Query("SELECT p FROM ArtigoCotas p order by p.id")
	List<ArtigoCotas> findAll();
	
}
