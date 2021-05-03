package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PrevisaoVendasItemTam;

@Repository
public interface PrevisaoVendasItemTamRepository extends JpaRepository<PrevisaoVendasItemTam, Long> {

	@Query("SELECT p FROM PrevisaoVendasItemTam p where p.idItemPrevisaoVendas = :idItemPrevisaoVendas")
	List<PrevisaoVendasItemTam> findByIdItemPrevisaoVendas(String idItemPrevisaoVendas);
	
	@Query("SELECT p FROM PrevisaoVendasItemTam p where p.idPrevisaoVendas = :idPrevisaoVendas and p.grupo = :grupo and p.item = :item and p.sub = :sub")
	PrevisaoVendasItemTam findByIdPrevisaoVendasGrupoItemSub(long idPrevisaoVendas, String grupo, String item, String sub);
		
	void deleteByIdPrevisaoVendas(long idPrevisaoVendas);

}
