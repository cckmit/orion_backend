package br.com.live.repository;

import br.com.live.entity.EstabelecimentoPagSeguroLojaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoPagSeguroLojaRepository extends JpaRepository<EstabelecimentoPagSeguroLojaEntity, Long> {
}
