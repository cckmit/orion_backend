package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_005")
public class Notificacao {
	
	public static final int REQUISICAO_TECIDOS = 1;
	public static final int PEDIDOS_CUSTOMIZADOS = 2;
	public static final int INTEGRACAO_TITULOS_RECEBER_NFS = 3;
	
	
	@Id
	public String id;
	
	@Column(name = "id_usuario")
	public int idUsuario;
	
	@Column(name = "tipo_notificacao")
	public int tipoNotificacao;
	
	public Notificacao() {
		
	}

	public Notificacao(int idUsuario, int tipoNotificacao) {
		this.id = idUsuario + "-" + tipoNotificacao;
		this.idUsuario = idUsuario;
		this.tipoNotificacao = tipoNotificacao;
	}
}
