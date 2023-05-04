package br.com.live.repository;

import br.com.live.entity.DreLojaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DreLojaRepository extends JpaRepository<DreLojaEntity, Long> {

    @Query("SELECT nvl(max(u.id),0) + 1 FROM DreLojaEntity u ")
    long findNextId();

}
