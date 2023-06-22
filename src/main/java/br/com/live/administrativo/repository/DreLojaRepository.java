package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.administrativo.entity.DreLojaEntity;

public interface DreLojaRepository extends JpaRepository<DreLojaEntity, Long> {

    @Query("SELECT nvl(max(u.id),0) + 1 FROM DreLojaEntity u ")
    long findNextId();

}
