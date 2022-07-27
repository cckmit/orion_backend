package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.TipoObservacao;

@Repository
public interface TipoObservacaoRepository extends JpaRepository<TipoObservacao, Long> {

	@Query("SELECT u FROM TipoObservacao u where u.id = :id")
	TipoObservacao findById(int id);
	
	@Query("SELECT u FROM TipoObservacao u where u.id = :id")
	TipoObservacao findByIdLong(long id);
	
	void deleteById(long id);
}
