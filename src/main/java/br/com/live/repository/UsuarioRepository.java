package br.com.live.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findAll();
	
	@Query("SELECT u FROM Usuario u where u.id = :idUsuario")
	Usuario findByIdUsuario(long idUsuario);
	
	@Query("SELECT u FROM Usuario u where u.usuario = :usuario and u.senha = :senha and u.situacao = 1")
	Usuario findByUsuarioSenha(String usuario, String senha);
	
	void deleteById(long idUsuario);
}
