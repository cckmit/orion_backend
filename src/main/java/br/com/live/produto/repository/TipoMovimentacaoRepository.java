package br.com.live.produto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.produto.entity.TipoMovimentacao;

@Repository
public interface TipoMovimentacaoRepository extends JpaRepository<TipoMovimentacao, Integer> {
	List<TipoMovimentacao> findAll();
	
	@Query("SELECT u FROM TipoMovimentacao u where u.id = :id")
	TipoMovimentacao findById(int id);
}
