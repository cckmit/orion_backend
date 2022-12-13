package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.PedidoCustomizado;


@Repository
public interface PedidoCustomizadoRepository extends JpaRepository<PedidoCustomizado, Integer>{
	
	List<PedidoCustomizado> findAll();
	
	@Query(" SELECT s FROM PedidoCustomizado s where s.id = :id ")
	List<PedidoCustomizado> findByIdPedidosCustom(long id);
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM PedidoCustomizado a ")
	long findNextId();
	
	@Query(" SELECT nvl(max(a.solicitacao),0) + 1 FROM PedidoCustomizado a ")
	int findNexSolic();

}
