package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.MetasDoOrcamento;

@Repository
public interface MetasDoOrcamentoRepository extends JpaRepository<MetasDoOrcamento, String> {
	
	List<MetasDoOrcamento> findAll();
	
	@Query(" SELECT u FROM MetasDoOrcamento u where u.id = :idMetas ")
	MetasDoOrcamento findByIdMetas(String idMetas);
	
	@Query(" SELECT u FROM MetasDoOrcamento u where u.descricao = :descricao and u.ano = :ano and tipoMeta = :tipoMeta ")
	MetasDoOrcamento findByDescricaoAndAnoAndTipoMeta(String descricao, int ano, int tipoMeta);
	
	void deleteById(String idMetas);
	void deleteByAnoAndTipoMeta(int ano, int tipoMeta);
}