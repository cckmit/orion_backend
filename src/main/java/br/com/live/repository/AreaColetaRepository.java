package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.AreaColeta;

@Repository
public interface AreaColetaRepository extends JpaRepository<AreaColeta, Long> {

	@Query("SELECT nvl(max(p.id),0) + 1 FROM AreaColeta p")
	Long findNextId();
	
	@Query("SELECT p FROM AreaColeta p order by p.id")
	List<AreaColeta> findAll();
		
	@Query("SELECT p FROM AreaColeta p where p.id = :id")
	AreaColeta findById(long id);	
	
	@Query("SELECT p FROM AreaColeta p where p.enderecoInicio >= :endereco and p.enderecoFim <= :endereco and rownum = 1")
	AreaColeta findAreaColetaByEndereco(String endereco);
}
