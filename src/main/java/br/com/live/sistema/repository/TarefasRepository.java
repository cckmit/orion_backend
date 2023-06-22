package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.Tarefas;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefas, Integer> {
	
	List<Tarefas> findAll();
	
	@Query("SELECT u FROM Tarefas u where u.id = :idTarefa")
	Tarefas findByIdTarefa(int idTarefa);
	
	void deleteById(int idTarefa);
}

