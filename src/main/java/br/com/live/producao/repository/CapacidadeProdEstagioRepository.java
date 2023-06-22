package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.CapacidadeProdEstagio;

@Repository
public interface CapacidadeProdEstagioRepository extends JpaRepository<CapacidadeProdEstagio, Long> {
	
}