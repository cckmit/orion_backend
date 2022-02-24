package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.AberturaCaixas;

@Repository
public interface AberturaCaixasRepository extends JpaRepository<AberturaCaixas, Integer> {
	
	@Query("SELECT u FROM AberturaCaixas u where u.numeroCaixa = :numeroCaixa")
	AberturaCaixas findByNumeroCaixa(int numeroCaixa);
}
