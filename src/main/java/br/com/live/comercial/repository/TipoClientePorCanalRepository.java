package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.TipoClientePorCanal;

@Repository
public interface TipoClientePorCanalRepository extends JpaRepository<TipoClientePorCanal, Integer>{
	
	List<TipoClientePorCanal> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM TipoClientePorCanal a")
	int findNextID();
	
	@Query("SELECT a FROM TipoClientePorCanal a where a.id = :id")
	TipoClientePorCanal findById(int id);
	
	void deleteById(int id);

}
