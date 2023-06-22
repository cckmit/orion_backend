package br.com.live.comercial.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.ParametrosMapaEndereco;

@Repository
public interface ParametrosMapaEndRepository extends JpaRepository<ParametrosMapaEndereco, Integer> {
	
	List<ParametrosMapaEndereco> findAll();

	@Query("SELECT u FROM ParametrosMapaEndereco u where u.deposito = :deposito")
	ParametrosMapaEndereco findByDeposito(int deposito);
	
	void deleteById(int deposito);
}