package br.com.live.body;

import br.com.live.model.ConsultaChamado;

import java.util.Date;
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
    public String dataChamado;
    public String nomeRequerente;
    public String dataAnalise;
    public String dataEntregaDes;
    public String dataEntregaUsuario;

    public List<ConsultaChamado> listChamados;
    public String dataInicio;
    public String dataFim;
}
