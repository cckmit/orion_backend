package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColeta;
@Repository
public interface LoteSugestaoColetaRepository extends JpaRepository<LoteSugestaoColeta, Long> {

		
	
}
