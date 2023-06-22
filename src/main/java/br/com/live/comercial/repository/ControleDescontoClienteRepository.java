package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.ControleDescontoCliente;

import java.util.List;

@Repository
public interface ControleDescontoClienteRepository extends JpaRepository<ControleDescontoCliente, Integer> {

    List<ControleDescontoCliente> findAll();

    @Query("SELECT u FROM ControleDescontoCliente u where u.idControle = :idControle")
    ControleDescontoCliente findByIdControle(String idControle);
}
