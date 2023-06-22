package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.comercial.entity.PoliticaVendas;

public interface PoliticaVendasRepository extends JpaRepository<PoliticaVendas, Integer> {
	
	List<PoliticaVendas> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM PoliticaVendas a")
	int findNextID();
	
	@Query("SELECT a FROM PoliticaVendas a where a.id = :id")
	PoliticaVendas findByIdPoliticaVendas(int id);
	
	void deleteByTipo(int tipo);
	
	void deleteById(int id);

}
