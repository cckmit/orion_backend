package br.com.live.controller;

import br.com.live.body.BodyChamado;
import br.com.live.custom.ChamadoCustom;
import br.com.live.entity.Chamado;
import br.com.live.model.ConsultaChamado;
import br.com.live.repository.ChamadoRepository;
import br.com.live.service.ChamadoService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/chamados")
public class ChamadoController {

    private ChamadoService chamadoService;
    private ChamadoRepository chamadoRepository;
    private ChamadoCustom chamadoCustom;

    @Autowired
    public ChamadoController(ChamadoService chamadoService, ChamadoRepository chamadoRepository, ChamadoCustom chamadoCustom) {
        this.chamadoService = chamadoService;
        this.chamadoRepository = chamadoRepository;
        this.chamadoCustom = chamadoCustom;
    }

    @RequestMapping(value = "/find-all-chamados", method = RequestMethod.GET)
    public List<ConsultaChamado> findAllChamados() {
        return chamadoCustom.findAllChamados();
    }

    @RequestMapping(value = "/find-all-chamados-by-data/{dataInicio}/{dataFim}", method = RequestMethod.GET)
    public List<ConsultaChamado> findAllChamadosByData(@PathVariable("dataInicio") String dataInicio, @PathVariable("dataFim") String dataFim) {
        return chamadoCustom.findAllChamadosByData(dataInicio, dataFim);
    }

    @RequestMapping(value = "/find-chamados-by-id/{codChamado}", method = RequestMethod.GET)
    public Chamado findChamadoByCodChamado(@PathVariable("codChamado") int codChamado) {
        return chamadoRepository.findByCodChamado(codChamado);
    }

    @RequestMapping(value = "/save-chamado", method = RequestMethod.POST)
    public void saveChamado(@RequestBody BodyChamado body) {
        chamadoService.saveChamado(body.codChamado, body.tituloChamado, body.codTecnico, body.codArea, body.codDepartamento, body.codSetor, body.impacto, body.descricaoChamado, body.nomeRequerente, body.dataInicioTriagem, body.dataFimTriagem, body.dataInicioAnalise, body.dataFimAnalise, body.dataInicioAprovEscopo, body.dataFimAprovEscopo, body.dataInicioOrcamento, body.dataFimOrcamento, body.dataInicioFilaDesenvForn, body.dataFimFilaDesenvForn, body.dataInicioDesenvForn, body.dataFimDesenvForn, body.dataInicioFilaDesenvInt, body.dataFimFilaDesenvInt, body.dataInicioDesenvInt, body.dataFimDesenvInt, body.dataInicioQualidadeTestes, body.dataFimQualidadeTestes, body.dataInicioEntrega, body.dataFimEntrega);
    }

    @RequestMapping(value = "/delete-by-id/{codChamado}", method = RequestMethod.DELETE)
    public List<ConsultaChamado> deleteChamadoById(@PathVariable("codChamado") int codChamado) {
        chamadoService.deleteByCodChamado(codChamado);
        return chamadoCustom.findAllChamados();
    }

    @RequestMapping(value = "/gerar-pdf-chamado", method = RequestMethod.POST)
    public String gerarPdfChamados(@RequestBody BodyChamado body) throws JRException, FileNotFoundException {
        return chamadoService.gerarPdfChamados(body.listChamados, body.dataInicio, body.dataFim);
    }
}
