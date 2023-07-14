package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.AtributosNaturezasDeOperacao;


@Repository
public interface AtributosNaturezaDeOperacaoRepository extends JpaRepository<AtributosNaturezasDeOperacao, Integer>{
	
	List<AtributosNaturezasDeOperacao> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM AtributosNaturezasDeOperacao a")
	int findNextID();
	
	@Query("SELECT e FROM AtributosNaturezasDeOperacao e where e.id = :id")
	AtributosNaturezasDeOperacao findById(int id);
	
	void deleteById(int id);

}
