package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.custom.OcupacaoCarteiraCustom;
import br.com.live.model.DadosOcupacaoCarteiraPorCanaisVenda;
import br.com.live.model.DadosOcupacaoCarteiraPorModalidade;
import br.com.live.model.ResumoOcupacaoCarteiraPorCanaisVenda;
import br.com.live.model.ResumoOcupacaoCarteiraPorModalidade;

@Service
@Transactional
public class OcupacaoCarteiraService {

	private final OcupacaoCarteiraCustom ocupacaoCarteiraCustom;
	
	public OcupacaoCarteiraService(OcupacaoCarteiraCustom ocupacaoCarteiraCustom) {
		this.ocupacaoCarteiraCustom = ocupacaoCarteiraCustom;
	}
	
	public BodyOcupacaoCarteira consultar(String valorResumir, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega) {				                                                																		
		List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisVarejo = ocupacaoCarteiraCustom.consultarDadosPorCanal(mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, "VAREJO");
		List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisAtacado = ocupacaoCarteiraCustom.consultarDadosPorCanal(mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, "ATACADO");
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorVarejo = agruparDadosPorModalidade("VAREJO", dadosCarteiraPorCanaisVarejo);
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorAtacado = agruparDadosPorModalidade("ATACADO", dadosCarteiraPorCanaisAtacado);	
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraTotal = somarModalidades(dadosOcupacaoCarteiraPorVarejo, dadosOcupacaoCarteiraPorAtacado);
		List<ResumoOcupacaoCarteiraPorCanaisVenda> resumoCarteiraPorCanaisVarejo = resumirCarteiraPorCanais(valorResumir, dadosCarteiraPorCanaisVarejo);
		List<ResumoOcupacaoCarteiraPorCanaisVenda> resumoCarteiraPorCanaisAtacado = resumirCarteiraPorCanais(valorResumir, dadosCarteiraPorCanaisAtacado);;
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo = resumirCarteiraPorModalidade(valorResumir, dadosOcupacaoCarteiraPorVarejo);
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado = resumirCarteiraPorModalidade(valorResumir, dadosOcupacaoCarteiraPorAtacado);;	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal = resumirCarteiraPorModalidade(valorResumir, dadosOcupacaoCarteiraTotal);;
		return new BodyOcupacaoCarteira(resumoCarteiraPorCanaisVarejo, resumoCarteiraPorCanaisAtacado, resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado, resumoOcupacaoCarteiraTotal);
	}
	
	private List<ResumoOcupacaoCarteiraPorCanaisVenda> resumirCarteiraPorCanais(String valorResumir, List<DadosOcupacaoCarteiraPorCanaisVenda> canais) {
		double valorOrcado = 0; 
		double valorReal = 0; 
		double valorConfirmar = 0; 
		double valorTotal = 0; 
		double percentual = 0;
		
		List<ResumoOcupacaoCarteiraPorCanaisVenda> listCanaisResumidos = new ArrayList<ResumoOcupacaoCarteiraPorCanaisVenda>();
		for (DadosOcupacaoCarteiraPorCanaisVenda canal : canais) {
			if (valorResumir.equalsIgnoreCase("VALORES")) {
				valorOrcado = canal.getValorOrcado(); 
				valorReal = canal.getValor(); 
				valorConfirmar = canal.getValorConfirmar(); 
			} else if (valorResumir.equalsIgnoreCase("PECAS")) {
				valorOrcado = canal.getQuantidadeOrcado(); 
				valorReal = canal.getQuantidade(); 
				valorConfirmar = canal.getQuantidadeConfirmar();
			} else {
				valorOrcado = canal.getMinutosOrcado(); 
				valorReal = canal.getMinutos(); 
				valorConfirmar = canal.getMinutosConfirmar();				
			}
			
			valorTotal = valorReal + valorConfirmar; 
			percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;
				
			listCanaisResumidos.add(new ResumoOcupacaoCarteiraPorCanaisVenda(canal.getCanal(), valorOrcado, valorReal, valorConfirmar, valorTotal, percentual));
		}
		return listCanaisResumidos;
	}

	private ResumoOcupacaoCarteiraPorModalidade resumirCarteiraPorModalidade(String valorResumir, DadosOcupacaoCarteiraPorModalidade modalidade) {
		double valorOrcado = 0; 
		double valorReal = 0; 
		double valorConfirmar = 0; 
		double valorTotal = 0; 
		double percentual = 0;
		
		if (valorResumir.equalsIgnoreCase("VALORES")) {
			valorOrcado = modalidade.getValorOrcado(); 
			valorReal = modalidade.getValorReal(); 
			valorConfirmar = modalidade.getValorConfirmar(); 
		} else if (valorResumir.equalsIgnoreCase("PECAS")) {
			valorOrcado = modalidade.getQtdeOrcado(); 
			valorReal = modalidade.getQtdeReal(); 
			valorConfirmar = modalidade.getQtdeConfirmar();
		} else {
			valorOrcado = modalidade.getMinutosOrcado(); 
			valorReal = modalidade.getMinutosReal(); 
			valorConfirmar = modalidade.getMinutosConfirmar();				
		}
		
		valorTotal = valorReal + valorConfirmar; 
		percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;

		return new ResumoOcupacaoCarteiraPorModalidade(modalidade.getModalidade(), valorOrcado, valorReal, valorConfirmar, valorTotal, percentual);
	}
	
	private DadosOcupacaoCarteiraPorModalidade somarModalidades(DadosOcupacaoCarteiraPorModalidade varejo, DadosOcupacaoCarteiraPorModalidade atacado) {
		return new DadosOcupacaoCarteiraPorModalidade("AMBOS", varejo.getValorOrcado() + atacado.getValorOrcado(), 
				varejo.getValorReal() + atacado.getValorReal(), 
				varejo.getValorConfirmar() + atacado.getValorConfirmar(), 
				varejo.getQtdeOrcado() + atacado.getQtdeOrcado(), 
				varejo.getQtdeReal() + atacado.getQtdeReal(), 
				varejo.getQtdeConfirmar() + atacado.getQtdeConfirmar(), 
				varejo.getMinutosOrcado() + atacado.getMinutosOrcado(), 
				varejo.getMinutosReal() + atacado.getMinutosReal(), 
				varejo.getMinutosConfirmar() + atacado.getMinutosConfirmar()); 
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
