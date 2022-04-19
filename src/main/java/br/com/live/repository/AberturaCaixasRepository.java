package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CaixasParaEnderecar;

@Repository
public interface AberturaCaixasRepository extends JpaRepository<CaixasParaEnderecar, Integer> {
	
	@Query("SELECT u FROM CaixasParaEnderecar u where u.numeroCaixa = :numeroCaixa")
	CaixasParaEnderecar findByNumeroCaixa(int numeroCaixa);
}
