package br.com.live.comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.live.comercial.entity.VolumesMinutaTransporte;

import java.util.List;

@Repository
public interface VolumesMinutaRepository extends JpaRepository<VolumesMinutaTransporte, Long> {

    List<VolumesMinutaTransporte> findAll();

    List<VolumesMinutaTransporte> findByMinuta(int minuta);

    void deleteById(long idVolume);
    void deleteByNota(int nota);
}
