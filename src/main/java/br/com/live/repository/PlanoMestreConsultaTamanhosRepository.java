package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PlanoMestreConsultaTamanhos;

@Repository
public interface PlanoMestreConsultaTamanhosRepository extends JpaRepository<PlanoMestreConsultaTamanhos, Long> {
	
	List<PlanoMestreConsultaTamanhos> findAll();
	
	@Query("SELECT p FROM PlanoMestreConsultaTamanhos p where p.idPlanoMestre = :idPlanoMestre order by p.grupo, p.item, p.ordem, p.sub ")
	List<PlanoMestreConsultaTamanhos> findByIdPlanoMestre(long idPlanoMestre);

	@Query("SELECT p FROM PlanoMestreConsultaTamanhos p where p.idPlanoMestre = :idPlanoMestre and p.grupo = :grupo and p.item = :item order by p.ordem ")	
	List<PlanoMestreConsultaTamanhos> findByIdPlanoGrupoItem(long idPlanoMestre, String grupo, String item);
	
}
