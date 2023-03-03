package br.com.live.repository;

import br.com.live.entity.PedidosGravadosComDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosGravadosComDescontoRepository extends JpaRepository<PedidosGravadosComDesconto, Integer> {

    List<PedidosGravadosComDesconto> findAll();

    @Query("SELECT u FROM PedidosGravadosComDesconto u where u.pedido = :pedido")
    PedidosGravadosComDesconto findByIdPedido(int pedido);
}
