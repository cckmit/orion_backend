package br.com.live.sistema.repository;

import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.entity.PessoaEnvolvidaProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaEnvolvidaProjetoRepository extends JpaRepository<PessoaEnvolvidaProjetoEntity, Long> {

    @Query(" SELECT nvl(max(a.id),0) + 1 FROM PessoaEnvolvidaProjetoEntity a ")
    long findNextId();

    @Query("SELECT a FROM PessoaEnvolvidaProjetoEntity a WHERE a.idProjeto = :idProjeto")
    List<PessoaEnvolvidaProjetoEntity> findAllByIdProjeto(@Param("idProjeto") Long idProjeto);

}
