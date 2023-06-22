package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.live.administrativo.entity.ConciliacaoLojaDreEntity;

public interface ConciliacaoLojaDreRepository extends JpaRepository<ConciliacaoLojaDreEntity, Long> {

    @Query("SELECT nvl(max(u.id),0) + 1 FROM ConciliacaoLojaDreEntity u ")
    int findNextId();

}
