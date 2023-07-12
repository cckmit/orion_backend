package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyRegistroAtividadeProjeto;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RegistroAtividadeProjetoService {

    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;

    public RegistroAtividadeProjetoService(RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository) {
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
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

        if (registroAtividadeProjeto.dataInicio != null) registroAtividadeProjetoEntity.setDataInicio(FormataData.parseStringToDate(registroAtividadeProjeto.dataInicio));
        if (registroAtividadeProjeto.horaInicio != null) registroAtividadeProjetoEntity.setHoraInicio(formatoHora.parse(registroAtividadeProjeto.horaInicio));
        if (registroAtividadeProjeto.dataFim != null) registroAtividadeProjetoEntity.setDataFim(FormataData.parseStringToDate(registroAtividadeProjeto.dataFim));
        if (registroAtividadeProjeto.horaFim != null) registroAtividadeProjetoEntity.setHoraFim(formatoHora.parse(registroAtividadeProjeto.horaFim));

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

            if (registroAtividadeProjetoEntity.getDataInicio() != null) registroAtividadeProjeto.dataInicio = dateFormat.format(registroAtividadeProjetoEntity.getDataInicio());
            if (registroAtividadeProjetoEntity.getHoraInicio() != null) registroAtividadeProjeto.horaInicio = timeFormat.format(registroAtividadeProjetoEntity.getHoraInicio());
            if (registroAtividadeProjetoEntity.getDataFim() != null) registroAtividadeProjeto.dataFim = dateFormat.format(registroAtividadeProjetoEntity.getDataFim());
            if (registroAtividadeProjetoEntity.getHoraFim() != null) registroAtividadeProjeto.horaFim = timeFormat.format(registroAtividadeProjetoEntity.getHoraFim());

            registroAtividadeProjetoBodyList.add(registroAtividadeProjeto);
        }
        return registroAtividadeProjetoBodyList;
    }
}