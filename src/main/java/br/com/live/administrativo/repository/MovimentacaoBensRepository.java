package br.com.live.administrativo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.MovimentacaoBens;

@Repository
public interface MovimentacaoBensRepository extends JpaRepository<MovimentacaoBens, Integer> {
	
	List<MovimentacaoBens> findAll();
	
	@Query("SELECT u FROM MovimentacaoBens u where u.sequencia = :sequencia")
	MovimentacaoBens findBySequencia(int sequencia);
	
	@Query("SELECT u FROM MovimentacaoBens u where u.idBem = :idBem")
	List<MovimentacaoBens> findByIdBem(int idBem);

}
