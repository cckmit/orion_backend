package br.com.live.service;

import br.com.live.entity.Servico;
import br.com.live.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicoService {

    private ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void saveServico(int id, String nomeServico, String objetivo, String timeResponsavel, boolean disponibilidade, String tecnicosFornecedores){

        Servico servico = null;

        servico = servicoRepository.findById(id);

        if (servico == null){
            servico = new Servico(servicoRepository.findNextId(), nomeServico, objetivo, timeResponsavel, disponibilidade, tecnicosFornecedores);
        } else {
            servico.nomeServico = nomeServico;
            servico.objetivo = objetivo;
            servico.timeResponsavel = timeResponsavel;
            servico.disponibilidade = disponibilidade;
            servico.tecnicosFornecedores = tecnicosFornecedores;
        }
        servicoRepository.save(servico);
    }

    public void deleteById(int id){ servicoRepository.deleteById(id);}
}
