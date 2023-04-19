package br.com.live.repository;

import br.com.live.entity.ConciliacaoLojaDreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConciliacaoLojaDreRepository extends JpaRepository<ConciliacaoLojaDreEntity, Long> {

    @Query("SELECT nvl(max(u.id),0) + 1 FROM ConciliacaoLojaDreEntity u ")
    int findNextId();

}
