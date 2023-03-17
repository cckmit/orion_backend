package br.com.live.repository;
import br.com.live.entity.Integracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegracaoRepository extends JpaRepository<Integracao, Integer> {
    List<Integracao> findAll();

    @Query("SELECT p FROM Integracao p where p.id = :id")
    Integracao findById(int id);

    @Query("SELECT nvl(max(p.id),0) + 1 FROM Integracao p")
    int findNextId();

    void deleteById(int id);
}
