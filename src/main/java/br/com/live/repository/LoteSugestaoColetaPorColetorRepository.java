package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColetaPorColetor;
@Repository
public interface LoteSugestaoColetaPorColetorRepository extends JpaRepository<LoteSugestaoColetaPorColetor, Long> {

}
