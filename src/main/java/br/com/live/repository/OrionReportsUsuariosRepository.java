package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.OrionReportsUsuarios;

@Repository
public interface OrionReportsUsuariosRepository extends JpaRepository<OrionReportsUsuarios, Integer>{
	
	List<OrionReportsUsuarios> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM OrionReportsUsuarios a")
	int findNextID();

	OrionReportsUsuarios findById(int id);
	
	void deleteById(int id);
}
