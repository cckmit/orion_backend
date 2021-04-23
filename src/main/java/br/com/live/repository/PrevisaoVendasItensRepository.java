package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PrevisaoVendasItens;

@Repository
public interface PrevisaoVendasItensRepository extends JpaRepository<PrevisaoVendasItens, Long> {

	@Query("SELECT p FROM PrevisaoVendasItens p where p.idPrevisaoVendas = :idPrevisaoVendas")
	List<PrevisaoVendasItens> findByIdPrevisaoVendas(long idPrevisaoVendas);

}
