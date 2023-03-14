package br.com.live.repository;

import br.com.live.entity.MetasDaEstacao;
import br.com.live.entity.ValorDescontoClientesImportados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorDescontoClientesImpRepository extends JpaRepository<ValorDescontoClientesImportados, String> {

    List<ValorDescontoClientesImportados> findAll();

    @Query("SELECT u FROM ValorDescontoClientesImportados u where u.id = :idCliente")
    ValorDescontoClientesImportados findByIdCliente(String idCliente);
}