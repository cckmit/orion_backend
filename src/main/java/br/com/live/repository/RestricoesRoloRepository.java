package br.com.live.repository;

import br.com.live.entity.RestricoesRolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestricoesRoloRepository extends JpaRepository<RestricoesRolo, Long> {
    List<RestricoesRolo> findAll();

    @Query(" SELECT u FROM RestricoesRolo u where u.id = :id ")
    RestricoesRolo findBySequencia(long id);

    void deleteById(long id);
}
