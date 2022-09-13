package br.com.live.repository;

import br.com.live.entity.Restricoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestricoesRepository extends JpaRepository<Restricoes, Long> {

    List<Restricoes> findAll();

    @Query("SELECT u FROM Restricoes u where u.id = :id")
    Restricoes findByIdRestricao(long id);

    void deleteById(long id);
}
