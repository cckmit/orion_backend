package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.PublicoAlvo;

@Repository
public interface PublicoAlvoRepository extends JpaRepository<PublicoAlvo, Long> {
	
	List<PublicoAlvo> findAll();			
	
}
