package br.com.live.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.custom.OcupacaoCarteiraCustom;
import br.com.live.model.DadosOcupacaoCarteiraPorCanaisVenda;
import br.com.live.model.DadosOcupacaoCarteiraPorModalidade;

@Service
@Transactional
public class OcupacaoCarteiraService {

	private final OcupacaoCarteiraCustom ocupacaoCarteiraCustom;
	
	public OcupacaoCarteiraService(OcupacaoCarteiraCustom ocupacaoCarteiraCustom) {
		this.ocupacaoCarteiraCustom = ocupacaoCarteiraCustom;
	}
	
	public BodyOcupacaoCarteira consultar(int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega) {				                                                																
		List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisVarejo = ocupacaoCarteiraCustom.consultarDadosPorCanal(mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, "VAREJO");
		List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisAtacado = ocupacaoCarteiraCustom.consultarDadosPorCanal(mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, "ATACADO");
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorVarejo = agruparDadosPorModalidade("VAREJO", dadosCarteiraPorCanaisVarejo);
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorAtacado = agruparDadosPorModalidade("ATACADO", dadosCarteiraPorCanaisAtacado);	
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraTotal = somarModalidades(dadosOcupacaoCarteiraPorVarejo, dadosOcupacaoCarteiraPorAtacado);
		return new BodyOcupacaoCarteira(dadosCarteiraPorCanaisVarejo, dadosCarteiraPorCanaisAtacado, dadosOcupacaoCarteiraPorVarejo, dadosOcupacaoCarteiraPorAtacado, dadosOcupacaoCarteiraTotal);
	}
	
	private DadosOcupacaoCarteiraPorModalidade somarModalidades(DadosOcupacaoCarteiraPorModalidade varejo, DadosOcupacaoCarteiraPorModalidade atacado) {
		return new DadosOcupacaoCarteiraPorModalidade("AMBOS", varejo.getValorOrcado() + atacado.getValorOrcado(), 
				varejo.getValorReal() + atacado.getValorReal(), 
				varejo.getValorConfirmar() + atacado.getValorConfirmar(), 
				varejo.getQtdeOrcado() + atacado.getQtdeOrcado(), 
				varejo.getQtdeReal() + atacado.getQtdeReal(), 
				varejo.getQtdeConfirmar() + atacado.getQtdeConfirmar(), 
				varejo.getMinutosOrcado() + atacado.getMinutosOrcado(), 
				varejo.getMinutosqtdeReal() + atacado.getMinutosqtdeReal(), 
				varejo.getMinutosqtdeConfirmar() + atacado.getMinutosqtdeConfirmar()); 
	}
	
	private DadosOcupacaoCarteiraPorModalidade agruparDadosPorModalidade(String modalidade, List<DadosOcupacaoCarteiraPorCanaisVenda> dados) {
		double valores = 0;
		double quantidades = 0;
		double minutos = 0;
		double valoresConfirmar = 0;
		double quantidadesConfirmar = 0;
		double minutosConfirmar = 0;
		double valoresOrcado = 0;
		double quantidadesOrcado = 0;
		double minutosOrcado = 0;

		for (DadosOcupacaoCarteiraPorCanaisVenda dado : dados) {
			valores += dado.getValor();
			quantidades += dado.getQuantidade(); 
			minutos += dado.getMinutos();
			valoresConfirmar += dado.getValorConfirmar();
			quantidadesConfirmar += dado.getQuantidadeConfirmar();
			minutosConfirmar += dado.getMinutosConfirmar();
			valoresOrcado += dado.getValorOrcado();
			quantidadesOrcado += dado.getQuantidadeOrcado();
			minutosOrcado += dado.getMinutosOrcado();
		}
		
		return new DadosOcupacaoCarteiraPorModalidade(modalidade, valoresOrcado, valores, valoresConfirmar, quantidadesOrcado, quantidades, quantidadesConfirmar, minutosOrcado, minutos, minutosConfirmar); 
	}
	
}
