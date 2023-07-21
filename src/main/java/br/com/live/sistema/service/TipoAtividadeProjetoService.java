package br.com.live.sistema.service;

import br.com.live.sistema.entity.TarefaTipoAtividadeProjetoEntity;
import br.com.live.sistema.entity.TipoAtividadeProjetoEntity;
import br.com.live.sistema.model.TipoAtividadeProjetoConsulta;
import br.com.live.sistema.repository.TarefaTipoAtividadeProjetoRepository;
import br.com.live.sistema.repository.TipoAtividadeProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoAtividadeProjetoService {

    TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository;
    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;

    public TipoAtividadeProjetoService(TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository, TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository) {
        this.tipoAtividadeProjetoRepository = tipoAtividadeProjetoRepository;
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
    }

    public List<TipoAtividadeProjetoConsulta> findAllTipoAtividadeProjeto(){

        List<TipoAtividadeProjetoEntity> tipoAtividadeProjetoEntityList = tipoAtividadeProjetoRepository.findAll();
        List<TipoAtividadeProjetoConsulta> tipoAtividadeProjetoConsultaList = new ArrayList<>();

        for (TipoAtividadeProjetoEntity entity : tipoAtividadeProjetoEntityList) {

            double tempoEstimado = findTempoEstimadoById(entity.getId());

            TipoAtividadeProjetoConsulta tipoAtividadeProjetoConsulta = new TipoAtividadeProjetoConsulta();
            tipoAtividadeProjetoConsulta.setId(entity.getId());
            tipoAtividadeProjetoConsulta.setDescricao(entity.getDescricao());
            tipoAtividadeProjetoConsulta.setExigeDoc(entity.getExigeDoc());
            tipoAtividadeProjetoConsulta.setTempoEstimado(tempoEstimado);
            tipoAtividadeProjetoConsultaList.add(tipoAtividadeProjetoConsulta);
        }

        return tipoAtividadeProjetoConsultaList;
    }

    public TipoAtividadeProjetoConsulta findByIdTipoAtividadeProjeto(Long id) {

        Optional<TipoAtividadeProjetoEntity> tipoAtividadeProjetoEntity = tipoAtividadeProjetoRepository.findById(id);
        double tempoEstimado = findTempoEstimadoById(id);

        TipoAtividadeProjetoConsulta tipoAtividadeProjetoConsulta = new TipoAtividadeProjetoConsulta();

        if (tipoAtividadeProjetoEntity.isPresent()) {
            TipoAtividadeProjetoEntity entity = tipoAtividadeProjetoEntity.get();
            tipoAtividadeProjetoConsulta.setId(entity.getId());
            tipoAtividadeProjetoConsulta.setDescricao(entity.getDescricao());
            tipoAtividadeProjetoConsulta.setExigeDoc(entity.getExigeDoc());
            tipoAtividadeProjetoConsulta.setTempoEstimado(tempoEstimado);
        }

        return tipoAtividadeProjetoConsulta;
    }


    public double findTempoEstimadoById(long idTipoAtividade){

        double totalMinutosEstimados = 0;

        List<TarefaTipoAtividadeProjetoEntity> tarefaTipoAtividadeProjetoEntityList = tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(idTipoAtividade);

        for (TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjetoEntity : tarefaTipoAtividadeProjetoEntityList){
            totalMinutosEstimados += tarefaTipoAtividadeProjetoEntity.getTempoEstimado();
        }

        return totalMinutosEstimados;
    }
}
