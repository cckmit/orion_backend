package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.live.entity.OperacaoXMicromovimentos;

@Repository
public interface OperXMicromvRepository extends JpaRepository<OperacaoXMicromovimentos, Integer> {
	List<OperacaoXMicromovimentos> findAll();
	
	@Query("SELECT nvl(max(a.id),0) + 1 FROM OperacaoXMicromovimentos a")
	long findNextID();
	
	@Query("SELECT a FROM OperacaoXMicromovimentos a where a.id = :id")
	OperacaoXMicromovimentos findById(long id);
	
	void deleteById(long id);

}
