package br.com.live.producao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.producao.entity.PreRequisicaoAlmoxarifado;
import br.com.live.producao.entity.PreRequisicaoAlmoxarifadoItem;
import br.com.live.producao.repository.PreRequisicaoAlmoxarifadoItemRepository;
import br.com.live.producao.repository.PreRequisicaoAlmoxarifadoRepository;

@Service
@Transactional
public class PreRequisicaoAlmoxarifadoService {

	private final PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository;
	private final PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoItemRepository;
	
	public PreRequisicaoAlmoxarifadoService(PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository, PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoItemRepository) {
		this.preRequisicaoAlmoxarifadoRepository = preRequisicaoAlmoxarifadoRepository;
		this.preRequisicaoAlmoxarifadoItemRepository = preRequisicaoAlmoxarifadoItemRepository;
	}	
	
	public PreRequisicaoAlmoxarifado savePreRequisicaoAlmoxarifado(long id, String descricao, int empresa, int divisaoProducao, int centroCusto) {
		
		PreRequisicaoAlmoxarifado preRequisicaoAlmoxarifado = preRequisicaoAlmoxarifadoRepository.findById(id);
		
		if (preRequisicaoAlmoxarifado == null) {
			id = preRequisicaoAlmoxarifadoRepository.findNextId();			
			preRequisicaoAlmoxarifado = new PreRequisicaoAlmoxarifado(id, descricao.toUpperCase(), empresa, divisaoProducao, centroCusto);
		} else {
			preRequisicaoAlmoxarifado.descricao = descricao.toUpperCase();
			preRequisicaoAlmoxarifado.empresa = empresa;
			preRequisicaoAlmoxarifado.divisaoProducao = divisaoProducao;
			preRequisicaoAlmoxarifado.centroCusto = centroCusto;
		}
		
		return preRequisicaoAlmoxarifadoRepository.save(preRequisicaoAlmoxarifado);
	}
	
	public void savePreRequisicaoAlmoxarifadoItem(String id, long idPreRequisicao, int sequencia, String nivel, String grupo, String sub, String item, int deposito, double quantidade) {
		
		PreRequisicaoAlmoxarifadoItem preRequisicaoAlmoxarifadoItem = preRequisicaoAlmoxarifadoItemRepository.findByIdItemPreRequisicao(id);
		
		if (preRequisicaoAlmoxarifadoItem == null) {
			sequencia = preRequisicaoAlmoxarifadoItemRepository.findNextSequenciaByIdPreRequisicao(idPreRequisicao);
			preRequisicaoAlmoxarifadoItem = new PreRequisicaoAlmoxarifadoItem(idPreRequisicao, sequencia, nivel, grupo, sub, item, deposito, quantidade); 
		} else {
			preRequisicaoAlmoxarifadoItem.nivel = nivel;
			preRequisicaoAlmoxarifadoItem.grupo = grupo;
			preRequisicaoAlmoxarifadoItem.sub = sub;
			preRequisicaoAlmoxarifadoItem.item = item;
			preRequisicaoAlmoxarifadoItem.deposito = deposito;
			preRequisicaoAlmoxarifadoItem.quantidade = quantidade;
		}
		
		preRequisicaoAlmoxarifadoItemRepository.save(preRequisicaoAlmoxarifadoItem);
	}
		
    public void deletePreRequisicaoAlmoxarifado(long idPreRequisicao) {
    	preRequisicaoAlmoxarifadoItemRepository.deleteByIdPreRequisicao(idPreRequisicao);
    	preRequisicaoAlmoxarifadoRepository.deleteById(idPreRequisicao);
    }
    
    public void deletePreRequisicaoAlmoxarifadoItem(String idPreRequisicaoItem) {
    	preRequisicaoAlmoxarifadoItemRepository.deleteById(idPreRequisicaoItem);    	
    }

}
