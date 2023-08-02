package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyRegistroAtividadeProjeto;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
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
public class RegistroAtividadeProjetoService {

    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    ProjetoService projetoService;

    public RegistroAtividadeProjetoService(RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, ProjetoService projetoService) {
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.projetoService = projetoService;
    }

    public List<BodyRegistroAtividadeProjeto> saveRegistroAtividadeProjeto(BodyRegistroAtividadeProjeto registroAtividadeProjeto) throws ParseException {
        saveRegistroAtividade(registroAtividadeProjeto);
        projetoService.atualizarStatusProjeto(registroAtividadeProjeto.idProjeto);
        return findAll(registroAtividadeProjeto.idProjeto);
    }

    public List<BodyRegistroAtividadeProjeto> deleteByIdRegistroAtividadeProjeto(Long id, Long idProjeto){
        deleteByIdRegistroAtividade(id);
        projetoService.atualizarStatusProjeto(idProjeto);
        return findAll(idProjeto);
    }

    @Transactional
    public void deleteByIdRegistroAtividade(Long id){
        registroAtividadeProjetoRepository.deleteById(id);
    }

    @Transactional
    public void saveRegistroAtividade(BodyRegistroAtividadeProjeto registroAtividadeProjeto) throws ParseException {

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        RegistroAtividadeProjetoEntity registroAtividadeProjetoEntity = new RegistroAtividadeProjetoEntity();

        if (registroAtividadeProjeto.id == 0) {
            registroAtividadeProjeto.id = registroAtividadeProjetoRepository.findNextId();
        } else {
            Optional<RegistroAtividadeProjetoEntity> registroAtividadeProjetoEntityOptional = registroAtividadeProjetoRepository.findById(registroAtividadeProjeto.id);
            if (registroAtividadeProjetoEntityOptional.isPresent()) registroAtividadeProjetoEntity = registroAtividadeProjetoEntityOptional.get();
        }

        registroAtividadeProjetoEntity.setId(registroAtividadeProjeto.id);
        registroAtividadeProjetoEntity.setIdProjeto(registroAtividadeProjeto.idProjeto);
        registroAtividadeProjetoEntity.setDescricao(registroAtividadeProjeto.descricao);
        registroAtividadeProjetoEntity.setAcaoRealizada(registroAtividadeProjeto.acaoRealizada);
        registroAtividadeProjetoEntity.setIdResponsavel(registroAtividadeProjeto.idResponsavel);
        registroAtividadeProjetoEntity.setDocumentoAssociado(registroAtividadeProjeto.documentoAssociado);
        registroAtividadeProjetoEntity.setCusto(registroAtividadeProjeto.custo);
        registroAtividadeProjetoEntity.setIdFase(registroAtividadeProjeto.idFase);
        registroAtividadeProjetoEntity.setTempoGasto(registroAtividadeProjeto.tempoGasto);
        registroAtividadeProjetoEntity.setMarco(registroAtividadeProjeto.marco);

        if (registroAtividadeProjeto.dataInicio != null && !registroAtividadeProjeto.dataInicio.isEmpty()) registroAtividadeProjetoEntity.setDataInicio(FormataData.parseStringToDate(registroAtividadeProjeto.dataInicio));
        if (registroAtividadeProjeto.horaInicio != null && !registroAtividadeProjeto.horaInicio.isEmpty()) registroAtividadeProjetoEntity.setHoraInicio(formatoHora.parse(registroAtividadeProjeto.horaInicio));
        if (registroAtividadeProjeto.dataFim != null && !registroAtividadeProjeto.dataFim.isEmpty()) registroAtividadeProjetoEntity.setDataFim(FormataData.parseStringToDate(registroAtividadeProjeto.dataFim));
        if (registroAtividadeProjeto.horaFim != null && !registroAtividadeProjeto.horaFim.isEmpty()) registroAtividadeProjetoEntity.setHoraFim(formatoHora.parse(registroAtividadeProjeto.horaFim));

        registroAtividadeProjetoRepository.save(registroAtividadeProjetoEntity);
    }

