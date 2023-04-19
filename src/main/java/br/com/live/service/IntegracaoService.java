package br.com.live.service;

import br.com.live.entity.Integracao;
import br.com.live.repository.IntegracaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IntegracaoService {

    private IntegracaoRepository integracaoRepository;

    public IntegracaoService(IntegracaoRepository integracaoRepository) { this.integracaoRepository = integracaoRepository; }

    public void saveIntegracao(int id, String nomeIntegracao, String objetivo, String tipoIntegracao, String tipoConexao, int sistemaOrigem, int sistemaDestino, int servidor,
    		String status, String fornecedor, String cnpj, String endereco){

        Integracao integracao = null;

        integracao = integracaoRepository.findById(id);

        if (integracao == null){
            integracao = new Integracao(integracaoRepository.findNextId(), nomeIntegracao, objetivo, tipoIntegracao, tipoConexao, sistemaOrigem, sistemaDestino, servidor, 
            		status, fornecedor, cnpj, endereco);
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
        }
        integracaoRepository.save(integracao);
    }

    public void deleteById(int id){ integracaoRepository.deleteById(id);}
}
