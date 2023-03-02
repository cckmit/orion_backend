package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.FaturamentoLiveClothing;

@Repository
public interface FaturamentoLiveClothingRepository extends JpaRepository<FaturamentoLiveClothing, Integer>{
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM FaturamentoLiveClothing a")
	int findNextID();
	
	@Query("SELECT a FROM FaturamentoLiveClothing a where a.id = :id")
	FaturamentoLiveClothing findById(int id);
	
	@Query("SELECT u FROM FaturamentoLiveClothing u where u.id = :idFaturamento")
	FaturamentoLiveClothing findByIdFaturamento(int idFaturamento);
	
	void deleteById(int id);

}
