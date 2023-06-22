package br.com.live.administrativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.ParcelaMostruario;

@Repository
public interface ParcelaMostruarioRepository extends JpaRepository<ParcelaMostruario, Long> {
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM ParcelaMostruario a")
	int findNextId();
	
	@Query("SELECT a FROM ParcelaMostruario a order by a.id")
	List<ParcelaMostruario> findAll();
	
	@Query("SELECT b FROM ParcelaMostruario b where b.id = :id")
	ParcelaMostruario findById(int id);	
	


}
