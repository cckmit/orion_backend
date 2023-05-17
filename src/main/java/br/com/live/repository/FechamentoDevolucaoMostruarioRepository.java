package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.FechamentoDevolucaoMostruario;

@Repository
public interface FechamentoDevolucaoMostruarioRepository extends JpaRepository<FechamentoDevolucaoMostruario, Integer>{
	
	List<FechamentoDevolucaoMostruario> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM FechamentoDevolucaoMostruario a")
	int findNextID();
	
	@Query("SELECT a FROM FechamentoDevolucaoMostruario a WHERE a.id = :id")
	FechamentoDevolucaoMostruario findById(int id);
	
	String deleteById(int id);

}
