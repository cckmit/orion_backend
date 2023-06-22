package br.com.live.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/painel-lista-prioridades")
public class PainelListaPrioridadesController {

/*

ProducaoListasService
EstoqueListasService
ProdutoListasService
ComercialListasService




-periodo - OK
-deposito Estoque - OK
-colecao - OK
-linha de produto - OK
-marca - ?
-segmento - OK
-artigo - OK
-referencia - ?
-tamanha - ?
-cor - ?
-natureza operacao - ?
-tipo natureza - ?
-situacao - ?
-faccao - ?
-familia - ?
-pedido das ordens - ?
-pedido comercial - ?
numero interno - ?
*/
	 	
	@GetMapping("/periodos")
    public List<ConteudoChaveNumerica> findPeriodos(){
        return null;
    }
	
	@GetMapping("/depositos")
    public List<ConteudoChaveNumerica> findDepositos(){
        return null;
    }

	@GetMapping("/colecoes")
    public List<ConteudoChaveNumerica> findColecoes(){
        return null;
    }
	
	@GetMapping("/linhas-produto")
    public List<ConteudoChaveNumerica> findLinhasProduto(){
        return null;
    }

	@GetMapping("/marcas")
    public List<ConteudoChaveNumerica> findMarcas(){
        return null;
    }

	@GetMapping("/segmentos")
    public List<ConteudoChaveNumerica> findSegmentos(){
        return null;
    }

	@GetMapping("/artigos")
    public List<ConteudoChaveNumerica> findArtigos(){
        return null;
    }
	
	@GetMapping("/referencias")
    public List<ConteudoChaveNumerica> findReferencias(){
        return null;
    }

	@GetMapping("/tamanhos")
    public List<ConteudoChaveNumerica> findTamanhos(){
        return null;
    }
	
	@GetMapping("/cores")
    public List<ConteudoChaveNumerica> findCores(){
        return null;
    }
	
	@GetMapping("/naturezas-operacao")
    public List<ConteudoChaveNumerica> findNaturezasOperacoes(){
        return null;
    }
	
	@GetMapping("/tipos-naturezas-operacao")
    public List<ConteudoChaveNumerica> findTiposNaturezasOperacoes(){
        return null;
    }

	@GetMapping("/situacoes-pedidos")
    public List<ConteudoChaveNumerica> findSituacoesPedidos(){
        return null;
    }

	@GetMapping("/faccoes")
    public List<ConteudoChaveNumerica> findFaccoes(){
        return null;
    }

	@GetMapping("/familias")
    public List<ConteudoChaveNumerica> findFamilias(){
        return null;
    }

	@GetMapping("/pedidos-ordens")
    public List<ConteudoChaveNumerica> findPedidosOrdens(){
        return null;
    }

	@GetMapping("/pedidos-comerciais")
    public List<ConteudoChaveNumerica> findPedidosComerciais(){
        return null;
    }

	@GetMapping("/numeros-interno")
    public List<ConteudoChaveNumerica> findNumerosInternos(){
        return null;
    }

}
