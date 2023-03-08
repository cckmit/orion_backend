package br.com.live.repository;

import br.com.live.entity.ControleDescontoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControleDescontoClienteRepository extends JpaRepository<ControleDescontoCliente, Integer> {

    List<ControleDescontoCliente> findAll();

    @Query("SELECT u FROM ControleDescontoCliente u where u.idControle = :idControle")
    ControleDescontoCliente findByIdControle(String idControle);
}
