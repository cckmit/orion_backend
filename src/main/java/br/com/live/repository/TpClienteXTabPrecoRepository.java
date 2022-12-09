package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.TpClienteXTabPreco;

@Repository
public interface TpClienteXTabPrecoRepository extends JpaRepository<TpClienteXTabPreco, String>{
	
	List<TpClienteXTabPreco> findAll();
	
	@Query(" SELECT a FROM TpClienteXTabPreco a where a.id = :id ")
	TpClienteXTabPreco findByIdTpCliTabPreco(String id);
	
	void deleteById(String id);
}
