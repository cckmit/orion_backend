package br.com.live.model;

public class Empresa {
		public int codigo;	
		public String nome;

		
		public int getCodigo() {
			return codigo;
		}

		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public Empresa () {
			
		}
		
		public Empresa (int codigo, String nome) {
			this.codigo = codigo;
			this.nome = nome;
		}

}
