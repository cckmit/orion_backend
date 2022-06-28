package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.entity.PreRequisicaoAlmoxarifado;

public interface PreRequisicaoAlmoxarifadoRepository extends JpaRepository<PreRequisicaoAlmoxarifado, Long> {

	@Query("SELECT p FROM PreRequisicaoAlmoxarifado p order by p.id desc")
	List<PreRequisicaoAlmoxarifado> findAll();
		
	@Query("SELECT p FROM PreRequisicaoAlmoxarifado p where p.id = :id")
	PreRequisicaoAlmoxarifado findById(long id);	

	@Query("SELECT nvl(max(p.id),0) + 1 FROM PreRequisicaoAlmoxarifado p")
	Integer findNextId();
}
