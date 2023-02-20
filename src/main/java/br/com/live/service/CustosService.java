package br.com.live.service;

import br.com.live.custom.CustosCustom;
import br.com.live.model.CopiaFichaCustos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustosService {

    private final CustosCustom custosCustom;

    public CustosService(CustosCustom custosCustom) {
        this.custosCustom = custosCustom;
    }

    public List<CopiaFichaCustos> findProdutosFichaCustos(String produto) {

        String[] prodConcat = produto.split("[.]");
        String grupo = prodConcat[1];

        return custosCustom.findProdutosByGrupo(produto, grupo);
    }

    public void inserirDadosParametrosFichaCustos(String nivel, String grupo, String subGrupo, String item, int empresa, int tipoParam, int mesDestino, int anoDestino, float consumo,
                                                  String descParam, String valorPercentual, int seqParam) {
        custosCustom.inserirParametrosFichaCustos(nivel, grupo, subGrupo, item, empresa, tipoParam, mesDestino, anoDestino, consumo, descParam, valorPercentual, seqParam);
    }

    public void copiarParametrosFichaCustos(int empresa, String produto, int tipoParam, int mesOrigem, int anoOrigem, int anoDestino, int mesDestino, List<String> listProdutosSel) {
        CopiaFichaCustos dadosOrigem = custosCustom.findDadosProdutoOrigemCopia(produto, empresa, tipoParam, mesOrigem, anoOrigem);

        for (String produtosSel : listProdutosSel) {
            String[] prodConcat = produtosSel.split("[.]");
            String nivel = prodConcat[0];
            String grupo = prodConcat[1];
            String subGrupo = prodConcat[2];
            String item = prodConcat[3];

            try {
                inserirDadosParametrosFichaCustos(nivel,grupo,subGrupo,item,empresa,tipoParam,mesDestino,anoDestino,dadosOrigem.consumo,dadosOrigem.descParametro,dadosOrigem.valorPercentual, dadosOrigem.sequenciaParam);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro na cópia dos parametros");
            }
        }
    }
}
