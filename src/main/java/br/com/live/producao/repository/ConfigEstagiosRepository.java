package br.com.live.producao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.producao.entity.ConfiguracaoEstagios;

@Repository
public interface ConfigEstagiosRepository extends JpaRepository<ConfiguracaoEstagios, Integer> {
	
	@Query("SELECT u FROM ConfiguracaoEstagios u where u.sequencia = :sequencia")
	ConfiguracaoEstagios findBySequencia(int sequencia);
}