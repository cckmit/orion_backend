package br.com.live.sistema.body;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

import java.util.List;

public class BodyPrograma {
	public long id;
	public String descricao;
	public String modulo;
	public String path;
	public String help;

	public List<ConteudoChaveAlfaNum> listaModulos;
	public List<ConteudoChaveNumerica> listaUsuarios;
}
