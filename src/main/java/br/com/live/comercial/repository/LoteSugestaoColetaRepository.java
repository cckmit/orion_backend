package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.LoteSugestaoColeta;

import java.util.List;

@Repository
public interface LoteSugestaoColetaRepository extends JpaRepository<LoteSugestaoColeta, Long> {

	@Query(" SELECT nvl(max(a.id),0) + 1 FROM LoteSugestaoColeta a ")
	long findNextId();

	@Query(" SELECT a FROM LoteSugestaoColeta a where a.idUsuario = :idUsuario and a.situacao = 0")
	List<LoteSugestaoColeta> findLotesNaoLiberadosByUsuario(long idUsuario);
}
