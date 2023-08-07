package br.com.live.sistema.service;

import br.com.live.sistema.custom.GestaoAtivosCustom;
import br.com.live.sistema.entity.Servico;
import br.com.live.sistema.model.ConsultaGestaoAtivos;
import br.com.live.sistema.repository.ServicoRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicoService {

    private ServicoRepository servicoRepository;
    private GestaoAtivosCustom gestaoAtivosCustom;

    public ServicoService(ServicoRepository servicoRepository, GestaoAtivosCustom gestaoAtivosCustom) {
        this.servicoRepository = servicoRepository;
        this.gestaoAtivosCustom = gestaoAtivosCustom;
    }
    
    public List<ConsultaGestaoAtivos> findAllServicos(){
    	return gestaoAtivosCustom.findAllServicos();
    }

    public void saveServico(int id, String nomeServico, String objetivo, String timeResponsavel, boolean disponibilidade, String tecnicosFornecedores, int gestorResponsavel, String status){

        Servico servico = null;

        servico = servicoRepository.findById(id);

        if (servico == null){
            servico = new Servico(servicoRepository.findNextId(), nomeServico, objetivo, timeResponsavel, disponibilidade, tecnicosFornecedores, gestorResponsavel, status);
        } else {
            servico.nomeServico = nomeServico;
            servico.objetivo = objetivo;
            servico.timeResponsavel = timeResponsavel;
            servico.disponibilidade = disponibilidade;
            servico.tecnicosFornecedores = tecnicosFornecedores;
            servico.gestorResponsavel = gestorResponsavel;
            servico.status = status;
        }
        servicoRepository.save(servico);
    }

    public void deleteById(int id){ 
    	servicoRepository.deleteById(id);}
}
