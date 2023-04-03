package br.com.live.service;

import br.com.live.custom.RelacionamentoLojaDreCustom;
import br.com.live.entity.CentroCustoLoja;
import br.com.live.entity.SupervisorLoja;
import br.com.live.model.ConsultaRelacionamentoLojaDre;
import br.com.live.repository.CentroCustoLojaRepository;
import br.com.live.repository.SupervisorLojaRepository;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RelacionamentoLojaDreService {

    RelacionamentoLojaDreCustom relacionamentoLojaDreCustom;
    CentroCustoLojaRepository centroCustoLojaRepository;
    SupervisorLojaRepository supervisorLojaRepository;

    public RelacionamentoLojaDreService(RelacionamentoLojaDreCustom relacionamentoLojaDreCustom, CentroCustoLojaRepository centroCustoLojaRepository, SupervisorLojaRepository supervisorLojaRepository) {
        this.relacionamentoLojaDreCustom = relacionamentoLojaDreCustom;
        this.centroCustoLojaRepository = centroCustoLojaRepository;
        this.supervisorLojaRepository = supervisorLojaRepository;
    }

    public List<ConsultaRelacionamentoLojaDre> consultaRelacionamentoLojaDre() {

        List<ConsultaRelacionamentoLojaDre> centroCustosLoja = relacionamentoLojaDreCustom.findAllLojasConfiguradas();

        Map<String, ConsultaRelacionamentoLojaDre> mapLojas = new HashMap<>();

        for (ConsultaRelacionamentoLojaDre loja : centroCustosLoja) {
            String cnpjLoja = loja.getCnpjLoja();
            if (!mapLojas.containsKey(cnpjLoja)) {
                mapLojas.put(cnpjLoja, loja);
            } else {
                ConsultaRelacionamentoLojaDre lojaExistente = mapLojas.get(cnpjLoja);
                String centroCustosExistente = lojaExistente.getCentroCustos();
                String centroCustosAtual = loja.getCentroCustos();
                if (centroCustosExistente != null) {
                    centroCustosAtual = centroCustosExistente + " / " + centroCustosAtual;
                }
                lojaExistente.setCentroCustos(centroCustosAtual);
            }
        }
        return new ArrayList<>(mapLojas.values());
    }

    public void deleteRleacionamentoLojaDreByCnpj(String cnpjLoja){

        supervisorLojaRepository.deleteById(cnpjLoja);

        List<ConteudoChaveNumerica> centroCustosLoja = relacionamentoLojaDreCustom.findAllCentroCustoLojaId(cnpjLoja);

        for (ConteudoChaveNumerica centroCusto : centroCustosLoja){
            centroCustoLojaRepository.deleteById((long) centroCusto.value);
        }
    }

    public void saveRleacionamentoLojaDre(String cnpjLoja, String cnpjSupervisor, List<ConteudoChaveNumerica> centroCustos){

        if (relacionamentoLojaDreCustom.existsLojaConfigurada(cnpjLoja)){
            deleteRleacionamentoLojaDreByCnpj(cnpjLoja);
        }

        SupervisorLoja supervisorLoja = new SupervisorLoja();
        supervisorLoja.cnpjLoja = cnpjLoja;
        supervisorLoja.cnpjSupervisor = cnpjSupervisor;
        supervisorLojaRepository.save(supervisorLoja);

        for(ConteudoChaveNumerica centroCusto : centroCustos){
            if(!relacionamentoLojaDreCustom.existsCentroCustoLoja(cnpjLoja, centroCusto.value)){
                CentroCustoLoja centroCustoLoja = new CentroCustoLoja();
                centroCustoLoja.id = centroCustoLojaRepository.findNextId();
                centroCustoLoja.cnpjLoja = cnpjLoja;
                centroCustoLoja.centroCusto = centroCusto.value;
                centroCustoLojaRepository.save(centroCustoLoja);
            }
        }
    }
}
