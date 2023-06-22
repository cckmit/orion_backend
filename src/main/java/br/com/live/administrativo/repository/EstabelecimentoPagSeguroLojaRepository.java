package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.EstabelecimentoPagSeguroLojaEntity;

@Repository
public interface EstabelecimentoPagSeguroLojaRepository extends JpaRepository<EstabelecimentoPagSeguroLojaEntity, Long> {
}
