package br.com.live.comercial.repository;

import java.util.List;

import javax.persistence.Column;

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
	
	@Query("SELECT a FROM TipoClientePorCanal a where a.idCanal = :idCanal and a.tipoCliente = :tipoCliente")
	TipoClientePorCanal findByIdCanalAndTipoCliente(int idCanal, int tipoCliente);
	
	void deleteById(int id);
}