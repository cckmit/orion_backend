package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.body.BodyRejeicaoPecaPorTecido;
import br.com.live.producao.model.ConsultaRejeicaoPecaPorTecido;
import br.com.live.producao.service.RejeicaoPecaPorTecidoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/rejeicao-peca-por-tecido")
public class RejeicaoPecaPorTecidoController {
	
	private RejeicaoPecaPorTecidoService rejeicaoPecaPorTecidoService;
	
	@Autowired
	public RejeicaoPecaPorTecidoController(RejeicaoPecaPorTecidoService rejeicaoPecaPorTecidoService) {
		this.rejeicaoPecaPorTecidoService = rejeicaoPecaPorTecidoService;
		
	}
	// Carregar todos as Rejeições Salva no Orion
	@RequestMapping(value = "/find-all-rejeicoes", method = RequestMethod.GET)
	public List<ConsultaRejeicaoPecaPorTecido> findAllRejeicoes() {
		return rejeicaoPecaPorTecidoService.findAllRejeicoes();
	}
	@RequestMapping(value = "/find-all-estagios", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findAllEstagios() {
		return rejeicaoPecaPorTecidoService.findAllEstagios();
	}
	// Encontrar Todas as Ordens
    @RequestMapping(value = "/find-all-ordens/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllOrdens(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findAllOrdensProducao(ordem); 
    }
    // Encontrar o Periodo da Ordem selecionada
    @RequestMapping(value = "/find-periodo-by-ordem/{ordem}", method = RequestMethod.GET)
    public String findPeriodoByOrdens(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findPeriodoByOP(ordem); 
    }
    // Encontrar os Tecidos da Ordem
    @RequestMapping(value = "/find-tecidos-by-ordem/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findTecidosByOrdem(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findTecidosByOrdem(ordem); 
    }
    // Encontrar as Partes da Peça
    @RequestMapping(value = "/find-partes-peca/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findPartesDaPeca(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findPartesDaPeca(ordem); 
    }
    // Encontrar os Motivos de Rejeições
    @RequestMapping(value = "/find-motivos-by-estagio/{estagio}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findMotivosByEstagio(@PathVariable("estagio") int estagio) {
          return rejeicaoPecaPorTecidoService.findMotivosByEstagio(estagio); 
    }
    // Encontrar a referência da Ordem selecionada
    @RequestMapping(value = "/find-referencia-by-ordem/{ordem}", method = RequestMethod.GET)
    public String findReferenciaByOrdens(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findReferenciaByOP(ordem); 
    }
    // Encontrar Tamanho da Referência da Ordem de Produção
    @RequestMapping(value = "/find-tamanhos-by-referencia/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findTamanhosByReferenciaOrdemProd(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findTamanhosByReferenciaOrdemProd(ordem); 
    }
    // Encontrar Tamanho da Referência da Ordem de Produção
    @RequestMapping(value = "/find-cor-by-referencia/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findCorsByReferenciaOrdemProd(@PathVariable("ordem") int ordem) {
          return rejeicaoPecaPorTecidoService.findCorByReferenciaOrdemProd(ordem); 
    }
    // Salvar Rejeição
    @RequestMapping(value = "/salvar-rejeicao", method = RequestMethod.POST)
    public List<ConsultaRejeicaoPecaPorTecido> salvarRejeicao(@RequestBody BodyRejeicaoPecaPorTecido body) {                  
    	return rejeicaoPecaPorTecidoService.salvarRejeicao(body.id, body.dataRejeicao, body.usuario, body.estagio, body.turno, body.ordemProducao, body.periodo, 
    			body.tecido, body.partePeca, body.quantidade, body.codMotivo, body.referencia, body.tamanho, body.cor);	 
    }
    // Deletar Rejeição
    @RequestMapping(value = "/delete-rejeicao/{idRejeicao}", method = RequestMethod.DELETE)
    public List<ConsultaRejeicaoPecaPorTecido> deleteIndicador(@PathVariable("idRejeicao") int idRejeicao) {                  
    	return rejeicaoPecaPorTecidoService.deleteRejeicao(idRejeicao);
    }

}
