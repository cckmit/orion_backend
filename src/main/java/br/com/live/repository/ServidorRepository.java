package br.com.live.repository;

import br.com.live.entity.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Integer> {

    List<Servidor> findAll();

    @Query("SELECT p FROM Servidor p where p.id = :id")
    Servidor findById(int id);

    @Query("SELECT nvl(max(p.id),0) + 1 FROM Servidor p")
    int findNextId();

    void deleteById(int id);
}
