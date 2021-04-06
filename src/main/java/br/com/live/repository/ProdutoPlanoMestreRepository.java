package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ProdutoPlanoMestre;

@Repository
public interface ProdutoPlanoMestreRepository extends JpaRepository<ProdutoPlanoMestre, Long> {
	
	List<ProdutoPlanoMestre> findAll();
	
	@Query("SELECT p FROM ProdutoPlanoMestre p where p.idPlanoMestre = :idPlanoMestre order by p.grupo")
	List<ProdutoPlanoMestre> findByIdPlanoMestre(long idPlanoMestre);

	@Query("SELECT p FROM ProdutoPlanoMestre p where p.idPlanoMestre = :idPlanoMestre and p.nivel = '1' and p.grupo = :grupo and p.item = :item")
	List<ProdutoPlanoMestre> findByIdPlanoCodGrupoCor(long idPlanoMestre, String grupo, String item);

	@Query("SELECT p FROM ProdutoPlanoMestre p where p.idPlanoMestre = :idPlanoMestre and p.nivel = '1' and p.grupo = :grupo and p.sub = :sub and p.item = :item")
	ProdutoPlanoMestre findByIdPlanoCodGrupoSubCor(long idPlanoMestre, String grupo, String sub, String item);
	
	void deleteByIdPlanoMestre(long idPlanoMestre);
	
}
