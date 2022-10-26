package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColetaPorArea;
@Repository
public interface LoteSugestaoColetaPorAreaRepository extends JpaRepository<LoteSugestaoColetaPorArea, Long> {

}
