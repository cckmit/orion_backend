package br.com.live.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.custom.OrdemBeneficiamentoCustom;
import br.com.live.entity.OrdemBeneficiamentoItem;
import br.com.live.model.OrdemBeneficiamentoItens;
import br.com.live.repository.OrdemBeneficiamentoItemRepository;


@Transactional
@Service
public class OrdemBeneficiamentoService {
	
	private final OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository;
	private final OrdemBeneficiamentoCustom ordemBeneficiamentoCustom;
	
	public OrdemBeneficiamentoService(OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository, OrdemBeneficiamentoCustom ordemBeneficiamentoCustom) {
		
		this.ordemBeneficiamentoItemRepository = ordemBeneficiamentoItemRepository;
		this.ordemBeneficiamentoCustom = ordemBeneficiamentoCustom;
	}
	public String saveItemTemporario(String id, String usuario, String produto, float qtdeRolos, float qtdeQuilos, String undMedida, float larguraTecido, float gramatura, float rendimento,
			int deposito) {
		String msgErro = "";
	
		String[] prodConcat = produto.split("[.]");
		String nivel = prodConcat[0];
		String referencia = prodConcat[1];
		String tamanho = prodConcat[2];
		String cor = prodConcat[3];
		
		OrdemBeneficiamentoItem dadosItem = ordemBeneficiamentoItemRepository.findById(id);
		
		int sequencia = ordemBeneficiamentoCustom.findNextSequencia();
				
		System.out.println(id);
		
		if(dadosItem == null) {
			dadosItem = new OrdemBeneficiamentoItem(sequencia, usuario, nivel, referencia, tamanho, cor, qtdeRolos, qtdeQuilos, undMedida, larguraTecido, gramatura, rendimento, deposito);			
		} else {
			dadosItem.ordemProducao = 0;
			dadosItem.panoSbgNivel99 = nivel;
			dadosItem.panoSbgGrupo = referencia;
			dadosItem.panoSbgSubgrupo = tamanho;
			dadosItem.panoSbgItem = cor;
			dadosItem.qtdeRolos = qtdeRolos;
			dadosItem.qtdeQuilos = qtdeQuilos;
			dadosItem.undMedida = undMedida;
			dadosItem.larguraTecido = larguraTecido;
			dadosItem.gramatura = gramatura;
			dadosItem.rendimento = rendimento;
			dadosItem.deposito = deposito;				
		}
		ordemBeneficiamentoItemRepository.save(dadosItem);	
		return msgErro;
	}
	
	public List<OrdemBeneficiamentoItens> deleteIdOrdem(String id, String usuario) {
		ordemBeneficiamentoItemRepository.deleteById(id);
		return ordemBeneficiamentoCustom.findAllItensOrdens(usuario);
	}
	
	public String gerarOrdemBeneficiamento(int periodoProducao, Date dataPrograma, Date previsaoTermino, String maquina, int tipoOrdem, String usuario) {
		String msgErro = "";
		List<OrdemBeneficiamentoItens> gerarOrdensBeneficItens = ordemBeneficiamentoCustom.findAllItensOrdens(usuario);
		
		String[] maqConcat = maquina.split("[.]");
		String grupo = maqConcat[0];
		String subGrupo = maqConcat[1];
		String numeroMaquina = maqConcat[2];
		for (OrdemBeneficiamentoItens itens : gerarOrdensBeneficItens) {
			int ordem = ordemBeneficiamentoCustom.findNextOrdemBeneficiamento();
			// Gerando Ordem na Capa
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop010(periodoProducao, dataPrograma, previsaoTermino, grupo, subGrupo, Integer.parseInt(numeroMaquina), tipoOrdem, ordem);
			// Gerando Ordem para os itens
			String[] prodConcat = itens.produto.split("[.]");
			String nivel = prodConcat[0];
			String referencia = prodConcat[1];
			String tamanho = prodConcat[2];
			String cor_label = prodConcat[3];
			String cor = cor_label.substring(0, 6);
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop020(nivel, referencia, tamanho, cor, itens.rolos, itens.quilos, itens.largura, itens.gramatura, itens.rendimento,
					itens.periodoProducao, itens.observacao, itens.alternativa, itens.roteiro, ordem);
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop030(nivel, referencia, tamanho, cor, itens.rolos, itens.quilos, itens.periodoProducao, 
					itens.deposito, itens.alternativa, itens.roteiro, ordem);
		}		
		return msgErro;		
	}

}