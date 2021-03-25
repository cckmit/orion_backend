package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ColecaoPermanente;

@Repository
public interface ColecaoPermanenteRepository extends JpaRepository<ColecaoPermanente, Long> {
	
	List<ColecaoPermanente> findAll();
	
}
