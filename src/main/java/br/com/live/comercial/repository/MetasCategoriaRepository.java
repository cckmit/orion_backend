package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.MetasCategoria;

@Repository
public interface MetasCategoriaRepository extends JpaRepository<MetasCategoria, String> {
	
	List<MetasCategoria> findAll();
	
	@Query(" SELECT u FROM MetasCategoria u where u.codEstacao = :codEstacao and u.tipoMeta = :tipoMeta ")
	List<MetasCategoria> findByCodEstacaoAndTipoMeta(long codEstacao, int tipoMeta);
	
	@Query(" SELECT u FROM MetasCategoria u where u.id = :idMetas ")
	MetasCategoria findByIdMetas(String idMetas);
	
	void deleteById(String idMetas);
	
	void deleteByCodEstacao(long codEstacao);
	void deleteByCodEstacaoAndTipoMeta(long codEstacao, int tipoMeta);
}
