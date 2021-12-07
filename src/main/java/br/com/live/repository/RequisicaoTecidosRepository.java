package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.RequisicaoTecidos;

@Repository
public interface RequisicaoTecidosRepository extends JpaRepository<RequisicaoTecidos, Long> {
	
	@Query("SELECT u FROM RequisicaoTecidos u order by u.id desc")
	List<RequisicaoTecidos> findAll();
	
	@Query("SELECT u FROM RequisicaoTecidos u where u.id = :id")
	RequisicaoTecidos findById(long id);
	
	@Query("SELECT NVL(MAX(u.id),0) + 1 FROM RequisicaoTecidos u")
	Integer findNextId();
	
	@Query("SELECT u FROM RequisicaoTecidos u where u.situacao = :situacao order by u.id desc")
	List<RequisicaoTecidos> findBySituacao(int situacao);
	
	@Query("SELECT u FROM RequisicaoTecidos u where u.situacao <> 0 order by u.id desc")
	List<RequisicaoTecidos> findByNotSituacaoDigitada();
}
