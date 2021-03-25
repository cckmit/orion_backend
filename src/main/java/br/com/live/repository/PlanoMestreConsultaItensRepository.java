package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PlanoMestreConsultaItens;

@Repository
public interface PlanoMestreConsultaItensRepository extends JpaRepository<PlanoMestreConsultaItens, Long> {
	
	List<PlanoMestreConsultaItens> findAll();
	
	@Query("SELECT p FROM PlanoMestreConsultaItens p where p.idPlanoMestre = :idPlanoMestre order by p.grupo, p.item")
	List<PlanoMestreConsultaItens> findByIdPlanoMestre(long idPlanoMestre);

}
