package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {
	
	List<Deposito> findAll();
	
}
