package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PedidoVenda;

@Repository
public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, Long> {

	@Query("SELECT p FROM PedidoVenda p where p.periodo >= :periodoInicio and p.periodo <= :periodoFim order by p.id")
	List<PedidoVenda> findByPeriodo(int periodoInicio, int periodoFim);
	
}
