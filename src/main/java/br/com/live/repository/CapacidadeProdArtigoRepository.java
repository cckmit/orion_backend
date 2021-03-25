package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CapacidadeProdArtigo;

@Repository
public interface CapacidadeProdArtigoRepository extends JpaRepository<CapacidadeProdArtigo, Long> {
	
}