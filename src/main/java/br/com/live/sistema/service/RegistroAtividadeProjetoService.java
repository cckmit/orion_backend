package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyRegistroAtividadeProjeto;
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

@Service
@Transactional
public class RegistroAtividadeProjetoService {

    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;

    public RegistroAtividadeProjetoService(RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository) {
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
    }

    public List<BodyRegistroAtividadeProjeto> saveRegistroAtividadeProjeto(BodyRegistroAtividadeProjeto registroAtividadeProjeto) throws ParseException {

        if (registroAtividadeProjeto.id == 0) registroAtividadeProjeto.id = registroAtividadeProjetoRepository.findNextId();

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        RegistroAtividadeProjetoEntity registroAtividadeProjetoEntity = new RegistroAtividadeProjetoEntity();
        registroAtividadeProjetoEntity.setId(registroAtividadeProjeto.id);
        registroAtividadeProjetoEntity.setIdProjeto(registroAtividadeProjeto.idProjeto);
        registroAtividadeProjetoEntity.setDescricao(registroAtividadeProjeto.descricao);
        registroAtividadeProjetoEntity.setAcaoRealizada(registroAtividadeProjeto.acaoRealizada);
        registroAtividadeProjetoEntity.setIdResponsavel(registroAtividadeProjeto.idResponsavel);
        registroAtividadeProjetoEntity.setDocumentoAssociado(registroAtividadeProjeto.documentoAssociado);
        registroAtividadeProjetoEntity.setCusto(registroAtividadeProjeto.custo);
        registroAtividadeProjetoEntity.setIdFase(registroAtividadeProjeto.idFase);
        registroAtividadeProjetoEntity.setTempoGasto(registroAtividadeProjeto.tempoGasto);

        if (registroAtividadeProjeto.dataInicio != null && !registroAtividadeProjeto.dataInicio.isEmpty()) registroAtividadeProjetoEntity.setDataInicio(FormataData.parseStringToDate(registroAtividadeProjeto.dataInicio));
        if (registroAtividadeProjeto.horaInicio != null && !registroAtividadeProjeto.horaInicio.isEmpty()) registroAtividadeProjetoEntity.setHoraInicio(formatoHora.parse(registroAtividadeProjeto.horaInicio));
        if (registroAtividadeProjeto.dataFim != null && !registroAtividadeProjeto.dataFim.isEmpty()) registroAtividadeProjetoEntity.setDataFim(FormataData.parseStringToDate(registroAtividadeProjeto.dataFim));
        if (registroAtividadeProjeto.horaFim != null && !registroAtividadeProjeto.horaFim.isEmpty()) registroAtividadeProjetoEntity.setHoraFim(formatoHora.parse(registroAtividadeProjeto.horaFim));

        registroAtividadeProjetoRepository.save(registroAtividadeProjetoEntity);

        return findAll(registroAtividadeProjeto.idProjeto);
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
            registroAtividadeProjeto.idResponsavel = registroAtividadeProjetoEntity.getIdResponsavel();
            registroAtividadeProjeto.documentoAssociado = registroAtividadeProjetoEntity.getDocumentoAssociado();
            registroAtividadeProjeto.custo = registroAtividadeProjetoEntity.getCusto();
            registroAtividadeProjeto.idFase = registroAtividadeProjetoEntity.getIdFase();
            registroAtividadeProjeto.tempoGasto = registroAtividadeProjetoEntity.getTempoGasto();

            if (registroAtividadeProjetoEntity.getDataInicio() != null) registroAtividadeProjeto.dataInicio = dateFormat.format(registroAtividadeProjetoEntity.getDataInicio());
            if (registroAtividadeProjetoEntity.getHoraInicio() != null) registroAtividadeProjeto.horaInicio = timeFormat.format(registroAtividadeProjetoEntity.getHoraInicio());
            if (registroAtividadeProjetoEntity.getDataFim() != null) registroAtividadeProjeto.dataFim = dateFormat.format(registroAtividadeProjetoEntity.getDataFim());
            if (registroAtividadeProjetoEntity.getHoraFim() != null) registroAtividadeProjeto.horaFim = timeFormat.format(registroAtividadeProjetoEntity.getHoraFim());

            registroAtividadeProjetoBodyList.add(registroAtividadeProjeto);
        }
        return registroAtividadeProjetoBodyList;
    }

    public int validaRegistroAtividadeProjetoApontado(Long idProjeto){

        int exist = 0;

        List<RegistroAtividadeProjetoEntity> registroAtividadeProjetoEntityList = registroAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);

        for (RegistroAtividadeProjetoEntity registroAtividadeProjeto : registroAtividadeProjetoEntityList){
            if ((registroAtividadeProjeto.getDataInicio() != null) || (registroAtividadeProjeto.getDataFim() != null) || registroAtividadeProjeto.getTempoGasto() != 0) exist = 1;

            List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoList = registroTarefaAtividadeProjetoRepository.findAllByRegistroAtividade(idProjeto, registroAtividadeProjeto.getId());

            for (RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjeto : registroTarefaAtividadeProjetoList){
                if ((registroTarefaAtividadeProjeto.getDataInicio() != null) || (registroTarefaAtividadeProjeto.getDataFim() != null) || registroAtividadeProjeto.getTempoGasto() != 0) exist = 1;
            }
        }

        return exist;
    }
}