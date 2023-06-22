package br.com.live.producao.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.ProdutoPlanoMestrePorCor;

@Repository
public interface ProdutoPlanoMestrePorCorRepository extends JpaRepository<ProdutoPlanoMestrePorCor, Long> {
	
	List<ProdutoPlanoMestrePorCor> findAll();
	
	@Query("SELECT p FROM ProdutoPlanoMestrePorCor p where p.idPlanoMestre = :idPlanoMestre order by p.grupo")
	List<ProdutoPlanoMestrePorCor> findByIdPlanoMestre(long idPlanoMestre);

	@Query("SELECT p FROM ProdutoPlanoMestrePorCor p where p.idPlanoMestre = :idPlanoMestre and p.grupo = :grupo and p.item = :item")
	ProdutoPlanoMestrePorCor findByCodigo(long idPlanoMestre, String grupo, String item);
		
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
