package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyConfigEstagios;
import br.com.live.custom.CalendarioProducaoCustom;
import br.com.live.custom.EmpresaCustom;
import br.com.live.entity.ParametrosCalendario;
import br.com.live.model.Empresa;
import br.com.live.model.EstagiosConfigurados;
import br.com.live.model.LayoutCalendarioPorArea;
import br.com.live.model.LayoutCalendarioProducao;
import br.com.live.repository.ParametrosCalendarioRepository;
import br.com.live.service.CalendarioProducaoService;

@RestController
@CrossOrigin
@RequestMapping("/calendario-producao")
public class CalendarioProducaoController {
	
    private EmpresaCustom empresaCustom;
    private CalendarioProducaoService calendarioProducaoService;
    private CalendarioProducaoCustom calendarioProducaoCustom;
    private ParametrosCalendarioRepository parametrosCalendarioRepository;

    @Autowired
    public CalendarioProducaoController(EmpresaCustom empresaCustom, CalendarioProducaoService calendarioProducaoService, CalendarioProducaoCustom calendarioProducaoCustom, ParametrosCalendarioRepository parametrosCalendarioRepository) {
          this.empresaCustom = empresaCustom;
          this.calendarioProducaoService = calendarioProducaoService;
          this.calendarioProducaoCustom = calendarioProducaoCustom;
          this.parametrosCalendarioRepository = parametrosCalendarioRepository;
    }
	
    @RequestMapping(value = "/empresas", method = RequestMethod.GET)
    public List<Empresa> findAll() {
          return empresaCustom.findEmpresas(); 
    }
    
	@RequestMapping(value = "/configuracao", method = RequestMethod.POST)
	public void saveEstagiosConfig(@RequestBody BodyConfigEstagios body) {
		calendarioProducaoService.salvarConfiguracaoEstagios(body.estagios);
	}
	
    @RequestMapping(value = "/{anoCalendario}", method = RequestMethod.DELETE)
    public void deleteParametros(@PathVariable("anoCalendario") int anoCalendario) {
          calendarioProducaoService.deleteAnoCalendario(anoCalendario);
    }
	
    @RequestMapping(value = "/get-configuracao", method = RequestMethod.GET)
    public List<EstagiosConfigurados> findAllConfig() {
          return calendarioProducaoService.findAllEstagiosConfig();
    }
    
    @RequestMapping(value = "/sequencia/{sequencia}/{anoCalendario}", method = RequestMethod.GET)
    public EstagiosConfigurados findSequencia(@PathVariable("sequencia") int sequencia, @PathVariable("anoCalendario") int anoCalendario) {
          return calendarioProducaoService.retornaDadosSequencia(anoCalendario, sequencia);
    }
    
    @RequestMapping(value = "/find-dados-gerais/{anoCalendario}", method = RequestMethod.GET)
    public List<EstagiosConfigurados> findDadosGrid(@PathVariable("anoCalendario") int anoCalendario) {
          return calendarioProducaoService.retornaDadosGridAbaDadosGerais(anoCalendario);
    }
    
	@RequestMapping(value = "/edicao-estagio", method = RequestMethod.POST)
	public void saveAll(@RequestBody BodyConfigEstagios body) {
		calendarioProducaoService.editarEstagio(body.sequencia,body.anoCalendario,body.lead,body.dataInicio,body.dataFim);
	}
	
    @RequestMapping(value = "/valida-calendario/{anoCalendario}", method = RequestMethod.GET)
    public boolean existsAnoCalendario(@PathVariable("anoCalendario") int anoCalendario) {
          return calendarioProducaoCustom.existsAnoCalendario(anoCalendario);
    }
    
    @RequestMapping(value = "/get-params-capa/{anoCalendario}", method = RequestMethod.GET)
    public ParametrosCalendario findParamsCapa(@PathVariable("anoCalendario") int anoCalendario) {
          return  parametrosCalendarioRepository.findByAnoCalendario(anoCalendario);
    }
	
	@RequestMapping(value = "/save-params", method = RequestMethod.POST)
	public void saveParametros(@RequestBody BodyConfigEstagios body) {
		calendarioProducaoService.saveParametros(body.anoCalendario,body.periodoInicio,body.periodoFim,body.consideraSabado,body.consideraDomingo,body.consideraFeriado);
	}
	
	@RequestMapping(value = "/gerar-calendario/{anoCalendario}", method = RequestMethod.GET)
    public LayoutCalendarioProducao gerarCalendario(@PathVariable("anoCalendario") int anoCalendario) {
        return calendarioProducaoService.geracaoCalendario(anoCalendario);
    }
	
	@RequestMapping(value = "/gerar-calendario-area/{empresa}/{anoCalendario}", method = RequestMethod.GET)
    public List<LayoutCalendarioPorArea> gerarCalendarioArea(@PathVariable("empresa") int empresa, @PathVariable("anoCalendario") int anoCalendario) {
        return calendarioProducaoService.geracaoCalendarioPorArea(empresa, anoCalendario);
    }
	
	@RequestMapping(value = "/gravar-periodos/{anoCalendario}", method = RequestMethod.GET) 
	public ParametrosCalendario gravarPeriodosCalculados(@PathVariable("anoCalendario") int anoCalendario) {
		return calendarioProducaoService.gravarPeriodoProducaoCalculado(anoCalendario);
	}	
	
	@RequestMapping(value = "/ver-todas-datas-info/{anoCalendario}", method = RequestMethod.GET)
	public boolean verificaTodasDatasInformadas(@PathVariable("anoCalendario") int anoCalendario) {
		return calendarioProducaoCustom.todasDatasEstagiosInformadas(anoCalendario);
	}
	
	@RequestMapping(value = "/verfica-periodo/{periodo}", method = RequestMethod.GET)
	public boolean verificaPeriodoInformado(@PathVariable("periodo") int periodo) {
		return calendarioProducaoCustom.verificaPeriodoExistente(periodo);
	}
}