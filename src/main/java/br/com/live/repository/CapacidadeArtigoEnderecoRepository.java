package br.com.live.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.CapacidadeArtigoEndereco;

@Repository
public interface CapacidadeArtigoEnderecoRepository extends JpaRepository<CapacidadeArtigoEndereco, Integer> {
	List<CapacidadeArtigoEndereco> findAll();
	
	@Query("SELECT u FROM CapacidadeArtigoEndereco u where u.artigo = :artigo")
	CapacidadeArtigoEndereco findByArtigo(int artigo);
}
