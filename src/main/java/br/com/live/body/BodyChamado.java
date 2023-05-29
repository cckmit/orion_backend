package br.com.live.body;

import br.com.live.model.ConsultaChamado;

import java.util.List;

public class BodyChamado {
    public int codChamado;
    public String tituloChamado;
    public int codTecnico;
    public int codArea;
    public int codSetor;
    public int codDepartamento;
    public int impacto;
    public String descricaoChamado;
    public String nomeRequerente;
    public String dataInicioTriagem;
    public String dataFimTriagem;
    public String dataInicioAnalise;
    public String dataFimAnalise;
    public String dataInicioAprovEscopo;
    public String dataFimAprovEscopo;
    public String dataInicioOrcamento;
    public String dataFimOrcamento;
    public String dataInicioFilaDesenvForn;
    public String dataFimFilaDesenvForn;
    public String dataInicioDesenvForn;
    public String dataFimDesenvForn;
    public String dataInicioFilaDesenvInt;
    public String dataFimFilaDesenvInt;
    public String dataInicioDesenvInt;
    public String dataFimDesenvInt;
    public String dataInicioQualidadeTestes;
    public String dataFimQualidadeTestes;
    public String dataInicioEntrega;
    public String dataFimEntrega;

    public List<ConsultaChamado> listChamados;
    public String dataInicio;
    public String dataFim;
}
