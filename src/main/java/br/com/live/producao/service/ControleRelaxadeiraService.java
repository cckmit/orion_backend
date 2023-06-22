package br.com.live.producao.service;

import br.com.live.producao.custom.ControleRelaxadeiraCustom;
import br.com.live.producao.entity.ControleRelaxadeira;
import br.com.live.producao.model.ConsultaControleRelaxadeira;
import br.com.live.producao.model.ObterInfoRolos;
import br.com.live.producao.repository.ControleRelaxadeiraRepository;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ControleRelaxadeiraService {

    final ControleRelaxadeiraRepository controleRelaxadeiraRepository;
    final ControleRelaxadeiraCustom controleRelaxadeiraCustom;

    public ControleRelaxadeiraService(ControleRelaxadeiraRepository controleRelaxadeiraRepository, ControleRelaxadeiraCustom controleRelaxadeiraCustom) {
        this.controleRelaxadeiraRepository = controleRelaxadeiraRepository;
        this.controleRelaxadeiraCustom = controleRelaxadeiraCustom;
    }

    public List<ConsultaControleRelaxadeira> consultaNaoSincronizados(int status) {
        return controleRelaxadeiraCustom.consultaDadosGridRelaxadeira(status);
    }

    public List<ConsultaControleRelaxadeira> consultaRelatorioRelaxadeira(String dataInicio, String dataFim, int statusSinc) {
        return controleRelaxadeiraCustom.consultaRelatorioRegistrosSincronizados(dataInicio, dataFim, statusSinc);
    }

    public void salvarRolosControleRelaxadeira(int status, int codRolo, List<ConteudoChaveNumerica> listMotivos, float quantidade, float perdaMetros, int codAnalista,
                                               float largura, float gramatura, float metragem) {

        LocalDateTime dataHoraLocal = LocalDateTime.now();
        java.sql.Timestamp dataHoraSql = java.sql.Timestamp.valueOf(dataHoraLocal);

        for (ConteudoChaveNumerica motivo : listMotivos) {
            ControleRelaxadeira dadosControle = new ControleRelaxadeira(status, codRolo, codAnalista, motivo.value, quantidade, perdaMetros, 0, null, dataHoraSql,
                    largura, gramatura, metragem);
            controleRelaxadeiraRepository.save(dadosControle);
        }
    }

    public void salvarSincronizarRegistrosMotivos() {
        List<ControleRelaxadeira> listSincronizar = controleRelaxadeiraRepository.findRegistrosParaSincronizar();
        List<Integer> rolosComDefeito = controleRelaxadeiraCustom.obterRolosComDefeitoRegistrado();

        LocalDate dataLocal = LocalDate.now();
        java.sql.Date dataSql = java.sql.Date.valueOf(dataLocal);

        for (ControleRelaxadeira dadosMotivo : listSincronizar) {
            // Atualiza pcpt_020
            controleRelaxadeiraCustom.atualizaInformacoesRolo(dadosMotivo.codAnalista, dadosMotivo.dataHoraBipagem, dadosMotivo.codRolo, dadosMotivo.largura, dadosMotivo.gramatura, dadosMotivo.metragem);

            if (dadosMotivo.codMotivo > 0) {
                ObterInfoRolos dadosRolo = controleRelaxadeiraCustom.obterInfoRolos(dadosMotivo.codRolo);

                //Insere efic_100
                controleRelaxadeiraCustom.inserirRejeicaoRolo(dadosRolo.nivel, dadosRolo.grupo, dadosRolo.sub, dadosRolo.item, dadosMotivo.codMotivo, dadosRolo.periodo, dadosMotivo.codRolo, dadosRolo.qtdeKilos, dadosMotivo.quantidade,
                        dadosMotivo.dataHoraBipagem, dadosMotivo.codAnalista, dadosMotivo.perdaMetros);
            }

            // Atualiza Controle Sincronização
            dadosMotivo.sitSincronizacao = 1;
            dadosMotivo.dataSincronizacao = dataSql;
            controleRelaxadeiraRepository.saveAndFlush(dadosMotivo);
        }

        // Insere pcpt_022
        for (Integer rolo : rolosComDefeito) {
            List<String> descMotivos = controleRelaxadeiraCustom.obterDescMotivosRolo(rolo);
            Date maiorDataBipagem = controleRelaxadeiraCustom.obterMaiorDataBipagem(rolo);
            int maiorAnalista = controleRelaxadeiraCustom.obterMaiorAnalista(rolo);

            String observacao = " Analista: " + maiorAnalista + " / ";

            for (String motivo : descMotivos) {
                observacao += motivo + " / ";
            }
            controleRelaxadeiraCustom.inserirInformacaoRejeicao(rolo,maiorAnalista,maiorDataBipagem,observacao);
        }
    }

    public void deleteLancamentoById(String id) {
        controleRelaxadeiraRepository.deleteById(id);
    }
    
    public ConsultaControleRelaxadeira findInformacoesRolo(int codigoRolo) {
    	return controleRelaxadeiraCustom.findInformacoesRolo(codigoRolo);
    }
}
