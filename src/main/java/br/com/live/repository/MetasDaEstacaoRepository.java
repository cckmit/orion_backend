package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasDaEstacao;

@Repository
public interface MetasDaEstacaoRepository extends JpaRepository<MetasDaEstacao, String> {
	
	List<MetasDaEstacao> findAll();
	
	@Query("SELECT u FROM MetasDaEstacao u where u.codEstacao = :codEstacao and u.tipoMeta = :tipoMeta order by u.mes")
	
	List<MetasDaEstacao> findByCodEstacaoAndTipoMeta(long codEstacao, int tipoMeta);
	@Query("SELECT u FROM MetasDaEstacao u where u.id = :idMetas")
	MetasDaEstacao findByIdMetas(String idMetas);
	
	void deleteById(String idMetas);
	
	void deleteByCodEstacao(long codEstacao);
}
