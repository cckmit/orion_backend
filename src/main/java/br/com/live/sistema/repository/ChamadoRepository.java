package br.com.live.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.sistema.entity.Chamado;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    @Query("SELECT u FROM Chamado u order by u.codChamado desc")
    List<Chamado> findAll();

    @Query("SELECT u FROM Chamado u where u.codChamado = :codChamado")
    Chamado findByCodChamado(int codChamado);

    @Query("SELECT nvl(max(u.codChamado),0) + 1 FROM Chamado u ")
    int findNextCodChamado();

    void deleteByCodChamado(int codChamado);
}
