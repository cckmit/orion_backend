package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.BloqueioTitulosForn;

@Repository
public interface BloqueioTitulosFornRepository extends JpaRepository<BloqueioTitulosForn, Integer> {
	List<BloqueioTitulosForn> findAll();
	
	@Query("SELECT u FROM BloqueioTitulosForn u where u.id = :id")
	BloqueioTitulosForn findByIdBloq(int id);
	
	void deleteById(int idBloq);
}
