package br.com.live.sistema.service;

import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.model.CronogramaRealizadoProjeto;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.sistema.repository.TarefaAtividadeProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CronogramaRealizadoProjetoService {

    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    AtividadeProjetoRepository atividadeProjetoRepository;
    TipoAtividadeProjetoService tipoAtividadeProjetoService;
    TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository;

    public CronogramaRealizadoProjetoService(RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, AtividadeProjetoRepository atividadeProjetoRepository, TipoAtividadeProjetoService tipoAtividadeProjetoService, TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository) {
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.tipoAtividadeProjetoService = tipoAtividadeProjetoService;
        this.tarefaAtividadeProjetoRepository = tarefaAtividadeProjetoRepository;
    }

    public List<CronogramaRealizadoProjeto> findCronogramaRealizadoProjeto(long idProjeto){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<CronogramaRealizadoProjeto> cronogramaRealizadoProjetoList = new ArrayList<>();

        List<RegistroAtividadeProjetoEntity> registroAtividadeProjetoEntityList = registroAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);

        for (RegistroAtividadeProjetoEntity registroAtividadeProjetoEntity : registroAtividadeProjetoEntityList){

            CronogramaRealizadoProjeto cronogramaRealizadoProjeto = new CronogramaRealizadoProjeto();
            cronogramaRealizadoProjeto.setId(registroAtividadeProjetoEntity.getId());
            cronogramaRealizadoProjeto.setIdProjeto(registroAtividadeProjetoEntity.getIdProjeto());
            cronogramaRealizadoProjeto.setDescricao(registroAtividadeProjetoEntity.getDescricao());
            cronogramaRealizadoProjeto.setIdFase(registroAtividadeProjetoEntity.getIdFase());
            cronogramaRealizadoProjeto.setTempoReal(registroAtividadeProjetoEntity.getTempoGasto());
            cronogramaRealizadoProjeto.setStatus(obterStatusRegistroAtividadeProjeto(idProjeto, registroAtividadeProjetoEntity.getId()));
            cronogramaRealizadoProjeto.setPercentualConclusao(obterPercentualConclusaoRegistroAtividadeProjeto(idProjeto, registroAtividadeProjetoEntity.getId()));
            cronogramaRealizadoProjeto.setCustoReal(registroAtividadeProjetoEntity.getCusto());

            if (registroAtividadeProjetoEntity.getIdResponsavel() != null) cronogramaRealizadoProjeto.setIdResponsavel(registroAtividadeProjetoEntity.getIdResponsavel());
            if (registroAtividadeProjetoEntity.getDataInicio() != null) cronogramaRealizadoProjeto.setDataInicioReal(dateFormat.format(registroAtividadeProjetoEntity.getDataInicio()));
            if (registroAtividadeProjetoEntity.getDataFim() != null) cronogramaRealizadoProjeto.setDataFimReal(dateFormat.format(registroAtividadeProjetoEntity.getDataFim()));

            Long idAtividadePrevista = registroAtividadeProjetoEntity.getIdAtividade();

            if (idAtividadePrevista != null){

                Optional<AtividadeProjetoEntity> atividadeProjetoOptional = atividadeProjetoRepository.findById(idAtividadePrevista);
                AtividadeProjetoEntity atividadeProjetoEntity = atividadeProjetoOptional.get();

                double tempoPrevisto = tarefaAtividadeProjetoRepository.calcularTempoPrevistoTarefaAtividade(idProjeto, idAtividadePrevista);
                cronogramaRealizadoProjeto.setTempoPrev(tempoPrevisto);

                if (atividadeProjetoEntity.getDataPrevInicio() != null) cronogramaRealizadoProjeto.setDataInicioPrev(dateFormat.format(atividadeProjetoEntity.getDataPrevInicio()));
                if (atividadeProjetoEntity.getDataPrevFim() != null) cronogramaRealizadoProjeto.setDataFimPrev(dateFormat.format(atividadeProjetoEntity.getDataPrevFim()));

            }

            cronogramaRealizadoProjetoList.add(cronogramaRealizadoProjeto);

        }

        return cronogramaRealizadoProjetoList;
    }

    public String obterStatusRegistroAtividadeProjeto(long idProjeto, long idAtividadeProjeto){

        String status = "";

        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoConcluidaList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadeConcluida(idProjeto, idAtividadeProjeto);
        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoPendenteList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadePendente(idProjeto, idAtividadeProjeto);

        boolean existTarefaConcluida = !registroTarefaAtividadeProjetoConcluidaList.isEmpty();
        boolean existTarefaPendente = !registroTarefaAtividadeProjetoPendenteList.isEmpty();

        if (existTarefaPendente && !existTarefaConcluida){
            status = "A FAZER";
        } else if (existTarefaConcluida && !existTarefaPendente){
            status = "CONCLUÍDO";
        } else {
            status = "EM ANDAMENTO";
        }

        return status;
    }

    public double obterPercentualConclusaoRegistroAtividadeProjeto(long idProjeto, long idAtividadeProjeto) {

        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoConcluidaList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadeConcluida(idProjeto, idAtividadeProjeto);
        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoPendenteList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadePendente(idProjeto, idAtividadeProjeto);

        int totalTarefas = registroTarefaAtividadeProjetoConcluidaList.size() + registroTarefaAtividadeProjetoPendenteList.size();
        int totalTarefasConcluidas = registroTarefaAtividadeProjetoConcluidaList.size();

        if (totalTarefas == 0) {
            return 0.0;
        }

        double percentualConcluidas = (double) totalTarefasConcluidas / totalTarefas * 100.0;

        return percentualConcluidas;
    }
}
