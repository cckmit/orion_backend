package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.PedidoCustomizado;


@Repository
public interface PedidoCustomizadoRepository extends JpaRepository<PedidoCustomizado, Integer>{
	
	List<PedidoCustomizado> findAll();
	
	@Query(" SELECT s FROM PedidoCustomizado s where s.id = :id ")
	List<PedidoCustomizado> findByIdPedidosCustom(long id);
	
	@Query(" SELECT s FROM PedidoCustomizado s where s.dataRegistro = TRUNC(sysdate) ")
	List<PedidoCustomizado> findAllByDate();
	
	@Query(" SELECT d FROM PedidoCustomizado d where d.solicitacao = :solicitacao ")
	List<PedidoCustomizado> findBySolicitacao(int solicitacao);
	
	@Query(" SELECT nvl(max(a.id),0) + 1 FROM PedidoCustomizado a ")
	long findNextId();
	
	@Query(" SELECT nvl(max(a.solicitacao),0) + 1 FROM PedidoCustomizado a ")
	int findNextSolicit();

}
