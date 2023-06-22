package br.com.live.comercial.repository;

import br.com.live.comercial.entity.MetasDaEstacao;
import br.com.live.comercial.entity.ValorDescontoClientesImportados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorDescontoClientesImpRepository extends JpaRepository<ValorDescontoClientesImportados, String> {

    List<ValorDescontoClientesImportados> findAll();

    @Query("SELECT u FROM ValorDescontoClientesImportados u where u.id = :idCliente")
    ValorDescontoClientesImportados findByIdCliente(String idCliente);

    @Query("SELECT u FROM ValorDescontoClientesImportados u where u.cnpj9 = :cnpj9 and u.cnpj4 = :cnpj4 and cnpj2 = :cnpj2")
    ValorDescontoClientesImportados findObservacao(int cnpj9, int cnpj4, int cnpj2);
}
