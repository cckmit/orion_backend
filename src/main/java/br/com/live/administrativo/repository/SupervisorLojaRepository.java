package br.com.live.administrativo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.administrativo.entity.SupervisorLojaEntity;

@Repository
public interface SupervisorLojaRepository extends JpaRepository<SupervisorLojaEntity, String> {

}
