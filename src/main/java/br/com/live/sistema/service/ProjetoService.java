package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyProjeto;
import br.com.live.sistema.entity.*;
import br.com.live.sistema.model.*;
import br.com.live.sistema.repository.*;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    ProjetoRepository projetoRepository;
    AprovadorProjetoRepository aprovadorProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    AtividadeProjetoRepository atividadeProjetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository, AprovadorProjetoRepository aprovadorProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, AtividadeProjetoRepository atividadeProjetoRepository) {
        this.projetoRepository = projetoRepository;
        this.aprovadorProjetoRepository = aprovadorProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.atividadeProjetoRepository = atividadeProjetoRepository;
    }

    public List<ProjetoEntity> saveProjeto(BodyProjeto projeto){

        save(projeto);

        if (projeto.copiarCronogramaProjeto > 0) {
            copiarCronogramaProjeto(projeto.id, projeto.copiarCronogramaProjeto);
        }

        return projetoRepository.findAllOrderByProjeto();
    }

    @Transactional
    public List<ProjetoEntity> save(BodyProjeto projeto){

        if (projeto.id == 0) projeto.id = projetoRepository.findNextId();

        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(projeto.id);
        projetoEntity.setCodProjeto(projeto.codProjeto);
        projetoEntity.setDescricao(projeto.descricao);
        projetoEntity.setDataCriacao(FormataData.parseStringToDate(projeto.dataCriacao));
        projetoEntity.setArea(projeto.area);
        projetoEntity.setDepartamento(projeto.departamento);
        projetoEntity.setSetor(projeto.setor);
        projetoEntity.setOrigemProjeto(projeto.origemProjeto);
        projetoEntity.setSubOrigemProjeto(projeto.subOrigemProjeto);
        projetoEntity.setStatus(projeto.status);

        projetoRepository.save(projetoEntity);

        return projetoRepository.findAllOrderByProjeto();
    }

    @Transactional
    public void copiarCronogramaProjeto(long idProjeto, long idProjetoCopia){

        List<AtividadeProjetoEntity> atividadeProjetoEntityList = atividadeProjetoRepository.findAllByIdProjeto(idProjetoCopia);

        for (AtividadeProjetoEntity atividadeProjetoCopia : atividadeProjetoEntityList){

            AtividadeProjetoEntity atividadeProjeto = new AtividadeProjetoEntity();
            atividadeProjeto.setId(atividadeProjetoRepository.findNextId());
            atividadeProjeto.setIdProjeto(idProjeto);
            atividadeProjeto.setDescricao(atividadeProjetoCopia.getDescricao());
            atividadeProjeto.setIdFase(atividadeProjetoCopia.getIdFase());
            atividadeProjeto.setIdTipoAtividade(atividadeProjetoCopia.getIdTipoAtividade());
            atividadeProjeto.setIdResponsavel(atividadeProjetoCopia.getIdResponsavel());
            atividadeProjeto.setDataPrevInicio(atividadeProjetoCopia.getDataPrevInicio());
            atividadeProjeto.setDataPrevFim(atividadeProjetoCopia.getDataPrevFim());
            atividadeProjeto.setTempoPrevisto(atividadeProjetoCopia.getTempoPrevisto());
            atividadeProjeto.setMarco(atividadeProjetoCopia.getMarco());

            atividadeProjetoRepository.save(atividadeProjeto);
        }
    }

    public BriefingProjeto findBriefingProjeto(Long id){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(id);
        ProjetoEntity projeto = projetoOptional.get();

        BriefingProjeto briefingProjeto = new BriefingProjeto();
        briefingProjeto.setIdProjeto(projeto.getId());
        briefingProjeto.setObjetivoProjeto(projeto.getObjetivoProjeto());
        briefingProjeto.setContextualizacao(projeto.getContextualizacao());
        briefingProjeto.setDescricaoProblema(projeto.getDescricaoProblema());
        briefingProjeto.setPerguntasAberta(projeto.getPerguntasAberta());
        briefingProjeto.setRiscosBriefing(projeto.getRiscosBriefing());

        return briefingProjeto;
    }

    @Transactional
    public void saveBriefing(BriefingProjeto briefingProjeto){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(briefingProjeto.getIdProjeto());

        ProjetoEntity projeto = projetoOptional.get();
        projeto.setObjetivoProjeto(briefingProjeto.getObjetivoProjeto());
        projeto.setContextualizacao(briefingProjeto.getContextualizacao());
        projeto.setDescricaoProblema(briefingProjeto.getDescricaoProblema());
        projeto.setPerguntasAberta(briefingProjeto.getPerguntasAberta());
        projeto.setRiscosBriefing(briefingProjeto.getRiscosBriefing());

        projetoRepository.save(projeto);
    }

    public TermoAberturaProjeto findTermoAberturaProjeto(Long id){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(id);
        ProjetoEntity projeto = projetoOptional.get();

        TermoAberturaProjeto termoAberturaProjeto = new TermoAberturaProjeto();
        termoAberturaProjeto.setIdProjeto(projeto.getId());
        termoAberturaProjeto.setJustificativaProjeto(projeto.getJustificativaProjeto());
        termoAberturaProjeto.setObjetivoSmart(projeto.getObjetivoSmart());
        termoAberturaProjeto.setBeneficio(projeto.getBeneficio());
        termoAberturaProjeto.setRestricao(projeto.getRestricao());
        termoAberturaProjeto.setRequisito(projeto.getRequisito());
        termoAberturaProjeto.setEntregavel(projeto.getEntregavel());
        termoAberturaProjeto.setRiscoAbertura(projeto.getRiscoAbertura());

        return termoAberturaProjeto;
    }

    @Transactional
    public void saveTermoAberturaProjeto(TermoAberturaProjeto termoAberturaProjeto){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(termoAberturaProjeto.getIdProjeto());

        ProjetoEntity projeto = projetoOptional.get();
        projeto.setJustificativaProjeto(termoAberturaProjeto.getJustificativaProjeto());
        projeto.setObjetivoSmart(termoAberturaProjeto.getObjetivoSmart());
        projeto.setBeneficio(termoAberturaProjeto.getBeneficio());
        projeto.setRestricao(termoAberturaProjeto.getRestricao());
        projeto.setRequisito(termoAberturaProjeto.getRequisito());
        projeto.setEntregavel(termoAberturaProjeto.getEntregavel());
        projeto.setRiscoAbertura(termoAberturaProjeto.getRiscoAbertura());

        projetoRepository.save(projeto);
    }

    public EscopoProjeto findEscopoProjeto(Long id){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(id);
        ProjetoEntity projeto = projetoOptional.get();
        EscopoProjeto escopoProjeto = new EscopoProjeto();
        escopoProjeto.setIdProjeto(projeto.getId());
        escopoProjeto.setMvps(projeto.getMvps());
        escopoProjeto.setParteAfetada(projeto.getParteAfetada());
        escopoProjeto.setSistemaProcessoAfetado(projeto.getSistemaProcessoAfetado());
        escopoProjeto.setExclusaoEscopo(projeto.getExclusaoEscopo());

        List<AprovadorProjetoEntity> listAprovadores = aprovadorProjetoRepository.findAprovadorByIdProjeto(id);
        List<AprovadorProjeto> listAprovadorFormat = new ArrayList<>();
        for (AprovadorProjetoEntity aprovadorEntity : listAprovadores){
            AprovadorProjeto aprovador = new AprovadorProjeto(aprovadorEntity.getId(), aprovadorEntity.getIdProjeto(), aprovadorEntity.getIdUsuario());
            listAprovadorFormat.add(aprovador);
        }

        escopoProjeto.setAprovadorProjetoList(listAprovadorFormat);

        return escopoProjeto;
    }

    @Transactional
    public void saveEscopo(EscopoProjeto escopoProjeto){

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(escopoProjeto.getIdProjeto());
        ProjetoEntity projeto = projetoOptional.get();
        projeto.setMvps(escopoProjeto.getMvps());
        projeto.setParteAfetada(escopoProjeto.getParteAfetada());
        projeto.setSistemaProcessoAfetado(escopoProjeto.getSistemaProcessoAfetado());
        projeto.setExclusaoEscopo(escopoProjeto.getExclusaoEscopo());
        projetoRepository.save(projeto);

        List<AprovadorProjetoEntity> listAprovadoresOld = aprovadorProjetoRepository.findAprovadorByIdProjeto(escopoProjeto.getIdProjeto());
        for (AprovadorProjetoEntity aprovadorOld : listAprovadoresOld) {
            aprovadorProjetoRepository.deleteById(aprovadorOld.getId());
        }

        List<AprovadorProjeto> listAprovadoresNew = escopoProjeto.getAprovadorProjetoList();
        for (AprovadorProjeto aprovador : listAprovadoresNew) {
            AprovadorProjetoEntity aprovadorEntity = new AprovadorProjetoEntity(aprovador.getId(), aprovador.getIdUsuario(), aprovador.getIdProjeto());
            aprovadorProjetoRepository.save(aprovadorEntity);
        }
    }

    @Transactional
    public void atualizarStatusProjeto(Long idProjeto){

        String status = "";

        Optional<ProjetoEntity> projetoOptional = projetoRepository.findById(idProjeto);

        if(projetoOptional.isPresent()) {

            ProjetoEntity projeto = projetoOptional.get();
            boolean existTarefaConcluidaProjeto = false;
            boolean existTarefaPendenteProjeto = false;

            List<RegistroAtividadeProjetoEntity> registroAtividadeProjetoList = registroAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);

            for (RegistroAtividadeProjetoEntity registroAtividadeProjeto : registroAtividadeProjetoList) {

                long idAtividadeProjeto = registroAtividadeProjeto.getId();

                List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoConcluidaList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadeConcluida(idProjeto, idAtividadeProjeto);
                List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoPendenteList = registroTarefaAtividadeProjetoRepository.findTarefaAtividadePendente(idProjeto, idAtividadeProjeto);

                if (!existTarefaPendenteProjeto && (registroAtividadeProjeto.getDataInicio() == null || registroAtividadeProjeto.getDataFim() == null)) {
                    existTarefaPendenteProjeto = true;
                }
                if (!existTarefaConcluidaProjeto) {
                    existTarefaConcluidaProjeto = !registroTarefaAtividadeProjetoConcluidaList.isEmpty();
                }
                if (!existTarefaPendenteProjeto) {
                    existTarefaPendenteProjeto = !registroTarefaAtividadeProjetoPendenteList.isEmpty();
                }
            }

            if (!existTarefaConcluidaProjeto && !existTarefaPendenteProjeto) {
                status = "NOVO";
            } else if (existTarefaPendenteProjeto && !existTarefaConcluidaProjeto) {
                status = "INICIANDO";
            } else if (existTarefaPendenteProjeto && existTarefaConcluidaProjeto) {
                status = "EM ANDAMENTO";
            } else {
                status = "ENCERRADO";
            }

            if (!projeto.getStatus().equals(status)){
                projeto.setStatus(status);
                projetoRepository.save(projeto);
            }
        }
    }
}
