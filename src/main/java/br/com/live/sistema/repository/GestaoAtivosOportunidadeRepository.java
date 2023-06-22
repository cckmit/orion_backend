package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.GestaoAtivosOportunidade;

@Repository
public interface GestaoAtivosOportunidadeRepository extends JpaRepository<GestaoAtivosOportunidade, String>{
	
	List<GestaoAtivosOportunidade> findAll();
	
	@Query("SELECT a FROM GestaoAtivosOportunidade a where a.id = :id")
	GestaoAtivosOportunidade findByIdOp(String id);

}
