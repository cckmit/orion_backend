package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CapacidadeCotasVendasItens;

@Repository
public interface CapacidadeCotasVendasItensRepository extends JpaRepository<CapacidadeCotasVendasItens, String> {
	
	
	@Query("SELECT u FROM CapacidadeCotasVendasItens u where u.id = :idCotasVendasItens")
	CapacidadeCotasVendasItens findByIdCapacidadeCotasVendas(String idCotasVendasItens);
	
	void deleteByIdCapa(String idCapa);
	
}
