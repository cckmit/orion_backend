package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyRegistroTarefaAtividadeProjeto;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroTarefaAtividadeProjetoService {

    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    ProjetoService projetoService;

    public RegistroTarefaAtividadeProjetoService(RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, ProjetoService projetoService) {
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.projetoService = projetoService;
    }

    public List<BodyRegistroTarefaAtividadeProjeto> saveRegistroTarefaAtividadeProjeto(BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto) throws ParseException {
        saveRegistroTarefaAtividade(registroTarefaAtividadeProjeto);
        atualizarDadosRegistroAtividade(registroTarefaAtividadeProjeto.idProjeto, registroTarefaAtividadeProjeto.idRegistroAtividade);
        projetoService.atualizarStatusProjeto(registroTarefaAtividadeProjeto.idProjeto);
        return findAll(registroTarefaAtividadeProjeto.idProjeto);
    }

    public List<BodyRegistroTarefaAtividadeProjeto> deleteByIdRegistroTarefaAtividadeProjeto(long id, long idProjeto, long idRegistroAtividade){
        deleteById(id);
        atualizarDadosRegistroAtividade(idProjeto, idRegistroAtividade);
        projetoService.atualizarStatusProjeto(idProjeto);
        return findAll(idProjeto);
    }

    @Transactional
    public void deleteById(long id) {
        registroTarefaAtividadeProjetoRepository.deleteById(id);
    }

    @Transactional
    public void saveRegistroTarefaAtividade(BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto) throws ParseException {

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjetoEntity = new RegistroTarefaAtividadeProjetoEntity();

        if (registroTarefaAtividadeProjeto.id == 0){
            registroTarefaAtividadeProjeto.id = registroTarefaAtividadeProjetoRepository.findNextId();
        } else {
            Optional<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoEntityOptional = registroTarefaAtividadeProjetoRepository.findById(registroTarefaAtividadeProjeto.id);
            if (registroTarefaAtividadeProjetoEntityOptional.isPresent()) registroTarefaAtividadeProjetoEntity = registroTarefaAtividadeProjetoEntityOptional.get();
        }

        registroTarefaAtividadeProjetoEntity.setId(registroTarefaAtividadeProjeto.id);
        registroTarefaAtividadeProjetoEntity.setIdProjeto(registroTarefaAtividadeProjeto.idProjeto);
        registroTarefaAtividadeProjetoEntity.setIdRegistroAtividade(registroTarefaAtividadeProjeto.idRegistroAtividade);
        registroTarefaAtividadeProjetoEntity.setDescricao(registroTarefaAtividadeProjeto.descricao);
        registroTarefaAtividadeProjetoEntity.setAcaoRealizada(registroTarefaAtividadeProjeto.acaoRealizada);
        registroTarefaAtividadeProjetoEntity.setIdResponsavel(registroTarefaAtividadeProjeto.idResponsavel);
        registroTarefaAtividadeProjetoEntity.setDocumentoAssociado(registroTarefaAtividadeProjeto.documentoAssociado);
        registroTarefaAtividadeProjetoEntity.setCusto(registroTarefaAtividadeProjeto.custo);
        registroTarefaAtividadeProjetoEntity.setTempoGasto(registroTarefaAtividadeProjeto.tempoGasto);

        if (registroTarefaAtividadeProjeto.dataInicio != null && !registroTarefaAtividadeProjeto.dataInicio.isEmpty()) registroTarefaAtividadeProjetoEntity.setDataInicio(FormataData.parseStringToDate(registroTarefaAtividadeProjeto.dataInicio));
        if (registroTarefaAtividadeProjeto.horaInicio != null && !registroTarefaAtividadeProjeto.horaInicio.isEmpty()) registroTarefaAtividadeProjetoEntity.setHoraInicio(formatoHora.parse(registroTarefaAtividadeProjeto.horaInicio));
        if (registroTarefaAtividadeProjeto.dataFim != null && !registroTarefaAtividadeProjeto.dataFim.isEmpty()) registroTarefaAtividadeProjetoEntity.setDataFim(FormataData.parseStringToDate(registroTarefaAtividadeProjeto.dataFim));
        if (registroTarefaAtividadeProjeto.horaFim != null && !registroTarefaAtividadeProjeto.horaFim.isEmpty()) registroTarefaAtividadeProjetoEntity.setHoraFim(formatoHora.parse(registroTarefaAtividadeProjeto.horaFim));

        registroTarefaAtividadeProjetoRepository.save(registroTarefaAtividadeProjetoEntity);
    }

    @Transactional
    public void atualizarDadosRegistroAtividade(long idProjeto, long idRegistroAtividade){

        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoList = registroTarefaAtividadeProjetoRepository.findAllByRegistroAtividade(idProjeto, idRegistroAtividade);
        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoMaiorDataList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadeMaiorData(idProjeto, idRegistroAtividade);
        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoMenorDataList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadeMenorData(idProjeto, idRegistroAtividade);

        Optional<RegistroAtividadeProjetoEntity> registroAtividadeProjeto = registroAtividadeProjetoRepository.findById(idRegistroAtividade);
        RegistroAtividadeProjetoEntity registroAtividadeProjetoEntity = registroAtividadeProjeto.get();

        registroAtividadeProjetoEntity.setDataInicio(null);
        registroAtividadeProjetoEntity.setHoraInicio(null);
        registroAtividadeProjetoEntity.setDataFim(null);
        registroAtividadeProjetoEntity.setHoraFim(null);
        registroAtividadeProjetoEntity.setCusto(0);
        registroAtividadeProjetoEntity.setTempoGasto(0);

        if (!registroTarefaAtividadeProjetoList.isEmpty()){

            double custoTotal = registroTarefaAtividadeProjetoRepository.calcularCustoTotalTarefaAtividade(idProjeto, idRegistroAtividade);
            double tempoGasto = registroTarefaAtividadeProjetoRepository.calcularTempoGastoTarefaAtividade(idProjeto, idRegistroAtividade);

            registroAtividadeProjetoEntity.setCusto(custoTotal);
            registroAtividadeProjetoEntity.setTempoGasto(tempoGasto);
        }

        if (!registroTarefaAtividadeProjetoMaiorDataList.isEmpty()) {
            registroAtividadeProjetoEntity.setDataFim(registroTarefaAtividadeProjetoMaiorDataList.get(0).getDataFim());
            registroAtividadeProjetoEntity.setHoraFim(registroTarefaAtividadeProjetoMaiorDataList.get(0).getHoraFim());
        }

        if (!registroTarefaAtividadeProjetoMenorDataList.isEmpty()) {
            registroAtividadeProjetoEntity.setDataInicio(registroTarefaAtividadeProjetoMenorDataList.get(0).getDataInicio());
            registroAtividadeProjetoEntity.setHoraInicio(registroTarefaAtividadeProjetoMenorDataList.get(0).getHoraInicio());
        }

        registroAtividadeProjetoRepository.save(registroAtividadeProjetoEntity);
    }

    public List<BodyRegistroTarefaAtividadeProjeto> findAll(Long idProjeto){

        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoEntityList = null;

        if (idProjeto == 0){
            registroTarefaAtividadeProjetoEntityList = registroTarefaAtividadeProjetoRepository.findAll();
        } else {
            registroTarefaAtividadeProjetoEntityList = registroTarefaAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);
        }

        List<BodyRegistroTarefaAtividadeProjeto> registroTarefaAtividadeProjetoBodyList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjetoEntity : registroTarefaAtividadeProjetoEntityList){

            BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto = new BodyRegistroTarefaAtividadeProjeto();
            registroTarefaAtividadeProjeto.id = registroTarefaAtividadeProjetoEntity.getId();
            registroTarefaAtividadeProjeto.idProjeto = registroTarefaAtividadeProjetoEntity.getIdProjeto();
            registroTarefaAtividadeProjeto.idRegistroAtividade = registroTarefaAtividadeProjetoEntity.getIdRegistroAtividade();
            registroTarefaAtividadeProjeto.descricao = registroTarefaAtividadeProjetoEntity.getDescricao();
            registroTarefaAtividadeProjeto.acaoRealizada = registroTarefaAtividadeProjetoEntity.getAcaoRealizada();
            registroTarefaAtividadeProjeto.documentoAssociado = registroTarefaAtividadeProjetoEntity.getDocumentoAssociado();
            registroTarefaAtividadeProjeto.custo = registroTarefaAtividadeProjetoEntity.getCusto();
            registroTarefaAtividadeProjeto.tempoGasto = registroTarefaAtividadeProjetoEntity.getTempoGasto();

            if (registroTarefaAtividadeProjetoEntity.getDataInicio() != null) registroTarefaAtividadeProjeto.dataInicio = dateFormat.format(registroTarefaAtividadeProjetoEntity.getDataInicio());
            if (registroTarefaAtividadeProjetoEntity.getHoraInicio() != null) registroTarefaAtividadeProjeto.horaInicio = timeFormat.format(registroTarefaAtividadeProjetoEntity.getHoraInicio());
            if (registroTarefaAtividadeProjetoEntity.getDataFim() != null) registroTarefaAtividadeProjeto.dataFim = dateFormat.format(registroTarefaAtividadeProjetoEntity.getDataFim());
            if (registroTarefaAtividadeProjetoEntity.getHoraFim() != null) registroTarefaAtividadeProjeto.horaFim = timeFormat.format(registroTarefaAtividadeProjetoEntity.getHoraFim());
            if (registroTarefaAtividadeProjetoEntity.getIdResponsavel() != null) registroTarefaAtividadeProjeto.idResponsavel = registroTarefaAtividadeProjetoEntity.getIdResponsavel();

            registroTarefaAtividadeProjetoBodyList.add(registroTarefaAtividadeProjeto);
        }
        return registroTarefaAtividadeProjetoBodyList;
    }
}