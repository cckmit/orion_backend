package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.ObservacaoOrdemPacote;

@Repository
public interface ObservacaoOrdemPacoteRepository extends JpaRepository<ObservacaoOrdemPacote, String> {
	
	List<ObservacaoOrdemPacote> findAll();

	@Query("SELECT u FROM ObservacaoOrdemPacote u where u.id = :id")
	ObservacaoOrdemPacote findByIdComposto(String id);
	
	@Query("SELECT u FROM ObservacaoOrdemPacote u where u.estagio = :estagio")
	List<ObservacaoOrdemPacote> findByEstagio(int estagio);
	
	void deleteById(long id);
}