    public List<BodyRegistroAtividadeProjeto> findAll(Long idProjeto){

        List<RegistroAtividadeProjetoEntity> registroAtividadeProjetoEntityList;

        if (idProjeto == 0) {
            registroAtividadeProjetoEntityList = registroAtividadeProjetoRepository.findAll();
        } else {
            registroAtividadeProjetoEntityList = registroAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);
        }

        List<BodyRegistroAtividadeProjeto> registroAtividadeProjetoBodyList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (RegistroAtividadeProjetoEntity registroAtividadeProjetoEntity : registroAtividadeProjetoEntityList){

            BodyRegistroAtividadeProjeto registroAtividadeProjeto = new BodyRegistroAtividadeProjeto();
            registroAtividadeProjeto.id = registroAtividadeProjetoEntity.getId();
            registroAtividadeProjeto.idProjeto = registroAtividadeProjetoEntity.getIdProjeto();
            registroAtividadeProjeto.descricao = registroAtividadeProjetoEntity.getDescricao();
            registroAtividadeProjeto.acaoRealizada = registroAtividadeProjetoEntity.getAcaoRealizada();
            registroAtividadeProjeto.documentoAssociado = registroAtividadeProjetoEntity.getDocumentoAssociado();
            registroAtividadeProjeto.custo = registroAtividadeProjetoEntity.getCusto();
            registroAtividadeProjeto.idFase = registroAtividadeProjetoEntity.getIdFase();
            registroAtividadeProjeto.tempoGasto = registroAtividadeProjetoEntity.getTempoGasto();
            registroAtividadeProjeto.marco = registroAtividadeProjetoEntity.getMarco();

            if (registroAtividadeProjetoEntity.getDataInicio() != null) registroAtividadeProjeto.dataInicio = dateFormat.format(registroAtividadeProjetoEntity.getDataInicio());
            if (registroAtividadeProjetoEntity.getHoraInicio() != null) registroAtividadeProjeto.horaInicio = timeFormat.format(registroAtividadeProjetoEntity.getHoraInicio());
            if (registroAtividadeProjetoEntity.getDataFim() != null) registroAtividadeProjeto.dataFim = dateFormat.format(registroAtividadeProjetoEntity.getDataFim());
            if (registroAtividadeProjetoEntity.getHoraFim() != null) registroAtividadeProjeto.horaFim = timeFormat.format(registroAtividadeProjetoEntity.getHoraFim());
            if (registroAtividadeProjetoEntity.getIdResponsavel() != null ) registroAtividadeProjeto.idResponsavel = registroAtividadeProjetoEntity.getIdResponsavel();
            if (registroAtividadeProjetoEntity.getIdAtividade() != null) registroAtividadeProjeto.idAtividade = registroAtividadeProjetoEntity.getIdAtividade();

            registroAtividadeProjetoBodyList.add(registroAtividadeProjeto);
        }
        return registroAtividadeProjetoBodyList;
    }

    public int validaRegistroAtividadeProjetoApontado(Long idProjeto, Long idAtividade){

        int exist = 0;

        Optional<RegistroAtividadeProjetoEntity> registroAtividadeProjetoEntityOptional = registroAtividadeProjetoRepository.findByIdProjetoIdAtividade(idProjeto, idAtividade);
        if (registroAtividadeProjetoEntityOptional.isPresent()) {
            RegistroAtividadeProjetoEntity registroAtividadeProjeto = registroAtividadeProjetoEntityOptional.get();
            if ((registroAtividadeProjeto.getDataInicio() != null) || (registroAtividadeProjeto.getDataFim() != null) || registroAtividadeProjeto.getTempoGasto() != 0)
                exist = 1;
        }

        return exist;
    }
}