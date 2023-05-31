package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.RejeicaoPecaPorTecido;

@Repository
public interface RejeicaoPecaPorTecidoRepository extends JpaRepository<RejeicaoPecaPorTecido, Integer>{
	
	List<RejeicaoPecaPorTecido> findAll();
	
	@Query("SELECT nvl(max(p.id),0) + 1 FROM RejeicaoPecaPorTecido p")
    int findNextId();

	List<RejeicaoPecaPorTecido> findById(int id);
    
    void deleteById(int id);

}
