package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.MetasProducaoSemana;

@Repository
public interface MetasProducaoSemanaRepository extends JpaRepository<MetasProducaoSemana, Long> {
	
	@Query(" SELECT a FROM MetasProducaoSemana a where a.id = :id ")
	MetasProducaoSemana findById(long id);
	
	void deleteById(long id);
}
