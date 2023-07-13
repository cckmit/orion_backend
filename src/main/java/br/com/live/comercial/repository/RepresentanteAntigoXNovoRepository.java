package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.RepresentanteAntigoXNovo;

@Repository
public interface RepresentanteAntigoXNovoRepository extends JpaRepository<RepresentanteAntigoXNovo, Integer>{
	
	List<RepresentanteAntigoXNovo> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM RepresentanteAntigoXNovo a")
	int findNextID();
	
	@Query("SELECT u FROM RepresentanteAntigoXNovo u where u.id = :id")
	RepresentanteAntigoXNovo findById(int id);
	
	void deleteById(int id);

}
