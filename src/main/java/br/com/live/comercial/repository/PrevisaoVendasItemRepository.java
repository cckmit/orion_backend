package br.com.live.comercial.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.PrevisaoVendasItem;

@Repository
public interface PrevisaoVendasItemRepository extends JpaRepository<PrevisaoVendasItem, Long> {

	@Query("SELECT p FROM PrevisaoVendasItem p where p.idPrevisaoVendas = :idPrevisaoVendas")
	List<PrevisaoVendasItem> findByIdPrevisaoVendas(long idPrevisaoVendas);
	
	void deleteByIdPrevisaoVendas(long idPrevisaoVendas);

}
