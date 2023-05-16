package br.com.live.service;

import br.com.live.custom.GestaoAtivosCustom;
import br.com.live.entity.Integracao;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.IntegracaoRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IntegracaoService {

    private IntegracaoRepository integracaoRepository;
    private GestaoAtivosCustom gestaoAtivosCustom;

    public IntegracaoService(IntegracaoRepository integracaoRepository, GestaoAtivosCustom gestaoAtivosCustom) { 
    	this.integracaoRepository = integracaoRepository; 
    	this.gestaoAtivosCustom = gestaoAtivosCustom;
    }

    public List<ConsultaGestaoAtivos> findAllIntegracoes(){
    	return gestaoAtivosCustom.findAllIntegracoes();
    }
    
    public void saveIntegracao(int id, String nomeIntegracao, String objetivo, String tipoIntegracao, String tipoConexao, int sistemaOrigem, int sistemaDestino, int servidor,
    		String status, String fornecedor, String cnpj, String endereco, int gestorResponsavel){

        Integracao integracao = null;

        integracao = integracaoRepository.findById(id);

        if (integracao == null){
            integracao = new Integracao(integracaoRepository.findNextId(), nomeIntegracao, objetivo, tipoIntegracao, tipoConexao, sistemaOrigem, sistemaDestino, servidor, 
            		status, fornecedor, cnpj, endereco, gestorResponsavel);
        } else {
            integracao.nomeIntegracao = nomeIntegracao;
            integracao.objetivo = objetivo;
            integracao.tipoIntegracao = tipoIntegracao;
            integracao.tipoConexao = tipoConexao;
            integracao.sistemaOrigem = sistemaOrigem;
            integracao.sistemaDestino = sistemaDestino;
            integracao.servidor = servidor;
            integracao.status = status;
            integracao.fornecedor = fornecedor;
            integracao.cnpj = cnpj;
            integracao.endereco = endereco;
            integracao.gestorResponsavel = gestorResponsavel;
        }
        integracaoRepository.save(integracao);
    }

    public void deleteById(int id){ integracaoRepository.deleteById(id);}
}
