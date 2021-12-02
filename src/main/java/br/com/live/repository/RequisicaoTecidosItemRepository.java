package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.RequisicaoTecidosItem;

@Repository
public interface RequisicaoTecidosItemRepository extends JpaRepository<RequisicaoTecidosItem, Long>{

	List<RequisicaoTecidosItem> findAll();
	
	@Query("SELECT u FROM RequisicaoTecidosItem u where u.id = :id")
	RequisicaoTecidosItem findById(long id);
	
	@Query("SELECT u FROM RequisicaoTecidosItem u where u.idRequisicao = :idRequisicao order by u.sequencia")
	List<RequisicaoTecidosItem> findByIdRequisicao(long idRequisicao);

	@Query("SELECT MAX(u.id) + 1 FROM RequisicaoTecidosItem u")
	Integer findNextId();

	@Query("SELECT MAX(u.sequencia) + 1 FROM RequisicaoTecidosItem u where u.idRequisicao = :idRequisicao")
	Integer findNextSequencia(long idRequisicao);

	void deleteByIdRequisicao(long idRequisicao);
}
