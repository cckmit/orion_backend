package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.MetasDoOrcamento;

@Repository
public interface MetasDoOrcamentoRepository extends JpaRepository<MetasDoOrcamento, String> {
	
	List<MetasDoOrcamento> findAll();
	
	@Query(" SELECT u FROM MetasDoOrcamento u where u.id = :idMetas ")
	MetasDoOrcamento findByIdMetas(String idMetas);
	
	@Query(" SELECT u FROM MetasDoOrcamento u where upper(u.descricao) = upper(:descricao) and u.ano = :ano and u.tipoMeta = :tipoMeta ")
	MetasDoOrcamento findByDescricaoAndAnoAndTipoMeta(String descricao, int ano, int tipoMeta);
	
	@Query(" SELECT u FROM MetasDoOrcamento u where u.ano = :ano and u.tipoMeta = :tipoMeta and upper(u.modalidade) = upper(:modalidade) order by u.descricao ")
	List<MetasDoOrcamento> findByAnoAndTipoMetaAndModalidade(int ano, int tipoMeta, String modalidade);
	
	void deleteById(String idMetas);
	void deleteByAnoAndTipoMeta(int ano, int tipoMeta);
}