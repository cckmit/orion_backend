package br.com.live.sistema.model;

import java.util.List;

public class DadosUsuario {
	private List <ConsultaProgramasUsuario> programas;
	private List <ConsultaModulosUsuario> modulos;
	private long idUsuario;
	
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public List<ConsultaProgramasUsuario> getProgramas() {
		return programas;
	}
	public void setProgramas(List<ConsultaProgramasUsuario> programas) {
		this.programas = programas;
	}
	public List<ConsultaModulosUsuario> getModulos() {
		return modulos;
	}
	public void setModulos(List<ConsultaModulosUsuario> modulos) {
		this.modulos = modulos;
	}
}
