package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyProjeto;
import br.com.live.sistema.entity.AprovadorProjetoEntity;
import br.com.live.sistema.entity.ProjetoEntity;
import br.com.live.sistema.model.AprovadorProjeto;
import br.com.live.sistema.model.BriefingProjeto;
import br.com.live.sistema.model.EscopoProjeto;
import br.com.live.sistema.model.TermoAberturaProjeto;
import br.com.live.sistema.repository.AprovadorProjetoRepository;
import br.com.live.sistema.repository.ProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjetoService {

    ProjetoRepository projetoRepository;
    AprovadorProjetoRepository aprovadorProjetoRepository;


    public ProjetoService(ProjetoRepository projetoRepository, AprovadorProjetoRepository aprovadorProjetoRepository) {
        this.projetoRepository = projetoRepository;
        this.aprovadorProjetoRepository = aprovadorProjetoRepository;
    }

    public List<ProjetoEntity> saveProjeto(BodyProjeto projeto){

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

        return projetoRepository.findAll();
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
}
