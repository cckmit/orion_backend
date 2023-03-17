package br.com.live.repository;
import br.com.live.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findAll();

    @Query("SELECT p FROM Servico p where p.id = :id")
    Servico findById(int id);

    @Query("SELECT nvl(max(p.id),0) + 1 FROM Servico p")
    int findNextId();

    void deleteById(int id);
}
