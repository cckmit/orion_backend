package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.CapacidadeProdArtigo;

@Repository
public interface CapacidadeProdArtigoRepository extends JpaRepository<CapacidadeProdArtigo, Long> {
	
}