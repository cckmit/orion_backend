package br.com.live.repository;

import br.com.live.entity.SupervisorLojaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorLojaRepository extends JpaRepository<SupervisorLojaEntity, String> {

}
