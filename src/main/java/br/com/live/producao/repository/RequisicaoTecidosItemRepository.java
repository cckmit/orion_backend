package br.com.live.producao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.RequisicaoTecidosItem;

@Repository
public interface RequisicaoTecidosItemRepository extends JpaRepository<RequisicaoTecidosItem, Long>{

	List<RequisicaoTecidosItem> findAll();
	
	@Query("SELECT u FROM RequisicaoTecidosItem u where u.id = :id")
	RequisicaoTecidosItem findById(long id);
	
	@Query("SELECT u FROM RequisicaoTecidosItem u where u.idRequisicao = :idRequisicao order by u.sequencia")
	List<RequisicaoTecidosItem> findByIdRequisicao(long idRequisicao);

	@Query("SELECT NVL(MAX(u.id),0) + 1 FROM RequisicaoTecidosItem u")
	Integer findNextId();

	@Query("SELECT NVL(MAX(u.sequencia),0) + 1 FROM RequisicaoTecidosItem u where u.idRequisicao = :idRequisicao")
	Integer findNextSequencia(long idRequisicao);

	void deleteByIdRequisicao(long idRequisicao);
}
