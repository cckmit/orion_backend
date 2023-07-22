package br.com.live.jobs;

import br.com.live.integracao.cigam.service.*;
import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JobIntegracaoCigam {
    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int ATIVO = 1;

    EnvioXmlCigamService envioXmlCigamService;
    RetornoMovimentosCigamService retornoMovimentosCigamService;
    RetornoDuplicatasCigamService retornoDuplicatasCigamService;
    RetornoClienteFornecedorCigamService retornoClienteFornecedorCigamService;
    RetornoFuncionariosCigamService retornoFuncionariosCigamService;
    RetornoEstoqueCigamService retornoEstoqueCigamService;
    ParametrosRepository parametrosRepository;

    public JobIntegracaoCigam(EnvioXmlCigamService envioXmlCigamService, RetornoMovimentosCigamService retornoMovimentosCigamService,
                              ParametrosRepository parametrosRepository, RetornoDuplicatasCigamService retornoDuplicatasCigamService,
                              RetornoClienteFornecedorCigamService retornoClienteFornecedorCigamService, RetornoFuncionariosCigamService retornoFuncionariosCigamService,
                              RetornoEstoqueCigamService retornoEstoqueCigamService) {
        this.envioXmlCigamService = envioXmlCigamService;
        this.retornoMovimentosCigamService = retornoMovimentosCigamService;
        this.parametrosRepository = parametrosRepository;
        this.retornoDuplicatasCigamService = retornoDuplicatasCigamService;
        this.retornoClienteFornecedorCigamService = retornoClienteFornecedorCigamService;
        this.retornoFuncionariosCigamService = retornoFuncionariosCigamService;
        this.retornoEstoqueCigamService = retornoEstoqueCigamService;
    }

    // XML NOTAS
    @Scheduled(fixedRate = MINUTO * 10)
    public void enviarXmlNotasCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            envioXmlCigamService.enviarNotas();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // MOVIMENTOS
    @Scheduled(fixedRate = MINUTO * 10)
    public void retornoMovimentosCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoMovimentosCigamService.gravarMovimentos();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // DUPLICATAS
    @Scheduled(fixedRate = MINUTO * 10)
    public void retornoDuplicatasCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoDuplicatasCigamService.gravarDadosRetornoDuplicatas();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // CLIENTE / FORNECEDOR
    @Scheduled(fixedRate = MINUTO * 10)
    public void retornoCliForCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoClienteFornecedorCigamService.gravarDadosRetornoClienteFornecedor();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // FUNCIONÁRIOS
    @Scheduled(fixedRate = MINUTO * 10)
    public void retornoFuncionariosCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoFuncionariosCigamService.gravarDadosRetornoFuncionarios();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // ESTOQUES
    @Scheduled(cron = "0 0 6 * * *") // Roda as 06h da manha
    public void retornoEstoquesCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoEstoqueCigamService.gravarDadosRetornoEstoque();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }

    // MOVIMENTOS RETROATIVOS 40 DIAS
    @Scheduled(cron = "0 0 2 * * *") // Roda as 02h da manha
    public void retornoMovimentosRetroativosCigam() throws IOException {
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_CIGAM_ATIVO");

        if (params.valorInt == ATIVO) {
            retornoMovimentosCigamService.gravarMovimentosRetroativos();
        } else {
            System.out.println("INTEGRAÇÃO CIGAM INATIVA!");
        }
    }
}
