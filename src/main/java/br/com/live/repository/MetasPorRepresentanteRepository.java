package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasPorRepresentante;

@Repository
public interface MetasPorRepresentanteRepository extends JpaRepository<MetasPorRepresentante, String> {
	
	List<MetasPorRepresentante> findAll();
	
	@Query("SELECT u FROM MetasPorRepresentante u where u.codEstacao = :codEstacao and u.tipoMeta = :tipoMeta order by u.codRepresentante")
	List<MetasPorRepresentante> findByCodEstacaoAndTipoMeta(int codEstacao, int tipoMeta);
	
	@Query("SELECT u FROM MetasPorRepresentante u where u.id = :idMetas")
	MetasPorRepresentante findByIdMetas(String idMetas);
	
	void deleteById(String idMetas);
}
