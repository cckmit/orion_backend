package br.com.live.comercial.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.PrevisaoVendas;

@Repository
public interface PrevisaoVendasRepository extends JpaRepository<PrevisaoVendas, Long> {

	@Query("SELECT p FROM PrevisaoVendas p order by p.id")
	List<PrevisaoVendas> findAll(); 
	
	@Query("SELECT p FROM PrevisaoVendas p where p.id = :id ")
	PrevisaoVendas findById(long id);
			
}
