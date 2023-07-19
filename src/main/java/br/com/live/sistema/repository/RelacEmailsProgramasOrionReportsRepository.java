package br.com.live.sistema.repository;

import br.com.live.sistema.entity.RelacEmailsProgramasOrionReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RelacEmailsProgramasOrionReportsRepository extends JpaRepository<RelacEmailsProgramasOrionReports, String> {

    @Query(" SELECT p FROM RelacEmailsProgramasOrionReports p where p.idPrograma = :idPrograma ")
    List<RelacEmailsProgramasOrionReports> findEmailsByPrograma(String idPrograma);

    void deleteById(String id);
}
