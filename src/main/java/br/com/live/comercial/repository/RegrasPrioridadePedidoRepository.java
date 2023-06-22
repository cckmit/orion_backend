package br.com.live.comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.RegrasPrioridadePedido;

@Repository
public interface RegrasPrioridadePedidoRepository extends JpaRepository<RegrasPrioridadePedido, Integer> {
    
    List<RegrasPrioridadePedido> findAll();

    void deleteByTipoCliente(int tipoCliente);

    @Query("select u from RegrasPrioridadePedido u where u.tipoCliente = :tipoCliente")
    RegrasPrioridadePedido findByTipoCliente(int tipoCliente);
}
