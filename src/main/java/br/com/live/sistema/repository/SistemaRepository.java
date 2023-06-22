package br.com.live.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.live.sistema.entity.Sistema;

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