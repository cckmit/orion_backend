package br.com.live.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.entity.LoteSugestaoColetaPorAreaItem;
@Repository
public interface LoteSugestaoColetaPorAreaItemRepository extends JpaRepository<LoteSugestaoColetaPorAreaItem, Long> {

}
