package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.ParametrosMapaEnderecoCaixa;

@Repository
public interface ParametrosEnderecoCaixaRepository extends JpaRepository<ParametrosMapaEnderecoCaixa, Integer> {
	
	@Query("SELECT u FROM ParametrosMapaEnderecoCaixa u where u.deposito = :deposito")
	ParametrosMapaEnderecoCaixa findByDeposito(int deposito);
}
