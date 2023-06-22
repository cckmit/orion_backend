package br.com.live.administrativo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.custom.BensCustom;
import br.com.live.administrativo.entity.MovimentacaoBens;
import br.com.live.administrativo.repository.MovimentacaoBensRepository;
import br.com.live.produto.entity.TipoMovimentacao;
import br.com.live.produto.repository.TipoMovimentacaoRepository;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Service
@Transactional
public class BensService {
	
	private final TipoMovimentacaoRepository tipoMovimentacaoRepository;
	private final BensCustom bensCustom;
	private final MovimentacaoBensRepository movimentacaoBensRepository;

	public BensService(TipoMovimentacaoRepository tipoMovimentacaoRepository, BensCustom bensCustom, MovimentacaoBensRepository movimentacaoBensRepository) {
		this.tipoMovimentacaoRepository = tipoMovimentacaoRepository;
		this.bensCustom = bensCustom;
		this.movimentacaoBensRepository = movimentacaoBensRepository;
	}
	
	public TipoMovimentacao saveTipoMovimentacao(int idTipoMovimentacao, String descricao) {
		
		TipoMovimentacao dadosTipoMov;
		
		if (idTipoMovimentacao == 0) {
			dadosTipoMov = new TipoMovimentacao(bensCustom.findNextIdTipoMovimentacao(),descricao);
		} else {
			dadosTipoMov = tipoMovimentacaoRepository.findById(idTipoMovimentacao);
			dadosTipoMov.descricao = descricao;
		}
		tipoMovimentacaoRepository.save(dadosTipoMov);
		
		return dadosTipoMov;
	}
	
	public void gerarMovimentacao(int sequencia, int tipoMovimentacao, String cnpjOrigem, String cnpjDestino, String dataEnvio, String notaFiscal, String observacao, List<ConteudoChaveNumerica> bens, int usuario) {
		MovimentacaoBens dadosMovimentacao = movimentacaoBensRepository.findBySequencia(sequencia);
		
		if (dadosMovimentacao == null) {
			for (ConteudoChaveNumerica dadosBem : bens) {
				dadosMovimentacao = new MovimentacaoBens(bensCustom.findNextSequenciaMovimentacao(), dadosBem.value, tipoMovimentacao, cnpjOrigem, cnpjDestino, FormataData.parseStringToDate(dataEnvio), notaFiscal, observacao, usuario);
				
				movimentacaoBensRepository.saveAndFlush(dadosMovimentacao);
			}
		}
	}
	
	public void alterarMovimentacao(int sequencia, int tipoMovimentacao, String cnpjOrigem, String cnpjDestino, String dataEnvio, String notaFiscal, String observacao) {
		MovimentacaoBens dadosMovimentacao = movimentacaoBensRepository.findBySequencia(sequencia);
		
		dadosMovimentacao.cnpjDestino = cnpjDestino;
		dadosMovimentacao.dataEnvio = FormataData.parseStringToDate(dataEnvio);
		dadosMovimentacao.notaFiscal = notaFiscal;
		dadosMovimentacao.observacao = observacao;
		
		movimentacaoBensRepository.save(dadosMovimentacao);
	}
	
	public void deleteById(int idTipoMovimentacao) {
		tipoMovimentacaoRepository.deleteById(idTipoMovimentacao);
	}
	
	public void deleteBySequencia(int sequencia) {
		movimentacaoBensRepository.deleteById(sequencia);
	}
}
