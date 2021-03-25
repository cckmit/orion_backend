package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CapacidadeProdEstagio;

@Repository
public interface CapacidadeProdEstagioRepository extends JpaRepository<CapacidadeProdEstagio, Long> {
	
}