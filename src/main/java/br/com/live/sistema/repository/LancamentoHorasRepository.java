package br.com.live.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.LancamentoHoras;

@Repository
public interface LancamentoHorasRepository extends JpaRepository<LancamentoHoras, String> {
	
	List<LancamentoHoras> findAll();
	
	@Query("SELECT u FROM LancamentoHoras u where u.idTarefa = :idTarefa and u.idUsuario = :idUsuario")
	List<LancamentoHoras> findByIdTarefaAndIdUsuario(int idTarefa, int idUsuario);
	
	@Query("SELECT u FROM LancamentoHoras u where u.id = :idLancamento")
	LancamentoHoras findByIdLancamento(String idLancamento);
	
	void deleteById(String idLancamento);
}
