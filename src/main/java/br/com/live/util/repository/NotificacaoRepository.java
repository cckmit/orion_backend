package br.com.live.util.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.util.entity.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, String> {
	
	@Query("SELECT u FROM Notificacao u where u.tipoNotificacao = :tipoNotificacao")
	List<Notificacao> findByTipoNotificacao(int tipoNotificacao);
	
}