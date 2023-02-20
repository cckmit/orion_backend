package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.EncolhimentoCad;

@Repository
public interface EncolhimentoCadRepository extends JpaRepository<EncolhimentoCad, Integer> {
	
	List<EncolhimentoCad> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM EncolhimentoCad a")
	int findNextID();
	
	@Query("SELECT a FROM EncolhimentoCad a WHERE a.id = :id")
	EncolhimentoCad findById(int id);
	
	String deleteById(int id);

}
