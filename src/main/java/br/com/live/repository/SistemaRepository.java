package br.com.live.repository;

import br.com.live.entity.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, Integer> {

    List<Sistema> findAll();

    @Query("SELECT p FROM Sistema p where p.id = :id")
    Sistema findById(int id);

    @Query("SELECT nvl(max(p.id),0) + 1 FROM Sistema p")
    int findNextId();

    void deleteById(int id);
}