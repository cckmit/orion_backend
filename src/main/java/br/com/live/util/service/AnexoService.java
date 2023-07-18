package br.com.live.util.service;

import br.com.live.util.body.BodyAnexo;
import br.com.live.util.entity.Anexo;
import br.com.live.util.repository.AnexoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnexoService {

    AnexoRepository anexoRepository;

    public AnexoService(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    public List<BodyAnexo> findByChave(String chave){

        List<Anexo> anexos = anexoRepository.findAllByChave(chave);
        List<BodyAnexo> bodyAnexos = new ArrayList<>();

        String formato = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);

        for (Anexo anexo : anexos){

            BodyAnexo bodyAnexo = new BodyAnexo();
            bodyAnexo.setId(anexo.getId());
            bodyAnexo.setChave(anexo.getChave());
            bodyAnexo.setNomeArquivo(anexo.getNomeArquivo());
            bodyAnexo.setTipoArquivo(anexo.getTipoArquivo());
            bodyAnexo.setProgramaGerador(anexo.getProgramaGerador());
            bodyAnexo.setTamanho(anexo.getTamanho());
            bodyAnexo.setDataRegistro(sdf.format(anexo.getDataRegistro()));
            bodyAnexo.setDadosArquivo(anexo.getDadosArquivo());
            bodyAnexos.add(bodyAnexo);
        }
        return bodyAnexos;
    }

    public List<BodyAnexo> deleteByMultipleIdAnexo(String chave, List<Anexo> anexoIds) {
        for (Anexo anexoId : anexoIds) {
            anexoRepository.deleteById(anexoId.getId());
        }
        return findByChave(chave);
    }

    public List<BodyAnexo> saveAnexo(String chave, BodyAnexo bodyAnexo) throws ParseException {

        if (bodyAnexo.getId() == 0) bodyAnexo.setId(anexoRepository.findNextId());

        String formato = "dd/MM/yyyy, HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);

        Anexo anexo = new Anexo();
        anexo.setId(bodyAnexo.getId());
        anexo.setChave(bodyAnexo.getChave());
        anexo.setNomeArquivo(bodyAnexo.getNomeArquivo());
        anexo.setTipoArquivo(bodyAnexo.getTipoArquivo());
        anexo.setProgramaGerador(bodyAnexo.getProgramaGerador());
        anexo.setTamanho(bodyAnexo.getTamanho());
        anexo.setDataRegistro(sdf.parse(bodyAnexo.getDataRegistro()));
        anexo.setDadosArquivo(bodyAnexo.getDadosArquivo());
        anexoRepository.save(anexo);

        return findByChave(chave);
    }
}
