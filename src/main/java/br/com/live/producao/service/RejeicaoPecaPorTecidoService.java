package br.com.live.producao.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;
import br.com.live.administrativo.entity.LancamentoContabeisImport;
import br.com.live.producao.custom.RejeicaoPecaPorTecidoCustom;
import br.com.live.producao.entity.RejeicaoPecaPorTecido;
import br.com.live.producao.model.ConsultaRejeicaoPecaPorTecido;
import br.com.live.producao.repository.RejeicaoPecaPorTecidoRepository;
import br.com.live.sistema.model.ConsultaIndicadores;

@Service
@Transactional
public class RejeicaoPecaPorTecidoService {
	
	private final RejeicaoPecaPorTecidoCustom rejeicaoPecaPorTecidoCustom;
	private final RejeicaoPecaPorTecidoRepository rejeicaoPecaPorTecidoRepository;
	
	public RejeicaoPecaPorTecidoService(RejeicaoPecaPorTecidoCustom rejeicaoPecaPorTecidoCustom, RejeicaoPecaPorTecidoRepository rejeicaoPecaPorTecidoRepository) {
		this.rejeicaoPecaPorTecidoCustom = rejeicaoPecaPorTecidoCustom;
		this.rejeicaoPecaPorTecidoRepository = rejeicaoPecaPorTecidoRepository;
		
	}
	public List<ConsultaRejeicaoPecaPorTecido> findAllRejeicoes(){
		return rejeicaoPecaPorTecidoCustom.findAllRejeicoes();
	}
	
	public List<ConteudoChaveNumerica> findAllEstagios(){
		return rejeicaoPecaPorTecidoCustom.findAllEstagios();
	}
	
	public List<ConteudoChaveNumerica> findAllOrdensProducao(int ordem){
		return rejeicaoPecaPorTecidoCustom.findAllOrdensProducao(ordem);
	}
	
	public String findPeriodoByOP(int ordem) {
		return rejeicaoPecaPorTecidoCustom.findPeriodoByOP(ordem);
	}
	
	public String findReferenciaByOP(int ordem) {
		return rejeicaoPecaPorTecidoCustom.findReferenciaByOP(ordem);
	}
	
	public List<ConteudoChaveAlfaNum> findTecidosByOrdem(int ordem) {
		
		List<ConteudoChaveAlfaNum> listaRetorno = null;
		
		listaRetorno = rejeicaoPecaPorTecidoCustom.findTecidosByOrdemProd(ordem); 
		
		if(listaRetorno.size() == 0) {
			listaRetorno = rejeicaoPecaPorTecidoCustom.findTecidosByOrdemDest(ordem);
		}		
		return listaRetorno;
	}
	
	public List<ConteudoChaveAlfaNum> findPartesDaPeca(int ordem){
		return rejeicaoPecaPorTecidoCustom.findPartesDaPeca(ordem);
	}
	
	public List<ConteudoChaveNumerica> findMotivosByEstagio(int ordem){
		return rejeicaoPecaPorTecidoCustom.findMotivosByEstagio(ordem);
	}
	
	public List<ConteudoChaveAlfaNum> findTamanhosByReferenciaOrdemProd(int ordem){
		return rejeicaoPecaPorTecidoCustom.findTamanhosByReferenciaOrdemProd(ordem);
	}
	
	public List<ConteudoChaveAlfaNum> findCorByReferenciaOrdemProd(int ordem){
		return rejeicaoPecaPorTecidoCustom.findCorByReferenciaOrdemProd(ordem);
	}
	
	public List<ConsultaRejeicaoPecaPorTecido> salvarRejeicao(int id, String dataRejeicao, int usuario, int codEstagio, int turno, int ordemProducao, int periodo, 
			String tecido, String partePeca, int quantidade, int codMotivo){
		 
		String[] tecidoConcat = tecido.split("[.]");
        String nivel = tecidoConcat[0];
        String grupo = tecidoConcat[1];
        String subgrupo = tecidoConcat[2];
        String item = tecidoConcat[3];
		
		RejeicaoPecaPorTecido motivoRejeicao = null;
		
		try {
			motivoRejeicao = new RejeicaoPecaPorTecido(rejeicaoPecaPorTecidoRepository.findNextId(), FormataData.parseStringToDate(dataRejeicao), usuario, codEstagio, turno,
					ordemProducao, periodo, nivel, grupo, subgrupo, item, partePeca, quantidade, codMotivo);
			rejeicaoPecaPorTecidoRepository.save(motivoRejeicao);
			rejeicaoPecaPorTecidoCustom.gravarSystextil(FormataData.parseStringToDate(dataRejeicao), codEstagio, turno, ordemProducao, periodo, nivel, grupo, subgrupo, item, quantidade, codMotivo);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return rejeicaoPecaPorTecidoCustom.findAllRejeicoes();
	}
	
	public List<ConsultaRejeicaoPecaPorTecido> deleteRejeicao(int idRejeicao) {
		
		List<RejeicaoPecaPorTecido> listaDados = rejeicaoPecaPorTecidoRepository.findById(idRejeicao);
		
		try {
			for (RejeicaoPecaPorTecido item : listaDados) {
				rejeicaoPecaPorTecidoCustom.deletarSystextil(item.dataRejeicao, item.estagio, item.turno, item.ordemProducao, item.periodo, item.nivelEstrutura,
						item.grupoEstrutura, item.subgruEstrutura, item.itemEstrutura, item.quantidade, item.motivo);				
			}
			rejeicaoPecaPorTecidoRepository.deleteById(idRejeicao);			
		} catch (Exception e) {
			System.out.println(e);			
		}
    	return rejeicaoPecaPorTecidoCustom.findAllRejeicoes();
	}
}
