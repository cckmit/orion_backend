package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.DebitoCreditoPorRepresentante;

@Repository
public interface DebitoCreditoPorRepresentanteRepository extends JpaRepository<DebitoCreditoPorRepresentante, Integer>{
	
	List<DebitoCreditoPorRepresentante> findAll();

    @Query("SELECT u FROM DebitoCreditoPorRepresentante u where u.id = :id")
    DebitoCreditoPorRepresentante findByIdLancto(long id);
    
    void deleteById(long id);

}
