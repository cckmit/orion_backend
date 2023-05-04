package br.com.live.service;

import br.com.live.custom.OrcamentoLojaDreCustom;
import br.com.live.entity.OrcamentoLojaDreEntity;
import br.com.live.repository.OrcamentoLojaDreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrcamentoLojaDreService {

    OrcamentoLojaDreRepository orcamentoLojaDreRepository;
    OrcamentoLojaDreCustom orcamentoLojaDreCustom;

    public OrcamentoLojaDreService(OrcamentoLojaDreRepository orcamentoLojaDreRepository, OrcamentoLojaDreCustom orcamentoLojaDreCustom) {
        this.orcamentoLojaDreRepository = orcamentoLojaDreRepository;
        this.orcamentoLojaDreCustom = orcamentoLojaDreCustom;
    }

    public void deleteOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento){
        orcamentoLojaDreCustom.deleteOrcamentoLojaByCnpjAno(cnpjLoja, anoOrcamento);
    }

    public void saveOrcamentoLojaDre(List<OrcamentoLojaDreEntity> orcamentoLojaDreList){

        long nextId = orcamentoLojaDreRepository.findNextId();

        for (OrcamentoLojaDreEntity fieldOrcamentoLojaDre : orcamentoLojaDreList){
            fieldOrcamentoLojaDre.id = (nextId++);

            String propriedade = fieldOrcamentoLojaDre.propriedade;

            if (propriedade.equals("Preço Médio")) {
                fieldOrcamentoLojaDre.valPropriedade = Math.round(fieldOrcamentoLojaDre.valPropriedade * 100.0) / 100.0;
            } else if (propriedade.contains("%")) {
                fieldOrcamentoLojaDre.valPropriedade *= 100;
            }else {
                fieldOrcamentoLojaDre.valPropriedade = Math.round(fieldOrcamentoLojaDre.valPropriedade);
            }
            orcamentoLojaDreRepository.save(fieldOrcamentoLojaDre);
        }
    }
}