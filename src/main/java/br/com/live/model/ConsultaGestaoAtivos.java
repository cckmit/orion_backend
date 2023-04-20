package br.com.live.model;


public class ConsultaGestaoAtivos {
	
    public int id;
    public String nomeServidor;
    public boolean maquinaFisica;
    public String sistemaOperacional;
    public String ip;
    public String hd;
    public String memoria;
    public String processador;
    public String aplicacoes;
    public String status;
    public int numOportunidades;
    
    public String nomeSistema;
    public String objetivo;
    public String bancoDeDados;
    public String tipo;
    public String fornecedor;
    public String cnpj;
    public String endereco;
    public String formaPagto;
    public boolean temContrato;
    public String ambiente;
    public int usuariosAtivos;
    public int capacidadeUsuarios;
    public String nomeIntegracao;
    public String tipoIntegracao;
    public String tipoConexao;
    public int sistemaOrigem;
    public int sistemaDestino;
    public int servidor;
    public String nomeServico;
    public String timeResponsavel;
    public boolean disponibilidade;
    public String tecnicosFornecedores;
    
    public String gestorResponsavel;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeServidor() {
		return nomeServidor;
	}
	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}
	public boolean isMaquinaFisica() {
		return maquinaFisica;
	}
	public void setMaquinaFisica(boolean maquinaFisica) {
		this.maquinaFisica = maquinaFisica;
	}
	public String getSistemaOperacional() {
		return sistemaOperacional;
	}
	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHd() {
		return hd;
	}
	public void setHd(String hd) {
		this.hd = hd;
	}
	public String getMemoria() {
		return memoria;
	}
	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}
	public String getProcessador() {
		return processador;
	}
	public void setProcessador(String processador) {
		this.processador = processador;
	}
	public String getAplicacoes() {
		return aplicacoes;
	}
	public void setAplicacoes(String aplicacoes) {
		this.aplicacoes = aplicacoes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNumOportunidades() {
		return numOportunidades;
	}
	public void setNumOportunidades(int numOportunidades) {
		this.numOportunidades = numOportunidades;
	}    
    public String getNomeSistema() {
		return nomeSistema;
	}
	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getBancoDeDados() {
		return bancoDeDados;
	}
	public void setBancoDeDados(String bancoDeDados) {
		this.bancoDeDados = bancoDeDados;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getFormaPagto() {
		return formaPagto;
	}
	public void setFormaPagto(String formaPagto) {
		this.formaPagto = formaPagto;
	}
	public boolean isTemContrato() {
		return temContrato;
	}
	public void setTemContrato(boolean temContrato) {
		this.temContrato = temContrato;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public int getUsuariosAtivos() {
		return usuariosAtivos;
	}
	public void setUsuariosAtivos(int usuariosAtivos) {
		this.usuariosAtivos = usuariosAtivos;
	}
	public int getCapacidadeUsuarios() {
		return capacidadeUsuarios;
	}
	public void setCapacidadeUsuarios(int capacidadeUsuarios) {
		this.capacidadeUsuarios = capacidadeUsuarios;
	}
	public String getNomeIntegracao() {
		return nomeIntegracao;
	}
	public void setNomeIntegracao(String nomeIntegracao) {
		this.nomeIntegracao = nomeIntegracao;
	}
	public String getTipoIntegracao() {
		return tipoIntegracao;
	}
	public void setTipoIntegracao(String tipoIntegracao) {
		this.tipoIntegracao = tipoIntegracao;
	}
	public String getTipoConexao() {
		return tipoConexao;
	}
	public void setTipoConexao(String tipoConexao) {
		this.tipoConexao = tipoConexao;
	}
	public int getSistemaOrigem() {
		return sistemaOrigem;
	}
	public void setSistemaOrigem(int sistemaOrigem) {
		this.sistemaOrigem = sistemaOrigem;
	}
	public int getSistemaDestino() {
		return sistemaDestino;
	}
	public void setSistemaDestino(int sistemaDestino) {
		this.sistemaDestino = sistemaDestino;
	}
	public int getServidor() {
		return servidor;
	}
	public void setServidor(int servidor) {
		this.servidor = servidor;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public String getTimeResponsavel() {
		return timeResponsavel;
	}
	public void setTimeResponsavel(String timeResponsavel) {
		this.timeResponsavel = timeResponsavel;
	}
	public boolean isDisponibilidade() {
		return disponibilidade;
	}
	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	public String getTecnicosFornecedores() {
		return tecnicosFornecedores;
	}
	public void setTecnicosFornecedores(String tecnicosFornecedores) {
		this.tecnicosFornecedores = tecnicosFornecedores;
	}
	public String getGestorResponsavel() {
		return gestorResponsavel;
	}
	public void setGestorResponsavel(String gestorResponsavel) {
		this.gestorResponsavel = gestorResponsavel;
	}
	public ConsultaGestaoAtivos() {
    	
    }
	public ConsultaGestaoAtivos(int id, String nomeServidor, boolean maquinaFisica, String sistemaOperacional,
			String ip, String hd, String memoria, String processador, String aplicacoes, String status,
			int numOportunidades, String nomeSistema, String objetivo, String bancoDeDados, String tipo,
			String fornecedor, String cnpj, String endereco, String formaPagto, boolean temContrato, String ambiente,
			int usuariosAtivos, int capacidadeUsuarios, String nomeIntegracao, String tipoIntegracao,
			String tipoConexao, int sistemaOrigem, int sistemaDestino, int servidor, String nomeServico,
			String timeResponsavel, boolean disponibilidade, String tecnicosFornecedores, String gestorResponsavel) {
		
		this.id = id;
		this.nomeServidor = nomeServidor;
		this.maquinaFisica = maquinaFisica;
		this.sistemaOperacional = sistemaOperacional;
		this.ip = ip;
		this.hd = hd;
		this.memoria = memoria;
		this.processador = processador;
		this.aplicacoes = aplicacoes;
		this.status = status;
		this.numOportunidades = numOportunidades;
		this.nomeSistema = nomeSistema;
		this.objetivo = objetivo;
		this.bancoDeDados = bancoDeDados;
		this.tipo = tipo;
		this.fornecedor = fornecedor;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.formaPagto = formaPagto;
		this.temContrato = temContrato;
		this.ambiente = ambiente;
		this.usuariosAtivos = usuariosAtivos;
		this.capacidadeUsuarios = capacidadeUsuarios;
		this.nomeIntegracao = nomeIntegracao;
		this.tipoIntegracao = tipoIntegracao;
		this.tipoConexao = tipoConexao;
		this.sistemaOrigem = sistemaOrigem;
		this.sistemaDestino = sistemaDestino;
		this.servidor = servidor;
		this.nomeServico = nomeServico;
		this.timeResponsavel = timeResponsavel;
		this.disponibilidade = disponibilidade;
		this.tecnicosFornecedores = tecnicosFornecedores;
		this.gestorResponsavel = gestorResponsavel;
	}
	

}
