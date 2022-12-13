package br.com.live.repository;

import br.com.live.entity.VolumesMinutaTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolumesMinutaRepository extends JpaRepository<VolumesMinutaTransporte, Long> {

    List<VolumesMinutaTransporte> findAll();

    List<VolumesMinutaTransporte> findByMinuta(int minuta);

    void deleteById(long idVolume);
    void deleteByNota(int nota);
}
