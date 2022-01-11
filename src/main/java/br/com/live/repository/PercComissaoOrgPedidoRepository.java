package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PercComissaoOrgPedido;

@Repository
public interface PercComissaoOrgPedidoRepository extends JpaRepository<PercComissaoOrgPedido, Integer> {
	
	List<PercComissaoOrgPedido> findAll();
	
	@Query("SELECT u FROM PercComissaoOrgPedido u where u.id = :id")
	PercComissaoOrgPedido findById(long id);
	
	void deleteAll();
}
