package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.entity.PreRequisicaoAlmoxarifado;
import br.com.live.entity.PreRequisicaoAlmoxarifadoItem;

public interface PreRequisicaoAlmoxarifadoItemRepository extends JpaRepository<PreRequisicaoAlmoxarifadoItem, String> {

	@Query("SELECT p FROM PreRequisicaoAlmoxarifadoItem p where p.idPreRequisicao = :idPreRequisicao order by p.sequencia")
	List<PreRequisicaoAlmoxarifadoItem> findByIdPreRequisicao(long idPreRequisicao);
	
	@Query("SELECT p FROM PreRequisicaoAlmoxarifadoItem p where p.id = :id")
	PreRequisicaoAlmoxarifadoItem findByIdItemPreRequisicao(String id);	
		
	@Query("SELECT p FROM PreRequisicaoAlmoxarifadoItem p where p.idPreRequisicao = :idPreRequisicao and p.sequencia = :sequencia")
	PreRequisicaoAlmoxarifadoItem findByIdPreRequisicaoAndSequencia(long idPreRequisicao, int sequencia);	
	
	@Query("SELECT nvl(max(p.sequencia),0) + 1 FROM PreRequisicaoAlmoxarifadoItem p where p.idPreRequisicao = :idPreRequisicao")
	Integer findNextSequenciaByIdPreRequisicao(long idPreRequisicao);
	
	void deleteByIdPreRequisicao(long idPreRequisicao);
}
