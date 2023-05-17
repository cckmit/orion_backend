package br.com.live.service;

import br.com.live.custom.DreLojaCustom;
import br.com.live.custom.OrcamentoLojaDreCustom;
import br.com.live.custom.RelacionamentoLojaDreCustom;
import br.com.live.entity.DreLojaEntity;
import br.com.live.entity.ParametroGeralDreEntity;
import br.com.live.model.*;
import br.com.live.repository.DreLojaRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class DreLojaService {

    DreLojaRepository dreLojaRepository;
    DreLojaCustom dreLojaCustom;
    RelacionamentoLojaDreCustom relacionamentoLojaDreCustom;
    OrcamentoLojaDreCustom orcamentoLojaDreCustom;
    ReportService reportService;

    private final static int PECAS_FATURADAS = 99900;
    private final static int PECAS_CONSUMO = 99901;
    private final static int PRECO_MEDIO = 99902;
    private final static int FATURAMENTO = 99903;
    private final static int IMPOSTOS = 99904;
    private final static int FATURAMENTO_LIQUIDO = 99905;
    private final static int CUSTO_DOS_MATERIAIS = 99906;
    private final static int LUCRO_BRUTO = 99907;
    private final static int GASTOS_VARIAVEIS = 99908;
    private final static int ENCARGOS_COMISSOES = 99909;
    private final static int TAXA_CAPTURA = 99910;
    private final static int MARGEM_CONTRIBUICAO = 99911;
    private final static int GASTOS_FIXOS = 99912;
    private final static int DESPESAS_FOLHA = 99913;
    private final static int DESPESAS_GERAIS = 99914;
    private final static int EBITDA = 99915;
    private final static int CUSTO_ANTECIPACAO = 99916;
    private final static int RESULTADO_OPERACIONAL = 99917;
    private final static int IMPOSTOS_PLANEJAMENTO = 99918;
    private final static int RESULTADO_LIQUIDO = 99919;
    private final static int PONTO_DE_EQUILIBRIO = 99920;
    private final static int CUSTO_COM_PESSOAL = 99921;
    private final static int ESTORNO_ENCARGO_COMISSOES = 99922;

    private final static int COMISSOES = 25895;
    private final static int CUSTO_OCUPACAO = 20630;
    private final static int DEPRECIACAO = 4987;
    private final static int AMORTIZACAO = 76130;
    private final static int MESVIGENTE = 1;
    private final static int MESACUMULADO = 2;


    public DreLojaService(DreLojaRepository dreLojaRepository, DreLojaCustom dreLojaCustom, RelacionamentoLojaDreCustom relacionamentoLojaDreCustom, OrcamentoLojaDreCustom orcamentoLojaDreCustom,ReportService reportService) {
        this.dreLojaRepository = dreLojaRepository;
        this.dreLojaCustom = dreLojaCustom;
        this.relacionamentoLojaDreCustom = relacionamentoLojaDreCustom;
        this.orcamentoLojaDreCustom = orcamentoLojaDreCustom;
        this.reportService = reportService;
    }

    public void gravarDreLojas(int mesDre, int anoDre, List<ConteudoChaveAlfaNum> cnpjLojaList){

        System.out.println("Iniciando Calculo");

        for(ConteudoChaveAlfaNum cnpjLoja : cnpjLojaList){

            dreLojaCustom.deleteDreLoja(cnpjLoja.value, mesDre, anoDre);

            System.out.println("Iniciando Calculo Dre Loja Mês Vigente");
            gravarDreLoja(mesDre, anoDre, cnpjLoja.value);
            System.out.println("Finalizou Calculo Dre Loja Mês Vigente");
            System.out.println("Iniciou Calculo Dre Loja Mês Acumulado");
            gravarDreAcumuladoLoja(mesDre, anoDre, cnpjLoja.value);
            System.out.println("Finalizou Calculo Dre Loja Mês Acumulado");
        }
    }

    public void gravarDreAcumuladoLoja(int mesDre, int anoDre, String cnpjLoja) {

        OrcamentoLojaDre orcamentoFaturamento = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(FATURAMENTO, cnpjLoja, mesDre, anoDre);
        String seqFaturamento = orcamentoFaturamento.seqConsulta;

        DreLojaCalculo dreFaturamentoAcumulado = dreLojaCustom.obterDadosDreAcumulado(seqFaturamento, cnpjLoja, mesDre, anoDre);
        double valorFaturamentoMesAnoAnterior = dreFaturamentoAcumulado.valPropriedadeMesAnoAnterior;
        double valorFaturamentoMesAnoAtualOrcado = dreFaturamentoAcumulado.valPropriedadeOrcadoMesAnoAtual;
        double valorFaturamentoMesAnoAtual = dreFaturamentoAcumulado.valPropriedadeMesAnoAtual;

        List<DreLoja> dreLojaList = dreLojaCustom.obterCamposDreLojaCnpjMesAnVigente(cnpjLoja, mesDre, anoDre);

        for (DreLoja dreLojaField : dreLojaList){

            String seqFieldDre = dreLojaField.seqConsulta;

            OrcamentoLojaDre dadosOrcamentoSequencia = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqFieldDre, cnpjLoja, mesDre, anoDre);
            int contaContabil = dadosOrcamentoSequencia.contaContabil;

            DreLojaCalculo dreValoresAcumulados = dreLojaCustom.obterDadosDreAcumulado(seqFieldDre, cnpjLoja, mesDre, anoDre);
            double valPropriedadeMesAnoAnterior = Math.abs(dreValoresAcumulados.valPropriedadeMesAnoAnterior);
            double valPropriedadeOrcadoMesAnoAtual = Math.abs(dreValoresAcumulados.valPropriedadeOrcadoMesAnoAtual);
            double valPropriedadeMesAnoAtual = Math.abs(dreValoresAcumulados.valPropriedadeMesAnoAtual);

            double valFatMesAnoAnterior = valorFaturamentoMesAnoAnterior;
            double valFatMesAnoAtualOrcado = valorFaturamentoMesAnoAtualOrcado;
            double valFatMesAnoAtual = valorFaturamentoMesAnoAtual;

            if (contaContabil == PECAS_FATURADAS || contaContabil == PECAS_CONSUMO || contaContabil == PRECO_MEDIO) {
                valFatMesAnoAnterior = 0;
                valFatMesAnoAtualOrcado = 0;
                valFatMesAnoAtual = 0;
            }

            gravarContaContabilDre(MESACUMULADO, seqFieldDre, cnpjLoja, mesDre, anoDre, valPropriedadeMesAnoAnterior, valPropriedadeOrcadoMesAnoAtual, valPropriedadeMesAnoAtual, valFatMesAnoAnterior, valFatMesAnoAtualOrcado, valFatMesAnoAtual);
        }
    }

    public void gravarDreLoja(int mesDre, int anoDre, String cnpjLoja){

        List<String> sequenciasOrcamentoList = orcamentoLojaDreCustom.findAllSequenciaOrcamento(cnpjLoja, mesDre, anoDre);
        List<ConteudoChaveNumerica> listCentroCustosLoja =  relacionamentoLojaDreCustom.findAllCentroCustoLoja(cnpjLoja);
        String centroCustoLojaConcat = converterListaNumericaEmString(listCentroCustosLoja);
        ParametroGeralDreEntity dadoParametroGeral = dreLojaCustom.findParametrosDreByMesAno(mesDre, anoDre);
        LancamentoLojaMesAno dadosLancamentoLojaMesAnoAnt = obterDadosLancamentoLojaMesAno(cnpjLoja, mesDre, anoDre -1);
        LancamentoLojaMesAno dadosLancamentoLojaMesAnoAtual = obterDadosLancamentoLojaMesAno(cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoFaturamento = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(FATURAMENTO, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoImpostoFaturamento = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(IMPOSTOS, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoPecasFaturadas = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(PECAS_FATURADAS, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoPecaConsumo = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(PECAS_CONSUMO, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoPrecoMedio = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(PRECO_MEDIO, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoFaturamentoLiquido = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(FATURAMENTO_LIQUIDO, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoCustoMaterial = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(CUSTO_DOS_MATERIAIS, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre orcamentoLucroBruto = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(LUCRO_BRUTO, cnpjLoja, mesDre, anoDre);

        double valorFaturamentoMesAnoAtualOrcado =  (int) Math.abs(orcamentoFaturamento.valPropriedade);
        double valorFaturamentoMesAnoAnterior =  Math.abs(dadosLancamentoLojaMesAnoAnt.valFaturamento); // TODO: Dados Microvix
        double valorFaturamentoMesAnoAtual =   Math.abs(dadosLancamentoLojaMesAnoAtual.valFaturamento); // TODO: Dados Microvix

        double valorImpostoFaturamentoMesAnoAtualOrcado = (int) Math.abs(orcamentoImpostoFaturamento.valPropriedade);
        double valorImpostoFaturamentoMesAnoAnterior = Math.abs(dadosLancamentoLojaMesAnoAnt.valImpostoFaturamento); // TODO: Dados Microvix
        double valorImpostoFaturamentoMesAnoAtual = Math.abs(dadosLancamentoLojaMesAnoAtual.valImpostoFaturamento); // TODO: Dados Microvix

        int qtdPecaFaturadaMesAnoAtualOrcado = (int) Math.abs(orcamentoPecasFaturadas.valPropriedade);
        int qtdPecaFaturadaMesAnoAnterior =  dadosLancamentoLojaMesAnoAnt.qtdPecaFaturada; // TODO: Dados Microvix
        int qtdPecaFaturadaMesAnoAtual =  dadosLancamentoLojaMesAnoAtual.qtdPecaFaturada; // TODO: Dados Microvix

        int qtdPecaConsumoMesAnoAtualOrcado = (int) Math.abs(orcamentoPecaConsumo.valPropriedade);
        int qtdPecaConsumoMesAnoAnterior = dadosLancamentoLojaMesAnoAnt.qtdPecaConsumo; // TODO: Dados Microvix
        int qtdPecaConsumoMesAnoAtual = dadosLancamentoLojaMesAnoAtual.qtdPecaConsumo; // TODO: Dados Microvix

        double valorPrecoMedioMesAnoAtualOrcado = Math.abs(orcamentoPrecoMedio.valPropriedade);
        double valorPrecoMedioMesAnoAnterior = valorFaturamentoMesAnoAnterior / qtdPecaFaturadaMesAnoAnterior;
        double valorPrecoMedioMesAnoAtual = valorFaturamentoMesAnoAtual / qtdPecaFaturadaMesAnoAtual;

        double valorFaturamentoLiquidoMesAnoAtualOrcado = orcamentoFaturamentoLiquido.valPropriedade;
        double valorFaturamentoLiquidoMesAnoAnterior = valorFaturamentoMesAnoAnterior - valorImpostoFaturamentoMesAnoAnterior;
        double valorFaturamentoLiquidoMesAnoAtual = valorFaturamentoMesAnoAtual - valorImpostoFaturamentoMesAnoAtual;

        double valorCustoMaterialMesAnoAtualOrcado = Math.abs(orcamentoCustoMaterial.valPropriedade);
        double valorCustoMaterialMesAnoAnterior = ((qtdPecaFaturadaMesAnoAnterior + qtdPecaConsumoMesAnoAnterior) * dadoParametroGeral.valCustoVendaProduto);
        double valorCustoMaterialMesAnoAtual = ((qtdPecaFaturadaMesAnoAtual + qtdPecaConsumoMesAnoAtual) * dadoParametroGeral.valCustoVendaProduto);

        double valorLucroBrutoMesAnoAtualOrcado = Math.abs(orcamentoLucroBruto.valPropriedade);
        double valorLucroBrutoMesAnoAnterior = valorFaturamentoLiquidoMesAnoAnterior - valorCustoMaterialMesAnoAnterior;
        double valorLucroBrutoMesAnoAtual = valorFaturamentoLiquidoMesAnoAtual - valorCustoMaterialMesAnoAtual;

        for (String seqOrcamento: sequenciasOrcamentoList ) {
            gravarContaContabilSystextil(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado,valorFaturamentoMesAnoAtual);

            gravarContaContabilFixo(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat,
                    valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual,
                    valorImpostoFaturamentoMesAnoAnterior, valorImpostoFaturamentoMesAnoAtualOrcado, valorImpostoFaturamentoMesAnoAtual,
                    qtdPecaFaturadaMesAnoAnterior, qtdPecaFaturadaMesAnoAtualOrcado, qtdPecaFaturadaMesAnoAtual,
                    qtdPecaConsumoMesAnoAnterior, qtdPecaConsumoMesAnoAtualOrcado, qtdPecaConsumoMesAnoAtual,
                    valorPrecoMedioMesAnoAnterior, valorPrecoMedioMesAnoAtualOrcado, valorPrecoMedioMesAnoAtual,
                    valorFaturamentoLiquidoMesAnoAnterior, valorFaturamentoLiquidoMesAnoAtualOrcado, valorFaturamentoLiquidoMesAnoAtual,
                    valorCustoMaterialMesAnoAnterior, valorCustoMaterialMesAnoAtualOrcado, valorCustoMaterialMesAnoAtual,
                    valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtualOrcado, valorLucroBrutoMesAnoAtual);
        }
    }

    public void gravarContaContabilSystextil(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamento = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqOrcamento, cnpjLoja, mesDre, anoDre);

        int codContaContabil = dadosOrcamento.contaContabil;

        if (dreLojaCustom.validaContaContabilSystextil(codContaContabil) == 1 && codContaContabil > 0){

            double valorOrcadoContaContabilMesAnoAtual = Math.abs(dadosOrcamento.valPropriedade);
            double valorLancamentosContaContabilMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(codContaContabil, centroCustoLojaConcat, mesDre, anoDre -1));
            double valorLancamentosContaContabilMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(codContaContabil, centroCustoLojaConcat, mesDre, anoDre));

            gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorLancamentosContaContabilMesAnoAnterior, valorOrcadoContaContabilMesAnoAtual, valorLancamentosContaContabilMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
        }
    }

    public void gravarContaContabilFixo(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat,
                                        double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual,
                                        double valorImpostoFaturamentoMesAnoAnterior, double valorImpostoFaturamentoMesAnoAtualOrcado, double valorImpostoFaturamentoMesAnoAtual,
                                        int qtdPecaFaturadaMesAnoAnterior, int qtdPecaFaturadaMesAnoAtualOrcado, int qtdPecaFaturadaMesAnoAtual,
                                        int qtdPecaConsumoMesAnoAnterior, int qtdPecaConsumoMesAnoAtualOrcado, int qtdPecaConsumoMesAnoAtual,
                                        double valorPrecoMedioMesAnoAnterior, double valorPrecoMedioMesAnoAtualOrcado, double valorPrecoMedioMesAnoAtual,
                                        double valorFaturamentoLiquidoMesAnoAnterior, double valorFaturamentoLiquidoMesAnoAtualOrcado, double valorFaturamentoLiquidoMesAnoAtual,
                                        double valorCustoMaterialMesAnoAnterior, double valorCustoMaterialMesAnoAtualOrcado, double valorCustoMaterialMesAnoAtual,
                                        double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtualOrcado, double valorLucroBrutoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamento = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqOrcamento, cnpjLoja, mesDre, anoDre);

        int codContaContabil = dadosOrcamento.contaContabil;

        if (dreLojaCustom.validaContaContabilSystextil(codContaContabil) == 0 && codContaContabil > 0){
            if (codContaContabil == PECAS_FATURADAS) {
                System.out.println("Gravando PECAS_FATURADAS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, qtdPecaFaturadaMesAnoAnterior, qtdPecaFaturadaMesAnoAtualOrcado, qtdPecaFaturadaMesAnoAtual, 0, 0, 0);

            } else if (codContaContabil == PECAS_CONSUMO) {
                System.out.println("Gravando PECAS_CONSUMO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, qtdPecaConsumoMesAnoAnterior, qtdPecaConsumoMesAnoAtualOrcado, qtdPecaConsumoMesAnoAtual, 0, 0, 0);

            } else if (codContaContabil == PRECO_MEDIO) {
                System.out.println("Gravando PRECO_MEDIO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorPrecoMedioMesAnoAnterior, valorPrecoMedioMesAnoAtualOrcado, valorPrecoMedioMesAnoAtual, 0, 0, 0);

            } else if (codContaContabil == FATURAMENTO) {
                System.out.println("Gravando FATURAMENTO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == IMPOSTOS) {
                System.out.println("Gravando IMPOSTOS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorImpostoFaturamentoMesAnoAnterior, valorImpostoFaturamentoMesAnoAtualOrcado, valorImpostoFaturamentoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == FATURAMENTO_LIQUIDO) {
                System.out.println("Gravando FATURAMENTO_LIQUIDO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorFaturamentoLiquidoMesAnoAnterior, valorFaturamentoLiquidoMesAnoAtualOrcado, valorFaturamentoLiquidoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == CUSTO_DOS_MATERIAIS) {
                System.out.println("Gravando CUSTO_DOS_MATERIAIS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorCustoMaterialMesAnoAnterior, valorCustoMaterialMesAnoAtualOrcado, valorCustoMaterialMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == LUCRO_BRUTO) {
                System.out.println("Gravando LUCRO_BRUTO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtualOrcado, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == GASTOS_VARIAVEIS) {
                System.out.println("Gravando GASTOS_VARIAVEIS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarGastosVariaveis(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == ENCARGOS_COMISSOES) {
                System.out.println("Gravando ENCARGOS_COMISSOES -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarEncargosComissoes(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == TAXA_CAPTURA) {
                System.out.println("Gravando TAXA_CAPTURA -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarTaxaCaptura(seqOrcamento, cnpjLoja, mesDre, anoDre, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == MARGEM_CONTRIBUICAO) {
                System.out.println("Gravando MARGEM_CONTRIBUICAO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarMargemContribuicao(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == GASTOS_FIXOS) {
                System.out.println("Gravando GASTOS_FIXOS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarGastosFixos(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == DESPESAS_FOLHA) {
                System.out.println("Gravando DESPESAS_FOLHA -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarDespesasFolha(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == DESPESAS_GERAIS) {
                System.out.println("Gravando DESPESAS_GERAIS -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarDespesasGerais(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == EBITDA) {
                System.out.println("Gravando EBITDA -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarEbitda(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == CUSTO_ANTECIPACAO) {
                System.out.println("Gravando CUSTO_ANTECIPACAO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarCustoAntecipacao(seqOrcamento, cnpjLoja, mesDre, anoDre, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == RESULTADO_OPERACIONAL) {
                System.out.println("Gravando RESULTADO_OPERACIONAL -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarResultadoOperacional(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == IMPOSTOS_PLANEJAMENTO) {
                System.out.println("Gravando IMPOSTOS_PLANEJAMENTO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarImpostoPlanejamento(seqOrcamento, cnpjLoja, mesDre, anoDre, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == RESULTADO_LIQUIDO) {
                System.out.println("Gravando RESULTADO_LIQUIDO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarResultadoLiquido(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == PONTO_DE_EQUILIBRIO) {
                System.out.println("Gravando PONTO_DE_EQUILIBRIO -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarPontoEquilibrio(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);

            } else if (codContaContabil == CUSTO_COM_PESSOAL) {
                System.out.println("Gravando CUSTO_COM_PESSOAL -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarCustoComPessoal(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
            } else if (codContaContabil == ESTORNO_ENCARGO_COMISSOES) {
                System.out.println("Gravando ESTORNO_ENCARGO_COMISSOES -> Cnpj:" + cnpjLoja + " | Sequência:" + codContaContabil + " | Mês:" + mesDre + " | Ano:" + anoDre);
                gravarEstornoComComissoes(seqOrcamento, cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
            }
        }
    }

    public void gravarEncargosComissoes(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosEncargosComissoes = obterValorCalculadoEncargoComissoes(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorEncargoComissoesMesAnoAnterior =  dadosEncargosComissoes.valPropriedadeMesAnoAnterior;
        double valorOrcadoEncargoComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeOrcadoMesAnoAtual;
        double valorEncargoComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorEncargoComissoesMesAnoAnterior, valorOrcadoEncargoComissoesMesAnoAtual, valorEncargoComissoesMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarTaxaCaptura(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosTaxaCaptura = obterValorCalculadoTaxaCaptura(cnpjLoja, mesDre, anoDre);
        double valorTaxaCapturaMesAnoAnterior =  dadosTaxaCaptura.valPropriedadeMesAnoAnterior;
        double valorOrcadoTaxaCapturaMesAnoAtual =  dadosTaxaCaptura.valPropriedadeOrcadoMesAnoAtual;
        double valorTaxaCapturaMesAnoAtual =  dadosTaxaCaptura.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorTaxaCapturaMesAnoAnterior, valorOrcadoTaxaCapturaMesAnoAtual, valorTaxaCapturaMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarCustoAntecipacao(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosCustoAntecipacao = obterValorCalculadoCustoAntecipacao(cnpjLoja, mesDre, anoDre);
        double valorCustoAntecipacaoMesAnoAnterior =  dadosCustoAntecipacao.valPropriedadeMesAnoAnterior;
        double valorOrcadoCustoAntecipacaoMesAnoAtual =  dadosCustoAntecipacao.valPropriedadeOrcadoMesAnoAtual;
        double valorCustoAntecipacaoMesAnoAtual =  dadosCustoAntecipacao.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorCustoAntecipacaoMesAnoAnterior, valorOrcadoCustoAntecipacaoMesAnoAtual, valorCustoAntecipacaoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarGastosVariaveis(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosGastosVariaveis = obterValorCalculadoGastosVariaveis(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorGastosVariaveisMesAnoAnterior =  dadosGastosVariaveis.valPropriedadeMesAnoAnterior;
        double valorOrcadoGastosVariaveisMesAnoAtual =  dadosGastosVariaveis.valPropriedadeOrcadoMesAnoAtual;
        double valorGastosVariaveisMesAnoAtual =  dadosGastosVariaveis.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorGastosVariaveisMesAnoAnterior, valorOrcadoGastosVariaveisMesAnoAtual, valorGastosVariaveisMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarMargemContribuicao (String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosMargemContribuicao = obterValorCalculadoMargemContribuicao(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorMargemContribuicaoMesAnoAnterior =  dadosMargemContribuicao.valPropriedadeMesAnoAnterior;
        double valorOrcadoMargemContribuicaoMesAnoAtual =  dadosMargemContribuicao.valPropriedadeOrcadoMesAnoAtual;
        double valorMargemContribuicaoMesAnoAtual =  dadosMargemContribuicao.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorMargemContribuicaoMesAnoAnterior, valorOrcadoMargemContribuicaoMesAnoAtual, valorMargemContribuicaoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarDespesasGerais(String seqOrcamento,String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosDespesasGerais = obterValorCalculadoDespesasGerais(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorDespesasGeraisMesAnoAnterior =  dadosDespesasGerais.valPropriedadeMesAnoAnterior;
        double valorOrcadoDespesasGeraisMesAnoAtual =  dadosDespesasGerais.valPropriedadeOrcadoMesAnoAtual;
        double valorDespesasGeraisMesAnoAtual =  dadosDespesasGerais.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorDespesasGeraisMesAnoAnterior, valorOrcadoDespesasGeraisMesAnoAtual, valorDespesasGeraisMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }
    public void gravarDespesasFolha(String seqOrcamento,String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosDespesasFolha = obterValorCalculadoDespesasFolha(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorDespesasFolhaMesAnoAnterior =  dadosDespesasFolha.valPropriedadeMesAnoAnterior;
        double valorOrcadoDespesasFolhaMesAnoAtual =  dadosDespesasFolha.valPropriedadeOrcadoMesAnoAtual;
        double valorDespesasFolhaMesAnoAtual =  dadosDespesasFolha.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorDespesasFolhaMesAnoAnterior, valorOrcadoDespesasFolhaMesAnoAtual, valorDespesasFolhaMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarGastosFixos(String seqOrcamento,String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosGastosFixos = obterValorCalculadoGastosFixos(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorGastosFixosMesAnoAnterior =  dadosGastosFixos.valPropriedadeMesAnoAnterior;
        double valorOrcadoGastosFixosMesAnoAtual =  dadosGastosFixos.valPropriedadeOrcadoMesAnoAtual;
        double valorGastosFixosMesAnoAtual =  dadosGastosFixos.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorGastosFixosMesAnoAnterior, valorOrcadoGastosFixosMesAnoAtual, valorGastosFixosMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarEbitda(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosEbitda = obterValorCalculadoEbitda(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorEbitdaMesAnoAnterior =  dadosEbitda.valPropriedadeMesAnoAnterior;
        double valorOrcadoEbitdaMesAnoAtual =  dadosEbitda.valPropriedadeOrcadoMesAnoAtual;
        double valorEbitdaMesAnoAtual =  dadosEbitda.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorEbitdaMesAnoAnterior, valorOrcadoEbitdaMesAnoAtual, valorEbitdaMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarResultadoOperacional(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosResultadoOperacional = obterValorCalculadoResultadoOperacional(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorResultadoOperacionalMesAnoAnterior =  dadosResultadoOperacional.valPropriedadeMesAnoAnterior;
        double valorOrcadoResultadoOperacionalMesAnoAtual =  dadosResultadoOperacional.valPropriedadeOrcadoMesAnoAtual;
        double valorResultadoOperacionalMesAnoAtual =  dadosResultadoOperacional.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorResultadoOperacionalMesAnoAnterior, valorOrcadoResultadoOperacionalMesAnoAtual, valorResultadoOperacionalMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarImpostoPlanejamento(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosImpostoPlanejamento = obterValorCalculadoImpostoPlanejamento(cnpjLoja, mesDre, anoDre);
        double valorImpostoPlanejamentoMesAnoAnterior =  dadosImpostoPlanejamento.valPropriedadeMesAnoAnterior;
        double valorOrcadoImpostoPlanejamentoMesAnoAtual =  dadosImpostoPlanejamento.valPropriedadeOrcadoMesAnoAtual;
        double valorImpostoPlanejamentoMesAnoAtual =  dadosImpostoPlanejamento.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorImpostoPlanejamentoMesAnoAnterior, valorOrcadoImpostoPlanejamentoMesAnoAtual, valorImpostoPlanejamentoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarResultadoLiquido(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosResultadoLiquido = obterValorCalculadoResultadoLiquido(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorResultadoLiquidoMesAnoAnterior =  dadosResultadoLiquido.valPropriedadeMesAnoAnterior;
        double valorOrcadoResultadoLiquidoMesAnoAtual =  dadosResultadoLiquido.valPropriedadeOrcadoMesAnoAtual;
        double valorResultadoLiquidoMesAnoAtual =  dadosResultadoLiquido.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorResultadoLiquidoMesAnoAnterior, valorOrcadoResultadoLiquidoMesAnoAtual, valorResultadoLiquidoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarPontoEquilibrio(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosPontoEquilibro = obterValorCalculadoPontoDeEquilibro(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtual);
        double valorImpostoPlanejamentoMesAnoAnterior =  dadosPontoEquilibro.valPropriedadeMesAnoAnterior;
        double valorOrcadoImpostoPlanejamentoMesAnoAtual =  dadosPontoEquilibro.valPropriedadeOrcadoMesAnoAtual;
        double valorImpostoPlanejamentoMesAnoAtual =  dadosPontoEquilibro.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorImpostoPlanejamentoMesAnoAnterior, valorOrcadoImpostoPlanejamentoMesAnoAtual, valorImpostoPlanejamentoMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarCustoComPessoal(String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        DreLojaCalculo dadosCustoComPessoal = obterValorCalculadoCustoComPessoal(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);

        double valorCustoComPessoalMesAnoAnterior =  dadosCustoComPessoal.valPropriedadeMesAnoAnterior;
        double valorOrcadoCustoComPessoalMesAnoAtual =  dadosCustoComPessoal.valPropriedadeOrcadoMesAnoAtual;
        double valorCustoComPessoalMesAnoAtual =  dadosCustoComPessoal.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorCustoComPessoalMesAnoAnterior, valorOrcadoCustoComPessoalMesAnoAtual, valorCustoComPessoalMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public void gravarEstornoComComissoes (String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        // Mesmo valor do Encargo Comissões, somente muda o sinal para negativo, e está posicionado dentro de despesas folha (Este valor é subtraido no despesas folha).
        DreLojaCalculo dadosEncargosComissoes = obterValorCalculadoEncargoComissoes(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorEstornoComComissoesMesAnoAnterior =  - dadosEncargosComissoes.valPropriedadeMesAnoAnterior;
        double valorOrcadoEstornoComComissoesMesAnoAtual =  - dadosEncargosComissoes.valPropriedadeOrcadoMesAnoAtual;
        double valorEstornoComComissoesMesAnoAtual =  - dadosEncargosComissoes.valPropriedadeMesAnoAtual;

        gravarContaContabilDre(MESVIGENTE, seqOrcamento, cnpjLoja, mesDre, anoDre, valorEstornoComComissoesMesAnoAnterior, valorOrcadoEstornoComComissoesMesAnoAtual, valorEstornoComComissoesMesAnoAtual, valorFaturamentoMesAnoAnterior, valorFaturamentoMesAnoAtualOrcado, valorFaturamentoMesAnoAtual);
    }

    public DreLojaCalculo obterValorCalculadoEncargoComissoes(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        ParametroGeralDreEntity dadoParametroGeral = dreLojaCustom.findParametrosDreByMesAno(mesDre, anoDre);
        double percentEncargoComissoes = Math.abs(dadoParametroGeral.percEncargos);

        OrcamentoLojaDre dadosOrcamentoComissoes = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(COMISSOES, cnpjLoja, mesDre, anoDre);
        double valorOrcadoComissoesMesAnoAtual = Math.abs(dadosOrcamentoComissoes.valPropriedade);

        double valorLancamentosComissoesMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(COMISSOES, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorLancamentosComissoesMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(COMISSOES, centroCustoLojaConcat, mesDre, anoDre));

        double valorEncargoComissoesMesAnoAnterior = (valorLancamentosComissoesMesAnoAnterior / 100) * percentEncargoComissoes;
        double valorOrcadoEncargoComissoesMesAnoAtual = (valorOrcadoComissoesMesAnoAtual / 100) * percentEncargoComissoes;
        double valorEncargoComissoesMesAnoAtual = (valorLancamentosComissoesMesAnoAtual / 100) * percentEncargoComissoes;

        DreLojaCalculo dadosValorCalculadoEncargoComissoes = new DreLojaCalculo();
        dadosValorCalculadoEncargoComissoes.valPropriedadeMesAnoAnterior = valorEncargoComissoesMesAnoAnterior;
        dadosValorCalculadoEncargoComissoes.valPropriedadeOrcadoMesAnoAtual = valorOrcadoEncargoComissoesMesAnoAtual;
        dadosValorCalculadoEncargoComissoes.valPropriedadeMesAnoAtual = valorEncargoComissoesMesAnoAtual;

        return dadosValorCalculadoEncargoComissoes;
    }

    public DreLojaCalculo obterValorCalculadoResultadoLiquido(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamentoResultadoLiquido = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(RESULTADO_LIQUIDO, cnpjLoja, mesDre, anoDre);
        double valorOrcadoResultadoLiquidoMesAnoAtual = Math.abs(dadosOrcamentoResultadoLiquido.valPropriedade);

        DreLojaCalculo dadosResultadoOperacional = obterValorCalculadoResultadoOperacional(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorResultadoOperacionalMesAnoAnterior =  dadosResultadoOperacional.valPropriedadeMesAnoAnterior;
        double valorResultadoOperacionalMesAnoAtual =  dadosResultadoOperacional.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosImpostoPlanejamento = obterValorCalculadoImpostoPlanejamento(cnpjLoja, mesDre, anoDre);
        double valorImpostoPlanejamentoMesAnoAnterior =  dadosImpostoPlanejamento.valPropriedadeMesAnoAnterior;
        double valorImpostoPlanejamentoMesAnoAtual =  dadosImpostoPlanejamento.valPropriedadeMesAnoAtual;

        double valorResultadoLiquidoMesAnoAnterior = valorResultadoOperacionalMesAnoAnterior + valorImpostoPlanejamentoMesAnoAnterior;
        double valorResultadoLiquidoMesAnoAtual = valorResultadoOperacionalMesAnoAtual + valorImpostoPlanejamentoMesAnoAtual;

        DreLojaCalculo dadosValorCalculadoResultadoLiquido = new DreLojaCalculo();
        dadosValorCalculadoResultadoLiquido.valPropriedadeMesAnoAnterior = valorResultadoLiquidoMesAnoAnterior;
        dadosValorCalculadoResultadoLiquido.valPropriedadeOrcadoMesAnoAtual = valorOrcadoResultadoLiquidoMesAnoAtual;
        dadosValorCalculadoResultadoLiquido.valPropriedadeMesAnoAtual = valorResultadoLiquidoMesAnoAtual;

        return dadosValorCalculadoResultadoLiquido;
    }

    public DreLojaCalculo obterValorCalculadoTaxaCaptura(String cnpjLoja, int mesDre, int anoDre){

        ConciliacaoLojaDre dadosConciliacaoMesAnoAnterior = dreLojaCustom.findConciliacaoLojaDreCnpjMesAno(cnpjLoja, mesDre, anoDre -1);
        double valorTaxaCapturaMesAnoAnterior = Math.abs(dadosConciliacaoMesAnoAnterior.valTaxaCaptura);

        OrcamentoLojaDre dadosOrcamentoTaxaCaptura = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(TAXA_CAPTURA, cnpjLoja, mesDre, anoDre);
        double valorOrcadoTaxaCapturaMesAnoAtual = Math.abs(dadosOrcamentoTaxaCaptura.valPropriedade);

        ConciliacaoLojaDre dadosConciliacaoMesAnoAtual = dreLojaCustom.findConciliacaoLojaDreCnpjMesAno(cnpjLoja, mesDre, anoDre);
        double valorTaxaCapturaMesAnoAtual = Math.abs(dadosConciliacaoMesAnoAtual.valTaxaCaptura);

        DreLojaCalculo dadosValorCalculadoTaxaCaptura = new DreLojaCalculo();
        dadosValorCalculadoTaxaCaptura.valPropriedadeMesAnoAnterior = valorTaxaCapturaMesAnoAnterior;
        dadosValorCalculadoTaxaCaptura.valPropriedadeOrcadoMesAnoAtual = valorOrcadoTaxaCapturaMesAnoAtual;
        dadosValorCalculadoTaxaCaptura.valPropriedadeMesAnoAtual = valorTaxaCapturaMesAnoAtual;

        return dadosValorCalculadoTaxaCaptura;
    }

    public DreLojaCalculo obterValorCalculadoCustoAntecipacao(String cnpjLoja, int mesDre, int anoDre){

        ConciliacaoLojaDre dadosConciliacaoMesAnoAnterior = dreLojaCustom.findConciliacaoLojaDreCnpjMesAno(cnpjLoja, mesDre, anoDre -1);
        double valorCustoAntecipacaoMesAnoAnterior = Math.abs(dadosConciliacaoMesAnoAnterior.valCustoAntecipacao);

        OrcamentoLojaDre dadosOrcamentoCustoAntecipacao = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(CUSTO_ANTECIPACAO, cnpjLoja, mesDre, anoDre);
        double valorOrcadoCustoAntecipacaoMesAnoAtual = Math.abs(dadosOrcamentoCustoAntecipacao.valPropriedade);

        ConciliacaoLojaDre dadosConciliacaoMesAnoAtual = dreLojaCustom.findConciliacaoLojaDreCnpjMesAno(cnpjLoja, mesDre, anoDre);
        double valorCustoAntecipacaoMesAnoAtual = Math.abs(dadosConciliacaoMesAnoAtual.valCustoAntecipacao);

        DreLojaCalculo dadosCustoAntecipacao = new DreLojaCalculo();
        dadosCustoAntecipacao.valPropriedadeMesAnoAnterior = valorCustoAntecipacaoMesAnoAnterior;
        dadosCustoAntecipacao.valPropriedadeOrcadoMesAnoAtual = valorOrcadoCustoAntecipacaoMesAnoAtual;
        dadosCustoAntecipacao.valPropriedadeMesAnoAtual = valorCustoAntecipacaoMesAnoAtual;

        return dadosCustoAntecipacao;
    }

    public DreLojaCalculo obterValorCalculadoGastosVariaveis(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        DreLojaCalculo dadosTaxaCaptura = obterValorCalculadoTaxaCaptura(cnpjLoja, mesDre, anoDre);
        double valorTaxaCapturaMesAnoAnterior =  dadosTaxaCaptura.valPropriedadeMesAnoAnterior;
        double valorTaxaCapturaMesAnoAtual =  dadosTaxaCaptura.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosEncargosComissoes = obterValorCalculadoEncargoComissoes(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorEncargoComissoesMesAnoAnterior =  dadosEncargosComissoes.valPropriedadeMesAnoAnterior;
        double valorEncargoComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeMesAnoAtual;

        String contaContabilConcat = obterContasContabeisSeqOrcamento(GASTOS_VARIAVEIS, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre dadosOrcamentoGastosVariaveis = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(GASTOS_VARIAVEIS, cnpjLoja, mesDre, anoDre);
        double valorLancamentosContaContabilMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorOrcadoGastosVariaveisMesAnoAtual = Math.abs(dadosOrcamentoGastosVariaveis.valPropriedade);
        double valorLancamentosContaContabilMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre));

        double valorTotalLancamentosContaContabilMesAnoAnterior = valorLancamentosContaContabilMesAnoAnterior + valorTaxaCapturaMesAnoAnterior + valorEncargoComissoesMesAnoAnterior;
        double valorTotalLancamentosContaContabilMesAnoAtual = valorLancamentosContaContabilMesAnoAtual + valorTaxaCapturaMesAnoAtual + valorEncargoComissoesMesAnoAtual;

        DreLojaCalculo dadosGastosVariaveis = new DreLojaCalculo();
        dadosGastosVariaveis.valPropriedadeMesAnoAnterior = valorTotalLancamentosContaContabilMesAnoAnterior;
        dadosGastosVariaveis.valPropriedadeOrcadoMesAnoAtual = valorOrcadoGastosVariaveisMesAnoAtual;
        dadosGastosVariaveis.valPropriedadeMesAnoAtual = valorTotalLancamentosContaContabilMesAnoAtual;

        return dadosGastosVariaveis;
    }

    public DreLojaCalculo obterValorCalculadoDespesasFolha(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        String contaContabilConcat = obterContasContabeisSeqOrcamento(DESPESAS_FOLHA, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre dadosOrcamentoDespesasFolha = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(DESPESAS_FOLHA, cnpjLoja, mesDre, anoDre);
        double valorLancamentosContaContabilMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorOrcadoDespesasFolhaMesAnoAtual = Math.abs(dadosOrcamentoDespesasFolha.valPropriedade);
        double valorLancamentosContaContabilMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre));

        DreLojaCalculo dadosEncargosComissoes = obterValorCalculadoEncargoComissoes(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorEstornoComComissoesMesAnoAnterior =  dadosEncargosComissoes.valPropriedadeMesAnoAnterior;
        double valorOrcadoEstornoComComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeOrcadoMesAnoAtual;
        double valorEstornoComComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosDespesasFolha = new DreLojaCalculo();
        dadosDespesasFolha.valPropriedadeMesAnoAnterior = valorLancamentosContaContabilMesAnoAnterior - valorEstornoComComissoesMesAnoAnterior;
        dadosDespesasFolha.valPropriedadeOrcadoMesAnoAtual = valorOrcadoDespesasFolhaMesAnoAtual - valorOrcadoEstornoComComissoesMesAnoAtual;
        dadosDespesasFolha.valPropriedadeMesAnoAtual = valorLancamentosContaContabilMesAnoAtual - valorEstornoComComissoesMesAnoAtual;

        return dadosDespesasFolha;
    }

    public DreLojaCalculo obterValorCalculadoDespesasGerais(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        String contaContabilConcat = obterContasContabeisSeqOrcamento(DESPESAS_GERAIS, cnpjLoja, mesDre, anoDre);
        OrcamentoLojaDre dadosOrcamentoDespesasGerais = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(DESPESAS_GERAIS, cnpjLoja, mesDre, anoDre);
        double valorLancamentosContaContabilMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorOrcadoDespesasGeraisMesAnoAtual = Math.abs(dadosOrcamentoDespesasGerais.valPropriedade);
        double valorLancamentosContaContabilMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContasContabeisMesAno(contaContabilConcat, centroCustoLojaConcat, mesDre, anoDre));

        DreLojaCalculo dadosDespesasGerais = new DreLojaCalculo();
        dadosDespesasGerais.valPropriedadeMesAnoAnterior = valorLancamentosContaContabilMesAnoAnterior;
        dadosDespesasGerais.valPropriedadeOrcadoMesAnoAtual = valorOrcadoDespesasGeraisMesAnoAtual;
        dadosDespesasGerais.valPropriedadeMesAnoAtual = valorLancamentosContaContabilMesAnoAtual;

        return dadosDespesasGerais;
    }

    public DreLojaCalculo obterValorCalculadoGastosFixos(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        OrcamentoLojaDre dadosOrcamentoGastosFixos = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(GASTOS_FIXOS, cnpjLoja, mesDre, anoDre);
        double valorOrcadoGastosFixosMesAnoAtual = Math.abs(dadosOrcamentoGastosFixos.valPropriedade);

        double valorLancamentosCustoOcupacaoMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(CUSTO_OCUPACAO, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorLancamentosCustoOcupacaoMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(CUSTO_OCUPACAO, centroCustoLojaConcat, mesDre, anoDre));

        DreLojaCalculo dadosDespesasFolha = obterValorCalculadoDespesasFolha(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorDespesasFolhaMesAnoAnterior =  dadosDespesasFolha.valPropriedadeMesAnoAnterior;
        double valorDespesasFolhaMesAnoAtual =  dadosDespesasFolha.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosDespesasGerais = obterValorCalculadoDespesasGerais(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorDespesasGeraisMesAnoAnterior =  dadosDespesasGerais.valPropriedadeMesAnoAnterior;
        double valorDespesasGeraisMesAnoAtual =  dadosDespesasGerais.valPropriedadeMesAnoAtual;

        double valorGastosFixosMesAnoAnterior = valorLancamentosCustoOcupacaoMesAnoAnterior + valorDespesasFolhaMesAnoAnterior + valorDespesasGeraisMesAnoAnterior;
        double valorGastosFixosMesAnoAtual = valorLancamentosCustoOcupacaoMesAnoAtual + valorDespesasFolhaMesAnoAtual + valorDespesasGeraisMesAnoAtual;

        DreLojaCalculo dadosGastosFixos = new DreLojaCalculo();
        dadosGastosFixos.valPropriedadeMesAnoAnterior = valorGastosFixosMesAnoAnterior;
        dadosGastosFixos.valPropriedadeOrcadoMesAnoAtual = valorOrcadoGastosFixosMesAnoAtual;
        dadosGastosFixos.valPropriedadeMesAnoAtual = valorGastosFixosMesAnoAtual;

        return dadosGastosFixos;
    }

    public DreLojaCalculo obterValorCalculadoMargemContribuicao(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual){

        DreLojaCalculo dadosGastosVariaveis = obterValorCalculadoGastosVariaveis(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorGastosVariaveisMesAnoAnterior =  dadosGastosVariaveis.valPropriedadeMesAnoAnterior;
        double valorGastosVariaveisMesAnoAtual =  dadosGastosVariaveis.valPropriedadeMesAnoAtual;

        OrcamentoLojaDre dadosOrcamentoMargemContribuicao = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(MARGEM_CONTRIBUICAO, cnpjLoja, mesDre, anoDre);
        double valorOrcadoMargemContribuicaoMesAnoAtual = Math.abs(dadosOrcamentoMargemContribuicao.valPropriedade);

        double valorMargemContribuicaoMesAnoAnterior = valorLucroBrutoMesAnoAnterior - valorGastosVariaveisMesAnoAnterior;
        double valorMargemContribuicaoMesAnoAtual =  valorLucroBrutoMesAnoAtual - valorGastosVariaveisMesAnoAtual;

        DreLojaCalculo dadosMargemContribuicao = new DreLojaCalculo();
        dadosMargemContribuicao.valPropriedadeMesAnoAnterior = valorMargemContribuicaoMesAnoAnterior;
        dadosMargemContribuicao.valPropriedadeOrcadoMesAnoAtual = valorOrcadoMargemContribuicaoMesAnoAtual;
        dadosMargemContribuicao.valPropriedadeMesAnoAtual = valorMargemContribuicaoMesAnoAtual;

        return dadosMargemContribuicao;
    }

    public DreLojaCalculo obterValorCalculadoEbitda(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamentoEbitda = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(EBITDA, cnpjLoja, mesDre, anoDre);
        double valorOrcadoEbitdaMesAnoAtual = Math.abs(dadosOrcamentoEbitda.valPropriedade);

        DreLojaCalculo dadosMargemContribuicao = obterValorCalculadoMargemContribuicao(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorMargemContribuicaoMesAnoAnterior =  dadosMargemContribuicao.valPropriedadeMesAnoAnterior;
        double valorMargemContribuicaoMesAnoAtual =  dadosMargemContribuicao.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosGastosFixos = obterValorCalculadoGastosFixos(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorGastosFixosMesAnoAnterior =  dadosGastosFixos.valPropriedadeMesAnoAnterior;
        double valorGastosFixosMesAnoAtual =  dadosGastosFixos.valPropriedadeMesAnoAtual;

        double valorEbitdaMesAnoAnterior =  valorMargemContribuicaoMesAnoAnterior - valorGastosFixosMesAnoAnterior;
        double valorEbitdaMesAnoAtual =  valorMargemContribuicaoMesAnoAtual - valorGastosFixosMesAnoAtual;

        DreLojaCalculo dadosEbitda = new DreLojaCalculo();
        dadosEbitda.valPropriedadeMesAnoAnterior = valorEbitdaMesAnoAnterior;
        dadosEbitda.valPropriedadeOrcadoMesAnoAtual = valorOrcadoEbitdaMesAnoAtual;
        dadosEbitda.valPropriedadeMesAnoAtual = valorEbitdaMesAnoAtual;

        return dadosEbitda;
    }

    public DreLojaCalculo obterValorCalculadoResultadoOperacional(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamentoResultadoOperacional = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(RESULTADO_OPERACIONAL, cnpjLoja, mesDre, anoDre);
        double valorOrcadoResultadoOperacionalMesAnoAtual = Math.abs(dadosOrcamentoResultadoOperacional.valPropriedade);

        DreLojaCalculo dadosEbitda = obterValorCalculadoEbitda(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorEbitdaMesAnoAnterior =  dadosEbitda.valPropriedadeMesAnoAnterior;
        double valorEbitdaMesAnoAtual =  dadosEbitda.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosCustoAntecipacao = obterValorCalculadoCustoAntecipacao(cnpjLoja, mesDre, anoDre);
        double valorCustoAntecipacaoMesAnoAnterior =  dadosCustoAntecipacao.valPropriedadeMesAnoAnterior;
        double valorCustoAntecipacaoMesAnoAtual =  dadosCustoAntecipacao.valPropriedadeMesAnoAtual;

        double valorLancamentosDepreciacaoMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(DEPRECIACAO, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorLancamentosDepreciacaoMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(DEPRECIACAO, centroCustoLojaConcat, mesDre, anoDre));

        double valorLancamentosAmortizacaoMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(AMORTIZACAO, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorLancamentosAmortizacaoMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(AMORTIZACAO, centroCustoLojaConcat, mesDre, anoDre));

        double valorResultadoOperacionalMesAnoAnterior = valorEbitdaMesAnoAnterior - valorLancamentosAmortizacaoMesAnoAnterior - valorLancamentosDepreciacaoMesAnoAnterior - valorCustoAntecipacaoMesAnoAnterior;
        double valorResultadoOperacionalMesAnoAtual = valorEbitdaMesAnoAtual - valorLancamentosAmortizacaoMesAnoAtual - valorLancamentosDepreciacaoMesAnoAtual - valorCustoAntecipacaoMesAnoAtual;

        DreLojaCalculo dadosResultadoOperacional = new DreLojaCalculo();
        dadosResultadoOperacional.valPropriedadeMesAnoAnterior = valorResultadoOperacionalMesAnoAnterior;
        dadosResultadoOperacional.valPropriedadeOrcadoMesAnoAtual = valorOrcadoResultadoOperacionalMesAnoAtual;
        dadosResultadoOperacional.valPropriedadeMesAnoAtual = valorResultadoOperacionalMesAnoAtual;

        return dadosResultadoOperacional;
    }

    public DreLojaCalculo obterValorCalculadoImpostoPlanejamento(String cnpjLoja, int mesDre, int anoDre){

        ParametroGeralDreEntity dadoParametroGeral = dreLojaCustom.findParametrosDreByMesAno(mesDre, anoDre);
        double valorImpostoPlanejamento = dadoParametroGeral.valImpostoPlanejamento;

        OrcamentoLojaDre dadosOrcamentoResultadoOperacional = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(IMPOSTOS_PLANEJAMENTO, cnpjLoja, mesDre, anoDre);
        double valorOrcadoImpostoPlanejamentoMesAnoAtual = Math.abs(dadosOrcamentoResultadoOperacional.valPropriedade);

        double valorFaturamentoGeralMesAnoAnterior = dreLojaCustom.obterValorTotalFaturamentoMesAno(mesDre, anoDre -1, null);
        double valorFaturamentoGeralMesAnoAtual = dreLojaCustom.obterValorTotalFaturamentoMesAno(mesDre, anoDre, null);

        double valorFaturamentoLojaMesAnoAnterior = dreLojaCustom.obterValorTotalFaturamentoMesAno(mesDre, anoDre -1, cnpjLoja);
        double valorFaturamentoLojaMesAnoAtual = dreLojaCustom.obterValorTotalFaturamentoMesAno(mesDre, anoDre, cnpjLoja);

        double percFaturamentoLojaMesAnoAnterior = (valorFaturamentoLojaMesAnoAnterior / valorFaturamentoGeralMesAnoAnterior) * 100;
        double percFaturamentoLojaMesAnoAtual = (valorFaturamentoLojaMesAnoAtual / valorFaturamentoGeralMesAnoAtual) * 100;

        double valorImpostoPlanejamentoMesAnoAnterior = (valorImpostoPlanejamento * percFaturamentoLojaMesAnoAnterior) / 100;
        double valorImpostoPlanejamentoMesAnoAtual = (valorImpostoPlanejamento * percFaturamentoLojaMesAnoAtual) / 100;

        DreLojaCalculo dadosImpostoPlanejamento = new DreLojaCalculo();
        dadosImpostoPlanejamento.valPropriedadeMesAnoAnterior = valorImpostoPlanejamentoMesAnoAnterior;
        dadosImpostoPlanejamento.valPropriedadeOrcadoMesAnoAtual = valorOrcadoImpostoPlanejamentoMesAnoAtual;
        dadosImpostoPlanejamento.valPropriedadeMesAnoAtual = valorImpostoPlanejamentoMesAnoAtual;

        return dadosImpostoPlanejamento;
    }

    public DreLojaCalculo obterValorCalculadoPontoDeEquilibro(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat, double valorLucroBrutoMesAnoAnterior, double valorLucroBrutoMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamentoResultadoOperacional = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(PONTO_DE_EQUILIBRIO, cnpjLoja, mesDre, anoDre);
        double valorOrcadoPontoEquilibrioMesAnoAtual = Math.abs(dadosOrcamentoResultadoOperacional.valPropriedade);

        DreLojaCalculo dadosMargemContribuicao = obterValorCalculadoMargemContribuicao(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat, valorLucroBrutoMesAnoAnterior, valorLucroBrutoMesAnoAtual);
        double valorMargemContribuicaoMesAnoAnterior =  dadosMargemContribuicao.valPropriedadeMesAnoAnterior;
        double valorMargemContribuicaoMesAnoAtual =  dadosMargemContribuicao.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosGastosFixos = obterValorCalculadoGastosFixos(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorGastosFixosMesAnoAnterior =  dadosGastosFixos.valPropriedadeMesAnoAnterior;
        double valorGastosFixosMesAnoAtual =  dadosGastosFixos.valPropriedadeMesAnoAtual;

        double percMargemContribuicaoMesAnoAnterior = calcularPercentual(valorMargemContribuicaoMesAnoAnterior, valorFaturamentoMesAnoAnterior);
        double percMargemContribuicaoMesAnoAtual = calcularPercentual(valorMargemContribuicaoMesAnoAtual, valorFaturamentoMesAnoAtual);

        double valorPontoEquilibrioMesAnoAnterior = (valorGastosFixosMesAnoAnterior / percMargemContribuicaoMesAnoAnterior) * 100;
        double valorPontoEquilibrioMesAnoAtual = (valorGastosFixosMesAnoAtual / percMargemContribuicaoMesAnoAtual) * 100;

        DreLojaCalculo dadosPontoEquilibrio = new DreLojaCalculo();
        dadosPontoEquilibrio.valPropriedadeMesAnoAnterior = valorPontoEquilibrioMesAnoAnterior;
        dadosPontoEquilibrio.valPropriedadeOrcadoMesAnoAtual = valorOrcadoPontoEquilibrioMesAnoAtual;
        dadosPontoEquilibrio.valPropriedadeMesAnoAtual = valorPontoEquilibrioMesAnoAtual;

        return dadosPontoEquilibrio;
    }

    public DreLojaCalculo obterValorCalculadoCustoComPessoal(String cnpjLoja, int mesDre, int anoDre, String centroCustoLojaConcat){

        OrcamentoLojaDre dadosOrcamentoCustoComPessoal = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(CUSTO_COM_PESSOAL, cnpjLoja, mesDre, anoDre);
        double valorOrcadoCustoComPessoalMesAnoAtual = Math.abs(dadosOrcamentoCustoComPessoal.valPropriedade);

        double valorLancamentosComissoesMesAnoAnterior = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(COMISSOES, centroCustoLojaConcat, mesDre, anoDre -1));
        double valorLancamentosComissoesMesAnoAtual = Math.abs(dreLojaCustom.obterValorLancamentosContaContabilMesAno(COMISSOES, centroCustoLojaConcat, mesDre, anoDre));

        DreLojaCalculo dadosEncargosComissoes = obterValorCalculadoEncargoComissoes(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorEncargoComissoesMesAnoAnterior =  dadosEncargosComissoes.valPropriedadeMesAnoAnterior;
        double valorEncargoComissoesMesAnoAtual =  dadosEncargosComissoes.valPropriedadeMesAnoAtual;

        DreLojaCalculo dadosDespesasFolha = obterValorCalculadoDespesasFolha(cnpjLoja, mesDre, anoDre, centroCustoLojaConcat);
        double valorDespesasFolhaMesAnoAnterior =  dadosDespesasFolha.valPropriedadeMesAnoAnterior;
        double valorDespesasFolhaMesAnoAtual =  dadosDespesasFolha.valPropriedadeMesAnoAtual;

        double valorCustoComPessoalMesAnoAnterior = valorLancamentosComissoesMesAnoAnterior + valorEncargoComissoesMesAnoAnterior + valorDespesasFolhaMesAnoAnterior;
        double valorCustoComPessoalMesAnoAtual = valorLancamentosComissoesMesAnoAtual + valorEncargoComissoesMesAnoAtual + valorDespesasFolhaMesAnoAtual;

        DreLojaCalculo dadosPontoEquilibrio = new DreLojaCalculo();
        dadosPontoEquilibrio.valPropriedadeMesAnoAnterior = valorCustoComPessoalMesAnoAnterior;
        dadosPontoEquilibrio.valPropriedadeOrcadoMesAnoAtual = valorOrcadoCustoComPessoalMesAnoAtual;
        dadosPontoEquilibrio.valPropriedadeMesAnoAtual = valorCustoComPessoalMesAnoAtual;

        return dadosPontoEquilibrio;
    }

    public void gravarContaContabilDre(int tipoDre, String seqOrcamento, String cnpjLoja, int mesDre, int anoDre, double valorLancamentosMesAnoAnterior, double valorOrcadoMesAnoAtual, double valorLancamentosMesAnoAtual, double valorFaturamentoMesAnoAnterior, double valorFaturamentoMesAnoAtualOrcado, double valorFaturamentoMesAnoAtual){

        OrcamentoLojaDre dadosOrcamento = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqOrcamento, cnpjLoja, mesDre, anoDre);
        String propriedade = dadosOrcamento.propriedade;
        int contaContabil = dadosOrcamento.contaContabil;

        DreLojaEntity contaContabilDre = new DreLojaEntity();
        contaContabilDre.id = dreLojaRepository.findNextId();
        contaContabilDre.tipoDre = tipoDre;
        contaContabilDre.seqConsulta = seqOrcamento;
        contaContabilDre.cnpjLoja = cnpjLoja;
        contaContabilDre.mesDre = mesDre;
        contaContabilDre.anoDre = anoDre;
        contaContabilDre.propriedade = propriedade;

        contaContabilDre.valRealAnoAnt = Math.round(valorLancamentosMesAnoAnterior);
        contaContabilDre.valOrcado = Math.round(valorOrcadoMesAnoAtual);
        contaContabilDre.valReal = Math.round(valorLancamentosMesAnoAtual);
        contaContabilDre.valDiferencaOrcadoReal = Math.round(Math.abs(valorOrcadoMesAnoAtual - valorLancamentosMesAnoAtual));

        if (contaContabil == PRECO_MEDIO){

            DecimalFormat df = new DecimalFormat("#,##0.00");

            double valorLancamentosMesAnoAnteriorFormat = Double.parseDouble(df.format(valorLancamentosMesAnoAnterior).replace(",", "."));
            double valorLancamentosMesAnoAtualFormat = Double.parseDouble(df.format(valorLancamentosMesAnoAtual).replace(",", "."));

            contaContabilDre.valRealAnoAnt = valorLancamentosMesAnoAnteriorFormat;
            contaContabilDre.valOrcado = valorOrcadoMesAnoAtual;
            contaContabilDre.valReal = valorLancamentosMesAnoAtualFormat;
            contaContabilDre.valDiferencaOrcadoReal = Math.abs(valorOrcadoMesAnoAtual - valorLancamentosMesAnoAtualFormat);
        }

        if (valorFaturamentoMesAnoAnterior != 0) contaContabilDre.percRealAnoAnt = calcularPercentual(valorLancamentosMesAnoAnterior, valorFaturamentoMesAnoAnterior);
        if (valorFaturamentoMesAnoAtualOrcado != 0) contaContabilDre.percOrcado = calcularPercentual(valorOrcadoMesAnoAtual, valorFaturamentoMesAnoAtualOrcado);
        if (valorFaturamentoMesAnoAtual != 0) contaContabilDre.percReal = calcularPercentual(valorLancamentosMesAnoAtual, valorFaturamentoMesAnoAtual);
        if (valorOrcadoMesAnoAtual != 0) contaContabilDre.percDiferencaOrcadoReal = calcularPercentual(valorLancamentosMesAnoAtual, valorOrcadoMesAnoAtual);
        if (valorLancamentosMesAnoAnterior != 0) contaContabilDre.percDiferencaRealVigAnt = calcularPercentual(valorLancamentosMesAnoAtual, valorLancamentosMesAnoAnterior);

        dreLojaRepository.saveAndFlush(contaContabilDre);
    }


    public String converterListaNumericaEmString (List<ConteudoChaveNumerica> list){

        String centroCustoLojaConcat = "";

        for(ConteudoChaveNumerica item : list){
            if (list.get(0) == item){
                centroCustoLojaConcat = Integer.toString(item.value);
            } else {
                centroCustoLojaConcat += "," + Integer.toString(item.value);
            }
        }
        return centroCustoLojaConcat;
    }

    public double calcularPercentual(double valor, double valorTotal) {

        double percentual = (valor / valorTotal) * 100;
        percentual = arredondarPercentual(percentual);

        return percentual;
    }

    public static List<String> encontrarSubitens(List<String> sequencias, String codSequencia) {
        List<String> subItens = new ArrayList<>();

        for (String item : sequencias) {
            if (item.startsWith(codSequencia + ".")) {
                subItens.add(item);
            }
        }

        return subItens;
    }

    public String obterContasContabeisSeqOrcamento(int contaContabil, String cnpjLoja, int mesDre, int anoDre){

        OrcamentoLojaDre dadosOrcamento = orcamentoLojaDreCustom.findOrcamentoByContaContabilCnpjMesAno(contaContabil, cnpjLoja, mesDre, anoDre);
        String seqOrcamento = dadosOrcamento.seqConsulta;

        List<String> sequenciasOrcamentoList = orcamentoLojaDreCustom.findAllSequenciaOrcamento(cnpjLoja, mesDre, anoDre);
        List<String> subItens = encontrarSubitens(sequenciasOrcamentoList, seqOrcamento);

        int codContaContabil = 0;
        String contaContabilConcat = "";

        for (String seqOrcamentoSubItem : subItens){

            OrcamentoLojaDre dadosOrcamentoSeq = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqOrcamentoSubItem, cnpjLoja, mesDre, anoDre);
            codContaContabil = dadosOrcamentoSeq.contaContabil;

            if (subItens.get(0) == seqOrcamentoSubItem){
                contaContabilConcat = Integer.toString(codContaContabil);
            } else {
                contaContabilConcat += "," + Integer.toString(codContaContabil);
            }
        }
        return contaContabilConcat;
    }

    public double arredondarPercentual(double valor) {
        double valorArredondado = Math.round(valor * 10);
        return valorArredondado / 10.0;
    }


    public String gerarPdfDre(String cnpjLoja, String nomeLoja, int mesDre, int anoDre) throws JRException, FileNotFoundException {

        String nomeRelatorioGerado = "";

        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        String descrMesDre = meses[mesDre - 1];

        List<DreLojaPdf> dreLojaPdfList = estruturarObjetoDrePdf(cnpjLoja, mesDre, anoDre);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dreLojaPdfList);

        Map<String, Object> parameters = setParameters(cnpjLoja, nomeLoja, mesDre, descrMesDre, anoDre);

        String descricaoPdf = "Dre-" + mesDre + "-" + anoDre + "-" + nomeLoja;

        nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "dreLoja", parameters, descricaoPdf, false);

        return nomeRelatorioGerado;
    }

    public Map<String, Object> setParameters(String cnpjLoja, String nomeLoja, int mesDre, String descrMesDre, int anoDre) {


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cnpjLoja", cnpjLoja);
        parameters.put("nomeLoja", nomeLoja);
        parameters.put("mesDre", mesDre);
        parameters.put("mesDreFormatado", descrMesDre);
        parameters.put("anoDre", anoDre);
        parameters.put("anoAntDre", anoDre - 1);

        return parameters;
    }

    List<DreLojaPdf> estruturarObjetoDrePdf(String cnpjLoja, int mesDre, int anoDre){

        List<DreLoja> dreLojaListMes = dreLojaCustom.obterCamposDreLojaCnpjMesAnVigente(cnpjLoja, mesDre, anoDre);
        List<DreLojaPdf> dreLojaPdfList = new ArrayList<>();

        for (DreLoja dreLoja : dreLojaListMes){

            String seqConsulta = dreLoja.seqConsulta;

            DreLoja dreLojaAcumulado = dreLojaCustom.obterDadosDreAcumuladoLojaCnpjMesSeq(seqConsulta, cnpjLoja, mesDre, anoDre);

            int contaContabil = 0;

            OrcamentoLojaDre dadosOrcamentoSequencia = orcamentoLojaDreCustom.findOrcamentoBySeqCnpjMesAno(seqConsulta, cnpjLoja, mesDre, anoDre);

            contaContabil = dadosOrcamentoSequencia.contaContabil;

            DreLojaPdf dreLojaPdf = new DreLojaPdf();
            dreLojaPdf.seqConsulta = dreLoja.seqConsulta;
            dreLojaPdf.cnpjLoja = dreLoja.cnpjLoja;
            dreLojaPdf.anoDre = dreLoja.anoDre;
            dreLojaPdf.mesDre = dreLoja.mesDre;
            dreLojaPdf.propriedade = dreLoja.propriedade;
            dreLojaPdf.valRealAnoAnt =  converterValorPdf(dreLoja.valRealAnoAnt, contaContabil);
            dreLojaPdf.percRealAnoAnt = dreLoja.percRealAnoAnt + "%";
            dreLojaPdf.valOrcado = converterValorPdf(dreLoja.valOrcado, contaContabil);
            dreLojaPdf.percOrcado = dreLoja.percOrcado + "%";
            dreLojaPdf.valReal = converterValorPdf(dreLoja.valReal, contaContabil);
            dreLojaPdf.percReal = dreLoja.percReal + "%";
            dreLojaPdf.valDiferencaOrcadoReal = converterValorPdf(dreLoja.valDiferencaOrcadoReal, contaContabil);
            dreLojaPdf.percDiferencaOrcadoReal = dreLoja.percDiferencaOrcadoReal + "%";
            dreLojaPdf.percDiferencaRealVigAnt = dreLoja.percDiferencaRealVigAnt + "%";
            dreLojaPdf.valRealAnoAntAcu = converterValorPdf(dreLojaAcumulado.valRealAnoAnt, contaContabil);
            dreLojaPdf.percRealAnoAntAcu = dreLojaAcumulado.percRealAnoAnt + "%";
            dreLojaPdf.valOrcadoAcu = converterValorPdf(dreLojaAcumulado.valOrcado, contaContabil);
            dreLojaPdf.percOrcadoAcu = dreLojaAcumulado.percOrcado + "%";
            dreLojaPdf.valRealAcu = converterValorPdf(dreLojaAcumulado.valReal, contaContabil);
            dreLojaPdf.percRealAcu = dreLojaAcumulado.percReal + "%";
            dreLojaPdf.valDiferencaOrcadoRealAcu = converterValorPdf(dreLojaAcumulado.valDiferencaOrcadoReal, contaContabil);
            dreLojaPdf.percDiferencaOrcadoRealAcu = dreLojaAcumulado.percDiferencaOrcadoReal + "%";
            dreLojaPdf.percDiferencaRealVigAntAcu = dreLojaAcumulado.percDiferencaRealVigAnt + "%";
            dreLojaPdfList.add(dreLojaPdf);
        }

        OrdenaRegistrosPdf(dreLojaPdfList);

        return dreLojaPdfList;
    }

    public LancamentoLojaMesAno obterDadosLancamentoLojaMesAno(String cnpjLoja, int mesLancamento, int anoLancamento){

        LancamentoLojaMesAno lancamentoLojaMesAnoMicrovix = dreLojaCustom.obterDadosLancamentoLojaMesAnoMicrovix(cnpjLoja, mesLancamento, anoLancamento);
        LancamentoLojaMesAno lancamentoLojaMesAnoCigam = obterDadosLancamentoLojaMesAnoCigam(cnpjLoja, mesLancamento, anoLancamento);

        LancamentoLojaMesAno lancamentoLojaMesAnoCalculado = new LancamentoLojaMesAno();
        lancamentoLojaMesAnoCalculado.qtdPecaFaturada = lancamentoLojaMesAnoMicrovix.qtdPecaFaturada + lancamentoLojaMesAnoCigam.qtdPecaFaturada;
        lancamentoLojaMesAnoCalculado.valFaturamento = lancamentoLojaMesAnoMicrovix.valFaturamento + lancamentoLojaMesAnoCigam.valFaturamento;
        lancamentoLojaMesAnoCalculado.valImpostoFaturamento = lancamentoLojaMesAnoMicrovix.valImpostoFaturamento + lancamentoLojaMesAnoCigam.valImpostoFaturamento;
        lancamentoLojaMesAnoCalculado.qtdPecaConsumo = lancamentoLojaMesAnoMicrovix.qtdPecaConsumo + lancamentoLojaMesAnoCigam.qtdPecaConsumo;

        return lancamentoLojaMesAnoCalculado;
    }

    public LancamentoLojaMesAno obterDadosLancamentoLojaMesAnoCigam(String cnpjLoja, int mesLancamento, int anoLancamento){

        LancamentoLojaMesAno lancamentoLojaMesAnoVenda = dreLojaCustom.obterDadosLancamentoLojaMesAnoVendaCigam(cnpjLoja, mesLancamento, anoLancamento);
        LancamentoLojaMesAno lancamentoLojaMesAnoCompra = dreLojaCustom.obterDadosLancamentoLojaMesAnoCompraCigam(cnpjLoja, mesLancamento, anoLancamento);
        int pecaConsumo = dreLojaCustom.obterDadosLancamentoLojaMesAnoPecaConsumoCigam(cnpjLoja, mesLancamento, anoLancamento);

        LancamentoLojaMesAno lancamentoLojaMesAnoCalculado = new LancamentoLojaMesAno();
        lancamentoLojaMesAnoCalculado.qtdPecaFaturada = lancamentoLojaMesAnoVenda.qtdPecaFaturada - lancamentoLojaMesAnoCompra.qtdPecaFaturada;
        lancamentoLojaMesAnoCalculado.valFaturamento = lancamentoLojaMesAnoVenda.valFaturamento - lancamentoLojaMesAnoCompra.valFaturamento;
        lancamentoLojaMesAnoCalculado.valImpostoFaturamento = lancamentoLojaMesAnoVenda.valImpostoFaturamento - lancamentoLojaMesAnoCompra.valImpostoFaturamento;
        lancamentoLojaMesAnoCalculado.qtdPecaConsumo = pecaConsumo;

        return lancamentoLojaMesAnoCalculado;
    }

    public List<DreLojaPdf> OrdenaRegistrosPdf(List<DreLojaPdf> dreLojaPdfList){

        Comparator<DreLojaPdf> comparator = new Comparator<DreLojaPdf>() {
            @Override
            public int compare(DreLojaPdf o1, DreLojaPdf o2) {
                String[] s1Array = o1.getSeqConsulta().split("\\.");
                String[] s2Array = o2.getSeqConsulta().split("\\.");
                int length = Math.min(s1Array.length, s2Array.length);
                for (int i = 0; i < length; i++) {
                    int s1Part = Integer.parseInt(s1Array[i]);
                    int s2Part = Integer.parseInt(s2Array[i]);
                    if (s1Part != s2Part) {
                        return s1Part - s2Part;
                    }
                }
                return s1Array.length - s2Array.length;
            }
        };

        Collections.sort(dreLojaPdfList, comparator);

        return dreLojaPdfList;
    }

    public Comparator<DreLojaPdf> comparator = new Comparator<DreLojaPdf>() {
        @Override
        public int compare(DreLojaPdf o1, DreLojaPdf o2) {
            String[] s1Array = o1.getSeqConsulta().split("\\.");
            String[] s2Array = o2.getSeqConsulta().split("\\.");
            int length = Math.min(s1Array.length, s2Array.length);
            for (int i = 0; i < length; i++) {
                int s1Part = Integer.parseInt(s1Array[i]);
                int s2Part = Integer.parseInt(s2Array[i]);
                if (s1Part != s2Part) {
                    return s1Part - s2Part;
                }
            }
            return s1Array.length - s2Array.length;
        }
    };

    public String converterValorPdf(Double valor, int contaContabil){

        String valorFormatado = (valor >= 1000 ? String.format("%.3f", valor / 1000.0).replace(",", ".") : String.valueOf((int) Math.round(valor)));

        if (contaContabil == PRECO_MEDIO){
            valorFormatado = Double.toString(valor);
        }

        return valorFormatado;
    }
}